import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI gui = new GUI(new Game(15, 15));
        frame.add(gui);
        frame.addKeyListener(gui);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
