def buildjar() {
    sh 'mvn clean install package'
}

def buildimage() {
    
    //sh 'ansible-playbook -i 192.168.132.146, setup.yml'
    //sh 'docker build -t yadavashu/demo:$BUILD_ID .'
    //sh 'docker push yadavashu/demo:$BUILD_ID'
    
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'setup.yml')])])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; ansible-playbook -i 192.168.132.146, setup.yml''')])])
}

def deploy() {
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'test-playbook.yml')])])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; ansible-playbook -i 192.168.132.146, test-playbook.yml''')])])

}

return this