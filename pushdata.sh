#!/bin/bash
# simple cmd to push data (image file) to GCS bucket 
# and to publish a pubsub message

gsutil cp ~/bootcamp-ml-next2018/sampledata/candy1.jpg gs://$BUCKET_IN/candy1.jpg
echo data copied to GCS bucket $BUCKET_IN
gcloud beta pubsub topics publish $PUBSUB_TOPIC --message "candy1.jpg,almondjoy"
echo msg published to Pubsub topic $PUBSUB_TOPIC
