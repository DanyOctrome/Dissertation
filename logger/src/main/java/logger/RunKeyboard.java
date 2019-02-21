package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class RunKeyboard {

	private static boolean run = true;
	private static final int freq = 128;
	private static PrintWriter pw;

	public static void main(String[] args) throws IOException {
		final File f = new File("logs/keyboard" + System.currentTimeMillis() + ".tsv");
		f.getParentFile().mkdirs(); 
		if (f.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			return;
		}
		pw = new PrintWriter(new FileWriter(f), true);

		pw.println("time\teventType\tkeycode\tcontrolPressed\tmenuPressed\tshiftPressed");

		// might throw a UnsatisfiedLinkError if the native library fails to load or a
		// RuntimeException if hooking fails
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of
																		// raw input

		System.out.println(
				"Global keyboard hook successfully started. Connected keyboards:");
		for (Entry<Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet())
			System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());

		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				System.out.println(event);
				printToFile(event, "keyDown");
				/*
				 * if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) run = false;
				 */
			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {
				System.out.println(event);
				printToFile(event, "keyUp");
			}
		});

		try {
			while (run)
				Thread.sleep(freq);
		} catch (InterruptedException e) {
			/* nothing to do here */
		} finally {
			keyboardHook.shutdownHook();
		}
	}

	static void printToFile(GlobalKeyEvent event, String eventType) {
		char c = event.getKeyChar();
		int control = event.isControlPressed() ? 1 : 0;
		int menu = event.isMenuPressed() ? 1 : 0;
		int shift = event.isShiftPressed() ? 1 : 0;
		pw.printf("%d\t%s\t%d\t%s\t%d\t%d\n", System.currentTimeMillis(), eventType, event.getVirtualKeyCode(), control, menu, shift);
	}

}
