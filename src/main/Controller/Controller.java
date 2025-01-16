package main.Controller;

import main.Model.TextSimilarity;
import main.View.View;

import java.util.List;

public class Controller {
    private TextSimilarity model;
    private View view;

    public Controller(TextSimilarity model, View view) {
        this.model = model;
        this.view = view;

        // Ajouter une action pour le bouton d'analyse
        view.addAnalyzeAction(e -> performAnalysis());
    }

    private void performAnalysis() {
        String originalText = view.getOriginalText();
        List<String> comparisonTexts = view.getComparisonTexts();

        if (originalText.isEmpty() || comparisonTexts.isEmpty()) {
            view.showResults("Veuillez charger un texte d'origine et au moins un texte à comparer.");
            return;
        }

        StringBuilder results = new StringBuilder();

        for (int i = 0; i < comparisonTexts.size(); i++) {
            List<String> sentencesA = model.tokenizeAndNormalize(originalText);
            List<String> sentencesB = model.tokenizeAndNormalize(comparisonTexts.get(i));
            List<String> plagiarizedBlocks = model.findPlagiarizedBlocks(sentencesA, sentencesB);

            results.append("Comparaison avec le Texte à comparer n°").append(i + 1).append(" :\n");
            if (plagiarizedBlocks.isEmpty()) {
                results.append("Pas de plagiat détecté.\n");
            } else {
                results.append("Plagiat détecté dans les blocs suivants :\n");
                plagiarizedBlocks.forEach(block -> results.append(block).append("\n"));
            }
            results.append("\n");
        }

        view.showResults(results.toString());
    }
}
