package Main;
import Panels.MainPanel;
import javax.swing.UIManager;

public class Workshop {

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Data.InitDatabase.init();
        java.awt.EventQueue.invokeLater(() -> new MainPanel().setVisible(true));
    }
}
