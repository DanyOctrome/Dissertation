package dbo.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Converter {

	public static void main(String[] args) throws IOException {
//		new Converter().convertEcg("C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\ecg1551025613609.tsv", "C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\convertedEcg1551025613609.tsv");
		new Converter().convertMouse("mouse1554805722730.tsv", "convertedMouse1554805722730.tsv");
	}

	public boolean convertEcg(String ecgFileName, String targetFileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;
		long currentTimestamp;

		File input = new File(ecgFileName);
		br = new BufferedReader(new FileReader(input));

		File output = new File(targetFileName);
		// output.getParentFile().mkdirs();
		if (output.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			br.close();
			return false;
		}
		pw = new PrintWriter(new FileWriter(output), true);

		String fullLine;
		String[] splitLine;
		boolean loop = true;
		int index = 1;

		fullLine = br.readLine();
		splitLine = fullLine.split("\t");
		currentTimestamp = Long.parseLong(splitLine[0]);

		for (int i = 1; i < splitLine.length; i++) {
			if (i != 1) {
				pw.write("\n");
			}
			pw.write(currentTimestamp + "\t");
			currentTimestamp += 2; // 2 because the frequency is 500 per second
			pw.write(index++ + "\t");
			pw.write(splitLine[i]);
		}

		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				for (int i = 1; i < splitLine.length; i++) {
					pw.write("\n" + currentTimestamp + "\t");
					currentTimestamp += 2; // 2 because the frequency is 500 per second
					pw.write(index++ + "\t");
					pw.write(splitLine[i]);
				}
			}
		}

		/*
		 * String fullLine; String[] splitLine; long lineTime, lastLineTime,
		 * lineIntervalTime; int index = 1, elementCount = 0; boolean loop = true;
		 * 
		 * fullLine = br.readLine(); splitLine = fullLine.split("\t"); lastLineTime =
		 * Long.parseLong(splitLine[0]); printStack.add(splitLine); elementCount++;
		 * 
		 * while (loop) { fullLine = br.readLine(); if (fullLine == null) { loop =
		 * false; } else { splitLine = fullLine.split("\t"); lineTime =
		 * Long.parseLong(splitLine[0]); printStack.add(splitLine); elementCount++;
		 * 
		 * if (lineTime > lastLineTime) { lineIntervalTime = (lineTime - lastLineTime) /
		 * (elementCount * 50); // 50 is the number of values per line
		 * 
		 * // write for (String[] i : printStack) { for (int j = 1; j < i.length; j++) {
		 * pw.write(lastLineTime + "\t"); pw.write(index++ + "\t"); pw.write(i[j] +
		 * "\n");
		 * 
		 * lastLineTime += lineIntervalTime; } }
		 * 
		 * // reset the values lastLineTime = lineTime; printStack.clear(); elementCount
		 * = 0; } } // }
		 */

		br.close();
		pw.close();
		return true;
	}

	public boolean convertMouse(String mouseFileName, String targetFileName) throws IOException {
		Calendar calendar = Calendar.getInstance();
		PrintWriter pw;
		BufferedReader br;

		File input = new File(mouseFileName);
		br = new BufferedReader(new FileReader(input));

		File output = new File(targetFileName);
		// output.getParentFile().mkdirs();
		if (output.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			br.close();
			return false;
		}
		pw = new PrintWriter(new FileWriter(output), true);

		String fullLine;
		String[] splitLine;
		boolean loop = true;
		int index = 1;
		long currentTimestamp;
		int hour, min, sec;
		double distance = 0, accDistance = 0, velocity = 0;
		int x, y;

		fullLine = br.readLine();
		fullLine += "\thour\tmin\tsec\tdistance\taccDistance\tvelocity";
		pw.write(fullLine);
		
		// First iteration
		fullLine = br.readLine();
		splitLine = fullLine.split("\t");
		x = Integer.parseInt(splitLine[1]);
		y = Integer.parseInt(splitLine[2]);
		currentTimestamp = Long.parseLong(splitLine[0]);
		calendar.setTimeInMillis(currentTimestamp);
		hour = calendar.get(Calendar.HOUR);
		min = calendar.get(Calendar.MINUTE);
		sec = calendar.get(Calendar.SECOND);
		pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t" + accDistance + "\t" + velocity);


		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				/* process values */

				distance = calcDistance(x, y, Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]));
				x = Integer.parseInt(splitLine[1]);
				y = Integer.parseInt(splitLine[2]);
				accDistance += distance;
				velocity = distance / (Long.parseLong(splitLine[0]) - currentTimestamp);
				currentTimestamp = Long.parseLong(splitLine[0]);
				calendar.setTimeInMillis(currentTimestamp);
				hour = calendar.get(Calendar.HOUR);
				min = calendar.get(Calendar.MINUTE);
				sec = calendar.get(Calendar.SECOND);

				/* write */
				pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t" + accDistance + "\t" + velocity);
			}
		}

		br.close();
		pw.close();
		return true;
	}

	double calcDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
}
