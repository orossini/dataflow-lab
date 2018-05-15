#!/bin/bash
# startup script for dataflow lab

gcloud pubsub topics create devicesdata
echo "pubsub topic created"
gsutil mb -c regional -l europe-west1 gs://$PROJECTID-imagesin
export BUCKETIN=gs://$PROJECTID-imagesin
gsutil mb -c regional -l europe-west1 gs://$PROJECTID-imagesout
export BUCKETOUT=gs://$PROJECTID-imagesout
gsutil mb -c regional -l europe-west1 gs://$PROJECTID-dataflowstagging
echo "buckets created"
exit 0

