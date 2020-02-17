import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI gui = new GUI(new Game(30, 30), 500, 500);
        frame.add(gui);
        frame.addKeyListener(gui);
        frame.pack();
        frame.setVisible(true);
    }
}
