package dbo.camlogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import io.humble.video.Rational;

public class CamLogger2 {

	public static void main(String[] args) throws InterruptedException, IOException {
		int duration = 36000; // recording stops after 10 hours
		String folderName = "logs";
		File f;
		boolean firstFrame = true;
		long delay = 0L;
		int fps = 15;
		int longProcessingCount = 0;

		// Create folder
		if (args.length != 0) {
			folderName = args[0];
			if (args.length > 2) {
				try {
					fps = Integer.parseInt(args[1]);
				} catch (Exception e) {
					//not an int
				}
			}
		}
		f = new File(folderName);
		f.mkdirs();
		
		final Rational framerate = Rational.make(1, fps);

		long initialTimestamp = System.currentTimeMillis();
		File file = new File(folderName + "/webcam" + initialTimestamp + ".ts");

		IMediaWriter writer = ToolFactory.makeWriter(file.getName());
		Dimension size = WebcamResolution.QVGA.getSize();

		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);

		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(size);
		webcam.open(true);

		InputStreamReader fileInputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(fileInputStream);

		long startProcessingTimestamp;
		System.out.println(
				"Recording started, press [ENTER] to save and quit.\nWARNING: Closing the window won't save the recording!!!");
		long start = System.currentTimeMillis();
		for (int i = 0; (i < duration / framerate.getDouble()) && (!bufferedReader.ready()); i++) {
			startProcessingTimestamp = System.currentTimeMillis();
			if (firstFrame) {
				delay = System.currentTimeMillis() - initialTimestamp;
				firstFrame = false;
			}

			BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
			IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

			IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
			frame.setKeyFrame(i == 0);
			frame.setQuality(0);

			writer.encodeVideo(0, frame);

			try {
				Thread.sleep((long) (1000 * framerate.getDouble())
						- (System.currentTimeMillis() - startProcessingTimestamp));
			} catch (IllegalArgumentException e) {
				longProcessingCount++;
			}
		}

		writer.close();

		/* Add the delay to the filename */
		file.renameTo(new File(folderName + "/webcam" + (initialTimestamp + delay) + ".ts"));
		System.out.println("Delay (" + delay + ") added to the initial timestmap (" + initialTimestamp + ").");
		System.out.println("Recording completed, you may now close the window.");
		if (longProcessingCount > 0) {
			System.err.println("There are " + longProcessingCount
					+ " delayed frames in the recording. In other words, you shouldn't use this potato.");
		}

	}

}
