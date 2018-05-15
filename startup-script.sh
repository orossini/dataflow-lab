#!/bin/bash
# startup script for dataflow lab

gcloud pubsub topics create devicesdata
echo "pubsub topic created"
gsutil mb -c regional -l europe-west1 gs://$PROJECTID-imagesin
gsutil mb -c regional -l europe-west1 gs://$PROJECTID-imagesout
echo "buckets created"
return 0

