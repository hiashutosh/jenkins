def buildjar() {
    sh 'mvn clean install package'
}

def buildimage() {
    sh "ansible-playbook -i 192.168.132.146, --ssh-extra-args='-o StrictHostKeyChecking=no' setup.yml'"
    //sh 'docker build -t yadavashu/demo:$BUILD_ID .'
    //sh 'docker push yadavashu/demo:$BUILD_ID'
        
}

def deploy() {
    sh 'ansible-playbook -i 192.168.132.146, test-playbook.yml'
}

return this