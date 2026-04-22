package ui;

import model.Node;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TreePanel extends JPanel {

    private static final Color COR_FUNDO = new Color(30, 32, 40);
    private static final Color COR_NO = new Color(96, 160, 255);
    private static final Color COR_BORDA = new Color(30, 60, 120);
    private static final Color COR_TEXTO = Color.WHITE;
    private static final Color COR_LINHA = new Color(180, 180, 180);
    private static final Color COR_DESTAQUE = new Color(255, 200, 60);

    private Node raiz;
    private String nicknameDestacado;

    public TreePanel() {
        setBackground(COR_FUNDO);
    }

    public void setRaiz(Node raiz) {
        this.raiz = raiz;
        nicknameDestacado = null;
        setPreferredSize(new Dimension(
                TreeLayout.larguraPreferida(raiz),
                TreeLayout.alturaPreferida(raiz)
        ));
        revalidate();
        repaint();
    }

    public void destacar(String nickname) {
        this.nicknameDestacado = nickname;
        repaint();
    }

    public void limparDestaque() {
        this.nicknameDestacado = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int largura = Math.max(getWidth(), TreeLayout.larguraPreferida(raiz));
        desenhar(g2, raiz, largura / 2, TreeLayout.MARGEM + TreeLayout.DIAMETRO_NO / 2, largura / 4);
    }

    private void desenhar(Graphics2D g, Node no, int x, int y, int deslocamento) {
        if (no == null) {
            return;
        }
        g.setStroke(new BasicStroke(2f));
        g.setColor(COR_LINHA);
        if (no.getLeft() != null) {
            int nx = x - deslocamento;
            int ny = y + TreeLayout.ESPACO_VERTICAL;
            g.drawLine(x, y, nx, ny);
            desenhar(g, no.getLeft(), nx, ny, Math.max(deslocamento / 2, TreeLayout.DIAMETRO_NO));
        }
        if (no.getRight() != null) {
            int nx = x + deslocamento;
            int ny = y + TreeLayout.ESPACO_VERTICAL;
            g.drawLine(x, y, nx, ny);
            desenhar(g, no.getRight(), nx, ny, Math.max(deslocamento / 2, TreeLayout.DIAMETRO_NO));
        }
        desenharNo(g, no, x, y);
    }

    private void desenharNo(Graphics2D g, Node no, int x, int y) {
        int raio = TreeLayout.DIAMETRO_NO / 2;
        boolean destacado = nicknameDestacado != null && nicknameDestacado.equals(no.getPlayer().getNickname());
        g.setColor(destacado ? COR_DESTAQUE : COR_NO);
        g.fillOval(x - raio, y - raio, TreeLayout.DIAMETRO_NO, TreeLayout.DIAMETRO_NO);
        g.setColor(COR_BORDA);
        g.setStroke(new BasicStroke(2f));
        g.drawOval(x - raio, y - raio, TreeLayout.DIAMETRO_NO, TreeLayout.DIAMETRO_NO);
        String texto = no.getPlayer().getNickname();
        desenharTextoCentralizado(g, texto, x, y);
    }

    private void desenharTextoCentralizado(Graphics2D g, String texto, int cx, int cy) {
        Font fonte = escolherFonte(g, texto);
        g.setFont(fonte);
        g.setColor(COR_TEXTO);
        FontMetrics fm = g.getFontMetrics();
        int largura = fm.stringWidth(texto);
        int altura = fm.getAscent() - fm.getDescent();
        g.drawString(texto, cx - largura / 2, cy + altura / 2);
    }

    private Font escolherFonte(Graphics2D g, String texto) {
        int tamanho = 13;
        Font fonte = new Font("Dialog", Font.BOLD, tamanho);
        FontMetrics fm = g.getFontMetrics(fonte);
        while (fm.stringWidth(texto) > TreeLayout.DIAMETRO_NO - 8 && tamanho > 8) {
            tamanho--;
            fonte = new Font("Dialog", Font.BOLD, tamanho);
            fm = g.getFontMetrics(fonte);
        }
        return fonte;
    }
}
