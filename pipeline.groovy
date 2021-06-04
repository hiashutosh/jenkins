def buildjar() {
    sh 'mvn clean install package'
}

def buildimage() {
    sh 'docker build -t yadavashu/demo:$BUILD_ID .'
    //sh 'docker push yadavashu/demo:$BUILD_ID'
    
}

def deploy() {
    sh 'ansible-playbook test-playbook.yml'
}

return this