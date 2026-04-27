package ui;

import io.LerCSV;
import model.Player;
import tree.BinarySearchTree;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

public class RankingAppFrame extends JFrame {

    private static final Color FUNDO_CONTROLES = new Color(42, 46, 56);
    private static final Color COR_TEXTO = new Color(230, 230, 230);
    private static final Color COR_STATUS = new Color(180, 200, 220);
    private static final Color COR_BOTAO = new Color(58, 64, 78);
    private static final Color COR_BOTAO_HOVER = new Color(74, 82, 100);
    private static final Color COR_BOTAO_PRESSED = new Color(46, 52, 64);
    private static final Color COR_BOTAO_BORDA = new Color(86, 94, 112);
    private static final Color COR_CAMPO_FUNDO = new Color(28, 32, 40);
    private static final Color COR_CAMPO_BORDA = new Color(72, 80, 96);
    private static final Color COR_TITULO = new Color(150, 180, 220);

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
        this.campoNicknameInserir = criarCampo();
        this.campoRankingInserir = criarCampo();
        this.campoNicknameBusca = criarCampo();
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

        JButton botaoCarregar = criarBotao("Carregar CSV...");
        botaoCarregar.addActionListener(e -> acaoCarregar());

        JButton botaoInserir = criarBotao("Inserir jogador");
        botaoInserir.addActionListener(e -> acaoInserir());

        JButton botaoBuscar = criarBotao("Buscar por nickname");
        botaoBuscar.addActionListener(e -> acaoBuscar());

        JButton botaoRemover = criarBotao("Remover por nickname");
        botaoRemover.addActionListener(e -> acaoRemover());

        JButton botaoInOrder = criarBotao("Imprimir em ordem");
        botaoInOrder.addActionListener(e -> acaoInOrder());

        JButton botaoLimpar = criarBotao("Limpar árvore");
        botaoLimpar.addActionListener(e -> acaoLimpar());

        JButton botaoZoomIn = criarBotao("Aumentar zoom");
        botaoZoomIn.addActionListener(e -> treePanel.ajustarZoom(1.15));

        JButton botaoZoomOut = criarBotao("Diminuir zoom");
        botaoZoomOut.addActionListener(e -> treePanel.ajustarZoom(0.85));

        JButton botaoZoomReset = criarBotao("Zoom 100%");
        botaoZoomReset.addActionListener(e -> treePanel.resetarZoom());

        JButton botaoAjustar = criarBotao("Ajustar à tela");
        botaoAjustar.addActionListener(e -> treePanel.ajustarParaCaber());

        painel.add(criarTitulo("Arquivo"));
        painel.add(espaco(6));
        painel.add(botaoCarregar);
        painel.add(espaco(18));

        painel.add(criarTitulo("Inserir"));
        painel.add(espaco(6));
        painel.add(criarRotulo("Nickname:"));
        painel.add(campoNicknameInserir);
        painel.add(espaco(6));
        painel.add(criarRotulo("Ranking:"));
        painel.add(campoRankingInserir);
        painel.add(espaco(8));
        painel.add(botaoInserir);
        painel.add(espaco(18));

        painel.add(criarTitulo("Buscar / Remover"));
        painel.add(espaco(6));
        painel.add(criarRotulo("Nickname:"));
        painel.add(campoNicknameBusca);
        painel.add(espaco(8));
        painel.add(botaoBuscar);
        painel.add(espaco(4));
        painel.add(botaoRemover);
        painel.add(espaco(18));

        painel.add(criarTitulo("Ações"));
        painel.add(espaco(6));
        painel.add(botaoInOrder);
        painel.add(espaco(4));
        painel.add(botaoLimpar);
        painel.add(espaco(18));

        painel.add(criarTitulo("Navegação"));
        painel.add(espaco(6));
        painel.add(botaoZoomIn);
        painel.add(espaco(4));
        painel.add(botaoZoomOut);
        painel.add(espaco(4));
        painel.add(botaoZoomReset);
        painel.add(espaco(4));
        painel.add(botaoAjustar);
        painel.add(espaco(8));
        painel.add(criarDica("Arraste para mover."));
        painel.add(criarDica("Ctrl+Scroll para zoom."));

        return painel;
    }

    private JLabel criarDica(String texto) {
        JLabel dica = new JLabel(texto);
        dica.setForeground(new Color(140, 160, 180));
        dica.setFont(new Font("Dialog", Font.ITALIC, 11));
        dica.setAlignmentX(Component.LEFT_ALIGNMENT);
        return dica;
    }

    private JLabel criarRotulo(String texto) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setForeground(COR_TEXTO);
        rotulo.setFont(new Font("Dialog", Font.PLAIN, 12));
        rotulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return rotulo;
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(250, 30));
        campo.setPreferredSize(new Dimension(250, 30));
        campo.setBackground(COR_CAMPO_FUNDO);
        campo.setForeground(COR_TEXTO);
        campo.setCaretColor(COR_TEXTO);
        campo.setFont(new Font("Dialog", Font.PLAIN, 12));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_CAMPO_BORDA, 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return campo;
    }

    private void acaoCarregar() {
        JFileChooser chooser = new JFileChooser("projeto");
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos CSV", "csv"));
        int resultado = chooser.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File arquivo = chooser.getSelectedFile();
        try {
            int total = LerCSV.carregar(arquivo.getAbsolutePath(), arvore);
            atualizarArvore();
            javax.swing.SwingUtilities.invokeLater(treePanel::ajustarParaCaber);
            setStatus("CSV carregado: " + total + " jogadores. Altura da árvore: " + arvore.height() + ".");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O CSV possui um ranking inválido: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoInserir() {
        String nickname = campoNicknameInserir.getText().trim();
        String rankingTexto = campoRankingInserir.getText().trim();
        if (nickname.isEmpty() || rankingTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha nickname e ranking.",
                    "Dados incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ranking;
        try {
            ranking = Integer.parseInt(rankingTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ranking deve ser um número inteiro.",
                    "Ranking inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (arvore.search(nickname)) {
            JOptionPane.showMessageDialog(this, "Já existe um jogador com esse nickname.",
                    "Nickname duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (arvore.containsRanking(ranking)) {
            JOptionPane.showMessageDialog(this, "Já existe um jogador com esse ranking.",
                    "Ranking duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        arvore.insert(new Player(nickname, ranking));
        campoNicknameInserir.setText("");
        campoRankingInserir.setText("");
        atualizarArvore();
        treePanel.destacar(nickname);
        setStatus("Jogador inserido: " + nickname + " (ranking " + ranking + ").");
    }

    private void acaoBuscar() {
        String nickname = campoNicknameBusca.getText().trim();
        if (nickname.isEmpty()) {
            return;
        }
        Player encontrado = arvore.find(nickname);
        if (encontrado == null) {
            treePanel.limparDestaque();
            setStatus("Jogador \"" + nickname + "\" não encontrado.");
            JOptionPane.showMessageDialog(this, "Jogador não encontrado.",
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        treePanel.destacar(encontrado.getNickname());
        setStatus("Encontrado: " + encontrado.getNickname() + " (ranking " + encontrado.getRanking() + ").");
    }

    private void acaoRemover() {
        String nickname = campoNicknameBusca.getText().trim();
        if (nickname.isEmpty()) {
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Remover o jogador \"" + nickname + "\"?",
                "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }
        Player removido = arvore.remove(nickname);
        if (removido == null) {
            setStatus("Jogador \"" + nickname + "\" não encontrado.");
            JOptionPane.showMessageDialog(this, "Jogador não encontrado.",
                    "Remoção", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        campoNicknameBusca.setText("");
        atualizarArvore();
        setStatus("Removido: " + removido.getNickname() + " (ranking " + removido.getRanking() + ").");
    }

    private void acaoInOrder() {
        if (arvore.isEmpty()) {
            setStatus("Árvore vazia.");
            return;
        }
        System.out.println("=== Ranking em ordem crescente ===");
        arvore.inOrder();
        setStatus("Ranking impresso no console (" + arvore.size() + " jogadores).");
    }

    private void acaoLimpar() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente limpar a árvore?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }
        while (!arvore.isEmpty()) {
            arvore.remove(arvore.getRoot().getPlayer().getNickname());
        }
        atualizarArvore();
        setStatus("Árvore limpa.");
    }

    private void atualizarArvore() {
        treePanel.setRaiz(arvore.getRoot());
    }

    private void setStatus(String mensagem) {
        status.setText(mensagem);
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
        BotaoEscuro botao = new BotaoEscuro(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(250, 34));
        botao.setPreferredSize(new Dimension(250, 34));
        return botao;
    }

    protected JLabel criarTitulo(String texto) {
        JLabel titulo = new JLabel(texto.toUpperCase());
        titulo.setForeground(COR_TITULO);
        titulo.setFont(new Font("Dialog", Font.BOLD, 11));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titulo;
    }

    protected Component espaco(int altura) {
        return Box.createVerticalStrut(altura);
    }

    private static final class BotaoEscuro extends JButton {

        BotaoEscuro(String texto) {
            super(texto);
            setForeground(COR_TEXTO);
            setFont(new Font("Dialog", Font.PLAIN, 12));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setRolloverEnabled(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ButtonModel modelo = getModel();
            Color fundo = COR_BOTAO;
            if (modelo.isPressed()) {
                fundo = COR_BOTAO_PRESSED;
            } else if (modelo.isRollover()) {
                fundo = COR_BOTAO_HOVER;
            }
            int arco = 10;
            g2.setColor(fundo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);
            g2.setColor(COR_BOTAO_BORDA);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arco, arco);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
