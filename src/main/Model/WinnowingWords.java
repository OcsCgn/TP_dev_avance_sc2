package main.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.nio.ByteBuffer;
import java.util.*;

public class WinnowingWords {

	public static Map<Long, List<Integer>> generateFingerprints(String text, int n, int k) throws Exception {
		text = text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
		String[] words = text.split("\\s+");

		List<String> nGrams = new ArrayList<>();
		for (int i = 0; i <= words.length - n; i++) {
			StringBuilder nGram = new StringBuilder();
			for (int j = 0; j < n; j++) {
				if (j > 0)
					nGram.append(" ");
				nGram.append(words[i + j]);
			}
			nGrams.add(nGram.toString());
		}

		MessageDigest md = MessageDigest.getInstance("MD5");
		Map<Long, List<Integer>> fingerprints = new HashMap<>();
		long[] hashes = new long[nGrams.size()];
		for (int i = 0; i < nGrams.size(); i++) {
			byte[] hashBytes = md.digest(nGrams.get(i).getBytes());
			hashes[i] = ByteBuffer.wrap(hashBytes).getLong();
		}

		for (int i = 0; i <= hashes.length - k; i++) {
			long minHash = Long.MAX_VALUE;
			int minIndex = i;
			for (int j = i; j < i + k; j++) {
				if (hashes[j] <= minHash) {
					minHash = hashes[j];
					minIndex = j;
				}
			}
			fingerprints.putIfAbsent(minHash, new ArrayList<>());
			fingerprints.get(minHash).add(minIndex);
		}

		return fingerprints;
	}

	public static List<String> findIdenticalParts(String text1, String text2, int n, int k) throws Exception {
		Map<Long, List<Integer>> fingerprints1 = generateFingerprints(text1, n, k);
		Map<Long, List<Integer>> fingerprints2 = generateFingerprints(text2, n, k);

		List<String> commonParts = new ArrayList<>();
		String[] words1 = text1.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");
		String[] words2 = text2.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");

		for (Long hash : fingerprints1.keySet()) {
			if (fingerprints2.containsKey(hash)) {
				for (int pos1 : fingerprints1.get(hash)) {
					StringBuilder commonPart = new StringBuilder();
					for (int i = 0; i < n; i++) {
						if (i > 0)
							commonPart.append(" ");
						commonPart.append(words1[pos1 + i]);
					}
					commonParts.add(commonPart.toString());
				}
			}
		}

		Set<String> uniqueParts = new HashSet<>(commonParts);
		return new ArrayList<>(uniqueParts);
	}

	public static void writeFile(String fileName, String content) {
		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}