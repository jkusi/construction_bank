pipeline {
	agent any

	stages {
		stage('Build Artifact') {
			steps{
				sh "mvn clean package -DskipTests=true"
				archive 'target/*.war'
			}

		}	
		stage('Unit Tests') {
			steps{
				sh "mvn test"
			}

		}

		stage('Docker Build and Push') {
	      steps {
	          sh 'printenv'
	          sh 'docker build -t carmichaelc09/bank-app:v1'
	      }
	    }


	}

}
