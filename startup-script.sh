#!/bin/bash
#test startup-script for vm-dev in qwiklab DM script
sudo apt-get update
sudo apt-get install -y openjdk-8-jdk
# java will be installed in /usr/lib/jvm/java-8-openjdk-amd64/
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/
export PATH=$PATH:/usr/lib/jvm/java-8-openjdk-amd64/bin
echo "export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/" >> ~/.profile
echo "export PATH=$PATH:/usr/lib/jvm/java-8-openjdk-amd64/bin" >> ~/.profile
wget http://www-us.apache.org/dist/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
tar xzvf apache-maven-3.5.3-bin.tar.gz
export PATH=$PATH:/apache-maven-3.5.3/bin
echo "export PATH=$PATH:/apache-maven-3.5.3/bin" >> ~/.profile
gcloud pubsub topics create iotdata
export TOPIC_URI=projects/$PROJECTID/topics/iotdata
echo "export TOPIC_URI=projects/$PROJECTID/topics/iotdata" >> ~/.profile
gsutil mb -c regional -l us-west1 gs://$PROJECTID-imagesin
export BUCKET_IN_PATH=gs://$PROJECTID-imagesin
echo "export BUCKET_IN_PATH=gs://$PROJECTID-imagesin" >> ~/.profile
gsutil mb -c regional -l us-west1 gs://$PROJECTID-imagesout
export BUCKET_OUT_PATH=gs://$PROJECTID-imagesout
echo "export BUCKET_OUT_PATH=gs://$PROJECTID-imagesout" >> ~/.profile
gsutil mb -c regional -l us-west1 gs://$PROJECTID-dataflowstagging
gsutil mb -c regional -l us-west1 gs://$PROJECTID-temp
gcloud iot registries create $PROJECTID-iotregistry --project=$PROJECTID --region=us-central1 --event-notification-config=topic=$TOPIC_URI
gcloud iam service-accounts create sa-iotdevice --display-name "sa-iotdevice"
gcloud projects add-iam-policy-binding $PROJECTID --member serviceAccount:sa-iotdevice@$PROJECTID.iam.gserviceaccount.com --role roles/storage.objectCreator
gcloud iam service-accounts keys create ~/key.json --iam-account sa-iotdevice@$PROJECTIDD.iam.gserviceaccount.com
 
