package dbo.printlogger;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import io.humble.video.Codec;
import io.humble.video.Encoder;
import io.humble.video.MediaPacket;
import io.humble.video.MediaPicture;
import io.humble.video.Muxer;
import io.humble.video.MuxerFormat;
import io.humble.video.PixelFormat;
import io.humble.video.Rational;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;

/**
 * Hello world!
 *
 */
public class PrintApp {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws AWTException, InterruptedException, IOException {
		File f;
		String codecname = null;
		int duration = 36000; // recording stops after 10 hours

		long delay = 0;
		final Robot robot = new Robot();
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Rectangle screenbounds = new Rectangle(toolkit.getScreenSize());

		final Rational framerate = Rational.make(1, 3); // 3 frames per second

		String folderName = "logs";
		if (args.length != 0) {
			folderName = args[0];
		}

		// Create folder
		f = new File(folderName);
		f.mkdirs();

		long initialTimestamp = System.currentTimeMillis();
		final Muxer muxer = Muxer.make(folderName + "/print" + initialTimestamp + ".mp4", null, "mp4");

		final MuxerFormat format = muxer.getFormat();
		final Codec codec;
		if (codecname != null) {
			codec = Codec.findEncodingCodecByName(codecname);
		} else {
			codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());
		}

		Encoder encoder = Encoder.make(codec);

		encoder.setWidth(screenbounds.width);
		encoder.setHeight(screenbounds.height);
		// We are going to use 420P as the format because that's what most video formats
		// these days use
		final PixelFormat.Type pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
		encoder.setPixelFormat(pixelformat);
		encoder.setTimeBase(framerate);

		if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
			encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);

		/** Open the encoder. */
		encoder.open(null, null);

		/** Add this stream to the muxer. */
		muxer.addNewStream(encoder);

		/** And open the muxer for business. */
		muxer.open(null, null);

		MediaPictureConverter converter = null;
		final MediaPicture picture = MediaPicture.make(encoder.getWidth(), encoder.getHeight(), pixelformat);
		picture.setTimeBase(framerate);

		InputStreamReader fileInputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(fileInputStream);

		final MediaPacket packet = MediaPacket.make();
		boolean firstFrame = true;
		long startProcessingTimestamp = 0L;
		int longProcessingCount = 0;
		
		System.out.println(
				"Recording started, press [ENTER] to save and quit.\nWARNING: Closing the window won't save the recording!!!");
		for (int i = 0; (i < duration / framerate.getDouble()) && (!bufferedReader.ready()); i++) {
			startProcessingTimestamp = System.currentTimeMillis();
			/** Make the screen capture && convert image to TYPE_3BYTE_BGR */
			final BufferedImage screen = convertToType(robot.createScreenCapture(screenbounds),
					BufferedImage.TYPE_3BYTE_BGR);

			if (firstFrame) {
				delay = System.currentTimeMillis() - initialTimestamp;
				firstFrame = false;
			}

			/**
			 * This is LIKELY not in YUV420P format, so we're going to convert it using some
			 * handy utilities.
			 */
			if (converter == null)
				converter = MediaPictureConverterFactory.createConverter(screen, picture);
			converter.toPicture(picture, screen, i);

			do {
				encoder.encode(packet, picture);
				if (packet.isComplete())
					muxer.write(packet, false);
			} while (packet.isComplete());

			/** now we'll sleep until it's time to take the next snapshot. */
			try {
				Thread.sleep((long) (1000 * framerate.getDouble())
						- (System.currentTimeMillis() - startProcessingTimestamp));
			} catch (IllegalArgumentException e) {
				longProcessingCount++;
			}
		}

		/**
		 * Encoders, like decoders, sometimes cache pictures so it can do the right
		 * key-frame optimizations. So, they need to be flushed as well. As with the
		 * decoders, the convention is to pass in a null input until the output is not
		 * complete.
		 */
		do {
			encoder.encode(packet, null);
			if (packet.isComplete())
				muxer.write(packet, false);
		} while (packet.isComplete());

		/** Finally, let's clean up after ourselves. */
		muxer.close();
		
		/* Add the delay to the filename */
		new File(folderName + "/print" + initialTimestamp + ".mp4").renameTo(new File(folderName + "/print" + (initialTimestamp + delay) + ".mp4"));
		System.out.println("Delay (" + delay + ") added to the initial timestmap ("  + initialTimestamp + ").");
		System.out.println("Recording completed, you may now close the window.");
		if (longProcessingCount > 0) {
			System.err.println("There are " + longProcessingCount
					+ " delayed frames in the recording. In other words, you shouldn't use this potato.");
		}
	}

	public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		BufferedImage image;

		// if the source image is already the target type, return the source image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

		// otherwise create a new image of the target type and draw the new
		// image

		else {
			image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}
}
