package main.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {
	private JTextPane originalTextPane;
	private List<JTextPane> comparisonTextPanes;
	private JTextPane resultPane;
	private JButton analyzeButton;
	private String lastOpenedFilePath;

	public View() {
		comparisonTextPanes = new ArrayList<>();

		setTitle("Analyse de Similarité de Textes");
		setSize(1800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new GridLayout(1, 3));
		JPanel originalPanel = new JPanel(new BorderLayout());
		originalPanel.setBorder(BorderFactory.createTitledBorder("Texte d'origine"));

		JButton loadOriginalButton = new JButton("Charger un fichier");
		loadOriginalButton.addActionListener(e -> loadText(originalTextPane));
		originalPanel.add(loadOriginalButton, BorderLayout.NORTH);

		originalTextPane = createTextPane();
		originalPanel.add(new JScrollPane(originalTextPane), BorderLayout.CENTER);
		mainPanel.add(originalPanel);

		JPanel comparisonPanel = new JPanel();
		comparisonPanel.setLayout(new BoxLayout(comparisonPanel, BoxLayout.Y_AXIS));
		comparisonPanel.setBorder(BorderFactory.createTitledBorder("Textes à comparer"));
		JButton addComparisonButton = new JButton("Ajouter un texte à comparer");
		addComparisonButton.addActionListener(e -> addComparisonTextPane(comparisonPanel));
		comparisonPanel.add(addComparisonButton);
		mainPanel.add(comparisonPanel);

		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.setBorder(BorderFactory.createTitledBorder("Résultats"));

		resultPane = createTextPane();
		resultPane.setEditable(false);
		resultPanel.add(new JScrollPane(resultPane), BorderLayout.CENTER);
		mainPanel.add(resultPanel);
		add(mainPanel, BorderLayout.CENTER);

		analyzeButton = new JButton("Analyser");
		JPanel analyzePanel = new JPanel();
		analyzePanel.add(analyzeButton);
		add(analyzePanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	public void loadText(JTextPane textPane) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choisir un fichier texte");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (lastOpenedFilePath != null) {
			fileChooser.setCurrentDirectory(new File(lastOpenedFilePath).getParentFile());
		}

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			lastOpenedFilePath = file.getAbsolutePath();

			try {
				String text = new String(Files.readAllBytes(file.toPath()));
				setTextWithJustification(textPane, text);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void addComparisonTextPane(JPanel comparisonPanel) {
		JPanel textPanePanel = new JPanel(new BorderLayout());
		JTextPane textPane = createTextPane();
		JButton loadFile = new JButton("Charger un fichier");
		JButton removeButton = new JButton("Supprimer");

		loadFile.addActionListener(e -> loadText(textPane));
		removeButton.addActionListener(e -> {
			comparisonPanel.remove(textPanePanel);
			comparisonTextPanes.remove(textPane);
			comparisonPanel.revalidate();
			comparisonPanel.repaint();
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loadFile);
		buttonPanel.add(removeButton);

		textPanePanel.add(buttonPanel, BorderLayout.NORTH);
		textPanePanel.add(new JScrollPane(textPane), BorderLayout.CENTER);

		comparisonPanel.add(textPanePanel);
		comparisonTextPanes.add(textPane);

		comparisonPanel.revalidate();
		comparisonPanel.repaint();
	}

	private JTextPane createTextPane() {
		JTextPane textPane = new JTextPane();
		textPane.setMargin(new Insets(10, 10, 10, 10));
		textPane.setContentType("text/html");
		return textPane;
	}

	private void setTextWithJustification(JTextPane textPane, String htmlContent) {
		textPane.setText("<html><body style='text-align: justify; font-size: 12px;'>"
				+ htmlContent + "</body></html>");
	}

	public String getOriginalText() {
		return originalTextPane.getText();
	}

	public void setOrignalText(String text) {
		originalTextPane.setText(text);
	}

	public List<String> getComparisonTexts() {
		List<String> texts = new ArrayList<>();
		for (JTextPane textPane : comparisonTextPanes) {
			texts.add(textPane.getText());
		}
		// System.out.println("texts: " + texts);
		return texts;
	}

	public void addAnalyzeAction(ActionListener listener) {
		analyzeButton.addActionListener(listener);
	}

	public void showResults(String results) {
		setTextWithJustification(resultPane, results);
	}

	public void highlightText(String text) {
		setTextWithJustification(originalTextPane, text);
	}

	public void setOriginalText(String originalText) {
		setTextWithJustification(originalTextPane, originalText);
	}
}
