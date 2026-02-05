pipeline {
    agent any

    tools {
        maven 'Maven3.9.9'
        jdk 'Java21'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/cyrine56/devops-spring-demo.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
            steps {
                sh '''
                mvn sonar:sonar \
                -Dsonar.projectKey=devops-spring-demo \
                -Dsonar.host.url=http://192.168.56.10:9000 \
                -Dsonar.login=squ_48890b4281db7c9cba74140be1f4e50a53f14154
                '''
            }
        }
    }
}

