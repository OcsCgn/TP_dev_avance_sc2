package TP_dev_avance_sc2.src.main.Model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PlagiarismChecker {

    // Method to check if a block of text is found in another text
    public boolean isPlagiarized(String[] block, String[] document) {
        // Convert document to a single string for easier matching
        StringBuilder documentBuilder = new StringBuilder();
        for (String word : document) {
            documentBuilder.append(word).append(" ");
        }
        String documentText = documentBuilder.toString().trim();

        // Convert block of text to a single string
        StringBuilder blockBuilder = new StringBuilder();
        for (String word : block) {
            blockBuilder.append(word).append(" ");
        }
        String blockText = blockBuilder.toString().trim();

        // Check if blockText is a substring of documentText
        return documentText.contains(blockText);
    }
    public String findPlagiarizedBlock(String[] block, String[] document) {
        // Convert document to a single string for easier matching
        StringBuilder documentBuilder = new StringBuilder();
        for (String word : document) {
            documentBuilder.append(word).append(" ");
        }
        String documentText = documentBuilder.toString().trim();

        // Convert block of text to a single string
        StringBuilder blockBuilder = new StringBuilder();
        for (String word : block) {
            blockBuilder.append(word).append(" ");
        }
        String blockText = blockBuilder.toString().trim();

        // Check if blockText is a substring of documentText
        return documentText.contains(blockText) ? blockText : null;
    }

    // Method to check for plagiarism with a minimum block size
    public String checkPlagiarism(String[] text1, String[] text2, int minBlockSize) {
        for (int i = 0; i <= text1.length - minBlockSize; i++) {
            String[] block = Arrays.copyOfRange(text1, i, i + minBlockSize);
            String plagiarizedBlock = findPlagiarizedBlock(block, text2);
            if (plagiarizedBlock != null) {
                return plagiarizedBlock;
            }
        }
        return null;
    }

    public static String[] readTextFromFile(String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));
        return content.split("\\s+");
    }

    public static void main(String[] args) {
        try {
            PlagiarismChecker checker = new PlagiarismChecker();

            // Import texts from files
            String[] text1 = readTextFromFile("/home/etudiant/co221148/TP/s6/r6.05_dev_avance/TP_dev_avance_sc2/src/main/Model/La_France_contre_les_robots_Texte_entier.txt");
            String[] text2 = readTextFromFile("../Ressources/Les_Pierres_de_venise_Texte_entier.txt");

            // Check for plagiarism with a minimum block size of 10 words
            String plagiarizedBlock = checker.checkPlagiarism(text1, text2, 10);
            if (plagiarizedBlock != null) {
                System.out.println("Plagiarized block found: " + plagiarizedBlock);
            } else {
                System.out.println("No plagiarism detected.");
            }

        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }   
}
