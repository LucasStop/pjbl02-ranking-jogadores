import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TreeVisualizer extends Application {

    // Classe Node para a árvore binária de busca
    static class Node {
        // Adicionar propriedades do Node
    }

    // Classe da árvore binária de busca
    static class MorseBST {
        private Node root;

        public MorseBST() {
            // Implementar inicializações necessárias
        }

        public void insert(char letter, String morseCode) {
            // Inserir lógica de inserção: ponto (.) para a esquerda e traço (-) para a direita
        }

        // Adicione métodos para codificar e decodificar código morse

        // Calcula a altura da árvore
        public int getHeight() {
            return getHeight(root);
        }

        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        public void drawTree(Canvas canvas) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);

            // Começa o desenho da árvore na raiz
            drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4, 1);
        }

        private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOffset, int level) {
            if (node == null) {
                return;
            }

            // Desenha um círculo ao redor do nó
            gc.setStroke(Color.BLACK);
            gc.strokeOval(x - 15, y - 15, 30, 30); // Desenha o círculo com raio 15

            // Desenha a letra dentro do círculo
            gc.strokeText(String.valueOf(node.letter == ' ' ? ' ' : node.letter), x - 5, y + 5);

            // Desenho das linhas para os nós filhos
            if (node.left != null) {
                double newX = x - xOffset;
                double newY = y + 120; // Aumentei o espaçamento vertical
                gc.strokeLine(x, y + 15, newX, newY - 15); // Linha entre o nó atual e o filho à esquerda
                drawNode(gc, node.left, newX, newY, xOffset / 2, level + 1);
            }

            if (node.right != null) {
                double newX = x + xOffset;
                double newY = y + 120; // Aumentei o espaçamento vertical
                gc.strokeLine(x, y + 15, newX, newY - 15); // Linha entre o nó atual e o filho à direita
                drawNode(gc, node.right, newX, newY, xOffset / 2, level + 1);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visualizador de Árvore Binária");

        MorseBST bst = new MorseBST();

        // Adicione o alfabeto com seus códigos Morse


        // Inicialização de Janela
        int height = bst.getHeight();
        int canvasHeight = 100 + height * 100;
        int canvasWidth = (int) Math.pow(2, height) * 40;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        bst.drawTree(canvas);

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, canvasWidth, canvasHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
