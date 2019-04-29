package dbo.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class Converter {
	final String ffmpegPath = "C:\\ffmpeg-20190428-45048ec-win64-static\\bin";

	public static void main(String[] args) throws IOException {
//		new Converter().convertEcg("C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\ecg1551025613609.tsv", "C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\convertedEcg1551025613609.tsv");
//		new Converter().convertMouse("mouse1554805722730.tsv", "convertedMouse1554805722730.tsv");

		File mouse, keyboard, ecg, print, webcam;
		mouse = new File("piloto4/mouse1556028472375.tsv");
		keyboard = new File("piloto4/keyboard1556028472375.tsv");
		ecg = new File("piloto4/ecg1556028596075.tsv");
		print = new File("piloto4/print1556028481251.mp4");
		webcam = new File("piloto4/webcam1556028488059.mp4");

		new Converter().cutTasks(1556029379496L, 1556030073755L, "piloto4/task1", mouse, keyboard, ecg, print, webcam);
		new Converter().cutTasks(1556030073756L, 1556030143275L, "piloto4/task2", mouse, keyboard, ecg, print, webcam);
		new Converter().cutTasks(1556030173294L, 1556030237202L, "piloto4/task3", mouse, keyboard, ecg, print, webcam);
		new Converter().cutTasks(1556030267209L, 1556030342165L, "piloto4/task4", mouse, keyboard, ecg, print, webcam);
		new Converter().cutTasks(1556030372173L, 1556030829483L, "piloto4/task5", mouse, keyboard, ecg, print, webcam);
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
		pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t" + accDistance + "\t"
				+ velocity);

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
				pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t" + accDistance
						+ "\t" + velocity);
			}
		}

		br.close();
		pw.close();
		return true;
	}

	public boolean cutTasks(long cutTimestampStart, long cutTimestampEnd, String newFolderName, File mouse,
			File keyboard, File ecg, File print, File webcam) throws IOException {
		BufferedReader br;
		PrintWriter pw;
		boolean loop;
		String fullLine;
		long timestamp;

		/* Mouse */
		if (mouse != null) {
			br = new BufferedReader(new FileReader(mouse));

			File mouseOut = new File(newFolderName + "/mouse" + cutTimestampStart + ".tsv");
			mouseOut.getParentFile().mkdirs();
			if (mouseOut.createNewFile() == false) {
				System.err.println("File already exists, exiting...");
				br.close();
				return false;
			}
			pw = new PrintWriter(new FileWriter(mouseOut), true);

			loop = true;

			pw.println(br.readLine()); // header

			while (loop) {
				fullLine = br.readLine();

				timestamp = Long.parseLong(fullLine.split("\t")[0]);

				if (timestamp < cutTimestampStart) {
					continue;
				}

				loop = timestamp < cutTimestampEnd;

				if (!loop) {
					break;
				}
				pw.println(fullLine);
			}
			br.close();
			pw.close();
		}

		/* Keyboard */
		if (keyboard != null) {
			br = new BufferedReader(new FileReader(keyboard));

			File keyboardOut = new File(newFolderName + "/keyboard" + cutTimestampStart + ".tsv");
			keyboardOut.getParentFile().mkdirs();
			if (keyboardOut.createNewFile() == false) {
				System.err.println("File already exists, exiting...");
				br.close();
				return false;
			}
			pw = new PrintWriter(new FileWriter(keyboardOut), true);

			loop = true;

			pw.println(br.readLine()); // header

			while (loop) {
				fullLine = br.readLine();

				timestamp = Long.parseLong(fullLine.split("\t")[0]);

				if (timestamp < cutTimestampStart) {
					continue;
				}

				loop = timestamp < cutTimestampEnd;

				if (!loop) {
					break;
				}
				pw.println(fullLine);
			}
			br.close();
			pw.close();
		}

		/* ECG */
		if (ecg != null) {
			br = new BufferedReader(new FileReader(ecg));

			File ecgOut = new File(newFolderName + "/ecg" + cutTimestampStart + ".tsv");
			ecgOut.getParentFile().mkdirs();
			if (ecgOut.createNewFile() == false) {
				System.err.println("File already exists, exiting...");
				br.close();
				return false;
			}
			pw = new PrintWriter(new FileWriter(ecgOut), true);

			loop = true;

			// pw.println(br.readLine()); // header

			while (loop) {
				fullLine = br.readLine();

				timestamp = Long.parseLong(fullLine.split("\t")[0]);

				if (timestamp < cutTimestampStart) {
					continue;
				}

				loop = timestamp < cutTimestampEnd;

				if (!loop) {
					break;
				}
				pw.println(fullLine);
			}
			br.close();
			pw.close();
		}

		/* Print */
		if (print != null)
			cutPrint(cutTimestampStart, cutTimestampEnd, newFolderName, print);

		/* Webcam */
		if (webcam != null)
			cutWebcam(cutTimestampStart, cutTimestampEnd, newFolderName, webcam);

		return true;
	}

	public void cutPrint(long cutTimestampStart, long cutTimestampEnd, String newFolderName, File print)
			throws IOException {
		FFmpeg ffmpeg = new FFmpeg(ffmpegPath + "\\ffmpeg.exe");
		FFprobe ffprobe = new FFprobe(ffmpegPath + "\\ffprobe.exe");

		long initialTimestamp = Long.parseLong(print.getName().replaceAll("print", "").replaceAll(".mp4", ""));
		long duration = cutTimestampEnd - cutTimestampStart;

		FFmpegBuilder builder = new FFmpegBuilder().setInput(print.getAbsolutePath())
				.addOutput(newFolderName + "/print" + cutTimestampStart + ".mp4")
//			     .setVideoCodec("copy")
//			     .setAudioCodec("copy")
				.setDuration(duration, TimeUnit.MILLISECONDS)
				.setStartOffset(cutTimestampStart - initialTimestamp, TimeUnit.MILLISECONDS).done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();

	}

	public void cutWebcam(long cutTimestampStart, long cutTimestampEnd, String newFolderName, File webcam)
			throws IOException {
		FFmpeg ffmpeg = new FFmpeg(ffmpegPath + "\\ffmpeg.exe");
		FFprobe ffprobe = new FFprobe(ffmpegPath + "\\ffprobe.exe");

		long initialTimestamp = Long.parseLong(webcam.getName().replaceAll("webcam", "").replaceAll(".mp4", ""));
		long duration = cutTimestampEnd - cutTimestampStart;

		FFmpegBuilder builder = new FFmpegBuilder().setInput(webcam.getAbsolutePath())
				.addOutput(newFolderName + "/webcam" + cutTimestampStart + ".mp4")
//			     .setVideoCodec("copy")
//			     .setAudioCodec("copy")
				.setDuration(duration, TimeUnit.MILLISECONDS)
				.setStartOffset(cutTimestampStart - initialTimestamp, TimeUnit.MILLISECONDS).done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();
	}

	private double calcDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
}
