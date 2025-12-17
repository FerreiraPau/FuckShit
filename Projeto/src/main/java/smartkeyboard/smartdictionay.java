/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartkeyboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class smartdictionay {
    private final TrieNode root;

    public smartdictionay() {
        root = new TrieNode();
    }

    // Insere palavras do dicionário na Trie
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toLowerCase().toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
        current.wordContent = word;
    }

    // Carrega o ficheiro de texto (requisito do PDF [cite: 40])
    public void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove espaços e pontuação básica
                String word = line.trim().replaceAll("[^a-zA-Z]", ""); 
                if (!word.isEmpty()) insert(word);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler dicionário: " + e.getMessage());
        }
    }

    // O Algoritmo Principal: Recebe "ola" e procura caminhos vizinhos
    public List<String> getSuggestions(String input) {
        List<String> results = new ArrayList<>();
        if (input == null || input.isEmpty()) return results;
        
        searchRecursive(root, input.toLowerCase(), 0, results);
        return results;
    }

    // Algoritmo recursivo que navega na Trie considerando vizinhos
    private void searchRecursive(TrieNode current, String target, int index, List<String> results) {
        // Limite de sugestões para não travar a GUI
        if (results.size() >= 20) return;

        // Caso Base 1: Processámos todo o input do utilizador
        if (index == target.length()) {
            // Agora recolhemos todas as palavras válidas que descendem deste ponto
            collectWords(current, results);
            return;
        }

        char typedChar = target.charAt(index);
        // Obtemos a tecla carregada E os seus vizinhos 
        char[] possibleChars = keyboardmap.getNeighbors(typedChar);

        for (char c : possibleChars) {
            // Se existe um caminho na árvore para esta letra vizinha...
            if (current.children.containsKey(c)) {
                // ... continuamos a descer na árvore para o próximo indice
                searchRecursive(current.children.get(c), target, index + 1, results);
            }
        }
    }

    // Recolhe palavras completas a partir de um nó (DFS simples)
    private void collectWords(TrieNode node, List<String> results) {
        if (results.size() >= 20) return;
        
        if (node.isEndOfWord) {
            results.add(node.wordContent);
        }
        
        for (TrieNode child : node.children.values()) {
            collectWords(child, results);
        }
    }
}
