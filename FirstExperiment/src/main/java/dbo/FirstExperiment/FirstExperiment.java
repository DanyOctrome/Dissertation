package dbo.FirstExperiment;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Hello world!
 *
 */
public class FirstExperiment extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WritingPanel[] writingPanel;
	final String configFileName = "firstExperimentSettings/config.cfg";
	Hashtable<String, String> data = new Hashtable<String, String>();
	//Hashtable<Long, String> events = new Hashtable<Long, String>();
	long startTimestamp, startFirstTask;
	String folderName = "logs";
	String participantID;
	private static PrintWriter pwEvents;
	final int totalTasks = 5;
	private HeaderPanel header;

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
		/* Read Properties */
		Properties prop = new Properties();
		InputStream is = null;
		try {
		    is = new FileInputStream(configFileName);
		    prop.load(is);
		    
		    folderName = prop.getProperty("folder");
		    prop.getProperty("task2.audiofile");
		    prop.getProperty("task2.audiospeed");
		    prop.getProperty("task3.audiofile");
		    prop.getProperty("task3.audiospeed");
		    prop.getProperty("task3.blockingdelay");
		    
		} catch (Exception ex) {
		    System.err.println("Config file is broken or non-existent, please make sure it is located in this path:\n" + configFileName);;
		    System.exit(1);
		}
		
		/* Setup the UI */
		this.setTitle("Writer 1.0");
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		this.setLocation(100, 100);
		this.setSize(960, 540);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 writingPanel = new WritingPanel[1];

		/* Initialize the components */
		writingPanel[0] = new WritingPanel();
		header = new HeaderPanel(totalTasks, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				nextTask();
			}
			
		});

		/* Add the components */
		this.add(writingPanel[0], BorderLayout.CENTER);
		this.add(header, BorderLayout.NORTH);

		/* Start the UI */
		this.setVisible(true);
		startTimestamp = System.currentTimeMillis();
		
		/* Setup Experiment */
		participantID = (String) JOptionPane.showInputDialog(null, "Enter the ID you have been given by the team:", "Enter your ID",
				JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (participantID != null) {
			data.put("participantID", participantID);
		} else {
			// Experiment Aborted
			System.exit(0);
		}
		startFirstTask = System.currentTimeMillis();
		
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
		pwEvents.println(startFirstTask + "\tFirst task started");
	}
	
	private void nextTask() {
		switch (header.getCurrTask()) {
		case 0:
			pwEvents.println(System.currentTimeMillis() + "\tFirst task completed");
			writingPanel[0].writeToFile(folderName + "/firsttask" + participantID + ".txt");
			
			header.updateCurrTask();
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
	}
}
