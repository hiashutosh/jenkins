cat >> /home/ubuntu/docker/varsfile.yml << EOF
build_id: ${currentBuild.number}
job_name: $JOB_NAME
build_id_old:${currentBuild.previousBuild.getNumber()}
job_name_old: $JOB_NAME
EOF