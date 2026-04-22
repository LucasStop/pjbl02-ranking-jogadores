package ui;

import tree.BinarySearchTree;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class RankingAppFrame extends JFrame {

    private static final Color FUNDO_CONTROLES = new Color(42, 46, 56);
    private static final Color COR_TEXTO = new Color(230, 230, 230);
    private static final Color COR_STATUS = new Color(180, 200, 220);

    private final BinarySearchTree arvore;
    private final TreePanel treePanel;
    private final JTextField campoNicknameInserir;
    private final JTextField campoRankingInserir;
    private final JTextField campoNicknameBusca;
    private final JLabel status;

    public RankingAppFrame() {
        super("Ranking de Jogadores — PBL 02");
        this.arvore = new BinarySearchTree();
        this.treePanel = new TreePanel();
        this.campoNicknameInserir = new JTextField(14);
        this.campoRankingInserir = new JTextField(14);
        this.campoNicknameBusca = new JTextField(14);
        this.status = new JLabel("Carregue o arquivo players.csv para começar.");

        setLayout(new BorderLayout());
        add(criarPainelControles(), BorderLayout.WEST);
        add(new JScrollPane(treePanel), BorderLayout.CENTER);
        add(criarBarraStatus(), BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 780);
        setLocationRelativeTo(null);
    }

    private JPanel criarPainelControles() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(FUNDO_CONTROLES);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        painel.setPreferredSize(new Dimension(280, 0));
        return painel;
    }

    private JPanel criarBarraStatus() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(FUNDO_CONTROLES);
        barra.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        status.setForeground(COR_STATUS);
        status.setFont(new Font("Dialog", Font.PLAIN, 12));
        barra.add(status, BorderLayout.CENTER);
        return barra;
    }

    protected JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(250, 34));
        botao.setFocusPainted(false);
        return botao;
    }

    protected JLabel criarTitulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setForeground(COR_TEXTO);
        titulo.setFont(new Font("Dialog", Font.BOLD, 13));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titulo;
    }

    protected Component espaco(int altura) {
        return Box.createVerticalStrut(altura);
    }
}
