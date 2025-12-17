/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartkeyboard;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    // Mapa dos filhos: chave é o caracter, valor é o próximo nó
    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    String wordContent; // Guarda a palavra completa no nó final para facilitar

    public TrieNode() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }
}