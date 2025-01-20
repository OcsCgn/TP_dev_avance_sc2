package main.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FaisMain {

	public static void findPlagiat(String file1, String file2) {
		String text1 = "Bonjour je m'appelle oscar et toi ?";
		String text2 = "Enchanté moi c'est oscar et toi ?";
		ArrayList<String> partieIdentique = new ArrayList<>();

		// Étape 1 : Lire les fichiers
		try {
			text1 = readFile(file1);
			text2 = readFile(file2);
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : Fichier non trouvé.");
			// return;
		}

		// Étape 2 : Nettoyer les textes et les diviser en mots
		text1 = text1.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
		String[] words1 = text1.split("\\s+");

		text2 = text2.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
		String[] words2 = text2.split("\\s+");

		// Étape 3 : Trouver les parties identiques
		for (int i = 0; i < words1.length; i++) {
			for (int j = 0; j < words2.length; j++) {
				if (words1[i].equals(words2[j])) {
					String partieIdentiqueTemp = findSuite(words1, words2, i, j);
					if (partieIdentiqueTemp.split("\\s+").length >= 20) {
						partieIdentique.add(partieIdentiqueTemp.trim());
					}
				}
			}
		}

		// Trier les éléments plagiés pour voir si certains ne sont pas qu'une partie
		// d'un autre
		for (int i = 0; i < partieIdentique.size(); i++) {
			for (int j = i + 1; j < partieIdentique.size(); j++) {
				if (partieIdentique.get(i).contains(partieIdentique.get(j))) {
					partieIdentique.remove(j);
					j--;
				} else if (partieIdentique.get(j).contains(partieIdentique.get(i))) {
					partieIdentique.remove(i);
					i--;
					break;
				}
			}
		}
		// Afficher les résultats
		System.out.println("Plagiat détecté : ");

		if (partieIdentique.isEmpty()) {
			System.out.println("Aucune partie identique trouvée.");
		} else {
			for (String s : partieIdentique) {
				System.out.println(s);
			}
		}
	}

	public static String findSuite(String[] words1, String[] words2, int i, int j) {
		StringBuilder partieIdentique = new StringBuilder();
		while (i < words1.length && j < words2.length && words1[i].equals(words2[j])) {
			partieIdentique.append(words1[i]).append(" ");
			i++;
			j++;
		}
		return partieIdentique.toString();
	}

	public static String readFile(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath));
		StringBuilder text = new StringBuilder();
		while (scanner.hasNextLine()) {
			text.append(scanner.nextLine()).append("\n");
		}
		scanner.close();
		return text.toString();
	}

	public static void main(String[] args) {
		// Exemple d'utilisation avec des chemins valides
		String file1 = "src/main/Model/La_France_contre_les_robots_Texte_entier.txt"; // Remplacez par un chemin valide
		String file2 = "src/main/Model/Les_Pierres_de_Venise_Texte_entier.txt"; // Remplacez par un chemin valide

		findPlagiat(file1, file2);
	}
}
