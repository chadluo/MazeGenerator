import java.util.*;

/**
 * <h1>Maze Game</h1> <h2>Maze node</h2>
 * <p>
 * Coordinate on maze representation. Maze a minimum spanning tree of nodes, where each node
 * specified with its coordinates and nodes connected to.
 * </p>
 */
public class MazeNode {

  private final int x;
  private final int y;
  private MazeNode parent;
  private final List<MazeNode> children;
  private int type;

  public MazeNode(int _x, int _y) {
    this.x = _x;
    this.y = _y;
    this.parent = null;
    this.children = new ArrayList<>();
  }

  public void addChild(MazeNode _child) {
    this.children.add(_child);
  }

  public List<MazeNode> children() {
    return this.children;
  }

  public MazeNode parent() {
    return this.parent;
  }

  public void setParent(MazeNode _parent) {
    this.parent = _parent;
  }

  public void setType(int _type) {
    this.type = _type;
  }

  public int type() {
    return this.type;
  }

  public int x() {
    return this.x;
  }

  public int y() {
    return this.y;
  }

}
