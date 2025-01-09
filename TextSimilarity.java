
import java.util.*;
import java.util.stream.Collectors;

public class TextSimilarity {

	// Méthode principale
	public static void main(String[] args) {
		String textA = "Le soleil se levait à l’horizon, peignant le ciel de teintes roses et orangées. Au loin, la mer scintillait, reflétant la lumière douce du matin.\n" + //
						"Plus tard dans la journée, des nuages se formèrent lentement, annonçant une pluie légère. Les gouttes dansaient sur les feuilles des arbres, produisant une douce mélodie. La nature, dans toute sa splendeur, offrait un spectacle apaisant et renouvelé.";
		String textB = "Le soleil se levait à l’horizon, peignant le ciel de teintes roses et orangées. Les oiseaux commençaient à chanter, célébrant l’aube d’une nouvelle journée. Au loin, la mer scintillait, reflétant la lumière douce du matin. Les vagues caressaient doucement le sable, laissant une empreinte éphémère de leur passage. C’était un moment paisible, où la nature semblait murmurer des promesses de sérénité.";
		// Prétraitement et découpage en phrases
		List<String> sentencesA = tokenizeAndNormalize(textA);
		List<String> sentencesB = tokenizeAndNormalize(textB);

		// Calcul du TF-IDF et extraction des blocs similaires
		List<String> plagiarizedBlocks = findPlagiarizedBlocks(sentencesA, sentencesB);

		// Affichage des résultats
		if (plagiarizedBlocks.isEmpty()) {
			System.out.println("Pas de plagiat détecté.");
		} else {
			System.out.println("Plagiat détecté dans les blocs suivants :");
			plagiarizedBlocks.forEach(System.out::println);
		}
	}

	// Méthode pour normaliser et tokeniser un texte en phrases
	public static List<String> tokenizeAndNormalize(String text) {
		// Découper le texte en phrases
		return Arrays.stream(text.split("\\."))
				.map(sentence -> sentence.trim().toLowerCase().replaceAll("[^a-zàâçéèêëîïôûùüÿñæœ]", " "))
				.filter(sentence -> sentence.length() > 50)
				.collect(Collectors.toList());
	}

	// Méthode pour trouver les blocs plagiés en comparant les phrases
	public static List<String> findPlagiarizedBlocks(List<String> sentencesA, List<String> sentencesB) {
		List<String> plagiarizedBlocks = new ArrayList<>();

		// Comparer chaque phrase de A avec chaque phrase de B
		for (String sentenceA : sentencesA) {
			for (String sentenceB : sentencesB) {
				double similarity = cosineSimilarity(calculateTFIDF(sentenceA), calculateTFIDF(sentenceB));
				if (similarity > 0.8) { // Seuil de 80% pour détecter un plagiat
					plagiarizedBlocks.add("Plagié : " + sentenceA + " ===> " + sentenceB);
				}
			}
		}

		return plagiarizedBlocks;
	}

	// Méthode pour calculer le TF-IDF d'une phrase
	public static Map<String, Double> calculateTFIDF(String sentence) {
		List<String> tokens = tokenizeAndNormalize(sentence);
		Set<String> uniqueTokens = new HashSet<>(tokens);
		Map<String, Double> tfidf = new HashMap<>();

		for (String token : uniqueTokens) {
			double tf = calculateTF(token, tokens);
			double idf = calculateIDF(token);
			tfidf.put(token, tf * idf);
		}

		return tfidf;
	}

	// Calcul du TF (Term Frequency)
	public static double calculateTF(String token, List<String> tokens) {
		long count = tokens.stream().filter(t -> t.equals(token)).count();
		return (double) count / tokens.size();
	}

	// Calcul du IDF (Inverse Document Frequency)
	public static double calculateIDF(String token) {
		// La base de calcul de l'IDF pourrait être un corpus complet d'exemples, ici
		// simplifiée
		return Math.log(2.0); // IDF simplifié
	}

	// Méthode pour calculer la similarité cosinus
	public static double cosineSimilarity(Map<String, Double> tfidfA, Map<String, Double> tfidfB) {
		Set<String> allTokens = new HashSet<>();
		allTokens.addAll(tfidfA.keySet());
		allTokens.addAll(tfidfB.keySet());

		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (String token : allTokens) {
			double valA = tfidfA.getOrDefault(token, 0.0);
			double valB = tfidfB.getOrDefault(token, 0.0);

			dotProduct += valA * valB;
			normA += valA * valA;
			normB += valB * valB;
		}

		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
}
