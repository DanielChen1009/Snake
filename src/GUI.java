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
    private int gWidth = 20;
    private int gHeight = 20;
    private Timer timer;
    private Game game;
    private int delay = 100;
    private JSlider speed, gridWidth, gridHeight;
    private JLabel instructions, speedLabel, widthLabel, heightLabel;
    // private JSlider pixelSize.

    public GUI(int width, int height) {
        this.width = width;
        this.height = height;

        this.speedLabel = new JLabel("Slide to set Speed");
        this.add(speedLabel);

        // Creates speed slider that will control the Speed of the Snake.
        this.speed = new JSlider(50, 250, this.delay);
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
        this.speed.setPreferredSize(new Dimension(this.width, this.height / 5));

        this.speed.setInverted(true);
        this.speed.addKeyListener(this);
        this.speed.addChangeListener(e -> this.delay = this.speed.getValue());
        this.add(speed);

        // Creates width and height slider
        this.widthLabel = new JLabel("Slide to set Width");
        this.add(widthLabel);
        this.gridWidth = new JSlider(15, 40, this.gWidth);
        this.gridWidth.setMajorTickSpacing(5);
        this.gridWidth.setMinorTickSpacing(1);
        this.gridWidth.setPaintTicks(true);
        this.gridWidth.setPaintLabels(true);
        this.gridWidth.setPreferredSize(new Dimension(this.width, this.height / 5));

        this.gridWidth.addKeyListener(this);
        this.gridWidth.addChangeListener(e -> this.gWidth = this.gridWidth.getValue());
        this.add(gridWidth);

        this.heightLabel = new JLabel("Slide to set Height");
        this.add(heightLabel);

        this.gridHeight = new JSlider(15, 40, this.gWidth);
        this.gridHeight.setMajorTickSpacing(5);
        this.gridHeight.setMinorTickSpacing(1);
        this.gridHeight.setPaintTicks(true);
        this.gridHeight.setPaintLabels(true);
        this.gridHeight.setPreferredSize(new Dimension(this.width, this.height / 5));

        this.gridHeight.addKeyListener(this);
        this.gridHeight.addChangeListener(e -> this.gHeight = this.gridHeight.getValue());
        this.add(gridHeight);

        this.instructions = new JLabel("Press Enter to Start");
        this.add(instructions);

        this.renderTitleScreen();
        this.addKeyListener(this);
    }

    private void renderTitleScreen() {
        this.instructions.setVisible(true);
        this.speed.setVisible(true);
        this.gridWidth.setVisible(true);
        this.gridHeight.setVisible(true);
        this.speedLabel.setVisible(true);
        this.heightLabel.setVisible(true);
        this.widthLabel.setVisible(true);
        this.game = null;
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (this.game == null) return;
        this.game.update();
        if (this.game.isNotAlive()) {
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
        if (this.game == null) {
            return;
        }
        int width = this.getWidth() / this.gWidth;
        int height = this.getHeight() / this.gHeight;
        for (int i = 0; i < this.gHeight; ++i) {
            for (int j = 0; j < this.gWidth; ++j) {
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
                g.fillRect(j * width, i * height, width, height);
            }
        }
        g.setColor(Color.BLUE);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 12));
        if (this.game.isNotAlive())
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
            this.gridWidth.setVisible(false);
            this.gridHeight.setVisible(false);
            this.speedLabel.setVisible(false);
            this.heightLabel.setVisible(false);
            this.widthLabel.setVisible(false);
            this.timer.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.renderTitleScreen();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
