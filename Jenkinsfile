pipeline {

    agent any

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    parameters {

        choice(
            name: 'BROWSER',
            choices: [ 'firefox','chrome'],
            description: 'Browser for Selenium execution'
        )

        choice(
            name: 'HEADLESS',
            choices: ['true', 'false'],
            description: 'Run browser in headless mode'
        )

        choice(
            name: 'TEST_TYPE',
            choices: ['smoke', 'regression'],
            description: 'TestNG test group to execute'
        )
        choice(
            name: 'EXECUTION_MODE',
            choices: ['single', 'parallel'],
            description: 'Run on one selected browser or both browsers in parallel'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('Test') {

            steps {

                script {

                    if (params.EXECUTION_MODE == 'parallel') {

                        parallel(

                            Chrome: {
                                bat "mvn clean test -Dbrowser=chrome -Dheadless=${params.HEADLESS} -DtestType=${params.TEST_TYPE}"
                            },

                            Firefox: {
                                bat "mvn clean test -Dbrowser=firefox -Dheadless=${params.HEADLESS} -DtestType=${params.TEST_TYPE}"
                            }
                        )

                    } else {

                        bat "mvn clean test -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS} -DtestType=${params.TEST_TYPE}"
                    }
                }
            }
        }
}
    post {

        always {

            junit(
                testResults: '**/target/surefire-reports/*.xml',
                allowEmptyResults: true
            )

            archiveArtifacts(
                artifacts: 'target/screenshots/**',
                allowEmptyArchive: true
            )
        }

        success {
            echo "Selenium ${params.TEST_TYPE} tests passed."
        }

        failure {
            echo "Selenium ${params.TEST_TYPE} tests failed."
        }
    }
}