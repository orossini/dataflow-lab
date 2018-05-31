package dataflowlab;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileSystems;
import org.apache.beam.sdk.io.fs.MoveOptions.StandardMoveOptions;
import org.apache.beam.sdk.io.fs.ResourceId;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.ByteStreams;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import java.awt.image.BufferedImage;
import org.json.*;


public class ImagesPipeline1 {
	
	private static final String PROJECT_ID =  "";
	private static final String BUCKET_IN_PATH = "gs://"+PROJECT_ID+"-imagesin";
	private static final String BUCKET_OUT_PATH = "gs://"+PROJECT_ID+"-imagesout";
	private static final String TOPIC_URI =  "projects/"+PROJECT_ID+"/topics/iotdata";
	
	
	public static class CopyImages extends DoFn<PubsubMessage, String>{
		
		CopyImages() {}
	    
	    @ProcessElement
	    public void processElement(ProcessContext c) {
			// TODO: complete the code here
	    	//Parse the message and get filename and label
	    	
  	        //Build the ResourceIds and related list (for bucket in and bucket out)
	  	    
			// copy from bucket in to bucket out
		    
	    	// return created filename 
            c.output("");
	    }
	    
	}
	
	

	public static void main(String[] args) {
		// TODO: complete the main method
		// Create PipelineOptions
		
		// Create Pipeline object
		
		// Create PCOllection by reading incoming PubSub message (apply I/O Transform to the pipeline object)
		
		// Apply PTransform to the pipeline object with the DoFn class (copy incoming file from bucket in to bucket out)
		
		// Run the pipeline
		
	}

}
