import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

public class GUI extends JPanel implements ActionListener, KeyListener {
    // Pixel width and height of Panel.
    private int width, height;
    // Grid width and height.
    private int gWidth, gHeight;
    private Timer timer;
    private Game game;
    private int delay = 100;
    private JSlider speed;
    // private JSlider pixelSize;
    private JSlider gridSize;
    private JLabel instructions;

    public GUI(int width, int height) {
        this.width = width;
        this.height = height;


        // Creates speed slider that will control the Speed of the Snake.
        this.speed = new JSlider(50, 250, delay);
        this.speed.setMajorTickSpacing(50);
        this.speed.setMinorTickSpacing(25);
        this.speed.setPaintTicks(true);

        this.speed.setPaintLabels(true);
        Hashtable<Integer, JLabel> position = new Hashtable<>();
        position.put(50, new JLabel("Fast"));
        position.put(100, new JLabel("Fastish"));
        position.put(150, new JLabel("Medium"));
        position.put(200, new JLabel("Slowish"));
        position.put(250, new JLabel("Slow"));
        this.speed.setLabelTable(position);

        Font font = new Font(this.speed.getFont().getFontName(), Font.BOLD, 5);
        this.speed.setFont(font);
        this.speed.setPreferredSize(new Dimension(this.width, this.height / 4));

        this.speed.setInverted(true);
        this.speed.addKeyListener(this);
        this.speed.addChangeListener(e -> delay = speed.getValue());
        this.add(speed);


        this.instructions = new JLabel("Press Enter to Start");
        this.add(instructions);

        this.renderTitleScreen();
        this.gWidth = 20;
        this.gHeight = 20;
        this.addKeyListener(this);
    }

    private void renderTitleScreen() {
        this.instructions.setVisible(true);
        this.speed.setVisible(true);
        this.game = null;
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (game == null) return;
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
        if (game == null) {
            return;
        }
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
            this.timer = new Timer(delay, this);
            this.game = new Game(this.gWidth, this.gHeight);
            this.speed.setVisible(false);
            this.instructions.setVisible(false);
            this.timer.start();
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.renderTitleScreen();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
