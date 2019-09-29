package dbo.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
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
		boolean convert = false;
		boolean cutEmotion = false;
		boolean merger = false;
		boolean zeroCleaner = false;

//		new Converter().convertEcg("C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\ecg1551025613609.tsv", "C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\LoggersV0.05\\sync2\\convertedEcg1551025613609.tsv");
//		new Converter().convertMouse("mouse1554805722730.tsv", "convertedMouse1554805722730.tsv");
//		new Converter().convertKeyboard("piloto2/keyboard1554805722715.tsv", "piloto2/convertedKeyboard1554805722715.tsv");
//		new Converter().convertMouse("piloto2/task5/mouse1554806616683.tsv", "piloto2/task5/convertedMouse1554806616683.tsv");
//		new Converter().convertKeyboard("piloto2/task5/keyboard1554806616683.tsv", "piloto2/task5/convertedKeyboard1554806616683.tsv");
//		new Converter().convertEcg("piloto2/task5/ecg1554806616683.tsv", "piloto2/task5/convertedEcg1554806616683.tsv");
//		new Converter().toMatlabFolder(new File("piloto2/task5"));

		if (cut) {
			File mouse, keyboard, ecg, print, webcam;
			String folderName;

			// 0010207
			folderName = "0010207";
			mouse = new File(folderName + "/mouse1562073502929.tsv");
			keyboard = new File(folderName + "/keyboard1562073503492.tsv");
			ecg = new File(folderName + "/ecg1562073442191.tsv");
			print = new File(folderName + "/print1562073514209.mp4");
			webcam = new File(folderName + "/webcam1562073522012.mp4");

			new Converter().cutTasks(1562074113195L, 1562074168310L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562074175001L, 1562074235901L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562074273059L, 1562074332925L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562074367178L, 1562074439850L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562074492826L, 1562074649365L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// 0010306
			folderName = "0010306";
			mouse = new File(folderName + "/mouse1559574967464.tsv");
			keyboard = new File(folderName + "/keyboard1559574966949.tsv");
			ecg = new File(folderName + "/ecg1559574840921.tsv");
			print = new File(folderName + "/print1559574979138.mp4");
			webcam = new File(folderName + "/webcam1559574986889.mp4");

			new Converter().cutTasks(1559575548033L, 1559575642813L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559575648610L, 1559575709764L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559575751465L, 1559575811339L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559575845421L, 1559575918094L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559576008212L, 1559576233283L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// 0010307
			folderName = "0010307";
			mouse = new File(folderName + "/mouse1562160155307.tsv");
			keyboard = new File(folderName + "/keyboard1562160156057.tsv");
			ecg = new File(folderName + "/ecg1562160081363.tsv");
			print = new File(folderName + "/print1562160168058.mp4");
			webcam = new File(folderName + "/webcam1562160174906.mp4");

			new Converter().cutTasks(1562160714691L, 1562160866557L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562160878699L, 1562160960413L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562160999217L, 1562161059085L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562161095298L, 1562161167963L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1562161212649L, 1562161395028L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// 0010506
			folderName = "0010506";
			mouse = new File(folderName + "/mouse1559729574696.tsv");
			keyboard = new File(folderName + "/keyboard1559729574102.tsv");
			ecg = new File(folderName + "/ecg1559729313334.tsv");
			print = new File(folderName + "/print1559729585521.mp4");
			webcam = new File(folderName + "/webcam1559729593507.mp4");

			new Converter().cutTasks(1559730104878L, 1559730186113L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559730190517L, 1559730278473L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559730318863L, 1559730378728L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559730412229L, 1559730484894L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559730519590L, 1559730622200L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// 0012506
			folderName = "0012506";
			mouse = new File(folderName + "/mouse1561466521214.tsv");
			keyboard = new File(folderName + "/keyboard1561466521933.tsv");
			ecg = new File(folderName + "/ecg1561466455682.tsv");
			print = new File(folderName + "/print1561466532678.mp4");
			webcam = new File(folderName + "/webcam1561466539926.mp4");

			new Converter().cutTasks(1561467595246L, 1561467895522L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1561467899871L, 1561467999449L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1561468036417L, 1561468096291L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1561468131638L, 1561468204308L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1561468241741L, 1561468470681L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// 0020306
			folderName = "0020306";
			mouse = new File(folderName + "/mouse1559578308275.tsv");
			keyboard = new File(folderName + "/keyboard1559578308275.tsv");
			ecg = new File(folderName + "/ecg1559578094289.tsv");
			print = new File(folderName + "/print1559578317100.mp4");
			webcam = new File(folderName + "/webcam1559578324832.mp4");

			new Converter().cutTasks(1559578775421L, 1559578948816L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559578964486L, 1559579027728L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559579068110L, 1559579127976L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559579160133L, 1559579232799L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1559579272774L, 1559579421919L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

			// 0072907
			folderName = "0072907";
			mouse = new File(folderName + "/mouse1564397795284.tsv");
			keyboard = new File(folderName + "/keyboard1564397795831.tsv");
			ecg = new File(folderName + "/ecg1564398076184.tsv");
			print = new File(folderName + "/print1564397808698.mp4");
			webcam = new File(folderName + "/webcam1564397816232.mp4");

			new Converter().cutTasks(1564398637191L, 1564398878945L, folderName + "/task1", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1564398895948L, 1564398991816L, folderName + "/task2", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1564399032190L, 1564399092064L, folderName + "/task3", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1564399124569L, 1564399197242L, folderName + "/task4", mouse, keyboard, ecg, print,
					webcam);
			new Converter().cutTasks(1564399236450L, 1564399444152L, folderName + "/task5", mouse, keyboard, ecg, print,
					webcam);

//			// piloto1
//			folderName = "piloto1";
//			mouse = new File(folderName + "/mouse1554460547405.tsv");
//			keyboard = new File(folderName + "/keyboard1554460547597.tsv");
//			ecg = null;
//			print = new File(folderName + "/print1554460598191.mp4");
//			webcam = new File(folderName + "/webcam1554460625725.mp4");
//
//			new Converter().cutTasks(1554461585527L, 1554461845333L, folderName + "/task1", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554461848543L, 1554461915635L, folderName + "/task2", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554461967239L, 1554462027165L, folderName + "/task3", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554462080494L, 1554462153226L, folderName + "/task4", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554462194808L, 1554462470931L, folderName + "/task5", mouse, keyboard, ecg, print,
//					webcam);
//			// piloto2
//			folderName = "piloto2";
//			mouse = new File(folderName + "/mouse1554805722730.tsv");
//			keyboard = new File(folderName + "/keyboard1554805722715.tsv");
//			ecg = new File(folderName + "/ecg1554805636765.tsv");
//			print = new File(folderName + "/print1554805726388.mp4");
//			webcam = new File(folderName + "/webcam1554805730369.mp4");
//
//			new Converter().cutTasks(1554806202789L, 1554806297600L, folderName + "/task1", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554806304613L, 1554806374191L, folderName + "/task2", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554806411948L, 1554806471812L, folderName + "/task3", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554806505220L, 1554806577887L, folderName + "/task4", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1554806616683L, 1554806905151L, folderName + "/task5", mouse, keyboard, ecg, print,
//					webcam);
//			// piloto3
//			folderName = "piloto3";
//			mouse = new File(folderName + "/mouse1556025153733.tsv");
//			keyboard = new File(folderName + "/keyboard1556025153765.tsv");
//			ecg = null;
//			print = new File(folderName + "/print1556025163560.mp4");
//			webcam = new File(folderName + "/webcam1556025171426.mp4");
//
//			new Converter().cutTasks(1556025863171L, 1556026003087L, folderName + "/task1", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556026006842L, 1556026101973L, folderName + "/task2", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556026139354L, 1556026199206L, folderName + "/task3", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556026233699L, 1556026306353L, folderName + "/task4", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556026342411L, 1556026525310L, folderName + "/task5", mouse, keyboard, ecg, print,
//					webcam);
//
//			// piloto4
//			folderName = "piloto4";
//			mouse = new File(folderName + "/mouse1556028472375.tsv");
//			keyboard = new File(folderName + "/keyboard1556028472375.tsv");
//			ecg = new File(folderName + "/ecg1556028596075.tsv");
//			print = new File(folderName + "/print1556028481251.mp4");
//			webcam = new File(folderName + "/webcam1556028488059.mp4");
//
//			new Converter().cutTasks(1556029391952L, 1556030073755L, folderName + "/task1", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556030078846L, 1556030143275L, folderName + "/task2", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556030177352L, 1556030237202L, folderName + "/task3", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556030269513L, 1556030342165L, folderName + "/task4", mouse, keyboard, ecg, print,
//					webcam);
//			new Converter().cutTasks(1556030385231L, 1556030829483L, folderName + "/task5", mouse, keyboard, ecg, print,
//					webcam);
		}

		if (convert) {
			String[] folders = new String[] { "0010207", "0010306", "0010307", "0010506", "0012506", "0020306",
					"0072907" };
			boolean deleteExisting = true;

//			for (String folder : folders) { // folders
//				new Converter().convertFolder(new File(folder));
//				for (int j = 1; j <= numTasks; j++) { // task
//					new Converter().convertFolder(new File(folder + "/task" + j));
//				}
//			}

			for (String folder : folders) { // folders
				new Converter().toMatlabFolder(new File(folder), deleteExisting);
				for (int j = 1; j <= numTasks; j++) { // task
					new Converter().toMatlabFolder(new File(folder + "/task" + j), deleteExisting);
				}
			}

//			for (int i = 1; i <= numPilotos; i++) { // piloto
//				new Converter().convertFolder(new File("piloto" + i));
//				for (int j = 1; j <= numTasks; j++) { // task
//					new Converter().convertFolder(new File("piloto" + i + "/task" + j));
//				}
//			}
//			for (int i = 1; i <= numPilotos; i++) { // piloto
//				new Converter().toMatlabFolder(new File("piloto" + i));
//				for (int j = 1; j <= numTasks; j++) { // task
//					new Converter().toMatlabFolder(new File("piloto" + i + "/task" + j));
//				}
//			}
		}

		if (cutEmotion) {
			File emotion;
			String folderName;

			// 0010207
			folderName = "0010207";
			emotion = new File(folderName + "/webcam1562073522012.mp4.emotion.tsv");

			new Converter().cutEmotion(1562074113195L, 1562074168310L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1562074175001L, 1562074235901L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1562074273059L, 1562074332925L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1562074367178L, 1562074439850L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1562074492826L, 1562074649365L, folderName + "/task5", emotion);

			// 0010306
			folderName = "0010306";
			emotion = new File(folderName + "/webcam1559574986889.mp4.emotion.tsv");

			new Converter().cutEmotion(1559575548033L, 1559575642813L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1559575648610L, 1559575709764L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1559575751465L, 1559575811339L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1559575845421L, 1559575918094L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1559576008212L, 1559576233283L, folderName + "/task5", emotion);

			// 0010307
			folderName = "0010307";
			emotion = new File(folderName + "/webcam1562160174906.mp4.emotion.tsv");

			new Converter().cutEmotion(1562160714691L, 1562160866557L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1562160878699L, 1562160960413L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1562160999217L, 1562161059085L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1562161095298L, 1562161167963L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1562161212649L, 1562161395028L, folderName + "/task5", emotion);

			// 0010506
			folderName = "0010506";
			emotion = new File(folderName + "/webcam1559729593507.mp4.emotion.tsv");

			new Converter().cutEmotion(1559730104878L, 1559730186113L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1559730190517L, 1559730278473L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1559730318863L, 1559730378728L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1559730412229L, 1559730484894L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1559730519590L, 1559730622200L, folderName + "/task5", emotion);

			// 0012506
			folderName = "0012506";
			emotion = new File(folderName + "/webcam1561466539926.mp4.emotion.tsv");

			new Converter().cutEmotion(1561467595246L, 1561467895522L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1561467899871L, 1561467999449L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1561468036417L, 1561468096291L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1561468131638L, 1561468204308L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1561468241741L, 1561468470681L, folderName + "/task5", emotion);

			// 0020306
			folderName = "0020306";
			emotion = new File(folderName + "/webcam1559578324832.mp4.emotion.tsv");

			new Converter().cutEmotion(1559578775421L, 1559578948816L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1559578964486L, 1559579027728L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1559579068110L, 1559579127976L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1559579160133L, 1559579232799L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1559579272774L, 1559579421919L, folderName + "/task5", emotion);

			// 0072907
			folderName = "0072907";
			emotion = new File(folderName + "/webcam1564397816232.mp4.emotion.tsv");

			new Converter().cutEmotion(1564398637191L, 1564398878945L, folderName + "/task1", emotion);
			new Converter().cutEmotion(1564398895948L, 1564398991816L, folderName + "/task2", emotion);
			new Converter().cutEmotion(1564399032190L, 1564399092064L, folderName + "/task3", emotion);
			new Converter().cutEmotion(1564399124569L, 1564399197242L, folderName + "/task4", emotion);
			new Converter().cutEmotion(1564399236450L, 1564399444152L, folderName + "/task5", emotion);

//			folderName = "piloto2";
//			emotion = new File(folderName + "/webcam1554805730369.mp4.emotion.tsv");
//			new Converter().cutEmotion(1554806202789L, 1554806297600L, folderName + "/task1", emotion);
//			new Converter().cutEmotion(1554806304613L, 1554806374191L, folderName + "/task2", emotion);
//			new Converter().cutEmotion(1554806411948L, 1554806471812L, folderName + "/task3", emotion);
//			new Converter().cutEmotion(1554806505220L, 1554806577887L, folderName + "/task4", emotion);
//			new Converter().cutEmotion(1554806616683L, 1554806905151L, folderName + "/task5", emotion);
//
//			folderName = "piloto3";
//			emotion = new File(folderName + "/webcam1556025171426.mp4.emotion.tsv");
//			new Converter().cutEmotion(1556025863171L, 1556026003087L, folderName + "/task1", emotion);
//			new Converter().cutEmotion(1556026006842L, 1556026101973L, folderName + "/task2", emotion);
//			new Converter().cutEmotion(1556026139354L, 1556026199206L, folderName + "/task3", emotion);
//			new Converter().cutEmotion(1556026233699L, 1556026306353L, folderName + "/task4", emotion);
//			new Converter().cutEmotion(1556026342411L, 1556026525310L, folderName + "/task5", emotion);
//
//			folderName = "piloto4";
//			emotion = new File(folderName + "/webcam1556028488059.mp4.emotion.tsv");
//			new Converter().cutEmotion(1556029391952L, 1556030073755L, folderName + "/task1", emotion);
//			new Converter().cutEmotion(1556030078846L, 1556030143275L, folderName + "/task2", emotion);
//			new Converter().cutEmotion(1556030177352L, 1556030237202L, folderName + "/task3", emotion);
//			new Converter().cutEmotion(1556030269513L, 1556030342165L, folderName + "/task4", emotion);
//			new Converter().cutEmotion(1556030385231L, 1556030829483L, folderName + "/task5", emotion);

		}

		if (merger) {

			String[] folders = new String[] { "0010207", "0010306", "0010307", "0010506", "0012506", "0020306",
					"0072907" };

			for (String folder : folders) { // folders
				for (int j = 1; j <= numTasks; j++) { // task
					new Converter().merge(folder + "/task" + j + "/" + findKeyboardFile(new File(folder + "/task" + j)),
							"C:\\Users\\DanyO\\OneDrive\\Ambiente de Trabalho\\dissert\\pratical\\ECG_P" + folder
									+ ".csv",
							folder + "/task" + j + "/merged.csv", j);
				}
			}
		}

		if (zeroCleaner) {
			String[] folders = new String[] { /*"0010207", "0010306",*/ "0010307", "0010506", "0012506", "0020306",
					"0072907" };
			for (int i = 0; i < folders.length; i++) {
				folders[i] = "Data Organizar" + File.separator + folders[i];
			}

			for (String folder : folders) { // folders
				for (int j = 1; j <= numTasks; j++) { // task
					new Converter()
							.removeNulls(folder + File.separator + "task" + j + File.separator + "merged_trab.csv");
				}
			}
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
			if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
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
		 * while (loop) { fullLine = br.readLine(); if (fullLine == null ||
		 * fullLine.isEmpty() || fullLine.equals("")) { loop = false; } else { splitLine
		 * = fullLine.split("\t"); lineTime = Long.parseLong(splitLine[0]);
		 * printStack.add(splitLine); elementCount++;
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
		if (fullLine != null) {
			splitLine = fullLine.split("\t");
			x = Integer.parseInt(splitLine[1]);
			y = Integer.parseInt(splitLine[2]);
			currentTimestamp = Long.parseLong(splitLine[0]);
			calendar.setTimeInMillis(currentTimestamp);
			hour = calendar.get(Calendar.HOUR);
			min = calendar.get(Calendar.MINUTE);
			sec = calendar.get(Calendar.SECOND);
			pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t" + accDistance
					+ "\t" + velocity);

			while (loop) {
				fullLine = br.readLine();
				if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
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
					pw.write("\n" + fullLine + "\t" + hour + "\t" + min + "\t" + sec + "\t" + distance + "\t"
							+ accDistance + "\t" + velocityString);
				}
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
			if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
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
					try {
						releaseDelay = Long.toString(currentTimestamp - keyHistory.get(splitLine[2]));
					} catch (NullPointerException ex) {
						continue;
						// there is a up without a down first
					}
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

	public static String findKeyboardFile(File folder) {
		for (File f : folder.listFiles()) {
			String fileName = f.getName();

			if (fileName.startsWith("convertedKeyboard")) {
				return fileName;
			}
		}
		return null;
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

	public boolean toMatlabFolder(File folder, boolean deleteExisting) throws IOException {
		for (File f : folder.listFiles()) {
			String fileName = f.getName();
			String outputFileName;

			if (fileName.startsWith("convertedMouse")) {
				outputFileName = f.getParent() + "/matlab" + fileName.substring(0, 1).toUpperCase()
						+ fileName.substring(1);
				outputFileName = outputFileName.replace(".tsv", ".csv");
				if (deleteExisting) {
					new File(outputFileName).delete();
				}
				toMatlabMouse(f.getAbsolutePath(), outputFileName);
			} else if (fileName.startsWith("convertedKeyboard")) {
				outputFileName = f.getParent() + "/matlab" + fileName.substring(0, 1).toUpperCase()
						+ fileName.substring(1);
				outputFileName = outputFileName.replace(".tsv", ".csv");
				if (deleteExisting) {
					new File(outputFileName).delete();
				}
				toMatlabKeyboard(f.getAbsolutePath(), outputFileName);
			}
			/*
			 * else if (fileName.startsWith("convertedEcg")) {
			 * toMatlabEcg(f.getAbsolutePath(), f.getParent() + "/converted" +
			 * fileName.substring(0, 1).toUpperCase() + fileName.substring(1)); }
			 */
		}

		return true;
	}

	public boolean toMatlabKeyboard(String keyboardFileName, String targetFileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;

		File input = new File(keyboardFileName);
		br = new BufferedReader(new FileReader(input));

		File output = new File(targetFileName);
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
			if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				if (splitLine[1].equals("keyUp")) { // ignore keyDowns
					pw.write(splitLine[3]);
					for (int i = 4; i <= 9; i++)
						pw.write("\t" + splitLine[i]);
					pw.write("\n");
				}

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
			if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

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

		br.readLine(); // ignore header

		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				// code here
			}
		}

		br.close();
		pw.close();
		return true;
	}

	public boolean merge(String keyboard, String ecg, String output, int task) throws IOException {
		// Declare variables
		File keyboardFile = new File(keyboard);
		File ecgFile = new File(ecg);
		File outputFile = new File(output);
		if (outputFile.exists()) {
			outputFile.delete();
		}
		PrintWriter pw = new PrintWriter(new FileWriter(outputFile), true);
		BufferedReader ecgBr = new BufferedReader(new FileReader(ecgFile));
		BufferedReader keyboardBr = new BufferedReader(new FileReader(keyboardFile));
		String fullLineKeyboard, fullLineEcg;
		String[] splitLineKeyboard, splitLineEcg;
		boolean loop = true;
		long previousTimestamp = 0;// = Long.parseLong(keyboardBr.readLine().split("\t")[0]);
		long currTimestamp;
		long keyboardTimestamp;
		int index = (task - 1) * 4;
		ArrayList<String> keyboardArray = new ArrayList<String>();
		ArrayList<String> ecgArray = new ArrayList<String>();
		Map<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();

		// ignore headers
		keyboardBr.readLine();
		ecgBr.readLine();

		// store in the array
		while ((fullLineKeyboard = keyboardBr.readLine()) != null) {
			if (fullLineKeyboard.split("\t")[1].equals("keyUp")) {
				keyboardArray.add(fullLineKeyboard);
			}
		}
		while ((fullLineEcg = ecgBr.readLine()) != null) {
			if (!fullLineEcg.equals("")) {
				ecgArray.add(fullLineEcg);
			} else {
				continue;
			}
		}

		// search for the closest element and save on the hashmap
		for (String i : ecgArray) {
			long distance = 999999999999L; // this number represents the maximum distance
			try {
				currTimestamp = Long.parseLong(i.split(",", -1)[0 + index]);
			} catch (java.lang.NumberFormatException e) {
				break;// its not a bug, it simply means the end of file ON THAT SPECIFIC COLUMN OF THE
						// TASK has been reached
			}
			int closerElement = -1;

			for (int j = 0; j < keyboardArray.size(); j++) {
				keyboardTimestamp = Long.parseLong(keyboardArray.get(j).split("\t")[0]);
				if (Math.abs(currTimestamp - keyboardTimestamp) < distance) {
					closerElement = j;
					previousTimestamp = keyboardTimestamp;
					distance = Math.abs(currTimestamp - keyboardTimestamp);
				} else {
					// break; having break here speeds up but might unintentionally break on
					// consecutive duplicate entries or somehow unsorted data
				}

				if (closerElement == -1) {
					try {
						throw new IOException("Could not link ecg entry " + currTimestamp + " to the keyboard");
					} catch (IOException e) {
						System.err.println(e.getMessage()); // Yes, I could directly print the message but you could
															// also shut up
						continue;
					}
				}
			}
			map.putIfAbsent(closerElement, new ArrayList<String>());
			map.get(closerElement).add(i);
		}

		// save to file
		for (int i = 0; i < keyboardArray.size(); i++) {
			splitLineKeyboard = keyboardArray.get(i).split("\t");
			splitLineEcg = convertMultipleLines(map.get(i));

			if (!splitLineKeyboard[3].equals("0")) {
				pw.write(splitLineKeyboard[3]);
			}
			for (int j = 4; j <= 9; j++) {
				pw.write(",");
				if (!splitLineKeyboard[j].equals("0")) {
					pw.write(splitLineKeyboard[j]);
				}
			}
			for (int j = 1; j <= 3; j++) {
				pw.write(",");
				if (!splitLineEcg[j + index].equals("0")) {
					pw.write(splitLineEcg[j + index]);
				}
			}
			pw.write("\n");
		}

		pw.close();
		ecgBr.close();
		keyboardBr.close();
		return true;
	}

	public void removeNulls(String fileName) throws IOException {
		PrintWriter pw;
		BufferedReader br;
		int colNumber = 4;

		File output = new File(fileName);
		File backup = new File(output.getParent() + File.separator + "backup_" + output.getName());
		Files.move(output.toPath(), backup.toPath());
		br = new BufferedReader(new FileReader(backup));

		
		output.delete();
		if (output.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			br.close();
			return;
		}
		pw = new PrintWriter(new FileWriter(output), true);

		String fullLine;
		String[] splitLine;
		boolean loop = true;
		boolean firstCol = true;

		// First Line
		fullLine = br.readLine();
		splitLine = fullLine.split(",");
		for (int i = 0; i < colNumber; i++) {
			if (firstCol) {
				if ((splitLine.length <= i) || (splitLine[i] == null)) {
					pw.write(0);
				} else {
					pw.write(splitLine[i]);
				}
				firstCol = false;
			} else {
				if ((splitLine.length <= i) || (splitLine[i] == null)) {
					pw.write("," + 0);
				} else {
					pw.write("," + splitLine[i]);
				}
			}
		}

		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null || fullLine.isEmpty() || fullLine.equals("")) {
				loop = false;
			} else {
				firstCol = true;

				splitLine = fullLine.split(",");
				for (int i = 0; i < colNumber; i++) {
					if (firstCol) {
						if ((splitLine.length <= i) || (splitLine[i] == null)) {
							pw.write("\n" + 0);
						} else {
							pw.write("\n" + splitLine[i]);
						}
						firstCol = false;
					} else {
						if ((splitLine.length <= i) || (splitLine[i] == null)) {
							pw.write("," + 0);
						} else {
							pw.write("," + splitLine[i]);
						}
					}
				}
			}
		}

		pw.close();
		br.close();
	}

	/**
	 * Makes averages and merges multiple lines into one
	 * 
	 * @param list
	 * @return
	 */
	private String[] convertMultipleLines(ArrayList<String> list) {
		if (list == null || list.isEmpty()) {
			String[] split = new String[20];
			Arrays.fill(split, "");
			return split;
		}
		double[] split = new double[20];

		for (String line : list) {
			String[] splitLine = line.split(",");
			for (int i = 0; i < splitLine.length; i++) { // calc average
				try {
					split[i] += Double.parseDouble(splitLine[i]) / list.size();
				} catch (NumberFormatException e) {
				}
			}
		}

		return doubleToStringArray(split);
	}

	private String[] doubleToStringArray(double[] array) {
		String[] result = new String[array.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = Double.toString(array[i]);
		}

		return result;
	}

}
