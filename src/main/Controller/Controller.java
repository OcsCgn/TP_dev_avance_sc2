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

	/**
	 * Effectue l'analyse de plagiat en utilisant les textes fournis par la vue.
	 */
	private void performAnalysis() {
		String originalText = view.getOriginalText();
		List<String> comparisonTexts = view.getComparisonTexts();

		// Vérifier si les textes sont valides
		if (originalText.isEmpty() || comparisonTexts.isEmpty()) {
			view.showResults("Veuillez charger un texte d'origine et au moins un texte à comparer.");
			return;
		}

		StringBuilder results = new StringBuilder();

		for (int i = 0; i < comparisonTexts.size(); i++) {
			try {
				// Analyse du texte comparé
				List<String> identicalParts = FaisMain.findPlagiat(originalText, comparisonTexts.get(i));

				results.append("Comparaison avec le Texte ").append(i + 1).append(" :\n");
				if (identicalParts.isEmpty()) {
					results.append("Pas de plagiat détecté.\n");
				} else {
					results.append("Plagiat détecté dans les parties suivantes :\n");
					for (String part : identicalParts) {
						results.append(part).append("\n");

						// Ajouter un style au texte d'origine
						originalText = originalText.replace(part,
								"<span style='color:red; font-weight:bold;'>" + part + "</span>");
					}
				}
				results.append("\n");
			} catch (Exception e) {
				results.append("Erreur lors de l'analyse du Texte ").append(i + 1).append(".\n");
				e.printStackTrace();
			}
		}

		// Afficher les résultats
		view.showResults(results.toString());
	}
}
