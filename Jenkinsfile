pipeline {
    agent { label 'java8' }
    environment { }

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
        stage('Docker image') {
            when { branch "master" }
            steps {
                sh '''
                    docker login -u "" -p ""
                    docker build -t spring-secure-jwt .
                    docker tag spring-secure-jwt:latest infinityc2/spring-secure-jwt:latest
                    docker push infinityc2/spring-secure-jwt:latest spring-secure-jwt:latest
                '''
            }
        }
        stage('Container') {
            when { branch "master" }
            steps {
                sh '''
                    docker login -u "" -p ""
                    docker pull infinityc2/spring-secure-jwt:latest
                    docker stop spring-secure-jwt
                    docker run -p 9000:9000 --name spring-secure-jwt -t -d infinityc2/spring-secure-jwt:latest
                '''
            }
        }
    }
}