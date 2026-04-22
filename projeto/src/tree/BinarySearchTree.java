package tree;

import model.Node;
import model.Player;

public class BinarySearchTree {

    private Node root;

    public void insert(Player j) {
        root = insert(root, j);
    }

    private Node insert(Node current, Player j) {
        if (current == null) {
            return new Node(j);
        }
        if (j.getRanking() < current.getPlayer().getRanking()) {
            current.setLeft(insert(current.getLeft(), j));
        } else if (j.getRanking() > current.getPlayer().getRanking()) {
            current.setRight(insert(current.getRight(), j));
        }
        return current;
    }

    public boolean containsRanking(int ranking) {
        return containsRanking(root, ranking);
    }

    private boolean containsRanking(Node current, int ranking) {
        if (current == null) {
            return false;
        }
        if (ranking == current.getPlayer().getRanking()) {
            return true;
        }
        if (ranking < current.getPlayer().getRanking()) {
            return containsRanking(current.getLeft(), ranking);
        }
        return containsRanking(current.getRight(), ranking);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node current) {
        if (current == null) {
            return 0;
        }
        return 1 + size(current.getLeft()) + size(current.getRight());
    }

    public int height() {
        return height(root);
    }

    private int height(Node current) {
        if (current == null) {
            return 0;
        }
        int esquerda = height(current.getLeft());
        int direita = height(current.getRight());
        return 1 + Math.max(esquerda, direita);
    }

    public Node getRoot() {
        return root;
    }
}
