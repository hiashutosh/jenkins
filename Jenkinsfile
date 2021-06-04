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
                sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '//opt//', remoteDirectorySDF: false, removePrefix: '/pipeline test/target/', sourceFiles: './webapp/target/mvnwebapp.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '//opt//', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'Dockerfile')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])           
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
