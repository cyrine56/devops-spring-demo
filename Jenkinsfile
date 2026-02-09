pipeline {
  agent any

  options {
    skipDefaultCheckout(true)
  }

  environment {
    DOCKER_IMAGE = "cyrineadmin/devops-spring-demo:latest"
  }

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

    stage('Deploy to Nexus') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'nexus-creds', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {

          writeFile file: 'settings.xml', text: """\
<settings>
  <servers>
    <server>
      <id>nexus-snapshots</id>
      <username>${env.NEXUS_USER}</username>
      <password>${env.NEXUS_PASS}</password>
    </server>
    <server>
      <id>nexus-releases</id>
      <username>${env.NEXUS_USER}</username>
      <password>${env.NEXUS_PASS}</password>
    </server>
  </servers>
</settings>
"""

          sh 'mvn -s settings.xml -B -DskipTests deploy'
        }
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t $DOCKER_IMAGE .'
      }
    }

    stage('Docker Login & Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
          sh '''
            echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
            docker push "$DOCKER_IMAGE"
          '''
        }
      }
    }

    stage('Run with docker-compose') {
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
}
