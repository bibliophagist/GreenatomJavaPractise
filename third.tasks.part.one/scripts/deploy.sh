#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/thirdtask.pem \
    target/third.tasks-1.0-SNAPSHOT.jar \
    ec2-user@ec2-3-120-244-87.eu-central-1.compute.amazonaws.com:/home/ec2-user/

echo 'Restart server...'

ssh -i ~/.ssh/thirdtask.pem ec2-user@ec2-3-120-244-87.eu-central-1.compute.amazonaws.com<< EOF
pgrep java | xargs kill -9
nohup java -jar third.tasks-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'
