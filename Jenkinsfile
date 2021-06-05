def gv

pipeline {
    agent any 
    triggers {
        pollSCM '* * * * *'
    }

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
                echo 'copying war to ansible host...'
             
            }
        }
        
        stage("deploy") {

            steps {
                
                             
                script {
                    echo 'Building docker image...'
                    gv.buildimage()
                    echo 'deploying docker image....'
                    gv.deploy()
                }
            
            }
            
        }
    }

}
