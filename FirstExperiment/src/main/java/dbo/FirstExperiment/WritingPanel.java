package dbo.FirstExperiment;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WritingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
	JTextArea textArea;

	public WritingPanel() {
		this.setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		scrollPane.setViewportView(textArea);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	public void writeToFile(String fileName) {
		try {
			BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
			textArea.write(outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
