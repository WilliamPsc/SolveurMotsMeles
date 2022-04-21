package solveurMotMele;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		/** MOTS MELES V2 **/
		MotsMelesV2 motsmelesV2 = new MotsMelesV2();
		FileInputStream file;
		String readLine = "";
		Scanner scanner;
		int i = 0;
		
		long startTime = System.nanoTime();
		
		try {
			file = new FileInputStream("src/files/mots.txt");
			scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				readLine = scanner.nextLine();
				i++;
				System.out.println("================ " + readLine.toUpperCase() + " ================");
				System.out.println(motsmelesV2.findMot(readLine));
				motsmelesV2.resetGrilleRes();
			}
			scanner.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println(motsmelesV2.toString());
		
		long endTime = System.nanoTime();
		double duration = (endTime - startTime);
		System.out.println("Temps d'exécution pour trouver " + motsmelesV2.getMotTrouveRes() + " / " + i + " mots : " + (duration / 1000000) + " ms");
		
//		System.out.println(motsmelesV2.eliminateChar("mur"));
//		System.out.println(motsmelesV2.countLetters('g'));
	}
}