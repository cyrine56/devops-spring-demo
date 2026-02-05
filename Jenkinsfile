e {
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
            steps {
                // 'SonarQube' = nom que tu as mis dans Jenkins → Configure System → SonarQube servers
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=devops-spring-demo'
                }
            }
        }
    }
}

