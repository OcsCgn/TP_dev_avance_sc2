package TP_dev_avance_sc2.src.main.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class View {

    public static void main(String[] args) {
        // Créer la fenêtre principale
        JFrame frame = new JFrame("Dual Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Créer le premier panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        JTextArea textArea1 = new JTextArea();
        JButton openFileButton1 = new JButton("Ouvrir Fichier");
        panel1.add(new JScrollPane(textArea1), BorderLayout.CENTER);
        panel1.add(openFileButton1, BorderLayout.SOUTH);

        // Créer le panel central avec le bouton et le label invisible
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JButton centralButton = new JButton("Action");
        JLabel centralLabel = new JLabel("Texte Modifiable");
        centralLabel.setVisible(false); // Rendre le label invisible initialement

        // Ajouter le bouton et le label au centre du panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(centralButton, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Espacement entre le bouton et le label
        centerPanel.add(centralLabel, gbc);

        // Créer le conteneur de droite
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // Zone défilable pour les mini panels
        JPanel dynamicContainer = new JPanel();
        dynamicContainer.setLayout(new BoxLayout(dynamicContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(dynamicContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Bouton pour ajouter un nouveau texte
        JButton addTextButton = new JButton("Ajouter un texte");
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(addTextButton, BorderLayout.SOUTH);

        // Ajouter des actions pour le bouton d'ajout
        addTextButton.addActionListener(new ActionListener() {
            int counter = 1; // Compteur pour nommer les zones de texte

            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer un mini panel avec une zone de texte et un bouton
                JPanel miniPanel = new JPanel();
                miniPanel.setLayout(new BorderLayout());

                JLabel lblTxt = new JLabel("Texte n°" + counter);
                JTextArea textArea = new JTextArea(5, 20);
                JButton openFileButton = new JButton("Ouvrir Fichier");

                // Ajouter des actions pour ouvrir un fichier
                openFileButton.addActionListener(new FileOpenAction(textArea));

                // Ajouter les composants au mini panel
                miniPanel.add(lblTxt, BorderLayout.NORTH); // Ajouter le label en haut
                miniPanel.add(new JScrollPane(textArea), BorderLayout.CENTER); // Ajouter la zone de texte au centre
                miniPanel.add(openFileButton, BorderLayout.SOUTH); // Ajouter le bouton en bas

                // Ajouter le mini panel au conteneur dynamique
                dynamicContainer.add(miniPanel);
                dynamicContainer.revalidate(); // Mettre à jour l'affichage
                dynamicContainer.repaint();

                counter++; // Incrémenter le compteur
            }
        });

        // Utiliser des JSplitPane pour ajuster les tailles relatives
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, centerPanel);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, rightPanel);

        splitPane1.setResizeWeight(0.4); // Panel gauche prend 40% de l'espace
        splitPane2.setResizeWeight(0.8); // Panel central et droit ajustent leurs proportions

        // Ajouter le JSplitPane à la frame
        frame.add(splitPane2, BorderLayout.CENTER);

        // Ajouter les actions pour ouvrir un fichier à gauche
        openFileButton1.addActionListener(new FileOpenAction(textArea1));

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    // Classe pour gérer l'ouverture de fichiers
    static class FileOpenAction implements ActionListener {
        private final JTextArea textArea;

        public FileOpenAction(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.setText("");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        textArea.append(line + "\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier : " + ex.getMessage());
                }
            }
        }
    }
}
