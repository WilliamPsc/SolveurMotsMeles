package solveurMotMele;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MotsMelesV2 {

	public FileInputStream file;
	public int line;
	public int column;
	public static String readLine;
	public char[][] matrix;
	public char[][] matrixResult;
	private boolean motTrouve = false;
	private int motTrouveRes;

	public MotsMelesV2() {
		try {
			this.file = new FileInputStream("src/files/grille.txt");
			Scanner scanner = new Scanner(this.file);

			this.line = scanner.nextInt();
			this.column = scanner.nextInt();
			this.matrix = new char[this.line][this.column];
			this.matrixResult = new char[this.line][this.column];
			int i = 0;
			this.motTrouveRes = 0;

			while (scanner.hasNextLine()) {
				readLine = scanner.nextLine();
				if (readLine.length() > 0) {
					for (int j = 0; j < readLine.length(); j++) {
						this.matrix[i][j] = readLine.charAt(j);
						this.matrixResult[i][j] = '-';
					}
					i++;
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetGrilleRes() {
		for (int i = 0; i < this.matrixResult.length; i++) {
			for (int j = 0; j < this.matrixResult[0].length; j++) {
				this.matrixResult[i][j] = '-';
			}
		}
	}

	public String eliminateChar(String mot) {
		mot = mot.toUpperCase();

		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				if (mot.indexOf(this.matrix[i][j]) != -1) {
					this.matrixResult[i][j] = this.matrix[i][j];
				}
			}
		}
		return this.toStringRes();
	}

	public String findMot(String mot) {
		mot = mot.toUpperCase();

		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				if (this.matrix[i][j] == mot.charAt(0) && this.motTrouve == false) {
					parcoursLigne(i, j, mot);
					if (this.motTrouve) {
						j += mot.length();
						this.motTrouveRes++;
					} else {
						parcoursVertical(i, j, mot);
						if(this.motTrouve) {
							this.motTrouveRes++;
						} else {
							parcoursDiagonaleHaute(i, j, mot);
							if(this.motTrouve) {
								this.motTrouveRes++;
							} else {
								parcoursDiagonaleBasse(i, j, mot);
								if(this.motTrouve) {
									this.motTrouveRes++;
								} else {
//									System.out.println("Mot INEXISTANT");
								}
							}
						}
					}
				}
			}
		}
		this.motTrouve = false;
		return this.toStringRes();
	}

	private void parcoursLigne(int i, int j, String mot) {
		int incr = j;
		int lettersFound = 1;

		if ((j + mot.length()) <= this.matrix[0].length) { // s'il y a assez de place à droite
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[i][incr] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[i][incr] = this.matrix[i][incr];
				} else {
					break;
				}
				incr++;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[i][incr - reset] = '_';
				}
			}
		}

		incr = j;
		lettersFound = 1;
		
		if ((j - (mot.length()-1)) >= 0) { // s'il y a assez de place à gauche
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[i][incr] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[i][incr] = this.matrix[i][incr];
				} else {
					break;
				}
				incr--;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[i][incr + reset] = '_';
				}
			}
		}
	}

	private void parcoursVertical(int i, int j, String mot) {
		int incr = i;
		int lettersFound = 1;

		if ((i + mot.length()) <= this.matrix.length) { // s'il y a assez de place en bas
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[incr][j] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[incr][j] = this.matrix[incr][j];
				} else {
					break;
				}
				incr++;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[incr - reset][j] = '_';
				}
			}
		}

		incr = i;
		lettersFound = 1;

		if ((i - mot.length()) >= 0) { // s'il y a assez de place en haut
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[incr][j] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[incr][j] = this.matrix[incr][j];
				} else {
					break;
				}
				incr--;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[incr + reset][j] = '_';
				}
			}
		}
	}

	private void parcoursDiagonaleHaute(int i, int j, String mot) {
		int incrI = i;
		int incrJ = j;
		int lettersFound = 1;

		if ((i - mot.length()) >= 0 && (j - mot.length()) >= 0) { // s'il y a assez de place en diagonale haute gauche
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[incrI][incrJ] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[incrI][incrJ] = this.matrix[incrI][incrJ];
				} else {
					break;
				}
				incrI--;
				incrJ--;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[incrI + reset][incrJ + reset] = '_';
				}
			}
		}

		incrI = i;
		incrJ = j;
		lettersFound = 1;

		if ((i - mot.length()) >= 0 && (j + mot.length()) <= this.matrix[0].length) { // s'il y a assez de place en diagonale haute droite
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[incrI][incrJ] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[incrI][incrJ] = this.matrix[incrI][incrJ];
				} else {
					break;
				}
				incrI--;
				incrJ++;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[incrI + reset][incrJ - reset] = '_';
				}
			}
		}
	}
	
	private void parcoursDiagonaleBasse(int i, int j, String mot) {
		int incrI = i;
		int incrJ = j;
		int lettersFound = 1;

		if ((i + mot.length()) <= this.matrix.length && (j - mot.length()) >= 0) { // s'il y a assez de place en diagonale basse gauche
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[incrI][incrJ] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[incrI][incrJ] = this.matrix[incrI][incrJ];
				} else {
					break;
				}
				incrI++;
				incrJ--;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[incrI - reset][incrJ + reset] = '_';
				}
			}
		}

		incrI = i;
		incrJ = j;
		lettersFound = 1;

		if ((i + mot.length()) <= this.matrix.length && (j + mot.length()) <= this.matrix[0].length) { // s'il y a assez de place en diagonale basse droite
			for (int indMot = 0; indMot < mot.length(); indMot++) {
				if (this.matrix[incrI][incrJ] == mot.charAt(indMot)) {
					lettersFound++;
					this.matrixResult[incrI][incrJ] = this.matrix[incrI][incrJ];
				} else {
					break;
				}
				incrI++;
				incrJ++;
			}

			if (lettersFound == (mot.length() + 1)) {
				this.motTrouve = true;
				return;
			} else {
				for (int reset = 0; reset < lettersFound; reset++) {
					this.matrixResult[incrI - reset][incrJ - reset] = '_';
				}
			}
		}
	}
	
	public String toString() {
		String res = "";

		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				res += this.matrix[i][j] + " ";
			}
			res += "\n";
		}

		res += "\n---------------------------------------\n";

		for (int i = 0; i < this.matrixResult.length; i++) {
			for (int j = 0; j < this.matrixResult[i].length; j++) {
				res += this.matrixResult[i][j] + " ";
			}
			res += "\n";
		}
		return res;
	}

	public String toStringRes() {
		String res = "";

		for (int i = 0; i < this.matrixResult.length; i++) {
			for (int j = 0; j < this.matrixResult[0].length; j++) {
				res += this.matrixResult[i][j] + " ";
			}
			res += "\n";
		}
		return res;
	}

	public int countLetters(char c) {
		c = Character.toUpperCase(c);
		int nb = 0;
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				if (this.matrix[i][j] == c)
					nb++;
			}

		}
		return nb;
	}
	
	public int getMotTrouveRes() {
		return this.motTrouveRes;
	}
}