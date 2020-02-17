import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JPanel implements ActionListener, KeyListener {
    int width, height;
    private Timer timer;
    private Game game;

    public GUI(Game game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.timer = new Timer(50, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        this.game.update();
        if (!this.game.isAlive()) {
            this.timer.stop();
        }
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth() / this.game.getGrid().length;
        int height = this.getHeight() / this.game.getGrid()[0].length;
        for (int i = 0; i < this.game.getGrid().length; ++i) {
            for (int j = 0; j < this.game.getGrid()[0].length; ++j) {
                State state = this.game.getGrid()[j][i];
                switch (state) {
                    case EMPTY:
                        g.setColor(Color.LIGHT_GRAY);
                        break;
                    case SNAKE:
                        g.setColor(Color.RED);
                        break;
                    case FOOD:
                        g.setColor(Color.GREEN);
                        break;
                }
                g.fillRect(j * (int) width, i * (int) height, (int) width, (int) height);
            }
        }
        g.setColor(Color.BLUE);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 12));
        if (!this.game.isAlive())
            g.drawString("GAME OVER, press enter to restart. Score: " + (this.game.getSnake().size() - 1), 10, 15);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && this.game.getCurrentDir() != Direction.LEFT) {
            this.game.setCurrentDir(Direction.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && this.game.getCurrentDir() != Direction.RIGHT) {
            this.game.setCurrentDir(Direction.LEFT);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && this.game.getCurrentDir() != Direction.DOWN) {
            this.game.setCurrentDir(Direction.UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && this.game.getCurrentDir() != Direction.UP) {
            this.game.setCurrentDir(Direction.DOWN);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.game.reset();
            this.timer.restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
