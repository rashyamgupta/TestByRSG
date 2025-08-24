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
                    // Email configuration as environment variables
                SMTP_SERVER = 'smtp.gmail.com'
                SMTP_PORT = '587'
                SMTP_USE_TLS = 'false'
                SMTP_USE_SSL = 'true'
                EMAIL_RECIPIENTS = 'rashyam.gupta@gmail.com'
                BUILD_TAG = "build-${env.BUILD_NUMBER}"
                EMAIL_PRIORITY="High";
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
                 // Determine email recipients
                def recipients = env.EMAIL_RECIPIENTS
                if (params.CUSTOM_EMAILS?.trim()) {
                    recipients += ",${params.CUSTOM_EMAILS}"
                }
                
                // Set email priority
                def priority = params.EMAIL_PRIORITY ?: 'NORMAL'
                
                // Prepare email content
                def buildStatus = currentBuild.result ?: 'SUCCESS'
                def duration = currentBuild.durationString.replace(' and counting', '')
                def subject = "${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
                
                def body = """
                <h2>Build Report</h2>
                <p><strong>Status:</strong> ${buildStatus}</p>
                <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                <p><strong>Duration:</strong> ${duration}</p>
                <p><strong>URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                <p><strong>Node:</strong> ${env.NODE_NAME}</p>
                <p><strong>Workspace:</strong> ${env.WORKSPACE}</p>
                
                <h3>Test Results</h3>
                <p>Check attached JUnit reports for detailed test results.</p>
                
                <h3>Build Log</h3>
                <pre>${currentBuild.rawBuild.log}</pre>
                """
                
                // Send email with retry logic
                retry(3) {
                    try {
                        emailext(
                            to: recipients,
                            subject: subject,
                            body: body,
                            priority: priority,
                            attachLog: true,
                            compressLog: true,
                            attachmentsPattern: 'target/surefire-reports/**/*.xml',
                            replyTo: 'jenkins-noreply@company.com',
                            mimeType: 'text/html'
                        )
                        echo "Email sent successfully to: ${recipients}"
                    } catch (Exception e) {
                        echo "Email sending failed: ${e.message}"
                        // Fallback to basic email
                        mail(
                            to: recipients,
                            subject: "URGENT: ${subject}",
                            body: "Build ${buildStatus}. Check Jenkins: ${env.BUILD_URL}"
                        )
                        throw e // Re-throw to trigger retry
                    }
                }
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