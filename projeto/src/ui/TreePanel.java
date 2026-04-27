package ui;

import model.Node;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class TreePanel extends JPanel {

    private static final Color COR_FUNDO = new Color(30, 32, 40);
    private static final Color COR_NO = new Color(96, 160, 255);
    private static final Color COR_BORDA = new Color(30, 60, 120);
    private static final Color COR_TEXTO = Color.WHITE;
    private static final Color COR_LINHA = new Color(180, 180, 180);
    private static final Color COR_DESTAQUE = new Color(255, 200, 60);
    private static final Color COR_NOME = new Color(220, 228, 240);
    private static final Color COR_NOME_DESTAQUE = new Color(255, 220, 120);
    private static final double ZOOM_MIN = 0.1;
    private static final double ZOOM_MAX = 3.0;
    private static final double LIMIAR_TEXTO = 14.0;

    private Node raiz;
    private String nicknameDestacado;
    private double zoom = 1.0;
    private Point pontoArraste;

    public TreePanel() {
        setBackground(COR_FUNDO);
        configurarInteracao();
        ToolTipManager.sharedInstance().registerComponent(this);
    }

    public void setRaiz(Node raiz) {
        this.raiz = raiz;
        nicknameDestacado = null;
        atualizarDimensoes();
    }

    public void destacar(String nickname) {
        this.nicknameDestacado = nickname;
        repaint();
        scrollPara(nickname);
    }

    public void limparDestaque() {
        this.nicknameDestacado = null;
        repaint();
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double novoZoom) {
        zoom = Math.max(ZOOM_MIN, Math.min(ZOOM_MAX, novoZoom));
        atualizarDimensoes();
    }

    public void ajustarZoom(double fator) {
        setZoom(zoom * fator);
    }

    public void resetarZoom() {
        setZoom(1.0);
    }

    public void ajustarParaCaber() {
        if (raiz == null) {
            return;
        }
        JScrollPane sp = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
        if (sp == null) {
            return;
        }
        Dimension viewport = sp.getViewport().getExtentSize();
        int largura = TreeLayout.larguraPreferida(raiz);
        int altura = TreeLayout.alturaPreferida(raiz);
        if (largura <= 0 || altura <= 0) {
            return;
        }
        double fx = (double) viewport.width / largura;
        double fy = (double) viewport.height / altura;
        setZoom(Math.min(1.0, Math.min(fx, fy)));
        sp.getViewport().setViewPosition(new Point(0, 0));
    }

    private void atualizarDimensoes() {
        int largura = raiz == null ? 0 : (int) (TreeLayout.larguraPreferida(raiz) * zoom);
        int altura = raiz == null ? 0 : (int) (TreeLayout.alturaPreferida(raiz) * zoom);
        setPreferredSize(new Dimension(largura, altura));
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.scale(zoom, zoom);
        int largura = TreeLayout.larguraPreferida(raiz);
        desenhar(g2, raiz, largura / 2, TreeLayout.MARGEM + TreeLayout.DIAMETRO_NO / 2, largura / 4);
        g2.dispose();
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
            desenhar(g, no.getLeft(), nx, ny, Math.max(deslocamento / 2, TreeLayout.DESLOCAMENTO_MIN));
        }
        if (no.getRight() != null) {
            int nx = x + deslocamento;
            int ny = y + TreeLayout.ESPACO_VERTICAL;
            g.drawLine(x, y, nx, ny);
            desenhar(g, no.getRight(), nx, ny, Math.max(deslocamento / 2, TreeLayout.DESLOCAMENTO_MIN));
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
        desenharRankingDentro(g, String.valueOf(no.getPlayer().getRanking()), x, y);
        if (TreeLayout.DIAMETRO_NO * zoom >= LIMIAR_TEXTO || destacado) {
            desenharNomeAbaixo(g, no.getPlayer().getNickname(), x, y + raio, destacado);
        }
    }

    private void desenharRankingDentro(Graphics2D g, String texto, int cx, int cy) {
        Font fonte = new Font("Dialog", Font.BOLD, 11);
        g.setFont(fonte);
        g.setColor(COR_TEXTO);
        FontMetrics fm = g.getFontMetrics();
        int largura = fm.stringWidth(texto);
        int altura = fm.getAscent() - fm.getDescent();
        g.drawString(texto, cx - largura / 2, cy + altura / 2);
    }

    private void desenharNomeAbaixo(Graphics2D g, String texto, int cx, int topo, boolean destacado) {
        Font fonte = new Font("Dialog", destacado ? Font.BOLD : Font.PLAIN, 11);
        g.setFont(fonte);
        g.setColor(destacado ? COR_NOME_DESTAQUE : COR_NOME);
        FontMetrics fm = g.getFontMetrics();
        int largura = fm.stringWidth(texto);
        int baseY = topo + 4 + fm.getAscent();
        g.drawString(texto, cx - largura / 2, baseY);
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        if (raiz == null) {
            return null;
        }
        int x = (int) (e.getX() / zoom);
        int y = (int) (e.getY() / zoom);
        int largura = TreeLayout.larguraPreferida(raiz);
        Node alvo = noEm(raiz, x, y, largura / 2,
                TreeLayout.MARGEM + TreeLayout.DIAMETRO_NO / 2, largura / 4);
        if (alvo == null) {
            return null;
        }
        return alvo.getPlayer().getNickname() + " — ranking " + alvo.getPlayer().getRanking();
    }

    private Node noEm(Node no, int px, int py, int x, int y, int deslocamento) {
        if (no == null) {
            return null;
        }
        int raio = TreeLayout.DIAMETRO_NO / 2;
        int dx = px - x;
        int dy = py - y;
        if (dx * dx + dy * dy <= raio * raio) {
            return no;
        }
        Node esq = noEm(no.getLeft(), px, py,
                x - deslocamento, y + TreeLayout.ESPACO_VERTICAL,
                Math.max(deslocamento / 2, TreeLayout.DESLOCAMENTO_MIN));
        if (esq != null) {
            return esq;
        }
        return noEm(no.getRight(), px, py,
                x + deslocamento, y + TreeLayout.ESPACO_VERTICAL,
                Math.max(deslocamento / 2, TreeLayout.DESLOCAMENTO_MIN));
    }

    private void scrollPara(String nickname) {
        if (raiz == null || nickname == null) {
            return;
        }
        int largura = TreeLayout.larguraPreferida(raiz);
        Point pos = posicaoDoNo(raiz, nickname, largura / 2,
                TreeLayout.MARGEM + TreeLayout.DIAMETRO_NO / 2, largura / 4);
        if (pos == null) {
            return;
        }
        int tamanho = TreeLayout.DIAMETRO_NO * 2;
        Rectangle alvo = new Rectangle(
                (int) ((pos.x - tamanho) * zoom),
                (int) ((pos.y - tamanho) * zoom),
                (int) (tamanho * 2 * zoom),
                (int) (tamanho * 2 * zoom)
        );
        scrollRectToVisible(alvo);
    }

    private Point posicaoDoNo(Node no, String nickname, int x, int y, int deslocamento) {
        if (no == null) {
            return null;
        }
        if (no.getPlayer().getNickname().equals(nickname)) {
            return new Point(x, y);
        }
        Point esq = posicaoDoNo(no.getLeft(), nickname,
                x - deslocamento, y + TreeLayout.ESPACO_VERTICAL,
                Math.max(deslocamento / 2, TreeLayout.DESLOCAMENTO_MIN));
        if (esq != null) {
            return esq;
        }
        return posicaoDoNo(no.getRight(), nickname,
                x + deslocamento, y + TreeLayout.ESPACO_VERTICAL,
                Math.max(deslocamento / 2, TreeLayout.DESLOCAMENTO_MIN));
    }

    private void configurarInteracao() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pontoArraste = e.getPoint();
                SwingUtilities.convertPointToScreen(pontoArraste, TreePanel.this);
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pontoArraste = null;
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (pontoArraste == null) {
                    return;
                }
                JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, TreePanel.this);
                if (viewport == null) {
                    return;
                }
                Point atual = e.getPoint();
                SwingUtilities.convertPointToScreen(atual, TreePanel.this);
                int dx = pontoArraste.x - atual.x;
                int dy = pontoArraste.y - atual.y;
                Point vista = viewport.getViewPosition();
                Dimension total = getPreferredSize();
                Dimension visivel = viewport.getExtentSize();
                int novoX = Math.max(0, Math.min(vista.x + dx, total.width - visivel.width));
                int novoY = Math.max(0, Math.min(vista.y + dy, total.height - visivel.height));
                viewport.setViewPosition(new Point(novoX, novoY));
                pontoArraste = atual;
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown() || e.isMetaDown()) {
                    double fator = e.getPreciseWheelRotation() < 0 ? 1.1 : 0.9;
                    ajustarZoom(fator);
                    e.consume();
                } else {
                    getParent().dispatchEvent(e);
                }
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
    }
}
