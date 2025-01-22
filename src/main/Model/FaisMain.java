package main.Model;

import java.io.*;
import java.util.*;

public class FaisMain {

	public static List<String> findPlagiat(String text1, String text2) {

		List<String> partieIdentique = new ArrayList<>();
		String[] words1 = text1.split("\\s+");
		String[] words2 = text2.split("\\s+");
		Set<String> wordsSet2 = new HashSet<>(Arrays.asList(words2));

		for (int i = 0; i < words1.length; i++) {
			if (wordsSet2.contains(words1[i])) {
				String partieIdentiqueTemp = findSuite(words1, words2, i);
				if (partieIdentiqueTemp.split("\\s+").length >= 5) {
					partieIdentique.add(partieIdentiqueTemp.trim());
					i += partieIdentiqueTemp.split("\\s+").length - 1;
				}
			}
		}
		return partieIdentique;
	}

	private static String findSuite(String[] words1, String[] words2, int i) {
		StringBuilder partieIdentique = new StringBuilder();
		int j = Arrays.asList(words2).indexOf(words1[i]);
		while (i < words1.length && j < words2.length && words1[i].equals(words2[j])) {
			partieIdentique.append(words1[i]).append(" ");
			i++;
			j++;
		}
		return partieIdentique.toString();
	}

	public static String readFile(String filePath) throws IOException {
		StringBuilder text = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				text.append(line).append("\n");
			}
		}
		return text.toString();
	}
}
