package ui;

import model.Node;

public class TreeLayout {

    public static final int DIAMETRO_NO = 28;
    public static final int ESPACO_VERTICAL = 40;
    public static final int MARGEM = 16;
    public static final int DESLOCAMENTO_MIN = 20;

    public static int altura(Node raiz) {
        if (raiz == null) {
            return 0;
        }
        int esquerda = altura(raiz.getLeft());
        int direita = altura(raiz.getRight());
        return 1 + Math.max(esquerda, direita);
    }

    public static int larguraPreferida(Node raiz) {
        int h = altura(raiz);
        if (h == 0) {
            return 0;
        }
        int niveisCheios = Math.min(h - 1, 8);
        int folhas = (int) Math.pow(2, niveisCheios);
        int larguraBalanceada = folhas * (DIAMETRO_NO + 8);
        int larguraDesbalanceada = h * (DIAMETRO_NO + 4) + MARGEM * 2;
        return Math.max(600, Math.max(larguraBalanceada, larguraDesbalanceada));
    }

    public static int alturaPreferida(Node raiz) {
        int h = altura(raiz);
        if (h == 0) {
            return 0;
        }
        return MARGEM * 2 + h * ESPACO_VERTICAL;
    }
}
