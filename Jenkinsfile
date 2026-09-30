pipeline {

    agent any

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
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
                bat "mvn clean test -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS} -DtestType=${params.TEST_TYPE}"
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
            echo 'Selenium tests passed.'
        }

        failure {
            echo 'Selenium tests failed.'
        }
    }
}