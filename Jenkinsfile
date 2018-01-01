pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Compile') {
            steps {
                sh './gradlew compileJava'
            }
        }
        stage('Unit test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Code coverage') {
            steps {
                sh './gradlew jacocoTestReport'
                publishHTML (target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Report'
                ])
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
        stage('Static code analysis') {
            steps {
                sh './gradlew checkstyleMain'
                publishHTML (target: [
                    reportDir: 'build/reports/checkstyle',
                    reportFiles: 'main.html',
                    reportName: 'Checkstyle Report'
                ])
            }
        }
        stage('Package') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Docker build') {
            steps {
                sh 'docker build -t egonjensen/calculator .'
            }
        }
        stage('Docker push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'docker login --username $USERNAME --password "$PASSWORD"'
                }
                sh 'docker push egonjensen/calculator'
            }
        }
        /*
        stage('Deploy to staging') {
            steps {
                // sh 'docker run -d --rm -p 8765:8080 --name calculator egonjensen/calculator'
                // [1] sh 'docker-compose up -d'
            }
        }
        */
        stage('Acceptence test') {
            steps {
                // [1] sleep 60
                // [1] sh './acceptence_test.sh'
                sh 'docker-compose -d docker-compose.yml -f acceptance/docker-compose-acceptance.yml build test'
                sh 'docker-compose -d docker-compose.yml -f acceptance/docker-compose-acceptance.yml -p acceptance up -d'
                sh 'test $(docker wait acceptance_test_1) -eq 0'
            }
        }
    }
    post {
        always {
            // sh 'docker stop calculator'
            // [1] sh 'docker-compose down'
            sh 'docker-compose -d docker-compose.yml -f acceptance/docker-compose-acceptance.yml -p acceptance down'
        }
    }
}
