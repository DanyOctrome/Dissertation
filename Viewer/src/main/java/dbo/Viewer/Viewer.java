package dbo.Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class Viewer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean pause = true;
	Sync sync;
	final JMenuBar menuBar;
	long currTime = 0; //TODO: update on mouse dragg
	int numVideos = 2;
	int rangeRadius = 5000;
	FileOpener files = new FileOpener();
	MarkerPanel markers;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Viewer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Viewer() throws Exception {
		this.setTitle("Viewer");

		/* Initialize the JFrame */
		this.setLocation(100, 100);
		this.setSize(960, 540);
		// this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JLabel timeLabel = new JLabel("x/x");

		if (!files.chooseFiles()) {
			System.exit(0);
		}

//		final DisplayGraph graph = new DisplayGraph("convertedEcg1548081379786.tsv") {
		final DisplayGraph graph = new DisplayGraph(files.getFileByPrefix("convertedEcg")) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public long getCurrentConvertedSynchronizedTimestamp() {
				return sync.convertSynchronizedTimestamp(2, currTime).getTimestamp();
			}
		};

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		final JMenuItem openAction = new JMenuItem("Open");
		openAction.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				files.chooseFiles();
			}

		});
		final JMenuItem saveAction = new JMenuItem("Save");
		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		menuBar.add(fileMenu);
		final JMenuItem exitAction = new JMenuItem("Exit");
		final JMenuItem aboutAction = new JMenuItem("About");
		helpMenu.add(exitAction);
		helpMenu.add(aboutAction);
		menuBar.add(helpMenu);

		final VideoPanel[] videos = new VideoPanel[numVideos];
		for (int i = 0; i < numVideos; i++) {
			videos[i] = new VideoPanel();
		}

		JButton playPause = new JButton("Play/Pause");
		playPause.setPreferredSize(new Dimension(100, 20));
		playPause.setMaximumSize(new Dimension(100, 20));
		playPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause = !pause;
				for (int i = 0; i < numVideos; i++) {
					videos[i].pause(pause);
				}
			}

		});

		final JSlider slider = new JSlider();
		slider.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				ConvertedTimestamp convertedTimestampCam, convertedTimestampScreen;
				double currentTimestamp = ((double) slider.getValue() / 100) * (double) sync.getSynchronizedDuration();

				convertedTimestampCam = sync.convertSynchronizedTimestamp(0, (long) currentTimestamp);
				convertedTimestampScreen = sync.convertSynchronizedTimestamp(1, (long) currentTimestamp);

				if (convertedTimestampCam.isOutside() || convertedTimestampScreen.isOutside()) {
					return;
				}
				videos[0].setTime(convertedTimestampCam.getTimestamp());
				videos[1].setTime(convertedTimestampScreen.getTimestamp());
			}

			public void mouseMoved(MouseEvent arg0) {
				return;
			}

		});
		slider.setValue(0);
		
		markers = new MarkerPanel(new File(files.getFolder().getAbsolutePath() + "/markers.tsv")) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public long getCurrentTimestamp() {
				return currTime;
			}
		};

		// Videos panel = cam / screen
		JSplitPane splitPanelVideos = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPanelVideos.setLeftComponent(videos[0]);
		splitPanelVideos.setRightComponent(videos[1]);
		splitPanelVideos.setResizeWeight(0.5);

		// GraEmo panel = ECG graphs / emotions
		JSplitPane splitPanelGraEmo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPanelGraEmo.setLeftComponent(graph);
		splitPanelGraEmo.setRightComponent(new JLabel("Emotions", SwingConstants.CENTER));
		splitPanelGraEmo.setResizeWeight(0.5);

		// Output panel = Videos / graphs and emotions
		JSplitPane splitPanelOutput = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPanelOutput.setLeftComponent(splitPanelVideos);
		splitPanelOutput.setRightComponent(graph);
//		splitPanelOutput.setRightComponent(splitPanelGraEmo);
//		splitPanelOutput.setResizeWeight(0.5);

		// Bar Panel
		JPanel splitPanelBar = new JPanel(new BorderLayout());
		splitPanelBar.add(slider, BorderLayout.CENTER);
		splitPanelBar.add(timeLabel, BorderLayout.EAST);

		// Input Panel
		JPanel splitPanelInput = new JPanel(new BorderLayout());
		JPanel inputButtons = new JPanel(new FlowLayout());
		inputButtons.add(playPause);
		splitPanelInput.add(splitPanelBar, BorderLayout.CENTER);
		splitPanelInput.add(inputButtons, BorderLayout.SOUTH);

		// Main Panel = Output / Input
		JSplitPane splitPanelMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPanelMain.setLeftComponent(splitPanelOutput);
		// splitPanelMain.setLeftComponent(splitPanelVideos);
		splitPanelMain.setRightComponent(splitPanelInput);
		splitPanelMain.setResizeWeight(0.95);
		
//		// Marker Panel
//		JPanel splitPanelMarker = new JPanel();
//		splitPanelMarker.add(new JLabel("markers"));
		
		// Super Panel = Main / Marker
		JSplitPane splitPanelSuper  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPanelSuper.setLeftComponent(splitPanelMain);
//		splitPanelSuper.setRightComponent(splitPanelMarker);
		splitPanelSuper.setRightComponent(markers);
		splitPanelSuper.setResizeWeight(0.85);

		this.add(splitPanelSuper, BorderLayout.CENTER); // Add the panel

		this.setVisible(true);

		// Load Videos
//		videos[0].prepareVideo("webcam1548081404991.mp4");
//		videos[1].prepareVideo("print1548081398095.mp4");
		videos[0].prepareVideo(files.getFileByPrefix("webcam").getAbsolutePath());
		videos[1].prepareVideo(files.getFileByPrefix("print").getAbsolutePath());

		// Intialize Synchronization
//		long[] durations = { videos[0].getLength(), videos[1].getLength(), files.getDurationByPrefix("convertedEcg") };//2933500L
		long[] durations = { videos[0].getLength(), videos[1].getLength(), graph.getDuration() };
		long[] initialTimestamps = { files.getTimestampByPrefix("webcam"), files.getTimestampByPrefix("print"),
				files.getTimestampByPrefix("convertedEcg") };
		sync = new Sync(durations, initialTimestamps);

		// Set the timestamps so they are all synchronized
		videos[0].setTime(sync.getFirstTimestamp(0));
		videos[1].setTime(sync.getFirstTimestamp(1));
		graph.setMarkerValueWithoutOffset(sync.getFirstTimestamp(2), rangeRadius);

		/* Set Duration Label */
		timeLabel.setText("0.0s/" + (sync.getSynchronizedDuration() / 1000) + "."
				+ (sync.getSynchronizedDuration() % 1000) + "s");

		// Update Duration Label
		videos[sync.getMainElementID()].addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
				currTime = newTime;
				timeLabel.setText(
						(newTime / 1000) + "." + (newTime % 1000) + "s/" + (sync.getSynchronizedDuration() / 1000) + "."
								+ (sync.getSynchronizedDuration() % 1000) + "s");
				slider.setValue((int) ((newTime * 100) / sync.getSynchronizedDuration()));
				if (graph.isAutoTimestamp()) {
					graph.setMarkerValueWithoutOffset(sync.convertSynchronizedTimestamp(2, newTime).getTimestamp(),
							rangeRadius);
				}
			}
		});

//		// Play the videos
//		videos[0].play();
//		videos[1].play();
	}
}
