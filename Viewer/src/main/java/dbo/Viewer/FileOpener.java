package dbo.Viewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;

public class FileOpener {
	JFileChooser chooser;
	File folder;
	int numFiles = 3;
	File[] files = new File[numFiles];
	String[] fileNamePrefix = { "convertedEcg", "webcam", "print" };
	String[] fileNameSuffix = { ".tsv", ".mp4", ".mp4" };
	long[] startTimestamp, duration;

	/**
	 * Constructor set with default values.
	 */
	public FileOpener() {
		startTimestamp = new long[numFiles];
		duration = new long[numFiles];
	}

	public FileOpener(String[] fileNamePrefix, String[] fileNameSuffix) throws Exception {
		numFiles = fileNameSuffix.length;
		if (numFiles != fileNamePrefix.length) {
			throw new Exception("Provided arrays of suffixes and prefixes' length doesn't match.");
		}
		this.fileNamePrefix = fileNamePrefix;
		this.fileNameSuffix = fileNameSuffix;
		startTimestamp = new long[numFiles];
		duration = new long[numFiles];
	}

	public boolean chooseFiles() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Open the folder with the logs");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			folder = chooser.getSelectedFile();

			boolean[] found = new boolean[numFiles];
			int leftToFind = numFiles;
			for (File f : folder.listFiles()) {
				for (int i = 0; i < numFiles; i++) {
					String fileName = f.getName();
					if (fileName.endsWith(fileNameSuffix[i]) && fileName.startsWith(fileNamePrefix[i])) {
						if (found[i]) {
							/* Another file was already found with the same suffix/prefix */
							continue;
						} else {
							found[i] = true;
							leftToFind--;

							files[i] = f;
							startTimestamp[i] = Long
									.parseLong(fileName.replace(fileNameSuffix[i], "").replace(fileNamePrefix[i], ""));

						}
					}
					/* Runs at every file check (includes irrelevant files too) */
				}
			}
			/* After checking all the files */
			if (leftToFind == 0) {
				return true;
			}
			/* Folder doesn't contain all the files */
		}

		/* User canceled the file chooser */
		return false;
	}

	public File getFileByPrefix(String prefix) {
		int i = findPrefix(prefix);
		if (i == numFiles) {
			return null;
		}
		return files[i];
	}

	public File getFileByID(int id) {
		return files[id];
	}
	
	public File getFolder() {
		return folder;
	}

	public long getTimestampByPrefix(String prefix) {
		int i = findPrefix(prefix);
		if (i == numFiles) {
			return 0L;
		}
		return startTimestamp[i];
	}

	public long getTimestampByID(int id) {
		return startTimestamp[id];
	}

	@Deprecated
	public long getDurationByPrefix(String prefix) {
		int i = findPrefix(prefix);
		if (i == numFiles) {
			return 0L;
		}
		return getDurationByID(i);
	}

	@Deprecated
	public long getDurationByID(int id) {
		long duraDuraDura = duration[id]; // Dady Yankee - "Dura" (or could also mean "duration", no one really knows)

		try {
			if (duraDuraDura == 0) {
				BufferedReader reader = new BufferedReader(new FileReader(files[id]));
				duraDuraDura -= 2;
				while (reader.readLine() != null) {
					duraDuraDura += 2;
				}
				reader.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		}

		return duraDuraDura;
	}

	/* Auxiliary functions */
	private int findPrefix(String prefix) {
		int i;
		for (i = 0; (i < numFiles) && (fileNamePrefix[i] != prefix); i++)
			;
		return i;
	}
}
