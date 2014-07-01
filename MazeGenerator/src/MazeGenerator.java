import java.util.*;

/**
 * <h1>Maze Game</h1> <h2>Maze Generator</h2>
 * <p>
 * Generate a maze via Prim's algorithm.
 * </p>
 */
public class MazeGenerator {

  private int width;
  private int height;
  private boolean[][] explored;

  private final int PATH = 1;
  private final int SOLUTION = 2;

  public MazeGenerator() {}

  public static void main(String[] args) {
    new MazeGenerator().generate(35, 35).print();
  }

  /**
   * generate a new maze of given size.
   * 
   * @param _x maze width
   * @param _y maze height
   * @return maze of given size
   */
  public Maze generate(int _x, int _y) {

    // width and height must be odd.
    this.width = _x;
    this.height = _y;
    if ((this.width % 2) == 0) {
      this.width++;
    }
    if ((this.height % 2) == 0) {
      this.height++;
    }

    // record maze exploration
    // init as straight falses by default
    this.explored = new boolean[this.width][this.height];

    // empty maze topology
    List<MazeNode> tree = new ArrayList<>();

    // random queue
    Queue<MazeNode> frontier = new PriorityQueue<MazeNode>(50, new Comparator<MazeNode>() {
      @Override
      public int compare(MazeNode n1, MazeNode n2) {
        return Math.random() < 0.5 ? -1 : 1;
      }
    });

    // with (0,y) and (x,0) as walls, start from 1,1
    MazeNode curr = new MazeNode(1, 1);
    MazeNode end = null;

    frontier.add(curr);
    while (!frontier.isEmpty()) {
      // explore a random point
      curr = frontier.poll();
      // mark end for later solution path generation
      if ((curr.x() == (this.width - 2)) && (curr.y() == (this.height - 2))) {
        end = curr;
      }
      // since expanding nodes are all on odd coordinates, we have to add
      // the node in between. If the node is start it has no parents.
      if (curr.parent() != null) {
        tree.add(curr.parent());
      }
      // 1st entry of tree would be maze start
      tree.add(curr);
      // add all possible ways to the queue for exploration.
      frontier.addAll(this.explore(curr));
    }

    // Minimum Spanning Tree as maze representation finished.

    // solution path generation
    while (end != null) {
      end.setType(this.SOLUTION);
      end = end.parent();
    }

    Maze maze = new Maze(this.width, this.height);
    maze.constructMaze(tree);
    return maze;

  }

  /**
   * Return all available nodes from this node. Where 'available' is that, the node has not been
   * explored and is within the maze size range.
   */
  private List<MazeNode> explore(MazeNode _node) {
    int x = _node.x();
    int y = _node.y();
    List<MazeNode> candidates = new ArrayList<>();

    // try four potential available nodes: left, right, up and down.
    if (((x - 2) > 0) && !this.explored[x - 2][y]) {
      MazeNode nmedial = new MazeNode(x - 1, y);
      MazeNode nend = new MazeNode(x - 2, y);
      this.connect(_node, nmedial, nend);
      candidates.add(nend);
      this.explored[x - 2][y] = true;
    }
    if (((x + 2) < this.width) && !this.explored[x + 2][y]) {
      MazeNode nmedial = new MazeNode(x + 1, y);
      MazeNode nend = new MazeNode(x + 2, y);
      this.connect(_node, nmedial, nend);
      candidates.add(nend);
      this.explored[x + 2][y] = true;
    }
    if (((y - 2) > 0) && !this.explored[x][y - 2]) {
      MazeNode nmedial = new MazeNode(x, y - 1);
      MazeNode nend = new MazeNode(x, y - 2);
      this.connect(_node, nmedial, nend);
      candidates.add(nend);
      this.explored[x][y - 2] = true;
    }
    if (((y + 2) < this.height) && !this.explored[x][y + 2]) {
      MazeNode nmedial = new MazeNode(x, y + 1);
      MazeNode nend = new MazeNode(x, y + 2);
      this.connect(_node, nmedial, nend);
      candidates.add(nend);
      this.explored[x][y + 2] = true;
    }

    return candidates;
  }

  /**
   * Given three nodes, assign their relationship on the MST.
   */
  private void connect(MazeNode start, MazeNode medial, MazeNode end) {
    start.setType(this.PATH);
    medial.setType(this.PATH);
    end.setType(this.PATH);
    start.addChild(medial);
    medial.setParent(start);
    medial.addChild(end);
    end.setParent(medial);
  }

}
