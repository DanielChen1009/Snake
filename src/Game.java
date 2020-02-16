import java.util.LinkedList;
import java.util.Queue;

enum Direction {
    UP(new Point(0, -1)), DOWN(new Point(0, 1)), LEFT(new Point(-1, 0)), RIGHT(new Point(1, 0));

    Point delta;

    Direction(Point delta) {
        this.delta = delta;
    }
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
    private int[][] grid;
    private Queue<Point> snake;
    private Direction currentDir;

    public Game() {
        this.grid = new int[15][15];
        this.snake = new LinkedList<>();
        this.head = new Point(7, 7);
        this.snake.add(this.head);
        this.currentDir = Direction.UP;
    }

    public void update() {
        Point newHead = this.head.add(this.currentDir.delta);
        this.snake.add(newHead);
        this.head = newHead;
    }

    public int[][] getGrid() {
        return grid;
    }

    public Queue<Point> getSnake() {
        return snake;
    }

    public void setCurrentDir(Direction currentDir) {
        this.currentDir = currentDir;
    }
}
