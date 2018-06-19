#!/bin/bash
gcloud pubsub topics create iotdata
gsutil mb -c regional -l us-west1 gs://$PROJECTID-imagesin
gsutil mb -c regional -l us-west1 gs://$PROJECTID-imagesout
gsutil mb -c regional -l us-west1 gs://$PROJECTID-dataflowstagging
gsutil mb -c regional -l us-west1 gs://$PROJECTID-temp
gcloud iot registries create $PROJECTID-iotregistry --project=$PROJECTID --region=us-central1 --event-notification-config=topic=$TOPIC_URI
gcloud iam service-accounts create sa-iotdevice --display-name "sa-iotdevice"
gcloud projects add-iam-policy-binding $PROJECTID --member serviceAccount:sa-iotdevice@$PROJECTID.iam.gserviceaccount.com --role roles/storage.objectCreator
gcloud iam service-accounts keys create ~/sa-key.p12 --key-file-type="p12" --iam-account=sa-iotdevice@$PROJECTID.iam.gserviceaccount.com
gsutil cp ~/sa-key.p12 gs://$PROJECTID-temp/sa-key.p12
return 0
 
