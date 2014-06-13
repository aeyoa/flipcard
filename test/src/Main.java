import javax.swing.*;

/**
 * Created by arsenykogan on 12/06/14.
 */
public class Main {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Test");
        frame.add(new JButton("Press me"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
