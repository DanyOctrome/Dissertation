package logger;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RunPrintScreen {

	public static void main(String[] args) throws AWTException, IOException, InterruptedException {
		boolean run = true;
		int period = 333;
		
		String filePath = "logs/screen" + System.currentTimeMillis() + '/';
		new File(filePath).mkdirs();

		Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot robot = new Robot();

		while (run) {
			BufferedImage img = robot.createScreenCapture(rec);
			ImageIO.write(img, "jpg", new File(filePath + System.currentTimeMillis() + ".jpg"));
			Thread.sleep(period);
		}
	}

}
