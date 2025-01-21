package main.Controller;

public class TextModifier {
	public static void main(String[] args) {
		// Texte original
		String originalText = "Salut je m'appelle Oscar et j'adore les pates";

		// Partie à entourer de parenthèses
		String partToHighlight = "Oscar et j'adore";

		// Vérification si la partie existe dans le texte original
		if (originalText.contains(partToHighlight)) {
			// Remplacer la partie par la version entourée de parenthèses
			String modifiedText = originalText.replace(partToHighlight, "(" + partToHighlight + ")");

			// Afficher le texte modifié
			System.out.println("Texte modifié : " + modifiedText);
		} else {
			System.out.println("La partie spécifiée n'existe pas dans le texte original.");
		}
	}
}