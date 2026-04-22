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
            String cabecalho = br.readLine();
            if (cabecalho == null) {
                return 0;
            }
            String linha;
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
        if (linha == null) {
            return null;
        }
        String limpa = linha.trim();
        if (limpa.isEmpty()) {
            return null;
        }
        String[] partes = limpa.split(",");
        if (partes.length < 2) {
            return null;
        }
        String nickname = partes[0].trim();
        String rankingTexto = partes[1].trim();
        if (nickname.isEmpty() || rankingTexto.isEmpty()) {
            return null;
        }
        try {
            int ranking = Integer.parseInt(rankingTexto);
            return new Player(nickname, ranking);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
