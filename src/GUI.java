import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Game game;

    public GUI(Game game) {
        this.game = game;
        this.timer = new Timer(100, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        this.game.update();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean rowStartWhite = true;
        int width = this.getWidth() / this.game.getGrid().length;
        int height = this.getHeight() / this.game.getGrid()[0].length;
        for(int i = 0; i < this.game.getGrid().length; ++i) {
            boolean cellWhite = rowStartWhite;
            for(int j = 0; j < this.game.getGrid()[0].length; ++j) {
                if (cellWhite = !cellWhite) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect( j * (int) width,  i * (int) height,(int) width,(int) height);
            }
            rowStartWhite = !rowStartWhite;
        }
        g.setColor(Color.RED);
        for(Point point : game.getSnake()) {
            g.fillRect(point.x * width, point.y * height, width, height);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.game.setCurrentDir(Direction.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.game.setCurrentDir(Direction.LEFT);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.game.setCurrentDir(Direction.UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.game.setCurrentDir(Direction.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
