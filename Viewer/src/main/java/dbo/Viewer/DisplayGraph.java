package dbo.Viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultXYDataset;

class DisplayGraph extends JPanel {
	private static final long serialVersionUID = 1L;
	DefaultXYDataset dataset;
	JFreeChart chart;
	final ChartPanel chartPanel;
	ValueMarker marker;
	final XYPlot plot;
	final int freq = 500;
	final int period = 1000 / freq;
	int rangeRadius = 5000;
	boolean autoTimestamp = false;
	long initialTimestamp = 0;
	long duration = -period;

	public DisplayGraph(String fileName) throws IOException {
		this(new File(fileName));
	}

	public DisplayGraph(File inputFile) throws IOException {
		this.setLayout(new BorderLayout());

		BufferedReader br = new BufferedReader(new FileReader(inputFile));

		this.initialTimestamp = Long
				.parseLong(inputFile.getName().replaceAll("convertedEcg", "").replaceAll(".tsv", ""));

		dataset = new DefaultXYDataset();

		boolean loop = true;
		String fullLine;
		String[] splitLine;
		List<Double> dataX = new ArrayList<Double>();
		List<Double> dataY = new ArrayList<Double>();
		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				dataX.add(Double.valueOf(splitLine[0]));
				dataY.add(Double.valueOf(splitLine[2]));

				duration += period;
			}
		}
		double[][] temp = new double[2][dataX.size()];
		for (int i = 0; i < dataX.size(); i++) {
			temp[0][i] = dataX.get(i);
			temp[1][i] = dataY.get(i);
		}

		dataset.addSeries(0, temp);

		chart = ChartFactory.createXYLineChart("ECG Input", "Timestamp", "ECG Value", dataset, PlotOrientation.VERTICAL,
				true, true, false);

		plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		marker = new ValueMarker(0);
		marker.setPaint(Color.blue);
		marker.setLabel("Current Timestamp");
		plot.addDomainMarker(marker);

		chartPanel = new ChartPanel(chart);

		/* Create the Input */
		JButton setButton = new JButton("Set Timestamp");
		JButton getButton = new JButton("Get Current Timestamp");
		final JToggleButton autoButton = new JToggleButton("Auto Mode");
		final JTextField textField = new JTextField(14);

		/* Set the properties of Input */
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					setMarkerValue(Double.parseDouble(textField.getText()), rangeRadius);
				} catch (Exception e) {

				}

			}
		});
		getButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				try {
					textField.setText(Long.toString(getCurrentConvertedSynchronizedTimestamp() + initialTimestamp));
				} catch (Exception e) {

				}
			}
		});
		autoButton.setSelected(autoTimestamp);
		autoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (autoButton.isSelected()) {
					autoTimestamp = true;
				} else {
					autoTimestamp = false;
				}
			}

		});

		/* Add input to the panel */
		JPanel inputPanel = new JPanel();
		inputPanel.add(textField);
		inputPanel.add(setButton);
		inputPanel.add(getButton);
		inputPanel.add(autoButton);

		/* Add the elements */
		this.add(chartPanel, BorderLayout.CENTER);
		this.add(inputPanel, BorderLayout.SOUTH);

		this.validate();
		this.setVisible(true);

		br.close();
	}

	/**
	 * Sets the marker value
	 * 
	 * @param value
	 * @param rangeRadius
	 */
	public void setMarkerValue(double value, int rangeRadius) {
		marker.setValue(value);
		plot.getDomainAxis().setRange(new Range(value - rangeRadius, value + rangeRadius));
	}

	/**
	 * Sets the marker to the synchronized time (adds the initial timestamp to the
	 * value)
	 * 
	 * @param value
	 * @param rangeRadius
	 */
	public void setMarkerValueWithoutOffset(double value, int rangeRadius) {
		value += initialTimestamp;
		marker.setValue(value);
		plot.getDomainAxis().setRange(new Range(value - rangeRadius, value + rangeRadius));
	}

	/**
	 * Meant to be overriden.
	 * 
	 * @return the current time stamp
	 */
	public long getCurrentConvertedSynchronizedTimestamp() {
		return 0;
	}

	public boolean isAutoTimestamp() {
		return autoTimestamp;
	}

	public long getDuration() {
		return duration;
	}
}