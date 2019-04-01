package dbo.FirstExperiment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;

public class WritingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
	JTextArea textArea;
	CompoundBorder border;

	public WritingPanel() {
		this.setLayout(new BorderLayout());
		scrollPane = new JScrollPane();

		border = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 50, 0, 50, Color.gray),
				BorderFactory.createMatteBorder(15, 20, 15, 20, Color.white));
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		textArea.setBorder(border);

		scrollPane.setViewportView(textArea);
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
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
