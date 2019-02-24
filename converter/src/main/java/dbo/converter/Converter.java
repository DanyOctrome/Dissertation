package dbo.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Converter {
	String fileName;

	public static void main(String[] args) throws IOException {
		new Converter("C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync\\ecg1550926107048.tsv").convert("C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync\\convertedEcg1550926107048.tsv");

	}

	public Converter(String fileName) {
		this.fileName = fileName;
	}

	public boolean convert(String targetFileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;
		long currentTimestamp;

		File input = new File(fileName);
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
}
