package dbo.Viewer;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EmotionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTabbedPane tabbedPane;
	String[] tabNames;
	EmotionGraph[] graphs;
	int numTabs;
	
	public EmotionPanel (File[] files, String[] tabNames) throws Exception {
		this.tabNames = tabNames;
		
		this.setLayout(new GridLayout(1, 1));
		numTabs = tabNames.length;
		
		if (numTabs != files.length) {
			throw new Exception("Files and tabnames length doesn't match.");
		}

		/* Initialize elements */
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		graphs = new EmotionGraph[numTabs];
		for (int i = 0; i < numTabs; i++) {
			graphs[i] = new EmotionGraph(files[i]);
			tabbedPane.addTab(tabNames[i], graphs[i]);
		}

		/* Add the elements */
		this.add(tabbedPane);
	}
}
