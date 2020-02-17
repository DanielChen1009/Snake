import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        GUI gui = new GUI(800, 800);
        frame.add(gui);
        frame.pack();
        frame.setVisible(true);
        gui.setFocusable(true);
        gui.requestFocusInWindow();
    }
}
