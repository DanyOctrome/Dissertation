package dbo.Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class VideoPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmbeddedMediaPlayerComponent ourMediaPlayer;

	VideoPanel() {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), System.getProperty("user.dir")+"\\VLC");

		ourMediaPlayer = new EmbeddedMediaPlayerComponent();

		/* Set the layout */
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(100, 150));

		this.add(ourMediaPlayer, BorderLayout.CENTER);

		this.setVisible(true);

	}

	public void prepareVideo(String fileName) throws InterruptedException {
		/* Prepare the video */
		ourMediaPlayer.getMediaPlayer().prepareMedia(fileName);
		ourMediaPlayer.getMediaPlayer().parseMedia();
		ourMediaPlayer.getMediaPlayer().play();
		ourMediaPlayer.getMediaPlayer().setPause(true);
		while(getLength() < 1L) {
			Thread.sleep(10);
		}
	}
	
	public void play () {
		ourMediaPlayer.getMediaPlayer().play();
	}

	public void pause(boolean pause) {
		/* Pause the video */
		ourMediaPlayer.getMediaPlayer().setPause(pause);
	}

	public long getTime() {
		return ourMediaPlayer.getMediaPlayer().getTime();
	}

	public void setTime(long time) {
		ourMediaPlayer.getMediaPlayer().setTime(time);
	}

	public long getLength() {
		return ourMediaPlayer.getMediaPlayer().getLength();
	}
	
	public void addMediaPlayerEventListener (MediaPlayerEventListener listener) {
		ourMediaPlayer.getMediaPlayer().addMediaPlayerEventListener(listener);
	}
}