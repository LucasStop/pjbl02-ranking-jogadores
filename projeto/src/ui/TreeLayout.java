package ui;

import model.Node;

public class TreeLayout {

    public static final int DIAMETRO_NO = 70;
    public static final int ESPACO_VERTICAL = 110;
    public static final int MARGEM = 60;

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
        int folhas = (int) Math.pow(2, h - 1);
        return Math.max(800, folhas * (DIAMETRO_NO + 20) + MARGEM * 2);
    }

    public static int alturaPreferida(Node raiz) {
        int h = altura(raiz);
        if (h == 0) {
            return 0;
        }
        return MARGEM * 2 + h * ESPACO_VERTICAL;
    }
}
