#!/bin/bash
#test startup-script for vm-dev in qwiklab DM script
sudo apt-get install -y git
sudo apt-get install -y openjdk-8-jdk
# java will be installed in /usr/lib/jvm/java-8-openjdk-amd64/
echo "export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/" >> ~/.profile
echo "export PATH=$PATH:/usr/lib/jvm/java-8-openjdk-amd64/bin" >> ~/.profile
wget http://www-us.apache.org/dist/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
tar xzvf apache-maven-3.5.3-bin.tar.gz
echo "export PATH=$PATH:/apache-maven-3.5.3/bin" >> ~/.profile
gsutil mb $PROJECTID-imagesin
gsutil mb $PROJECTID-imagesout
gsutil mb $PROJECTID-dataflowstagging


