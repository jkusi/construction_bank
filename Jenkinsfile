pipeline {
	agent any

	stages {
		stage('Build Artifact') {
			steps{
				sh "mvn clean package"
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
	          sh 'sudo docker build -t carmichaelc09/bank-app:v1 .'
		  sh 'sudo docker push carmichaelc09/bank-app:v1'    
	      }
	    }


	}

}
