package dbo.FirstExperiment;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class HeaderPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JProgressBar progressBar;
	JButton next;
	int totalTasks, currTask;
	
	public HeaderPanel (int totalTasks, ActionListener nextOnClick) {
		this(totalTasks, 0, nextOnClick);
	}
	
	public HeaderPanel (int totalTasks, int currTask, ActionListener nextOnClick) {
		this.totalTasks = totalTasks;
		this.currTask = currTask;
		
		/* Initialize components */
		progressBar = new JProgressBar(0, totalTasks);
		progressBar.setValue(currTask);
		progressBar.setStringPainted(true);
		next = new JButton("Next");
		next.addActionListener(nextOnClick);
		
		/* Add components */
		this.add(progressBar);
		this.add(next);
		
		this.setVisible(true);
	}

	public int getCurrTask() {
		return currTask;
	}

	public void updateCurrTask(int currTask) {
		this.currTask = currTask;
		progressBar.setValue(currTask);
	}
	
	public void updateCurrTask() {
		updateCurrTask(++currTask);
	}

	public int getTotalTasks() {
		return totalTasks;
	}
}
