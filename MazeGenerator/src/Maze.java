import java.util.*;

/**
 * <h1>Maze Game</h1> <h2>Maze implementation</h2>
 * <p>
 * A grid of coordinates adjacent to others or not.
 * </p>
 */
public class Maze {

  private final int width;
  private final int height;
  /**
   * A maze is a 2d array and at each coordinate the type indicates whether it is a piece of wall, a
   * piece of the path or potentially the correct path to the end.
   */
  private final int[][] maze;

  private final int PATH = 1;
  private final int SOLUTION = 2;

  /**
   * generates a new empty maze of given size.
   * 
   * @param _width given maze width
   * @param _height given maze height
   */
  public Maze(int _width, int _height) {
    // for maze
    this.width = _width;
    this.height = _height;
    this.maze = new int[this.width][this.height];
  }

  /**
   * called by a maze generator, to explore paths of the maze.
   * 
   * @param _tree
   */
  public void constructMaze(List<MazeNode> _tree) {
    for (MazeNode n : _tree) {
      int x = n.x();
      int y = n.y();
      this.maze[x][y] = n.type();
    }
    this.maze[0][1] = this.SOLUTION;
    this.maze[this.width - 1][this.height - 2] = this.SOLUTION;
  }

  /**
   * @param _x x-coordinate of given point on maze
   * @param _y y-coordinate of given point on maze
   * @return the situation of the point on maze
   */
  public int get(int _x, int _y) {
    if ((_x < 0) || (_x > this.width) || (_y < 0) || (_y > this.height)) {
      return -1;
    }
    return this.maze[_x][_y];
  }

  public int[][] getMaze() {
    return this.maze;
  }

  public int height() {
    return this.height;
  }

  public int width() {
    return this.width;
  }

  /**
   * Print maze to console. - Ws stand for walls - spaces stand for available paths - dots stand for
   * path from start (top-left) to end (bottom-left).
   */
  public void print() {
    StringBuilder s = new StringBuilder();
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        switch (this.maze[x][y]) {
          case PATH:
            s.append(" ");
            break;
          case SOLUTION:
            s.append(".");
            break;
          default: // WALL
            s.append("W");

        }
      }
      s.append("\n");
    }
    System.out.println(s.toString());
  }

}
