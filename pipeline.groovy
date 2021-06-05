def buildjar() {
    sh 'mvn clean install package'
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: 'cd /home/ubuntu/docker/ ; rm -rf *')], verbose:true)])           
   
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', removePrefix: 'target/', sourceFiles: 'target/mvnwebapp.war')], verbose:true)])
             
}

def buildimage() {
    
    //sh 'ansible-playbook -i 192.168.132.146, setup.yml'
    //sh 'docker build -t yadavashu/demo:$BUILD_ID .'
    //sh 'docker push yadavashu/demo:$BUILD_ID'
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cat >> /home/ubuntu/docker/varsfile.yml << EOF
    build_id: ${currentBuild.number}
    job_name: $JOB_NAME
    build_id_old:${currentBuild.previousBuild.getNumber()}
    job_name_old: $JOB_NAME
EOF''')], verbose: true)])
    
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'setup.yml')], verbose:true)])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/master/Dockerfile ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/master/setup.yml; ansible-playbook -i 192.168.132.146, setup.yml --extra-vars "ansible_password=ashu1234"''')], verbose:true)])
}

def deploy() {
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', sourceFiles: 'test-playbook.yml')], verbose:true)])
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/master/test-playbook.yml ; ansible-playbook -i 192.168.132.146, test-playbook.yml --extra-vars "ansible_password=ashu1234"''')], verbose:true)])

}

return this