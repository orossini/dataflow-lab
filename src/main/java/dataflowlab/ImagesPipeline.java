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
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.apache.beam.sdk.values.PCollection;
import com.google.common.io.ByteStreams;

import dataflowlab.ImagesPipeline.CopyImages;
import dataflowlab.ImagesPipeline.FlipImages;
import dataflowlab.ImagesPipeline.GrayImages;
import dataflowlab.ImagesPipeline.RotateImages270;
import dataflowlab.ImagesPipeline.RotateImages90;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import java.awt.image.BufferedImage;


public class ImagesPipeline {

public static class RotateImages90 extends DoFn<PubsubMessage, String>{
		
	    RotateImages90() {}
	    
	    @ProcessElement
	    public void processElement(ProcessContext c) {
	    	String msg = new String(c.element().getPayload());
	    	String label = msg.substring(msg.indexOf(",")+1, msg.length());
	    	label = label.trim().toLowerCase();
            String fileName = msg.substring(0,msg.indexOf(","));          
            String pathToFileIn = "<your BUCKETIN path>" + fileName;
            Instant timestamp = Instant.now();
  	        String pathToFileOut = "<your BUCKETOUT path>" + label + "/" + timestamp.toString() + "-rot90-" + fileName;
  	        
            ReadableByteChannel rChan;
            try {
				rChan = FileSystems.open(FileSystems.matchNewResource(pathToFileIn, false ));
				try (InputStream stream = Channels.newInputStream(rChan)) {
					BufferedImage buffImg = ImageIO.read(stream);
			        buffImg = Scalr.rotate(buffImg, Rotation.CW_90);
			        
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        ImageIO.write(buffImg, "jpeg", bos);
			        
			        try (ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        	    ReadableByteChannel readerChannel = Channels.newChannel(in);
			        	    WritableByteChannel writerChannel = FileSystems.create(FileSystems.matchNewResource(pathToFileOut, false ), "image/jpg")) {

			        	  ByteStreams.copy(readerChannel, writerChannel);
			        	}
			        catch(IOException ioex) {
						// TODO Auto-generated catch block
						ioex.printStackTrace();
					}
				}
				catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	}
	
	public static class RotateImages270 extends DoFn<PubsubMessage, String>{
		
	    RotateImages270() {}
	    
	    @ProcessElement
	    public void processElement(ProcessContext c) {
	    	String msg = new String(c.element().getPayload());
	    	String label = msg.substring(msg.indexOf(",")+1, msg.length());
	    	label = label.trim().toLowerCase();
            String fileName = msg.substring(0,msg.indexOf(","));          
            String pathToFileIn = "<your BUCKETIN path>" + fileName;
            Instant timestamp = Instant.now();
  	        String pathToFileOut = "<your BUCKETOUt path>" + label + "/" + timestamp.toString() + "-rot270-" + fileName;
  	        
            ReadableByteChannel rChan;
            try {
				rChan = FileSystems.open(FileSystems.matchNewResource(pathToFileIn, false ));
				try (InputStream stream = Channels.newInputStream(rChan)) {
					BufferedImage buffImg = ImageIO.read(stream);
			        buffImg = Scalr.rotate(buffImg, Rotation.CW_270);			        
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        ImageIO.write(buffImg, "jpeg", bos);			        
			        try (ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        	    ReadableByteChannel readerChannel = Channels.newChannel(in);
			        	    WritableByteChannel writerChannel = FileSystems.create(FileSystems.matchNewResource(pathToFileOut, false ), "image/jpg")) {

			        	  ByteStreams.copy(readerChannel, writerChannel);
			        	}
			        catch(IOException ioex) {
						// TODO Auto-generated catch block
						ioex.printStackTrace();
					}
				}
				catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public static class FlipImages extends DoFn<PubsubMessage, String>{
		
	    FlipImages() {}
	    
	    @ProcessElement
	    public void processElement(ProcessContext c) {
	    	String msg = new String(c.element().getPayload());
	    	String label = msg.substring(msg.indexOf(",")+1, msg.length());
	    	label = label.trim().toLowerCase();
            String fileName = msg.substring(0,msg.indexOf(","));          
            String pathToFileIn = "<your BUCKETIN path>" + fileName;
            Instant timestamp = Instant.now();
  	        String pathToFileOut = "<your BUCKETOUT path>" + label + "/" + timestamp.toString() + "-flipV-" + fileName;
  	        
            ReadableByteChannel rChan;
            try {
				rChan = FileSystems.open(FileSystems.matchNewResource(pathToFileIn, false ));
				try (InputStream stream = Channels.newInputStream(rChan)) {
					BufferedImage buffImg = ImageIO.read(stream);
			        buffImg = Scalr.rotate(buffImg, Rotation.FLIP_VERT);      
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        ImageIO.write(buffImg, "jpeg", bos);
			        
			        try (ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        	    ReadableByteChannel readerChannel = Channels.newChannel(in);
			        	    WritableByteChannel writerChannel = FileSystems.create(FileSystems.matchNewResource(pathToFileOut, false ), "image/jpg")) {

			        	  ByteStreams.copy(readerChannel, writerChannel);
			        	}
			        catch(IOException ioex) {
						// TODO Auto-generated catch block
						ioex.printStackTrace();
					}
				}
				catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	}
	
	public static class GrayImages extends DoFn<PubsubMessage, String>{
		
		GrayImages() {}
	    
	    @ProcessElement
	    public void processElement(ProcessContext c) {
	    	String msg = new String(c.element().getPayload());
	    	String label = msg.substring(msg.indexOf(",")+1, msg.length());
	    	label = label.trim().toLowerCase();
            String fileName = msg.substring(0,msg.indexOf(","));          
            String pathToFileIn = "<your BUCKETIN path>" + fileName;
            Instant timestamp = Instant.now();
  	        String pathToFileOut = "<your BUCKETOUT path>" + label + "/" + timestamp.toString() + "-gray-" + fileName;
  	        
            ReadableByteChannel rChan;
            try {
				rChan = FileSystems.open(FileSystems.matchNewResource(pathToFileIn, false ));
				try (InputStream stream = Channels.newInputStream(rChan)) {
					BufferedImage buffImg = ImageIO.read(stream);
			        buffImg = Scalr.apply(buffImg, Scalr.OP_GRAYSCALE);			        
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        ImageIO.write(buffImg, "jpeg", bos);
			        
			        try (ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        	    ReadableByteChannel readerChannel = Channels.newChannel(in);
			        	    WritableByteChannel writerChannel = FileSystems.create(FileSystems.matchNewResource(pathToFileOut, false ), "image/jpg")) {

			        	  ByteStreams.copy(readerChannel, writerChannel);
			        	}
			        catch(IOException ioex) {
						// TODO Auto-generated catch block
						ioex.printStackTrace();
					}
				}
				catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	}
	
	public static class CopyImages extends DoFn<PubsubMessage, String>{
		
		CopyImages() {}
	    
	    @ProcessElement
	    public void processElement(ProcessContext c) {
	    	//parse the message and get filename and label
	    	String msg = new String(c.element().getPayload());
	    	String label = msg.substring(msg.indexOf(",")+1, msg.length());
	    	label = label.trim().toLowerCase();
            String fileName = msg.substring(0,msg.indexOf(","));          
            String pathToFileIn = "<your BUCKETIN path>" + fileName;
            Instant timestamp = Instant.now();
  	        String pathToFileOut = "<your BUCKETOUT path>" + label + "/" + timestamp.toString() + "-copy-" + fileName;
  	        //build the ResourceIds and related list (in and out)
	  	    List<ResourceId> listIn = new ArrayList<ResourceId> ();   
	        List<ResourceId> listOut = new ArrayList<ResourceId> ();        				
            listIn.add(FileSystems.matchNewResource(pathToFileIn,false));
			listOut.add(FileSystems.matchNewResource(pathToFileOut,false));
			// copy from in to out
		    try {
		    	FileSystems.copy(listIn, listOut, StandardMoveOptions.IGNORE_MISSING_FILES);
		    }
		    catch (IOException ioe) {
		    	// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
            c.output(fileName.toString());
	    }
	    
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
		FileSystems.setDefaultPipelineOptions(options);
		  
		Pipeline p = Pipeline.create(options);
		PCollection<PubsubMessage> pc1 = p.apply("read from PubSub", PubsubIO.readMessages().fromTopic("projects/<YOUR_PROJECT_NAME>/topics/<YOUR_PUBSUB_TOPIC>"));
		pc1.apply("copy", ParDo.of(new CopyImages()));
		pc1.apply("rotate90", ParDo.of(new RotateImages90())); 
		pc1.apply("rotate270", ParDo.of(new RotateImages270()));
		pc1.apply("flip", ParDo.of(new FlipImages()));
		pc1.apply("gray", ParDo.of(new GrayImages()));
		p.run();
	}

}
