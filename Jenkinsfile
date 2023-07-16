pipeline {
	agent any

	stages {
		stage('Build Artifact') {
			steps {
				sh "mvn clean package "
				archive 'target/*.war'
			}
		}	
		stage('Unit Tests') {
			steps {
				sh "mvn test"
			}
		}

		// stage('SonarQube - SAST') {
		// 	steps {
		// 		withSonarQubeEnv('SonarQube') {
		// 		sh "mvn sonar:sonar"
		// 		//sh "mvn sonar:sonar -Dsonar.projectKey=bank -Dsonar.projectName='bank' -Dsonar.host.url=https://7675-65-24-213-8.ngrok-free.app/"
		// 		}
		// 	}

		// }

		stage('Docker Build and Push') {
			steps {
				withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
					sh 'printenv'
					sh 'docker build -t carmichaelc09/bank-app:v2 .'
					sh 'docker push carmichaelc09/bank-app:v2'
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

