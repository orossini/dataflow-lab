{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf400
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww16920\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 #!/bin/bash\
sudo apt-get remove google-cloud-sdk -y\
sudo apt-get update\
sudo apt-get install -y git\
sudo apt-get install -y openjdk-8-jdk\
# java will be installed in /usr/lib/jvm/java-8-openjdk-amd64/\
echo 'ID=$(curl "http://metadata.google.internal/computeMetadata/v1/project/project-id" -H "Metadata-Flavor: Google")' | sudo tee -a /etc/bash.bashrc\
echo 'export PROJECTID=$ID' | sudo tee -a /etc/bash.bashrc\
echo 'export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/' | sudo tee -a /etc/bash.bashrc\
echo 'export PATH=$PATH:/usr/lib/jvm/java-8-openjdk-amd64/bin' | sudo tee -a /etc/bash.bashrc\
\
sudo mkdir -p /home/devtools/\
cd /home/devtools\
sudo -p /home/devtools/ wget http://www-us.apache.org/dist/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz\
sudo tar xzvf /home/devtools/apache-maven-3.5.3-bin.tar.gz\
echo 'export PATH=$PATH:/home/devtools/apache-maven-3.5.3/bin' | sudo tee -a /etc/bash.bashrc\
\
gcloud pubsub topics create iotdata\
sudo echo 'export TOPIC_URI=projects/$PROJECTID/topics/iotdata' | sudo tee -a /etc/bash.bashrc\
gsutil mb -c regional -l us-west1 gs://$PROJECTID-imagesin\
sudo echo 'export BUCKET_IN_PATH=gs://$PROJECTID-imagesin' | sudo tee -a /etc/bash.bashrc\
gsutil mb -c regional -l us-west1 gs://$PROJECTID-imagesout\
sudo echo 'export BUCKET_OUT_PATH=gs://$PROJECTID-imagesout' | sudo tee -a /etc/bash.bashrc\
gsutil mb -c regional -l us-west1 gs://$PROJECTID-dataflowstagging\
gsutil mb -c regional -l us-west1 gs://$PROJECTID-temp\
gcloud iot registries create $PROJECTID-iotregistry --project=$PROJECTID --region=us-central1 --event-notification-config=topic=$TOPIC_URI$\
gcloud iam service-accounts create sa-iotdevice --display-name 'sa-iotdevice'\
gcloud projects add-iam-policy-binding $PROJECTID --member serviceAccount:sa-iotdevice@$PROJECTID.iam.gserviceaccount.com --role roles/storage.objectCreator\
gcloud iam service-accounts keys create ~/sa-key.p12 --key-file-type='p12' --iam-account=sa-iotdevice@$PROJECTID.iam.gserviceaccount.com\
gsutil cp ~/sa-key.p12 gs://$PROJECTID-temp/sa-key.p12\
return 0}