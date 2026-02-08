pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Unit Tests') {
      steps {
        sh 'mvn -B clean test'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube-Local') {
          sh 'mvn -B -DskipTests sonar:sonar -Dsonar.projectKey=devops-spring-demo'
        }
      }
    }
  }
}
