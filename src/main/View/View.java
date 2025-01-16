package main.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {
    private JTextArea originalTextArea; // Zone pour le texte d'origine
    private JTextField originalFileName; // Champ pour afficher le nom du fichier d'origine
    private List<JTextArea> comparisonTextAreas; // Liste des zones de texte à comparer
    private List<JTextField> comparisonFileNames; // Liste des noms de fichiers des textes à comparer
    private JPanel comparisonContainer; // Conteneur pour les textes à comparer
    private JButton addComparisonButton; // Bouton pour ajouter un texte à comparer
    private JButton analyzeButton; // Bouton pour lancer l'analyse
    private JTextArea resultArea; // Zone pour afficher les résultats

    public View() {
        comparisonTextAreas = new ArrayList<>();
        comparisonFileNames = new ArrayList<>();

        // Configuration de la fenêtre principale
        setTitle("Analyse de Similarité de Textes");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel gauche pour le texte d'origine
        JPanel originalPanel = new JPanel(new BorderLayout());
        originalPanel.setBorder(BorderFactory.createTitledBorder("Texte d'origine"));
        
        originalTextArea = new JTextArea(10, 30);
        originalFileName = new JTextField("Nom du fichier d'origine");
        originalFileName.setEditable(false);
        JButton loadOriginalButton = new JButton("Charger texte d'origine");
        
        originalPanel.add(new JScrollPane(originalTextArea), BorderLayout.CENTER);
        originalPanel.add(originalFileName, BorderLayout.NORTH);
        originalPanel.add(loadOriginalButton, BorderLayout.SOUTH);

        // Panel central pour les textes à comparer
        comparisonContainer = new JPanel();
        comparisonContainer.setLayout(new BoxLayout(comparisonContainer, BoxLayout.Y_AXIS));
        JScrollPane comparisonScrollPane = new JScrollPane(comparisonContainer);
        comparisonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Bouton pour ajouter un texte à comparer
        addComparisonButton = new JButton("Ajouter un texte à comparer");

        // Panel pour afficher les résultats
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultPanel.setBorder(BorderFactory.createTitledBorder("Résultats de l'analyse"));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Bouton pour lancer l'analyse
        analyzeButton = new JButton("Analyser les textes");

        // Ajouter les composants à la fenêtre
        add(originalPanel, BorderLayout.WEST);
        add(comparisonScrollPane, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.EAST);
        add(addComparisonButton, BorderLayout.NORTH);
        add(analyzeButton, BorderLayout.SOUTH);

        // Action par défaut : ajouter une première zone pour comparer
        addComparisonTextArea();

        // Rendre la fenêtre visible
        setVisible(true);

        // Ajouter action pour charger le texte d'origine
        loadOriginalButton.addActionListener(e -> loadFile(originalTextArea, originalFileName));
    }

    // Méthode pour ajouter une nouvelle zone de texte à comparer
    private void addComparisonTextArea() {
        JPanel comparisonPanel = new JPanel(new BorderLayout());

        JTextField fileNameField = new JTextField("Nom du fichier");
        fileNameField.setEditable(false);
        JTextArea textArea = new JTextArea(5, 20);
        JButton loadFileButton = new JButton("Charger texte à comparer");

        loadFileButton.addActionListener(e -> loadFile(textArea, fileNameField));

        comparisonPanel.add(fileNameField, BorderLayout.NORTH);
        comparisonPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        comparisonPanel.add(loadFileButton, BorderLayout.SOUTH);

        comparisonContainer.add(comparisonPanel);
        comparisonTextAreas.add(textArea);
        comparisonFileNames.add(fileNameField);

        comparisonContainer.revalidate();
        comparisonContainer.repaint();
    }

    // Méthode pour charger un fichier dans une zone de texte
    private void loadFile(JTextArea textArea, JTextField fileNameField) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                fileNameField.setText(file.getName());
                textArea.setText(new String(java.nio.file.Files.readAllBytes(file.toPath())));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier : " + ex.getMessage());
            }
        }
    }

    // Méthode pour récupérer le texte d'origine
    public String getOriginalText() {
        return originalTextArea.getText();
    }

    // Méthode pour récupérer les textes à comparer
    public List<String> getComparisonTexts() {
        List<String> texts = new ArrayList<>();
        for (JTextArea textArea : comparisonTextAreas) {
            texts.add(textArea.getText());
        }
        return texts;
    }

    // Ajouter un écouteur pour le bouton d'analyse
    public void addAnalyzeAction(ActionListener listener) {
        analyzeButton.addActionListener(listener);
    }

    // Afficher les résultats
    public void showResults(String results) {
        resultArea.setText(results);
    }
}
