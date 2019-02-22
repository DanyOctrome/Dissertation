package dbo.Viewer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

public class EmotionGraph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<List<Double>> values;
	String[] variables;
	int numVars, numLines;
	DefaultXYDataset dataset;
	JFreeChart chart;
	final ChartPanel chartPanel;
	final XYPlot plot;
	int breaki;

//	public EmotionGraph(String fileName, int[] ignoreCols) {
//		this(new File(fileName), ignoreCols)
//	}

	public EmotionGraph(String fileName) throws IOException {
		this(new File(fileName));
	}

	public EmotionGraph(File file) throws IOException {
		readFile(file);
		
		chart = ChartFactory.createXYLineChart("ECG Input", "Timestamp", "ECG Value", dataset, PlotOrientation.VERTICAL,
				true, true, false);

		plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

//		marker = new ValueMarker(0);
//		marker.setPaint(Color.blue);
//		marker.setLabel("Current Timestamp");
//		plot.addDomainMarker(marker);

		chartPanel = new ChartPanel(chart);
		
		/* Add elements */
		this.add(chartPanel);
	}

	private void readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		boolean loop = true;
		String fullLine;
		String[] splitLine;

		/* read header */ // TODO: remove hardcode in the header
		variables = br.readLine().split("\t");

		String[] arrayTemp = new String[variables.length - 4];
		arrayTemp[0] = variables[0];
		for (int i = 1; i < arrayTemp.length; i++) {
			arrayTemp[i] = variables[i + 4];
		}
		variables = arrayTemp;

		numVars = variables.length;
		values = new ArrayList<List<Double>>(numVars);
		for (int i = numVars; i > 0; i--) {
			values.add(new ArrayList<Double>());
		}

		/* read data */ // TODO: remove hardcode in this loop
		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				values.get(0).add(Double.valueOf(splitLine[0].replaceAll(",", ".")));

				for (int i = 5; i < numVars; i++) {
					values.get(i - 4).add(Double.valueOf(splitLine[i].replaceAll(",", ".")));
				}
			}
		}
		br.close();
		numLines = values.get(0).size();

		/* Populate array */ //TODO: remove hardcode on numVars
		double[][][] temp = new double[numVars-5][2][numLines];
		for (int i = 0; i < numVars-5; i++) {
			breaki =i;
			for (int j = 0; j < numLines; j++) {
				temp[i][0][j] = values.get(0).get(j);
				temp[i][1][j] = values.get(i + 1).get(j);
			}
		}

		/* Add to dataset */
		dataset = new DefaultXYDataset();
		int i = 1;
		for (double[][] data : temp) {
			dataset.addSeries(variables[i], data);
			i++;
		}
	}
}
