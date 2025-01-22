package main.Controller;

import main.Model.FaisMain;
import main.View.View;

import java.util.List;

public class Controller {
	private final View view;

	public Controller(View view) {
		this.view = view;
		this.view.addAnalyzeAction(e -> performAnalysis());
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
			try {
				List<String> identicalParts = FaisMain.findPlagiat(originalText, comparisonTexts.get(i));
				results.append("Comparaison avec le Texte ").append(i + 1).append(" :\n\n");
				if (identicalParts.isEmpty()) {
					results.append("Pas de plagiat détecté.\n");
				} else {
					results.append("Plagiat détecté dans les parties suivantes :\n\n");
					for (String part : identicalParts) {
						results.append(part).append("\n");
					}
				}
				results.append("\n\n\n");
			} catch (Exception e) {
				results.append("Erreur lors de l'analyse du Texte ").append(i + 1).append(".\n");
				e.printStackTrace();
			}
		}
		view.showResults(results.toString());
	}

}
