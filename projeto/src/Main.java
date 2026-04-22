import ui.RankingAppFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RankingAppFrame app = new RankingAppFrame();
            app.setVisible(true);
        });
    }
}
