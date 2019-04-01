package dbo.FirstExperiment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class SlideshowPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	final String folderPath = "firstExperimentSettings/breakData";
	final String imagesPath = folderPath + "/images";
	final String songName = "song.mp3";
	File folder;
	File[] images;
	JLabel pic;
	Timer timer;
	int freq = 2000;
	int currImageID = 0, totalImages;
//	Border border;

	public SlideshowPanel() {
		super(new BorderLayout());

		folder = new File(imagesPath);
		images = folder.listFiles(); // TODO: only open image files
		totalImages = images.length;
		pic = new JLabel();
//		pic.setMaximumSize(new Dimension(800, 800));
		pic.setVerticalAlignment(SwingConstants.CENTER);
		pic.setHorizontalAlignment(SwingConstants.CENTER);
//		border = BorderFactory.createLineBorder(Color.BLACK);
//		pic.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createMatteBorder(50, 50, 50, 50, Color.WHITE)));
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		this.add(pic, BorderLayout.CENTER);
		this.add(mediaPlayerComponent, BorderLayout.SOUTH);
		this.setVisible(true);

		timer = new Timer(freq, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeImage();
			}

		});
	}

	protected void changeImage() {
		currImageID = ++currImageID % totalImages;

		ImageIcon icon = new ImageIcon(images[currImageID].getAbsolutePath());
//		Image img = icon.getImage();
//		Image newImg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
//		ImageIcon newImc = new ImageIcon(newImg);
//		pic.setIcon(newImc);
		pic.setIcon(icon);
	}

	public void play() {
		if (!timer.isRunning()) {
			mediaPlayerComponent.getMediaPlayer().playMedia(folderPath + "/" + songName);
			mediaPlayerComponent.getMediaPlayer().setRepeat(true);
			pic.setIcon(new ImageIcon(images[currImageID].getAbsolutePath()));
			timer.setDelay(freq);
			timer.setInitialDelay(freq);
			timer.start();
		}
	}
	
	public void play(int duration) {
		if (!timer.isRunning()) {
			this.freq = duration / (totalImages - 1);
			play();
		}
	}

	public void pause() {
		if (timer.isRunning()) {
			mediaPlayerComponent.getMediaPlayer().stop();
			timer.stop();
		}
	}
}
