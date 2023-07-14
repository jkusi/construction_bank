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

		stage('SonarQube - SAST') {
			steps {
				withSonarQubeEnv('SonarQube') {
				sh "mvn sonar:sonar -Dsonar.projectKey=bank -Dsonar.projectName='bank' -Dsonar.host.url=http://192.168.50.20:9000"
				}
			}

		}

		stage('Docker Build and Push') {
			steps {
				withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
					sh 'printenv'
					sh 'docker build -t carmichaelc09/bank-app:""GIT_COMMIT"" .'
					sh 'docker push carmichaelc09/bank-app:""$GIT_COMMIT""'
				}
			}
		}


		stage('Kubernetes Deployment - DEV') {
			steps {
				withKubeConfig([credentialsId: 'kubeconfig']) {
					sh "sed -i 's#replace#carmichaelc09/bank-app:${GIT_COMMIT}#g'k8s_deployment_service.yaml"
					sh "kubectl apply -f k8s_deployment_service.yaml"
				}
			}
		}
	}
}

