pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven 3.9.6"
    }

    environment {
            TEST_USER     = 'user@pflb.ru'
            TEST_PASSWORD = 'user'
    }

    parameters{
        choice(choices: ['chrome', 'firefox', 'edge', 'safari'], name: 'BROWSER')
    }

    stages {
        stage('Run tests') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/danteawake/PFLBTestAPI.git'

                // Run Maven on a Unix agent.
                sh "mvn clean test -Dheadless=true \
                 -Dbrowser=${params.BROWSER} \
                 -Dtest.user=${TEST_USER} \
                 -Dtest.password=${TEST_PASSWORD}"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    allure includeProperties: false, jdk: '', resultPolicy: 'LEAVE_AS_IS', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}