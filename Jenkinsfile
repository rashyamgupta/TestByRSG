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
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                    
                    // Send notifications
            script {
                def buildStatus = currentBuild.result ?: 'SUCCESS'
                def duration = currentBuild.durationString.replace(' and counting', '')
                
                echo "Pipeline completed with status: ${buildStatus}"
                echo "Build duration: ${duration}"
                
                // Example: Send email notification
                emailext (
                    subject: "LambdaTest Build ${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    Build: ${env.JOB_NAME} #${env.BUILD_NUMBER}
                    Status: ${buildStatus}
                    Duration: ${duration}
                    URL: ${env.BUILD_URL}
                    Branch: ${env.GIT_BRANCH}
                    Commit: ${env.GIT_COMMIT}
                    """,
                    to: "rashyam.gupta@gmail.com",
                    attachLog: buildStatus != 'SUCCESS'
                )
            }
        }
        
        success {
            script {
                echo "LambdaTest automation completed successfully!"
                // Optional: Trigger downstream jobs
                // build job: 'deploy-to-staging', wait: false
            }
        }
        
        failure {
            script {
                echo "LambdaTest automation failed. Check logs for details."
                // Optional: Send alert to Slack/Teams
            }
        }
        
        unstable {
            script {
                echo "LambdaTest automation completed with test failures."
            }
        }

                }
            }
        }
}