package io;

import model.Player;
import tree.BinarySearchTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LerCSV {

    public static int carregar(String caminho, BinarySearchTree arvore) throws IOException {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                Player jogador = parseLinha(linha);
                if (jogador != null) {
                    arvore.insert(jogador);
                    total++;
                }
            }
        }
        return total;
    }

    private static Player parseLinha(String linha) {
        if (linha == null || linha.trim().isEmpty()) {
            return null;
        }
        String[] partes = linha.split(",");
        if (partes.length < 2) {
            return null;
        }
        String nickname = partes[0].trim();
        int ranking = Integer.parseInt(partes[1].trim());
        return new Player(nickname, ranking);
    }
}
