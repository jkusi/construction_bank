pipeline {
	agent any

	stages {
		stage('Build Artifact') {
			steps{
				sh "mvn clean package "
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
	        withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
	          sh 'printenv'
	          sh 'docker build -t carmichaelc09/bank-app:v3 .'
	          sh 'docker push carmichaelc09/bank-app:v3'
	        }
	      }
	    }
		stage('Mutation Tests - PIT') {
		steps {
			sh "mvn org.pitest:pitest-maven:mutationCoverage"
		}
		post {
			always {
				pitmutation mutationStatsFile: '**/target/pit-reports/**/mutations.xml'
				}
			}
		}

	    stage('Kubernetes Deployment - DEV') {
	      steps {
	        withKubeConfig([credentialsId: 'kubeconfig']) {
	          sh "kubectl apply -f k8s_deployment_service.yaml"
	        }
	      }
	    }


	}

}
