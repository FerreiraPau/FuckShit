/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartkeyboard;

import java.util.HashMap;
import java.util.Map;

public class keyboardmap {
    private static final Map<Character, char[]> adjacencies = new HashMap<>();

    static {
        // Mapeamento baseado no teclado QWERTY e exemplos do enunciado
        // Linha 1
        adjacencies.put('q', new char[]{'q', 'w', 'a'});
        adjacencies.put('w', new char[]{'w', 'q', 'e', 'a', 's'});
        adjacencies.put('e', new char[]{'e', 'w', 'r', 's', 'd'});
        adjacencies.put('r', new char[]{'r', 'e', 't', 'd', 'f'});
        adjacencies.put('t', new char[]{'t', 'r', 'y', 'f', 'g'});
        adjacencies.put('y', new char[]{'y', 't', 'u', 'g', 'h'});
        adjacencies.put('u', new char[]{'u', 'y', 'i', 'h', 'j'});
        adjacencies.put('i', new char[]{'i', 'u', 'o', 'j', 'k'});
        // Exemplo do PDF: 'o' rodeado por 'i','p','k','l' [cite: 33]
        adjacencies.put('o', new char[]{'o', 'i', 'p', 'k', 'l'});
        adjacencies.put('p', new char[]{'p', 'o', 'l'});

        // Linha 2
        // Exemplo do PDF: 'a' rodeado por 'q','w','s' [cite: 35]
        adjacencies.put('a', new char[]{'a', 'q', 'w', 's', 'z'});
        adjacencies.put('s', new char[]{'s', 'a', 'd', 'w', 'e', 'z', 'x'});
        adjacencies.put('d', new char[]{'d', 's', 'f', 'e', 'r', 'x', 'c'});
        adjacencies.put('f', new char[]{'f', 'd', 'g', 'r', 't', 'c', 'v'});
        adjacencies.put('g', new char[]{'g', 'f', 'h', 't', 'y', 'v', 'b'});
        adjacencies.put('h', new char[]{'h', 'g', 'j', 'y', 'u', 'b', 'n'});
        adjacencies.put('j', new char[]{'j', 'h', 'k', 'u', 'i', 'n', 'm'});
        adjacencies.put('k', new char[]{'k', 'j', 'l', 'i', 'o', 'm'});
        // Exemplo do PDF: 'l' rodeado por 'o','p','k' [cite: 34]
        adjacencies.put('l', new char[]{'l', 'k', 'o', 'p'});
        
        // Linha 3 (Simplificada, podes completar o resto)
        adjacencies.put('z', new char[]{'z', 'a', 's', 'x'});
        adjacencies.put('x', new char[]{'x', 'z', 'c', 's', 'd'});
        adjacencies.put('c', new char[]{'c', 'x', 'v', 'd', 'f'});
        adjacencies.put('v', new char[]{'v', 'c', 'b', 'f', 'g'});
        adjacencies.put('b', new char[]{'b', 'v', 'n', 'g', 'h'});
        adjacencies.put('n', new char[]{'n', 'b', 'm', 'h', 'j'});
        adjacencies.put('m', new char[]{'m', 'n', 'j', 'k'});
    }

    public static char[] getNeighbors(char c) {
        // Se o caracter não estiver mapeado, retorna ele próprio
        return adjacencies.getOrDefault(c, new char[]{c});
    }
}
