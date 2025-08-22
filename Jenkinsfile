pipeline {
    agent any
    
    environment {
        LT_USERNAME = credentials('lambdatest-username')
        LT_ACCESS_KEY = credentials('lambdatest-access-key')
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', 
                url: 'https://github.com/rashyamgupta/TestByRSG.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        
    }
    
    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            slackSend channel: '#automation-tests',
                color: 'good',
                message: "LambdaTest Build ${env.BUILD_NUMBER} succeeded: ${env.BUILD_URL}"
        }
        failure {
            slackSend channel: '#automation-tests',
                color: 'danger',
                message: "LambdaTest Build ${env.BUILD_NUMBER} failed: ${env.BUILD_URL}"
        }
    }
}