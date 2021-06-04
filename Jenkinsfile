def gv

pipeline {
    agent any 
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "pipeline.groovy"
                    git 'https://github.com/hiashutosh/webapp.git'
                }
            }
        }

        stage("build-jar") {
            steps {
                echo 'building the application....'
                
                script {
                   gv.buildjar()
               }
            }
        }
        stage("test") {
            steps {
                echo 'testing the application.....'
                
            }
        }
        stage("deploy") {

            steps("build-image") {
                echo 'Building docker image...'
                
                script {
                    gv.buildimage()
                    gv.deploy()
                }
            
            }

            //steps("deploting") {
             //  echo 'deploying the application.....'
              //  script {
                   
              //  }
            //}
        }
    }

}
