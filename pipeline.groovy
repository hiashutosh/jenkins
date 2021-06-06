def buildjar() {
    //Building war package
    sh 'mvn clean install package'
    //removing previous files
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: 'cd /home/ubuntu/docker/ ; rm -rf *')], verbose:true)])           
    // copying war package to the ansible server
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( remoteDirectory: '//home//ubuntu//docker//', removePrefix: 'target/', sourceFiles: 'target/mvnwebapp.war')], verbose:true)])
}

def setup() {
    //writing script for vars_files so to provide required versions 
    //and delete previous version, if any 
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''
declare -a arr

cat >> /home/ubuntu/docker/varsfile.yml << EOF
build_id: $BUILD_NUMBER
job_name: test
build_id_old: $(( $BUILD_NUMBER-1 ))
job_name_old: test
version:
EOF
for (( i=0; i<$BUILD_NUMBER; i++ ))
do
arr[$i]=$i
cat >> /home/ubuntu/docker/varsfile.yml << EOF
        - ${arr[$i]}
EOF
done
''')], verbose: true)])
    // installing and startind docker on our node and 
    //Downloading required playbooks and dockerfile on ansible server
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/version-deploy/Dockerfile ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/version-deploy/setup.yml; ansible-playbook -i 192.168.132.146, setup.yml --extra-vars "ansible_password=ashu1234"''')], verbose:true)])
}

def deploy() {
    //removing previous version
    //building new image
    //running new cont with new image
    sshPublisher(publishers: [sshPublisherDesc(configName: 'docker', transfers: [sshTransfer( execCommand: '''cd /home/ubuntu/docker/ ; wget https://raw.githubusercontent.com/hiashutosh/jenkins/version-deploy/test-playbook.yml ; ansible-playbook -i 192.168.132.146, test-playbook.yml --extra-vars "ansible_password=ashu1234"''')], verbose:true)])
    //here I have not pushed the image to docker hub
}

return this