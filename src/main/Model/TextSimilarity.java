package main.Model;
import java.util.*;
import java.util.stream.Collectors;

public class TextSimilarity {

    // Méthode pour normaliser et découper un texte en phrases
    public List<String> tokenizeAndNormalize(String text) {
        return Arrays.stream(text.split("\\."))
                .map(sentence -> sentence.trim().toLowerCase().replaceAll("[^a-zàâçéèêëîïôûùüÿñæœ]", " "))
                .filter(sentence -> sentence.length() > 50)
                .collect(Collectors.toList());
    }

    // Méthode pour détecter les blocs plagiés
    public List<String> findPlagiarizedBlocks(List<String> sentencesA, List<String> sentencesB) {
        List<String> plagiarizedBlocks = new ArrayList<>();

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

    // Calcul du TF-IDF pour une phrase
    public Map<String, Double> calculateTFIDF(String sentence) {
        List<String> tokens = Arrays.asList(sentence.split("\\s+"));
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
    public double calculateTF(String token, List<String> tokens) {
        long count = tokens.stream().filter(t -> t.equals(token)).count();
        return (double) count / tokens.size();
    }

    // Calcul du IDF (Inverse Document Frequency)
    public double calculateIDF(String token) {
        return Math.log(2.0); // IDF simplifié pour un corpus de deux documents
    }

    // Calcul de la similarité cosinus
    public double cosineSimilarity(Map<String, Double> tfidfA, Map<String, Double> tfidfB) {
        Set<String> allTokens = new HashSet<>(tfidfA.keySet());
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
