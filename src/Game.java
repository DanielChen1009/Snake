import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

enum Direction {
    UP(new Point(0, -1)), DOWN(new Point(0, 1)), LEFT(new Point(-1, 0)), RIGHT(new Point(1, 0));

    Point delta;

    Direction(Point delta) {
        this.delta = delta;
    }
}

enum State {
    EMPTY, SNAKE, FOOD
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    boolean equals(Point o) {
        return x == o.x && y == o.y;
    }
}

public class Game {
    Point head;
    // 0 is empty, 1 is snake body, 2 is food
    private State[][] grid;
    private Queue<Point> snake;
    private Direction currentDir;
    private Point food;
    private boolean alive;
    private int width;
    private int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        reset();
    }

    public void reset() {
        this.grid = new State[this.width][this.height];
        for (int i = 0; i < this.width; ++i) {
            for (int j = 0; j < this.height; ++j) {
                this.grid[i][j] = State.EMPTY;
            }
        }
        this.snake = new LinkedList<>();
        this.head = new Point(width / 2, height / 2);
        this.snake.add(this.head);
        setState(this.head, State.SNAKE);
        this.currentDir = Direction.UP;
        this.alive = true;
        this.createFood();
    }

    private State getState(Point p) {
        return this.grid[p.x][p.y];
    }

    private void setState(Point p, State state) {
        if (p == null) return;
        this.grid[p.x][p.y] = state;
    }

    private void createFood() {
        Random rand = new Random();
        do {
            food = new Point(rand.nextInt(this.height), rand.nextInt(this.width));
        } while (getState(food) != State.EMPTY);

        setState(food, State.FOOD);
    }

    public void update() {
        Point newHead = this.head.add(this.currentDir.delta);
        if (isOutOfBounds(newHead)) {
            this.alive = false;
            return;
        }
        if (hitsBody(newHead)) {
            this.alive = false;
            return;
        }
        this.snake.add(newHead);
        setState(newHead, State.SNAKE);
        setState(this.snake.poll(), State.EMPTY);
        this.head = newHead;
        this.checkFood();
    }

    public void checkFood() {
        if (food.equals(head)) {
            snake.add(head);
            createFood();
        }
    }

    public State[][] getGrid() {
        return grid;
    }

    public Queue<Point> getSnake() {
        return snake;
    }

    public void setCurrentDir(Direction currentDir) {
        this.currentDir = currentDir;
    }

    public Point getFood() {
        return this.food;
    }

    public boolean isNotAlive() {
        return !this.alive;
    }

    public boolean isOutOfBounds(Point newHead) {
        return newHead.x < 0 || newHead.x + 1 > grid.length || newHead.y < 0 || newHead.y + 1 > grid[0].length;
    }

    public boolean hitsBody(Point head) {
        return getState(head) == State.SNAKE;
    }

    public Direction getCurrentDir() {
        return currentDir;
    }
}
