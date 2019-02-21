package dbo.camlogger;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

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

public class CamLogger {
	public static void main(String[] args) throws IOException, InterruptedException {
		Webcam webcam;
		int choiceIndex = 0, choice;
		Scanner sc = new Scanner(System.in);
		File f;
		Dimension size = WebcamResolution.QVGA.getSize();
		// Dimension size = WebcamResolution.HD.getSize();
		@SuppressWarnings("unused")
		String codecname = null;
		int duration = 36000; //recording stops after 10 hours

		// Create folder
		f = new File("logs");
		f.mkdirs();

		// Create webcam
		if (Webcam.getWebcams().size() == 1) {
			webcam = Webcam.getDefault();
		} else {
			System.out.println("Choose webcam:");
			for (Webcam tempcam : Webcam.getWebcams()) {
				System.out.println(choiceIndex + ": " + tempcam.getName());
				choiceIndex++;
			}
			System.out.println(choiceIndex + ": exit programm");

			System.out.print("choice -> ");
			choice = sc.nextInt();
			sc.close();

			if (choice == choiceIndex) {
				System.out.println("Closing program...");
				System.exit(0);
			}

			webcam = Webcam.getWebcams().get(choice);
		}
		// webcam.setViewSize(new Dimension(640, 480));
		webcam.setViewSize(size);
		// webcam.setCustomViewSizes(size);
		webcam.open(true);

		final Rational framerate = Rational.make(1, 30); // 30 fps
		long initialTimestamp = System.currentTimeMillis();
		final Muxer muxer = Muxer.make("logs/webcam" + initialTimestamp + ".mp4", null, "mp4");
		

		/**
		 * Now, we need to decide what type of codec to use to encode video. Muxers have
		 * limited sets of codecs they can use. We're going to pick the first one that
		 * works, or if the user supplied a codec name, we're going to force-fit that in
		 * instead.
		 */
		final MuxerFormat format = muxer.getFormat();
		final Codec codec;
		/*
		 * if (codecname != null) { codec = Codec.findEncodingCodecByName(codecname); }
		 * else {
		 */
		codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());
		// }

		/**
		 * Now that we know what codec, we need to create an encoder
		 */
		Encoder encoder = Encoder.make(codec);

		/**
		 * Video encoders need to know at a minimum: width height pixel format Some also
		 * need to know frame-rate (older codecs that had a fixed rate at which video
		 * files could be written needed this). There are many other options you can set
		 * on an encoder, but we're going to keep it simpler here.
		 */
		encoder.setWidth(size.width);
		encoder.setHeight(size.height);
		// We are going to use 420P as the format because that's what most video formats
		// these days use
		final PixelFormat.Type pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
		encoder.setPixelFormat(pixelformat);
		encoder.setTimeBase(framerate);

		/**
		 * An annoynace of some formats is that they need global (rather than
		 * per-stream) headers, and in that case you have to tell the encoder. And since
		 * Encoders are decoupled from Muxers, there is no easy way to know this beyond
		 */
		if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
			encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);

		/** Open the encoder. */
		encoder.open(null, null);

		/** Add this stream to the muxer. */
		muxer.addNewStream(encoder);

		/** And open the muxer for business. */
		muxer.open(null, null);

		/**
		 * Next, we need to make sure we have the right MediaPicture format objects to
		 * encode data with. Java (and most on-screen graphics programs) use some
		 * variant of Red-Green-Blue image encoding (a.k.a. RGB or BGR). Most video
		 * codecs use some variant of YCrCb formatting. So we're going to have to
		 * convert. To do that, we'll introduce a MediaPictureConverter object later.
		 * object.
		 */
		MediaPictureConverter converter = null;
		final MediaPicture picture = MediaPicture.make(encoder.getWidth(), encoder.getHeight(), pixelformat);
		picture.setTimeBase(framerate);

		InputStreamReader fileInputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(fileInputStream);

		/**
		 * Now begin our main loop of taking screen snaps. We're going to encode and
		 * then write out any resulting packets.
		 */
		final MediaPacket packet = MediaPacket.make();
		System.out.println("Recording started, press [ENTER] to save and quit.\nWARNING: Closing the window won't save the recording!!!");
		for (int i = 0; (i < duration / framerate.getDouble()) && (!bufferedReader.ready()); i++) {
			/** Make the screen capture && convert image to TYPE_3BYTE_BGR */
			final BufferedImage screen = convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);

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
			Thread.sleep((long) (1000 * framerate.getDouble()));
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
