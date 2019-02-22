package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

public class RunMouse {

	private static boolean run = true;
	private static final int freq = 128;
	private static PrintWriter pw;

	public static void main(String[] args) throws IOException, InterruptedException {
		String folderName = "logs";
		if (args.length!=0) {
			folderName = args[0];
		}
		
		final File f = new File(folderName + "/mouse" + System.currentTimeMillis() + ".tsv");
		f.getParentFile().mkdirs(); 
		if (f.createNewFile() == false) {
			System.err.println("File already exists, exiting...");
			return;
		}
		pw = new PrintWriter(new FileWriter(f), true);

		pw.println("time\tx\ty\tleft\tright\tmiddle\tdelta");
		
		// might throw a UnsatisfiedLinkError if the native library fails to load or a
		// RuntimeException if hooking fails
		GlobalMouseHook mouseHook = new GlobalMouseHook(); // add true to the constructor, to switch to raw input mode

		System.out.println("Global mouse hook successfully started. Connected mice:");
		for (Entry<Long, String> mouse : GlobalMouseHook.listMice().entrySet())
			System.out.format("%d: %s\n", mouse.getKey(), mouse.getValue());

		mouseHook.addMouseListener(new GlobalMouseAdapter() {
			@Override
			public void mousePressed(GlobalMouseEvent event) {
				System.out.println(event);
				printToFile(event);
				/*
				 * if ((event.getButtons() & GlobalMouseEvent.BUTTON_LEFT) !=
				 * GlobalMouseEvent.BUTTON_NO && (event.getButtons() &
				 * GlobalMouseEvent.BUTTON_RIGHT) != GlobalMouseEvent.BUTTON_NO)
				 * System.out.println("Both mouse buttons are currenlty pressed!"); if
				 * (event.getButton() == GlobalMouseEvent.BUTTON_MIDDLE) run = false;
				 */
			}

			@Override
			public void mouseReleased(GlobalMouseEvent event) {
				System.out.println(event);
				printToFile(event);
			}

			@Override
			public void mouseMoved(GlobalMouseEvent event) {
				System.out.println(event);
				printToFile(event);
			}

			@Override
			public void mouseWheel(GlobalMouseEvent event) {
				System.out.println(event);
				printToFile(event);
			}
		});

		try {
			while (run)
				Thread.sleep(freq);
		} catch (InterruptedException e) {
			/* nothing to do here */
		} finally {
			mouseHook.shutdownHook();
		}
	}

	static void printToFile(GlobalMouseEvent event) {
		int left = (event.getButtons() & GlobalMouseEvent.BUTTON_LEFT) != 0 ? 1 : 0;
		int right = (event.getButtons() & GlobalMouseEvent.BUTTON_RIGHT) != 0 ? 1 : 0;
		int middle = (event.getButtons() & GlobalMouseEvent.BUTTON_MIDDLE) != 0 ? 1 : 0;
		int delta = event.getDelta();
		pw.printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\n", System.currentTimeMillis(), event.getX(), event.getY(), left, right,
				middle, delta);
	}
}
