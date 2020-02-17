import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    private static int delay;
    public static void main(String[] args) {
//        JFrame frame1 = new JFrame();
//        JPanel panel = new JPanel();
//		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		JSlider slider = new JSlider(50, 400, 100);
//		panel.add(slider);
//		frame1.add(panel);
//        frame1.setVisible(true);
//        slider.addChangeListener(e -> {
//            delay = slider.getValue();
//        });
        JFrame frame = new JFrame("Snake");
        GUI gui = new GUI(300, 300);
        frame.add(gui);
        frame.pack();
        frame.setVisible(true);
        gui.setFocusable(true);
        gui.requestFocusInWindow();
    }
}
