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
					script {
						def reports = findFiles(glob: 'target/pit-reports/mutations.xml')
						if (reports.length > 0) {
							step([$class: 'PitPublisher', mutationStatsFile: reports[0].path])
						} else {
							error('No PIT reports found')
						}
					}
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
