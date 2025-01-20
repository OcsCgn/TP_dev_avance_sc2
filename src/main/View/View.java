package main.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {
	private JTextArea originalTextArea;
	private List<JTextArea> comparisonTextAreas;
	private JTextArea resultArea;
	private JButton analyzeButton;

	public View() {
		comparisonTextAreas = new ArrayList<>();

		setTitle("Analyse de Similarité de Textes");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel originalPanel = new JPanel(new BorderLayout());
		originalPanel.setBorder(BorderFactory.createTitledBorder("Texte d'origine"));
		JButton loadOriginalButton = new JButton("Charger un fichier");
		loadOriginalButton.addActionListener(e -> {
			loadText(originalTextArea);
		});
		originalPanel.add(loadOriginalButton, BorderLayout.NORTH);

		originalTextArea = new JTextArea(10, 30);
		originalPanel.add(new JScrollPane(originalTextArea), BorderLayout.CENTER);
		add(originalPanel, BorderLayout.WEST);

		JPanel comparisonPanel = new JPanel();
		comparisonPanel.setLayout(new BoxLayout(comparisonPanel, BoxLayout.Y_AXIS));
		comparisonPanel.setBorder(BorderFactory.createTitledBorder("Textes à comparer"));

		JButton addComparisonButton = new JButton("Ajouter un texte à comparer");
		addComparisonButton.addActionListener(e -> addComparisonTextArea(comparisonPanel));
		comparisonPanel.add(addComparisonButton);

		add(comparisonPanel, BorderLayout.CENTER);

		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.setBorder(BorderFactory.createTitledBorder("Résultats"));

		resultArea = new JTextArea();
		resultArea.setEditable(false);
		resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
		add(resultPanel, BorderLayout.EAST);

		analyzeButton = new JButton("Analyser");
		add(analyzeButton, BorderLayout.SOUTH);

		setVisible(true);
	}

	public void loadText(JTextArea textArea) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choisir un fichier texte");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				String text = new String(Files.readAllBytes(file.toPath()));
				textArea.setText(text);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void addComparisonTextArea(JPanel comparisonPanel) {
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		JTextArea textArea = new JTextArea(5, 20);
		JButton loadFile = new JButton("Charger un fichier");
		JButton removeButton = new JButton("Supprimer");

		// Ajouter des actions pour les boutons
		loadFile.addActionListener(e -> loadText(textArea));
		removeButton.addActionListener(e -> {
			comparisonPanel.remove(textAreaPanel); // Supprime le panneau contenant le JTextArea et les boutons
			comparisonTextAreas.remove(textArea); // Supprime le JTextArea de la liste
			comparisonPanel.revalidate();
			comparisonPanel.repaint();
		});

		// Ajouter les composants au panneau local
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loadFile);
		buttonPanel.add(removeButton);

		textAreaPanel.add(buttonPanel, BorderLayout.NORTH);
		textAreaPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

		// Ajouter le panneau local au panneau principal
		comparisonPanel.add(textAreaPanel);
		comparisonTextAreas.add(textArea); // Ajouter le JTextArea à la liste

		comparisonPanel.revalidate();
		comparisonPanel.repaint();
	}

	public String getOriginalText() {
		return originalTextArea.getText();
	}

	public List<String> getComparisonTexts() {
		List<String> texts = new ArrayList<>();
		for (JTextArea textArea : comparisonTextAreas) {
			texts.add(textArea.getText());
		}
		return texts;
	}

	public void addAnalyzeAction(ActionListener listener) {
		analyzeButton.addActionListener(listener);
	}

	public void showResults(String results) {
		resultArea.setText(results);
	}

	// méthode pour mettre les partis copiées en rouge dans le texte original
	public void highlightText(String text) {
		originalTextArea.setText(text);
	}

}