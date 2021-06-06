def gv

pipeline {
    agent any 
    triggers {
        pollSCM '*/3 * * * *'
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

            }
        }
        stage("setting up node and ansible") {
            steps {
                echo 'copying war to ansible host...'
                gv.setup()
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo 'Building docker image and deploying contianer...'
                    gv.deploy()
                }
            }
        }
    }
}
