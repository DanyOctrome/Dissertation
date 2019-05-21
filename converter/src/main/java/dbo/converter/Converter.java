package dbo.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class Converter {
	final String ffmpegPath = "C:\\ffmpeg-20190428-45048ec-win64-static\\bin";
	static final int numPilotos = 4;
	static final int numTasks = 5;
	static final String[] errorChars = { "46", "8" };

	public static void main(String[] args) throws IOException {
		boolean cut = false;
		boolean convert = true;
		boolean cutEmotion = false;

//		new Converter().convertEcg("C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\ecg1551025613609.tsv", "C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\convertedEcg1551025613609.tsv");
//		new Converter().convertMouse("mouse1554805722730.tsv", "convertedMouse1554805722730.tsv");
//		new Converter().convertKeyboard("piloto2/keyboard1554805722715.tsv", "piloto2/convertedKeyboard1554805722715.tsv");

		if (cut) {
			File mouse, keyboard, ecg, print, webcam;
			String folderName;

			// piloto1
			folderName = "piloto1";
			mouse = new File(folderName + "/mouse1554460547405.tsv");
			keyboard = new File(folderName + "/keyboard1554460547597.tsv");
			ecg = null;
			print = new File(folderName + "/print1554460598191.mp4");
			webcam = new File(folderName + "/webcam1554460625725.mp4");

			new Converter().cutTasks(1554461585527L, 1554461845333L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554461848543L, 1554461915635L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554461967239L, 1554462027165L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554462080494L, 1554462153226L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554462194808L, 1554462470931L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);
			// piloto2
			folderName = "piloto2";
			mouse = new File(folderName + "/mouse1554805722730.tsv");
			keyboard = new File(folderName + "/keyboard1554805722715.tsv");
			ecg = new File(folderName + "/ecg1554805636765.tsv");
			print = new File(folderName + "/print1554805726388.mp4");
			webcam = new File(folderName + "/webcam1554805730369.mp4");

			new Converter().cutTasks(1554806202789L, 1554806297600L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554806304613L, 1554806374191L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554806411948L, 1554806471812L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554806505220L, 1554806577887L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1554806616683L, 1554806905151L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);
			// piloto3
			folderName = "piloto3";
			mouse = new File(folderName + "/mouse1556025153733.tsv");
			keyboard = new File(folderName + "/keyboard1556025153765.tsv");
			ecg = null;
			print = new File(folderName + "/print1556025163560.mp4");
			webcam = new File(folderName + "/webcam1556025171426.mp4");

			new Converter().cutTasks(1556025863171L, 1556026003087L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556026006842L, 1556026101973L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556026139354L, 1556026199206L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556026233699L, 1556026306353L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556026342411L, 1556026525310L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// piloto4
			folderName = "piloto4";
			mouse = new File(folderName + "/mouse1556028472375.tsv");
			keyboard = new File(folderName + "/keyboard1556028472375.tsv");
			ecg = new File(folderName + "/ecg1556028596075.tsv");
			print = new File(folderName + "/print1556028481251.mp4");
			webcam = new File(folderName + "/webcam1556028488059.mp4");

			new Converter().cutTasks(1556029391952L, 1556030073755L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556030078846L, 1556030143275L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556030177352L, 1556030237202L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556030269513L, 1556030342165L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1556030385231L, 1556030829483L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);
		}

		if (convert) {
//			for (int i = 1; i <= numPilotos; i++) { // piloto
//				new Converter().convertFolder(new File("piloto" + i));
//				for (int j = 1; j <= numTasks; j++) { // task
//					new Converter().convertFolder(new File("piloto" + i + "/task" + j));
//				}
//			}
			for (int i = 1; i <= numPilotos; i++) { // piloto
				new Converter().toMatlabFolder(new File("piloto" + i));
				for (int j = 1; j <= numTasks; j++) { // task
					new Converter().toMatlabFolder(new File("piloto" + i + "/task" + j));
				}
			}
		}

		if (cutEmotion) {
			File emotion;
			String folderName;

			folderName = "piloto2";
			emotion = new File(folderName + "/webcam1554805730369.mp4.emotion.tsv");
			new Converter().cutEmotion(1554806202789L, 1554806297600L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1554806304613L, 1554806374191L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1554806411948L, 1554806471812L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1554806505220L, 1554806577887L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1554806616683L, 1554806905151L, folderName + "/task5", emotion);

			folderName = "piloto3";
			emotion = new File(folderName + "/webcam1556025171426.mp4.emotion.tsv");
			new Converter().cutEmotion(1556025863171L, 1556026003087L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1556026006842L, 1556026101973L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1556026139354L, 1556026199206L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1556026233699L, 1556026306353L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1556026342411L, 1556026525310L, folderName + "/task5", emotion);

			folderName = "piloto4";
			emotion = new File(folderName + "/webcam1556028488059.mp4.emotion.tsv");
			new Converter().cutEmotion(1556029391952L, 1556030073755L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1556030078846L, 1556030143275L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1556030177352L, 1556030237202L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1556030269513L, 1556030342165L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1556030385231L, 1556030829483L, folderName + "/task5", emotion);

		}
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
				String velocityString = Double.isInfinite(velocity) ? "NaN" : String.valueOf(velocity);
				pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t" + accDistance
						+ "\t" + velocityString);
			}
		}

		br.close();
		pw.close();
		return true;
	}

	public boolean convertKeyboard(String keyboardFileName, String targetFileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;

		File input = new File(keyboardFileName);
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
		long currentTimestamp, lastTimestamp = 0;
		String keyDelay = "NaN", releaseDelay = "NaN";
		int numError = 0, numStrokes = 0;
		Hashtable<String, Long> keyHistory = new Hashtable<String, Long>();
		boolean first = true;

		fullLine = br.readLine();
		fullLine += "\tkeyDelay\treleaseDelay\tnumStrokes\tnumError";
		pw.write(fullLine);

		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				/* process values */
				currentTimestamp = Long.parseLong(splitLine[0]);
				if (splitLine[1].equals("keyDown")) {
					releaseDelay = "NaN";
					keyDelay = "NaN";
					keyHistory.putIfAbsent(splitLine[2], currentTimestamp);
				} else if (splitLine[1].equals("keyUp")) {
					if (first) {
						lastTimestamp = currentTimestamp;
						first = false;
					}
					numStrokes++;
					releaseDelay = Long.toString(currentTimestamp - keyHistory.get(splitLine[2]));
					keyHistory.remove(splitLine[2]);
					keyDelay = Long.toString(currentTimestamp - lastTimestamp);
					for (String err : errorChars) {
						if (splitLine[2] == err) {
							numError++;
							break;
						}
					}
					lastTimestamp = currentTimestamp;
				}

				/* write */
				pw.write("\n" + fullLine + "\t" + keyDelay + "\t" + releaseDelay + "\t" + numStrokes + "\t" + numError);
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
		FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-20190428-45048ec-win64-static\\bin\\ffmpeg.exe");
		FFprobe ffprobe = new FFprobe("C:\\ffmpeg-20190428-45048ec-win64-static\\bin\\ffprobe.exe");

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
		FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-20190428-45048ec-win64-static\\bin\\ffmpeg.exe");
		FFprobe ffprobe = new FFprobe("C:\\ffmpeg-20190428-45048ec-win64-static\\bin\\ffprobe.exe");

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

	public boolean convertFolder(File folder) throws IOException {
		for (File f : folder.listFiles()) {
			String fileName = f.getName();

			if (fileName.startsWith("ecg")) {
				convertEcg(f.getAbsolutePath(),
						f.getParent() + "/converted" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			} else if (fileName.startsWith("mouse")) {
				convertMouse(f.getAbsolutePath(),
						f.getParent() + "/converted" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			} else if (fileName.startsWith("keyboard")) {
				convertKeyboard(f.getAbsolutePath(),
						f.getParent() + "/converted" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			}
		}

		return true;
	}

	public boolean cutEmotion(long cutTimestampStart, long cutTimestampEnd, String newFolderName, File emotion)
			throws IOException {
		BufferedReader br;
		PrintWriter pw;
		boolean loop;
		String fullLine;
		double timestamp;
		double relTimestampStart, relTimestampEnd;

		if (emotion == null)
			return false;

		br = new BufferedReader(new FileReader(emotion));

		// convert from absolute to relative timestamp
		long initialTimestamp = Long
				.parseLong(emotion.getName().replaceAll("webcam", "").replaceAll(".mp4.emotion.tsv", ""));
		relTimestampStart = ((double) (cutTimestampStart - initialTimestamp)) / 1000;
		relTimestampEnd = ((double) (cutTimestampEnd - initialTimestamp)) / 1000;

		File emotionOut = new File(newFolderName + "/webcam" + cutTimestampStart + ".mp4.emotion.tsv");
		emotionOut.getParentFile().mkdirs();
		if (emotionOut.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			br.close();
			return false;
		}
		pw = new PrintWriter(new FileWriter(emotionOut), true);

		loop = true;

		pw.println(br.readLine()); // header

		while (loop) {
			fullLine = br.readLine();

			timestamp = Double.parseDouble(fullLine.split("\t")[0].replaceAll(",", "."));

			if (timestamp < relTimestampStart) {
				continue;
			}

			loop = timestamp < relTimestampEnd;

			if (!loop) {
				break;
			}
			pw.println(fullLine);
		}
		br.close();
		pw.close();

		return true;
	}

	public boolean toMatlabFolder(File folder) throws IOException {
		for (File f : folder.listFiles()) {
			String fileName = f.getName();

			if (fileName.startsWith("convertedMouse")) {
				toMatlabKeyboard(f.getAbsolutePath(),
						f.getParent() + "/matlab" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			} else if (fileName.startsWith("convertedKeyboard")) {
				toMatlabKeyboard(f.getAbsolutePath(),
						f.getParent() + "/matlab" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			} /*else if (fileName.startsWith("convertedEcg")) {
				toMatlabEcg(f.getAbsolutePath(),
						f.getParent() + "/converted" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
			}*/
		}

		return true;
	}

	public boolean toMatlabKeyboard(String keyboardFileName, String targetFileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;

		File input = new File(keyboardFileName);
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

		br.readLine(); // ignore header
		
		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				pw.write(splitLine[3]);
				for (int i = 4; i <= 9; i++)
					pw.write("\t" + splitLine[i]);
				pw.write("\n");

			}
		}

		br.close();
		pw.close();
		return true;
	}

	public boolean toMatlabMouse(String mouseFileName, String targetFileName) throws IOException {
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

		br.readLine(); // ignore header

		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				pw.write(splitLine[10]);
				for (int i = 11; i <= 12; i++)
					pw.write("\t" + splitLine[i]);
				pw.write("\n");
			}
		}

		br.close();
		pw.close();
		return true;
	}

	public boolean toMatlabEcg(String ecgFileName, String targetFileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;

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

		br.readLine(); // ignore headerw
		
		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				//code here
			}
		}

		br.close();
		pw.close();
		return true;
	}
}
