/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package smartkeyboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class mainframe extends JFrame {

    // --- CORES & ESTILO ---
    private final Color COLOR_BG = new Color(240, 242, 245);
    private final Color COLOR_PRIMARY = new Color(0, 122, 255);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT = new Color(51, 51, 51);
    
    private JTextField inputField;
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;
    private smartdictionay dictionary; 
    private JPanel keyboardPanel;

    public mainframe() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        dictionary = new smartdictionay();
        try {
            dictionary.loadFromFile("dicionario.txt");
        } catch (Exception e) {
            System.out.println("Erro ao ler ficheiro: " + e.getMessage());
        }

        setTitle("Smart Keyboard");
        setSize(480, 750); // Um pouco mais largo para caber pontuação
        setMinimumSize(new Dimension(350, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG);

        // --- 1. TOPO: BARRA DE TÍTULO E INPUT ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_PRIMARY);
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Smart Prediction");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        inputField.setForeground(COLOR_TEXT);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        topPanel.add(inputField, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        // --- 2. CENTRO: LISTA DE SUGESTÕES ---
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        suggestionList.setFixedCellHeight(40);
        suggestionList.setSelectionBackground(new Color(220, 230, 255));
        suggestionList.setSelectionForeground(COLOR_PRIMARY);
        
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        scrollPane.getViewport().setBackground(COLOR_WHITE);
        
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. FUNDO: TECLADO INTERATIVO ---
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(new Color(225, 227, 230));
        bottomContainer.setBorder(new EmptyBorder(10, 5, 10, 5));

        // Layout de grelha para as linhas do teclado
        keyboardPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 Linhas agora
        keyboardPanel.setOpaque(false);

        // Adicionar linhas de teclas (COM AÇÕES)
        keyboardPanel.add(createKeyboardRow("Q W E R T Y U I O P"));
        keyboardPanel.add(createKeyboardRow("A S D F G H J K L Ç")); // Adicionei Ç
        keyboardPanel.add(createKeyboardRow("Z X C V B N M , . ; !")); // Adicionei pontuação
        
        // Linha especial para Espaço e Backspace
        keyboardPanel.add(createSpecialRow());

        bottomContainer.add(keyboardPanel, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);

        // --- LISTENERS (Físico + Lógica) ---
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateSuggestions();
            }
        });

        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectSuggestion();
                }
            }
        });
    }

    // --- CRIAÇÃO DOS BOTÕES DO TECLADO ---
    private JPanel createKeyboardRow(String keys) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        row.setOpaque(false);
        
        for (String key : keys.split(" ")) {
            JButton keyButton = new JButton(key);
            keyButton.setPreferredSize(new Dimension(40, 45)); // Tamanho do botão
            keyButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            keyButton.setBackground(Color.WHITE);
            keyButton.setForeground(COLOR_TEXT);
            keyButton.setFocusable(false); // Importante: não roubar o foco do teclado físico
            
            // Estilo "Flat" moderno
            keyButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            // AÇÃO AO CLICAR
            keyButton.addActionListener(e -> {
                inputField.setText(inputField.getText() + key);
                updateSuggestions(); // Atualiza sugestões em tempo real
            });

            row.add(keyButton);
        }
        return row;
    }

    // --- CRIAÇÃO DA LINHA ESPECIAL (Espaço e Apagar) ---
    private JPanel createSpecialRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        row.setOpaque(false);

        // Botão Espaço
        JButton spaceBtn = new JButton("ESPAÇO");
        spaceBtn.setPreferredSize(new Dimension(200, 45));
        spaceBtn.setBackground(Color.WHITE);
        spaceBtn.setFocusable(false);
        spaceBtn.addActionListener(e -> {
            inputField.setText(inputField.getText() + " ");
            updateSuggestions();
        });

        // Botão Apagar (Backspace)
        JButton backspaceBtn = new JButton("⌫"); // Símbolo de apagar
        backspaceBtn.setPreferredSize(new Dimension(80, 45));
        backspaceBtn.setBackground(new Color(255, 230, 230)); // Levemente vermelho
        backspaceBtn.setFocusable(false);
        backspaceBtn.addActionListener(e -> {
            String text = inputField.getText();
            if (text.length() > 0) {
                inputField.setText(text.substring(0, text.length() - 1));
                updateSuggestions();
            }
        });

        row.add(spaceBtn);
        row.add(backspaceBtn);
        return row;
    }

    // --- LÓGICA DE SUGESTÃO E SELEÇÃO ---
    private void selectSuggestion() {
        String selectedWord = suggestionList.getSelectedValue();
        if (selectedWord == null) return;

        String currentText = inputField.getText();
        int lastSpaceIndex = currentText.lastIndexOf(" ");
        
        String newText;
        if (lastSpaceIndex == -1) {
            newText = selectedWord + " ";
        } else {
            String prefix = currentText.substring(0, lastSpaceIndex + 1);
            newText = prefix + selectedWord + " ";
        }

        inputField.setText(newText);
        listModel.clear(); 
        inputField.requestFocus(); 
    }

    private void updateSuggestions() {
        String text = inputField.getText();

        if (text.trim().isEmpty() || text.endsWith(" ")) {
            listModel.clear();
            return;
        }

        String[] words = text.split(" ");
        if (words.length == 0) return;
        String currentWord = words[words.length - 1];

        List<String> suggestions = dictionary.getSuggestions(currentWord);
        
        listModel.clear();
        for (String s : suggestions) {
            listModel.addElement(s);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new mainframe().setVisible(true);
        });
    }
}