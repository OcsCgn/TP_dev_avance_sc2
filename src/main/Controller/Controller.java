package main.Controller;

import main.Model.WinnowingWords;
import main.View.View;

import java.util.List;

public class Controller {
	private View view;

	public Controller(View view) {
		this.view = view;
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
			try {
				List<String> identicalParts = WinnowingWords.findIdenticalParts(originalText, comparisonTexts.get(i), 5,
						4);

				results.append("Comparaison avec le Texte ").append(i + 1).append(" :\n");
				if (identicalParts.isEmpty()) {
					results.append("Pas de plagiat détecté.\n");
				} else {
					results.append("Plagiat détecté dans les parties suivantes :\n");
					for (String part : identicalParts) {
						results.append(part).append("\n");
						originalText = originalText.replace(part,
								"<span style='color:red; font-weight:bold;'>" + part + "</span>");
					}
				}
				results.append("\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		view.showResults(results.toString());
		WinnowingWords.writeFile("OriginalTextHighlighted.html", originalText);
	}
}
