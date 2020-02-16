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
    EMPTY, SNAKE, FOOD;
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
}

public class Game {
    Point head;
    // 0 is empty, 1 is snake body, 2 is food
    private State[][] grid;
    private Queue<Point> snake;
    private Direction currentDir;
    private Point food;
    boolean alive;

    public Game(int width, int height) {
        this.grid = new State[width][height];
        this.snake = new LinkedList<>();
        this.head = new Point(7, 7);
        this.snake.add(this.head);
        this.currentDir = Direction.UP;
        this.alive = true;
        this.createFood();
    }

    private void createFood() {
        Random rand = new Random();
        food = new Point(rand.nextInt(grid.length), rand.nextInt(grid[0].length));
    }

    public void update() {
        Point newHead = this.head.add(this.currentDir.delta);
        if(checkEdges())  {
            this.alive = false;
            return;
        }
        this.snake.add(newHead);
        this.snake.poll();
        this.head = newHead;
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

    public boolean isAlive() {
        return this.alive;
    }

    public boolean checkEdges() {
        return head.x < 0 || head.x + 1 > grid.length || head.y < 0 || head.y + 1 > grid[0].length;
    }
}
