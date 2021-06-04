def buildjar() {
    sh 'mvn clean install package'
}

def buildimage() {
    sh 'cd /home/ubuntu/docker'
    sh 'ansible-playbook -i 192.168.132.146, setup.yml'
    //sh 'docker build -t yadavashu/demo:$BUILD_ID .'
    //sh 'docker push yadavashu/demo:$BUILD_ID'
        
}

def deploy() {
    sh 'ansible-playbook -i 192.168.132.146, test-playbook.yml'
}

return this