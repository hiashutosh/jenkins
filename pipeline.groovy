def buildjar() {
    sh 'mvn clean install package'
    
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', removePrefix: 'target/', sourceFiles: 'target/mvnwebapp.war')])])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'Dockerfile')])])           
              
}

def buildimage() {
    
    //sh 'ansible-playbook -i 192.168.132.146, setup.yml'
    //sh 'docker build -t yadavashu/demo:$BUILD_ID .'
    //sh 'docker push yadavashu/demo:$BUILD_ID'
    
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'setup.yml')])])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/master/Dockerfile ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/master/setup.yml; ansible-playbook -i 192.168.132.146, setup.yml --extra-vars "ansible_password=ashu1234"''')])])
}

def deploy() {
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'test-playbook.yml')])])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/master/test-playbook.yml ; ansible-playbook -i 192.168.132.146, test-playbook.yml --extra-vars "ansible_password=ashu1234"''')])])

}

return this