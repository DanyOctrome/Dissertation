package logger;

import java.io.IOException;
import java.util.Scanner;

public class Run {

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		int choice;

		System.out.print("1 - mouse\n2 - keyboard\n3 - both\nchoice - > ");
		choice = sc.nextInt();

		switch (choice) {
		case 1:
			RunMouse.main(null);
			break;
		case 2:
			RunKeyboard.main(null);
			break;
		case 3:
			System.out.println("Currently unsuported, please delete system32 folder."); //TODO: run both loggers simultaneously
			break;
			/*RunKeyboard.main(null);
			RunMouse.main(null);
			break;*/
		}

		sc.close();

	}

}
