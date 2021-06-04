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
                echo 'copying war to ansible host...'
                sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', removePrefix: 'target/', sourceFiles: 'target/mvnwebapp.war')])])
                sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'Dockerfile test-playbook.yml setup.yml')])])           
               
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
