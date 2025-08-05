package MarkovChain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MarkovUI {
    private JFrame frame;
    private JButton uploadButton;
    private JButton generateButton;
    private JTextField seedField;
    private JTextArea outputArea;
    private JLabel statusLabel;
    private String fileName;

    private MarkovChain chain; // built after upload

    public MarkovUI() {
        frame = new JFrame("Markov Chain Text Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        /* NORTH PANEL — file upload and status */
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadButton = new JButton("Upload Reference");
        uploadButton.addActionListener(new UploadListener());
        statusLabel = new JLabel("No reference loaded");
        north.add(uploadButton);
        north.add(statusLabel);

        /* CENTER PANEL — seed input */
        JPanel center = new JPanel(new BorderLayout());
        seedField = new JTextField();
        seedField.setFont(seedField.getFont().deriveFont(Font.PLAIN, 16f));
        seedField.setHorizontalAlignment(JTextField.CENTER);
        seedField.setEnabled(false);
        center.add(seedField, BorderLayout.CENTER);

        generateButton = new JButton("Generate");
        generateButton.setEnabled(false);
        generateButton.addActionListener(new GenerateListener());
        center.add(generateButton, BorderLayout.EAST);

        /* SOUTH PANEL — output area */
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(outputArea);

        frame.add(north, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(scroll, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /** Handles uploading the reference corpus and building the Markov chain. */
    private class UploadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                fileName = file.getName();
                statusLabel.setText("Loading reference …     (this may take a while for large files)");
                uploadButton.setEnabled(false);

                /* Build chain in a background thread so UI stays responsive. */
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        chain = new MarkovChain();
//                        chain.buildFromFile(file.getAbsolutePath());
//                        long startTime = System.currentTimeMillis();

                        MarkovChain.addWordsFromFile(chain, file.getAbsolutePath());
//                        long elapsedMillis = System.currentTimeMillis() - startTime;
//                        long elapsedSeconds = elapsedMillis / 1000; // convert to seconds
//                        System.out.println(elapsedSeconds + " seconds");
//                        chain.updateProbabilities();
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            get(); // rethrow exceptions, if any
                            statusLabel.setText("Reference loaded: " + fileName);
                            seedField.setEnabled(true);
                            generateButton.setEnabled(true);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame,
                                    "Could not load reference: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            statusLabel.setText("No reference loaded");
                        } finally {
                            uploadButton.setEnabled(true);
                        }
                    }
                };
                worker.execute();
            }
        }
    }

    /** Handles generating text once the user provides a seed. */
    private class GenerateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (chain == null) return; // safeguard
            String seed = seedField.getText().trim();
            System.out.println(seed);
            if (seed.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a seed word first.",
                        "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            statusLabel.setText("Generating …");
            generateButton.setEnabled(false);

            SwingWorker<String, Void> generator = new SwingWorker<>() {
                @Override
                protected String doInBackground() throws Exception {
//                    chain.generate(seed, 100);
                    return chain.generateString(seed, 100);
                }

                @Override
                protected void done() {
                    try {
                        String text = get();
                        outputArea.setText(text);
                        statusLabel.setText("Generation complete. Reference: " + fileName);
                    } catch (Exception ex) {
                        if (ex.getCause() instanceof NonExistingNode) {
                            JOptionPane.showMessageDialog(frame,
                                    "Seed word not found in reference.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Generation failed: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        statusLabel.setText("Ready.  Reference: " + fileName);
                    } finally {
                        generateButton.setEnabled(true);
                    }
                }
            };
            generator.execute();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarkovUI::new);
    }
}
