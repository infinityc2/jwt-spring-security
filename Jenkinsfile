pipeline {
    agent { label 'java8' }

    stages {
        stage('checkout') {
            checkout scm
        }
        stage('Test Project') {
            script {
                sh "gradle clean"
                sh "gradle test"
            }
        }
        stage('Code Coverage') {
            script {
                sh "gradle jacocoTestReport"
            }
        }
        stage('Build Project') {
            script {
                sh 'gradle build'
            }
        }
    }
}
