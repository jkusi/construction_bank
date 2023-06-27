pipeline {
  agent any

  stages {
    stage('Build Artifact') {
      steps {
        sh "mvn clean package"
        archive 'target/*.war'
      }
    }

    stage('Unit Tests') {
      steps {
        sh "mvn test"
      }
    }

    stage('Docker Build and Push') {
      steps {
        script {
          docker.withRegistry('docker-hub') {
            def customImage = docker.build("carmichaelc09/bank-app:v1")
            customImage.push()
          }
        }
      }
    }
  }
}
