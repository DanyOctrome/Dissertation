package dbo.FirstExperiment;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class FirstExperiment extends JFrame {
	private static final long serialVersionUID = 1L;
	private WritingPanel[] writingPanel;
	final String configFileName = "config.cfg", settingsFolder = "firstExperimentSettings";
	Hashtable<String, String> data = new Hashtable<String, String>();
	// Hashtable<Long, String> events = new Hashtable<Long, String>();
	long startTimestamp, idGiven;
	String folderName = "logs";
	String participantID;
	String[] instructions;
	private static PrintWriter pwEvents;
	final int totalTasks = 5;
	private FooterPanel footer;
	Properties prop;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	SlideshowPanel slideshow;
	boolean breakTask = true;
	MediaPlayerEventAdapter eventAdapter;
	JLabel instructionLabel;
	JEditorPane preQuest, posQuest;
	Font defaultFont;
	ImageIcon icon;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new FirstExperiment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private FirstExperiment() throws Exception {
		/* Initialize Variables */
		instructions = new String[totalTasks];
		writingPanel = new WritingPanel[totalTasks];
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), System.getProperty("user.dir") + "\\VLC");
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		slideshow = new SlideshowPanel();
		defaultFont = new Font("Segoe UI", Font.PLAIN, 18);
		ImageIcon icon = new ImageIcon("firstExperimentSettings/icon.png");

		/* Read Properties */
		prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(settingsFolder + "/" + configFileName);
			prop.load(is);

			folderName = prop.getProperty("folder");
			instructions[0] = prop.getProperty("task1.instructions");
			instructions[1] = prop.getProperty("task2.instructions");
			instructions[2] = prop.getProperty("task3.instructions");
			instructions[3] = prop.getProperty("task4.instructions");
			instructions[4] = prop.getProperty("task5.instructions");
			prop.getProperty("task2.audiofile");
			prop.getProperty("task2.audiospeed");
			prop.getProperty("task2.initialdelay");
			prop.getProperty("task3.audiofile");
			prop.getProperty("task3.audiospeed");
			prop.getProperty("task3.blockingdelay");

		} catch (Exception ex) {
			System.err.println("Config file is broken or non-existent, please make sure it is located in this path:\n"
					+ settingsFolder);
			;
			System.exit(1);
		}

		/* Setup the UI */
		this.setTitle("Padrões de Utilização do Teclado e Rato");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		this.setLocation(100, 100);
		this.setSize(960, 540);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 400));
		this.setIconImage(icon.getImage());
		setUIFont(defaultFont);

		/* Initialize the components */
		this.pack();
		writingPanel[0] = new WritingPanel();
		footer = new FooterPanel(totalTasks, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextTask();
			}

		});
		instructionLabel = new JLabel("Aqui vão aparecer as instruções das tarefas.");
		instructionLabel.setHorizontalAlignment(JLabel.CENTER);
		instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		preQuest = new JEditorPane("text/html",
				"<html>Antes de começar por favor preencha o pré questionário clicando <a href=\""
						+ prop.getProperty("questionnaire.1") + "\">aqui</a>.</html>");
		preQuest.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		preQuest.setFont(defaultFont);
		preQuest.setEditable(false);
		preQuest.setBackground(new JLabel().getBackground());
		preQuest.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
		posQuest = new JEditorPane("text/html", "<html>Por favor preencha o questionário final clicando <a href=\""
				+ prop.getProperty("questionnaire.2") + "\">aqui</a>.</html>");
		posQuest.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		posQuest.setFont(defaultFont);
		posQuest.setEditable(false);
		posQuest.setBackground(new JLabel().getBackground());
		posQuest.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});

		/* Add the components */
		this.add(writingPanel[0], BorderLayout.CENTER);
		this.add(footer, BorderLayout.SOUTH);
		this.add(mediaPlayerComponent, BorderLayout.NORTH);
		this.add(instructionLabel, BorderLayout.NORTH);

		/* Start the UI */
		this.setVisible(true);
		startTimestamp = System.currentTimeMillis();

		/* Setup Experiment */
		participantID = (String) JOptionPane.showInputDialog(null, "Introduza o ID fornecido pela equipa:",
				"Introduza o seu ID", JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (participantID != null) {
			data.put("participantID", participantID);
		} else {
			// Experiment Aborted
			System.exit(0);
		}
		idGiven = System.currentTimeMillis();

		/* Open file to save events */
		final File fEvents = new File(folderName + "/events" + participantID + ".tsv");
		fEvents.getParentFile().mkdirs();
		if (fEvents.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			System.exit(1);
		}
		pwEvents = new PrintWriter(new FileWriter(fEvents), true);

		/* Save previously saved events */
		pwEvents.println("timestamp\tdescription");
		pwEvents.println(startTimestamp + "\tProgram started");
		pwEvents.println(idGiven + "\tID written");

		/* Open pop up with questionnaire */
		pwEvents.println(System.currentTimeMillis() + "\tPre Questionnaire opened");
		JOptionPane.showMessageDialog(null, preQuest);
		pwEvents.println(System.currentTimeMillis() + "\tFirst task started");

		/* Open pop up with instructions */
		instructionLabel.setText(instructions[0]);
		JOptionPane.showMessageDialog(this, instructions[0], "Instructions", JOptionPane.PLAIN_MESSAGE);
		pwEvents.println(System.currentTimeMillis() + "\tFirst task instructions read");
	}

	private void nextTask() {
		footer.setNextEnabled(false);
		switch (footer.getCurrTask()) {
		case 0:
			footer.updateCurrTask();
			/* Save data to file */
			pwEvents.println(System.currentTimeMillis() + "\tFirst task completed");
			writingPanel[0].writeToFile(folderName + "/firsttask" + participantID + ".txt");

			pwEvents.println(System.currentTimeMillis() + "\tSecond task started");

			/* Open pop up with instructions */
			instructionLabel.setText(instructions[1]);
			JOptionPane.showMessageDialog(this, instructions[1], "Instructions", JOptionPane.PLAIN_MESSAGE);
			pwEvents.println(System.currentTimeMillis() + "\tSecond task instructions read");

			/* Update UI */
			this.remove(writingPanel[0]);
			writingPanel[1] = new WritingPanel();
			this.add(writingPanel[1], BorderLayout.CENTER);
			this.setVisible(true);

			/* Play sound after delay */
			new Timer(Integer.parseInt(prop.getProperty("task2.initialdelay")), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mediaPlayerComponent.getMediaPlayer()
							.setRate(Float.parseFloat(prop.getProperty("task2.audiospeed")));
					mediaPlayerComponent.getMediaPlayer()
							.playMedia(settingsFolder + "/" + prop.getProperty("task2.audiofile"));
					eventAdapter = new MediaPlayerEventAdapter() {
						@Override
						public void finished(MediaPlayer mediaPlayer) {
							pwEvents.println(System.currentTimeMillis() + "\tSecond task audio finished");
							footer.setNextEnabled(true);
						}
					};
					mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(eventAdapter);

					((Timer) e.getSource()).stop();
				}

			}).start();

			break;
		case 1:
			if (breakTask) {
				/* Save data to file */
				pwEvents.println(System.currentTimeMillis() + "\tSecond task completed");
				writingPanel[1].writeToFile(folderName + "/secondtask" + participantID + ".txt");

				/* First Break */
				this.remove(writingPanel[1]);
				instructionLabel.setText("Intervalo");
				this.add(slideshow, BorderLayout.CENTER);
//				slideshow.play(Integer.parseInt(prop.getProperty("breakduration.first")));
				slideshow.play();
				this.setVisible(true);
				new Timer(Integer.parseInt(prop.getProperty("breakduration.first")), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						breakTask = false;
						nextTask();

						((Timer) e.getSource()).stop();
					}
				}).start();
			} else {
				footer.updateCurrTask();
				pwEvents.println(System.currentTimeMillis() + "\tThird task started");

				/* Update UI */
				// this.remove(slideshow);
				slideshow.pause();
				slideshow.setVisible(false);
				writingPanel[2] = new WritingPanel();
				this.add(writingPanel[2], BorderLayout.CENTER);
				this.setVisible(true);

				/* Open pop up with instructions */
				instructionLabel.setText(instructions[2]);
				JOptionPane.showMessageDialog(this, instructions[2], "Instructions", JOptionPane.PLAIN_MESSAGE);
				pwEvents.println(System.currentTimeMillis() + "\tThird task instructions read");

				/* Play sound after delay */
				new Timer(Integer.parseInt(prop.getProperty("task3.initialdelay")), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mediaPlayerComponent.getMediaPlayer()
								.setRate(Float.parseFloat(prop.getProperty("task3.audiospeed")));
						mediaPlayerComponent.getMediaPlayer().playMedia(
								new File(settingsFolder + "/" + prop.getProperty("task3.audiofile")).getAbsolutePath());
						mediaPlayerComponent.getMediaPlayer().removeMediaPlayerEventListener(eventAdapter);
						eventAdapter = new MediaPlayerEventAdapter() {
							@Override
							public void finished(MediaPlayer mediaPlayer) {
								pwEvents.println(System.currentTimeMillis() + "\tThird task audio finished");

								/* Wait before blocking the input */
								new Timer(Integer.parseInt(prop.getProperty("task3.blockingdelay")),
										new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												nextTask();

												((Timer) e.getSource()).stop();
											}
										}).start();
								/* End of the wait */
							}
						};
						mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(eventAdapter);

						((Timer) e.getSource()).stop();
					}

				}).start();
				breakTask = true;
			}

			break;
		case 2:
			if (breakTask) {
				/* Save data to file */
				pwEvents.println(System.currentTimeMillis() + "\tThird task completed");
				writingPanel[2].writeToFile(folderName + "/thirdtask" + participantID + ".txt");

				/* Second Break */
				this.remove(writingPanel[2]);
				instructionLabel.setText("Intervalo");
//				slideshow = new SlideshowPanel();
//				this.add(slideshow, BorderLayout.CENTER);
				this.add(slideshow, BorderLayout.CENTER);
//				slideshow.play(Integer.parseInt(prop.getProperty("breakduration.second")));
				slideshow.play();
				slideshow.setVisible(true);
				new Timer(Integer.parseInt(prop.getProperty("breakduration.second")), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						breakTask = false;
						nextTask();

						((Timer) e.getSource()).stop();
					}
				}).start();
			} else {
				footer.updateCurrTask();
				pwEvents.println(System.currentTimeMillis() + "\tFourth task started");

				/* Update UI */
//				this.remove(slideshow);
				slideshow.pause();
				slideshow.setVisible(false);
				writingPanel[3] = new WritingPanel();
				this.add(writingPanel[3], BorderLayout.CENTER);
				this.setVisible(true);

				/* Open pop up with instructions */
				JOptionPane.showMessageDialog(this, instructions[3], "Instructions", JOptionPane.PLAIN_MESSAGE);
				pwEvents.println(System.currentTimeMillis() + "\tFourth task instructions read");

				/* Play sound after delay */
				new Timer(Integer.parseInt(prop.getProperty("task4.initialdelay")), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mediaPlayerComponent.getMediaPlayer()
								.setRate(Float.parseFloat(prop.getProperty("task4.audiospeed")));
						mediaPlayerComponent.getMediaPlayer().playMedia(
								new File(settingsFolder + "/" + prop.getProperty("task4.audiofile")).getAbsolutePath());
						mediaPlayerComponent.getMediaPlayer().removeMediaPlayerEventListener(eventAdapter);
						eventAdapter = new MediaPlayerEventAdapter() {
							@Override
							public void finished(MediaPlayer mediaPlayer) {
								pwEvents.println(System.currentTimeMillis() + "\tFourth task audio finished");

								/* Wait before blocking the input */
								new Timer(Integer.parseInt(prop.getProperty("task4.blockingdelay")),
										new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												nextTask();

												((Timer) e.getSource()).stop();
											}
										}).start();
								/* End of the wait */
							}
						};
						mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(eventAdapter);

						((Timer) e.getSource()).stop();
					}

				}).start();
				breakTask = true;
			}

			break;
		case 3:
			if (breakTask) {
				/* Save data to file */
				pwEvents.println(System.currentTimeMillis() + "\tFourth task completed");
				writingPanel[3].writeToFile(folderName + "/fourthtask" + participantID + ".txt");

				/* Third Break */
				this.remove(writingPanel[3]);
				instructionLabel.setText("Intervalo");
//				slideshow = new SlideshowPanel();
//				this.add(slideshow, BorderLayout.CENTER);
				this.add(slideshow, BorderLayout.CENTER);
//				slideshow.play(Integer.parseInt(prop.getProperty("breakduration.third")));
				slideshow.play();
				slideshow.setVisible(true);
				new Timer(Integer.parseInt(prop.getProperty("breakduration.third")), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						breakTask = false;
						nextTask();

						((Timer) e.getSource()).stop();
					}
				}).start();
			} else {
				footer.updateCurrTask();
				pwEvents.println(System.currentTimeMillis() + "\tFifth task started");

				/* Update UI */
//				this.remove(slideshow);
				slideshow.pause();
				slideshow.setVisible(false);
				writingPanel[4] = new WritingPanel();
				this.add(writingPanel[4], BorderLayout.CENTER);
				this.setVisible(true);
				footer.setNextEnabled(true);

				/* Open pop up with instructions */
				JOptionPane.showMessageDialog(this, instructions[4], "Instructions", JOptionPane.PLAIN_MESSAGE);
				pwEvents.println(System.currentTimeMillis() + "\tFifth task instructions read");
			}
			break;
		case 4:
			footer.updateCurrTask();

			/* Save data to file */
			pwEvents.println(System.currentTimeMillis() + "\tFifth task completed");
			writingPanel[4].writeToFile(folderName + "/fifthtask" + participantID + ".txt");

			/* Open pop up with questionnaire */
			pwEvents.println(System.currentTimeMillis() + "\tPos Questionnaire opened");
			JOptionPane.showMessageDialog(null, posQuest);
			pwEvents.println(System.currentTimeMillis() + "\tOpening of thank you message");

			/* Open pop up with instructions */
			JOptionPane.showMessageDialog(this, "Obrigado pela sua contribuição.", "Obrigado!",
					JOptionPane.PLAIN_MESSAGE);
			pwEvents.println(System.currentTimeMillis() + "\tClosing of thank you message");

			System.exit(0);

			break;
		case 5:
			break;
		}
	}

	@SuppressWarnings("rawtypes")
	public static void setUIFont(Font f) {
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}
}
