def gv

pipeline {
    agent any 
    stages {
        stage("initialization") {
            steps {
                script {
                    gv = load "pipeline.groovy"
                }
            }
        }

        stage("build-jar") {
            steps {
                echo 'building the application....'
                gv.buildjar()
            }
        }
        stage("test") {
            steps {
                echo 'testing the application.....'
                
            }
        }
        stage("deploy") {

            steps {
                echo 'Building docker image...'
                gv.buildimage()
            
            }

            steps {
                echo 'deploying the application.....'
                gv.deploy()
            }
        }
    }

}
