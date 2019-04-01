package dbo.FirstExperiment;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FooterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JProgressBar timerBar;
	JButton next;
	JLabel progressLabel;
	int totalTasks, currTask;

	public FooterPanel(int totalTasks, ActionListener nextOnClick) {
		this(totalTasks, 0, nextOnClick);
	}

	public FooterPanel(int totalTasks, int currTask, ActionListener nextOnClick) {
		this.totalTasks = totalTasks;
		this.currTask = currTask;

		/* Initialize components */
		next = new JButton("Seguinte");
		next.addActionListener(nextOnClick);
		progressLabel = new JLabel("Tarefa " + (currTask + 1) + " de " + totalTasks);
//		timerBar = new JProgressBar(0, totalTasks);
//		timerBar.setValue(currTask);
//		timerBar.setStringPainted(true);

		/* Add components */
		this.add(progressLabel);
		this.add(next);

		this.setVisible(true);
	}

	public int getCurrTask() {
		return currTask;
	}

	public void updateCurrTask(int currTask) {
		if (currTask < totalTasks) {
			this.currTask = currTask;
			progressLabel.setText("Tarefa " + (currTask + 1) + " de " + totalTasks);
		}
	}

	public void updateCurrTask() {
		updateCurrTask(++currTask);
	}

	public int getTotalTasks() {
		return totalTasks;
	}

	public void setNextEnabled(boolean enabled) {
		next.setEnabled(enabled);
	}
}
