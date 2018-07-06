#!/bin/bash
# simple cmd to push data (image file) to GCS bucket 
# and to publish a pubsub message

gsutil cp ~/dataflow-lab/sampledata/candy1.jpg ${BUCKET_IN_PATH}/candy1.jpg
echo data copied to GCS bucket $BUCKET_IN_PATH
gcloud beta pubsub topics publish $TOPIC_URI --message "candy1.jpg,almondjoy"
echo msg published to Pubsub topic $PUBSUB_TOPIC
