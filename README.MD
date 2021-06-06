Project: CI/CD pipeline with jenkins script master branch

jenkinsfile contains script
pipeline.groovy contains functions to be performed
here we have downloaded the playbook and dockerfile on ansible server then we have executed the deployment process.
we do not have created versioning of our images.

Prokect fix 1: CI/CD pipeline with jenkinsfile and bash script version-deploy branch

To include versioning of images bash script is being tried.
In "setup" stage I include a script to write varsfile which contains information about the versions and job name
Using ansible loop I tried to remove all previous images which are present at node (whether container running or not)

I have not pushed the image to docker hub, bugt can simply do by adding a new task in test-playbook.yml
but for this we need to configure our docker hub on the node
    
    - name: Pushing image
      shell: docker push yadavashu/{{ job_name }}:{{ build_id }}
      become: true