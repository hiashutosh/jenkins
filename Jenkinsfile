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
                sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '//home//ubuntu//docker//', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'Dockerfile')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])           
                sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', sshCredentials: [encryptedPassphrase: '{AQAAABAAAAAQAQB2G2U1SFaGU2VAFkGRyfqWe55QkOA5My0z0FlNtN8=}', key: '', keyPath: '', username: 'ubuntu'], transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '//home//ubuntu//', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'Jenkinsfile')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
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
