pipeline {
    agent any

    tools {
        maven "maven 3.9.6"
    }

    parameters {
        // Выбор браузера
        choice(choices: ['chrome', 'firefox', 'edge', 'safari'], name: 'BROWSER', description: 'Выберите браузер для тестирования')

        // Строковый параметр для логина
        string(name: 'TEST_USER', defaultValue: 'user@pflb.ru', description: 'Логин (email) для авторизации в тестах')

        // Безопасный параметр для пароля (скрывается звездочками)
        password(name: 'TEST_PASSWORD', defaultValue: 'user', description: 'Пароль для авторизации в тестах')
    }

    stages {
        stage('Checkout') {
            steps {
                // Скачивание исходного кода из вашего Git-репозитория
                git branch: 'master', url: 'https://github.com/danteawake/PFLBTestAPI.git'
            }
        }

        stage('Run Tests') {
            steps {
                sh """
                    mvn clean test -Dheadless=true \
                    -Dbrowser=${params.BROWSER} \
                    -Dtest.user=${params.TEST_USER} \
                    -Dtest.password=${params.TEST_PASSWORD}
                """
            }

            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    allure includeProperties: false, jdk: '', resultPolicy: 'LEAVE_AS_IS', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}