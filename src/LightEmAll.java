import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// to represent an instance of a LightEmAll game
class LightEmAll extends World {
  // to store the LightEmAll grid
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all GamePieces in this game
  ArrayList<GamePiece> nodes;
  // a mock HashMap to represent Edges between GamePieces
  HashMap<GamePiece, GamePiece> representatives;
  // a list of all possible Edges between GamePieces and their neighbors
  ArrayList<Edge> worklist;
  // to store the Minimum Spanning Tree
  ArrayList<Edge> edgesInTree;
  // a list of all powered GamePieces
  ArrayList<GamePiece> powered;
  // dimensions of the board and GamePieces
  int width;
  int height;
  int pieceSize;
  // to store location of powerStation
  int powerRow;
  int powerCol;
  // to store the effective radius of powerStation
  int radius;
  // to store the game mode. Can be one of "MANUAL", "FRACTAL", "KRUSKAL", "EMPTY"
  String mode;
  // is the board going to be scrambled?
  boolean scramble;
  // a random object for testing
  Random rand;

  // Constructor to start a LightEmAll game. Default mode is "KRUSKAL"
  LightEmAll(int width, int height) {
    this(width, height, "KRUSKAL");
  }

  // Constructor to start a LightEmAll game with a specified game mode
  LightEmAll(int width, int height, String mode) {
    this(width, height, mode, false, new Random());
  }

  // Constructor with a flag to show the minimum spanning tree at the beginning of the match
  LightEmAll(int width, int height, String mode, boolean scramble) {
    this(width, height, mode, false, new Random());
  }

  // Constructor with a specified random object for testing
  LightEmAll(int width, int height, String mode, boolean scramble, Random rand) {
    this.mode = mode;
    this.board = new ArrayList<>();
    this.nodes = new ArrayList<>();
    this.representatives = new HashMap<>();
    this.worklist = new ArrayList<>();
    this.edgesInTree = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.powerRow = 0;
    this.powerCol = 0;
    this.pieceSize = 40;
    this.scramble = scramble;
    this.rand = rand;
    this.init();
  }

  // EFFECT: calls helpers to initialize a LightEmAll game
  void init() {
    this.genBoard();
    this.connectNeighbors();
    this.genWires();
    this.updateConnections();
    this.findRadius();
    this.powered = this.board.get(powerRow).get(powerCol).powerUp(this.radius, 0,
            new ArrayList<>(), new ArrayList<>());
  }

  // to draw the graphical interface of a LightEmAll game
  @Override
  public WorldScene makeScene() {
    WorldScene ws = new WorldScene(this.width * this.pieceSize, this.height * this.pieceSize);
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        if (i == this.powerCol && j == this.powerRow) {
          ws.placeImageXY(this.board.get(i).get(j).draw(this.pieceSize, this.radius),
                  this.pieceSize / 2 + this.pieceSize * i, this.pieceSize / 2 + this.pieceSize * j);
          ws.placeImageXY(
                  new OverlayImage(new StarImage(16, 7, OutlineMode.OUTLINE, Color.ORANGE),
                          new StarImage(16, 7, OutlineMode.SOLID, Color.CYAN)),
                  this.pieceSize / 2 + this.pieceSize * i, this.pieceSize / 2 + this.pieceSize * j);
        }
        else {
          ws.placeImageXY(this.board.get(i).get(j).draw(this.pieceSize, this.radius),
                  this.pieceSize / 2 + this.pieceSize * i, this.pieceSize / 2 + this.pieceSize * j);
        }
      }
    }
    return ws;
  }

  // to interact by rotating a GamePiece depending on where the click is
  public void onMouseClicked(Posn pos, String key) {
    this.powerDown();
    this.powered = new ArrayList<>();
    if (key.equals("LeftButton") || key.equals("RightButton")) {
      GamePiece current = this.board.get(Math.floorDiv(pos.x,
              this.pieceSize)).get(Math.floorDiv(pos.y, this.pieceSize));
      // GamePiece.rotate receives the key to specify direction
      current.rotate(key);
    }
    this.updateConnections();
    this.powered = this.board.get(powerCol).get(powerRow).powerUp(this.radius, 0,
            new ArrayList<>(), new ArrayList<>());

  }

  // to move powerStation depending on which key is pressed and its surroundings
  public void onKeyEvent(String str) {
    this.powerDown();
    this.powered = new ArrayList<>();
    if (str.equals("up") && this.board.get(this.powerCol).get(this.powerRow).top) {
      this.powerRow = this.powerRow - 1;
    }
    else if (str.equals("right") && this.board.get(this.powerCol).get(this.powerRow).rig) {
      this.powerCol = this.powerCol + 1;
    }
    else if (str.equals("down") && this.board.get(this.powerCol).get(this.powerRow).bot) {
      this.powerRow = this.powerRow + 1;
    }
    else if (str.equals("left") && this.board.get(this.powerCol).get(this.powerRow).lef) {
      this.powerCol = this.powerCol - 1;
    }
    this.updateConnections();
    this.powered = this.board.get(powerCol).get(powerRow).powerUp(this.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    // this.nodeFurthestFrom(this.board.get(this.powerCol).get(this.powerRow));
  }

  // EFFECT: establishes relations between a certain cell and its adjacent cells
  void connectNeighbors() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        // target node
        GamePiece target = this.board.get(i).get(j);
        // top left corner
        if (i == 0 && j == 0) {
          target.addNeighbor("bottom", this.board.get(i).get(j + 1));
          target.addNeighbor("right", this.board.get(i + 1).get(j));
        }
        // top right corner
        else if (i == this.width - 1 && j == 0) {
          target.addNeighbor("left", this.board.get(i - 1).get(j));
          target.addNeighbor("bottom", this.board.get(i).get(j + 1));
        }
        // top row
        else if (j == 0 && i != 0 && i != this.width - 1) {
          target.addNeighbor("left", this.board.get(i - 1).get(j));
          target.addNeighbor("right", this.board.get(i + 1).get(j));
          target.addNeighbor("bottom", this.board.get(i).get(j + 1));
        }
        // left column
        else if (i == 0 && j != 0 && j != this.height - 1) {
          target.addNeighbor("top", this.board.get(i).get(j - 1));
          target.addNeighbor("bottom", this.board.get(i).get(j + 1));
          target.addNeighbor("right", this.board.get(i + 1).get(j));
        }
        // right column
        else if (i == this.width - 1 && j != 0 && j != this.height - 1) {
          target.addNeighbor("top", this.board.get(i).get(j - 1));
          target.addNeighbor("bottom", this.board.get(i).get(j + 1));
          target.addNeighbor("left", this.board.get(i - 1).get(j));
        }
        // bottom left corner
        else if (i == 0 && j == this.height - 1) {
          target.addNeighbor("top", this.board.get(i).get(j - 1));
          target.addNeighbor("right", this.board.get(i + 1).get(j));
        }
        // bottom right corner
        else if (i == this.width - 1 && j == this.height - 1) {
          target.addNeighbor("left", this.board.get(i - 1).get(j));
          target.addNeighbor("top", this.board.get(i).get(j - 1));
        }
        // bottom row
        else if (j == this.height - 1 && i != 0 && i != this.width - 1) {
          target.addNeighbor("top", this.board.get(i).get(j - 1));
          target.addNeighbor("right", this.board.get(i + 1).get(j));
          target.addNeighbor("left", this.board.get(i - 1).get(j));
        }
        // center pieces
        else {
          target.addNeighbor("top", this.board.get(i).get(j - 1));
          target.addNeighbor("bottom", this.board.get(i).get(j + 1));
          target.addNeighbor("right", this.board.get(i + 1).get(j));
          target.addNeighbor("left", this.board.get(i - 1).get(j));
        }
      }
    }
  }

  // EFFECT: generates the LightEmAll grid
  void genBoard() {
    for (int i = 0; i < this.width; i++) {
      this.board.add(new ArrayList<>());
      for (int j = 0; j < this.height; j++) {
        GamePiece gp = new GamePiece(i, j);
        this.board.get(i).add(gp);
        this.nodes.add(gp);
      }
    }
  }

  // EFFECT: generates wires according to the specified game mode
  void genWires() {
    if (this.mode.equals("MANUAL")) {
      this.manualWires();
    }
    if (this.mode.equals("FRACTALS")) {
      this.fractalWires(0, this.width - 1, 0, this.height - 1);
    }
    if (this.mode.equals("KRUSKAL")) {
      this.kruskalWires();
    }
    if (this.mode.equals("EMPTY")) {
      return; // This mode is only for testing
    }
  }

  // EFFECT: generates a previously hard-coded wire board
  void manualWires() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        // top left corner
        if (i == 0 && j == 0) {
          this.board.get(i).get(j).wireBot = true;
        }
        // top row
        else if (i > 0 && j == 0 && i < this.width - 1) {
          this.board.get(i).get(j).wireBot = true;
        }
        // top right corner
        else if (i == this.width - 1 && j == 0) {
          this.board.get(i).get(j).wireBot = true;
        }
        // left column
        else if (i == 0 && j != 0 && j != this.height - 1) {
          this.board.get(i).get(j).wireTop = true;
          this.board.get(i).get(j).wireBot = true;
          if (j == 4) {
            this.board.get(i).get(j).wireRight = true;
          }
        }
        // right column
        else if (i == this.width - 1 && j != 0 && j != height - 1) {
          this.board.get(i).get(j).wireTop = true;
          this.board.get(i).get(j).wireBot = true;
          if (j == 4) {
            this.board.get(i).get(j).wireLeft = true;
          }
        }
        // bottom left corner
        else if (i == 0 && j == this.height - 1) {
          this.board.get(i).get(j).wireTop = true;
        }
        // bottom row
        else if (i != 0 && i != this.width - 1 && j == this.height - 1) {
          this.board.get(i).get(j).wireTop = true;
        }
        // bottom right corner
        else if (i == this.width - 1 && j == this.height - 1) {
          this.board.get(i).get(j).wireTop = true;
        }
        // middle row
        else if (j == 4 && i != 0 && i != this.width - 1) {
          this.board.get(i).get(j).wireLeft = true;
          this.board.get(i).get(j).wireTop = true;
          this.board.get(i).get(j).wireBot = true;
          this.board.get(i).get(j).wireRight = true;
        }
        // every other cell that is not along a border
        else {
          this.board.get(i).get(j).wireTop = true;
          this.board.get(i).get(j).wireBot = true;
        }
      }
    }
  }

  // EFFECT: generates a fractal-like pattern that works for any dimensions
  void fractalWires(int x1, int x2, int y1, int y2) {
    for (int k = x1; k <= x2; k++) {
      for (int h = y1; h <= y2; h++) {
        // top left corner
        if (k == x1 && h == y1) {
          this.board.get(k).get(h).wireBot = true;
        }
        // top right corner
        else if (k == x2 && h == y1) {
          this.board.get(k).get(h).wireBot = true;
        }
        // bottom left corner
        else if (k == x1 && h == y2) {
          this.board.get(k).get(h).wireRight = true;
          this.board.get(k).get(h).wireTop = true;
        }
        // bottom right corner
        else if (k == x2 && h == y2) {
          this.board.get(k).get(h).wireLeft = true;
          this.board.get(k).get(h).wireTop = true;
        }
        // left column
        else if (h != y1 && h != y2 && k == x1) {
          this.board.get(k).get(h).wireTop = true;
          this.board.get(k).get(h).wireBot = true;
        }
        // right column
        else if (h != y1 && h != y2 && k == x2) {
          this.board.get(k).get(h).wireTop = true;
          this.board.get(k).get(h).wireBot = true;
        }
        // bottom row
        else if (k != x1 && k != x2 && h == y2) {
          this.board.get(k).get(h).wireLeft = true;
          this.board.get(k).get(h).wireRight = true;
        }
      }
    }

    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 > 1 && x1 % 2 == 0 && y1 % 2 == 0) {
      // top left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), y1, Math.floorDiv(y2 + y1, 2));
    }
    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 > 1 && x1 % 2 == 1 && y1 % 2 == 0) {
      // top left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), y1, Math.floorDiv(y2 + y1, 2));
    }
    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 > 1 && x1 % 2 == 0 && y1 == 0 && y2 == 1) {
      // top left quadrant
      this.fractalWires(x1, Math.round(x2 / 2), y1, y2);
    }
    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 > 1 && x1 % 2 == 0 && y1 % 2 == 1 && y2 - y1 > 1) {
      // top left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), y1, Math.floorDiv(y2 + y1, 2));// +1
    }
    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 > 1 && x1 % 2 == 0 && y1 % 2 == 1 && y2 - y1 == 1) {
      // top left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), y1, Math.floorDiv(y2 + y1, 2) + 1);// +1
    }
    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 > 1 && x1 % 2 == 1 && y1 % 2 == 1 && y2 - y1 > 1) {
      // top left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), y1, Math.floorDiv(y2 + y1, 2));
    }
    if (Math.ceil(x2 - x1 / 2) >= 1 && x2 - x1 >= 1 && y2 - y1 == 1) {
      // top left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), y1, Math.floorDiv(y2 + y1, 2) + 1);
    }
    if (x2 - ((Math.floorDiv((x2 - x1), 2)) + 1) >= 1 && x2 - x1 > 2 && y2 - y1 >= 2) {
      // top right quadrant when x1 == 0
      this.fractalWires(Math.floorDiv(x2 + x1, 2) + 1, x2, y1, Math.floorDiv(y2 + y1, 2));
    }
    if (x2 - ((Math.floorDiv((x2 - x1), 2)) + 1) >= 1 && x2 - x1 > 2 && y2 - y1 == 1) {
      // top right quadrant when x1 == 0
      this.fractalWires(Math.floorDiv(x2 + x1, 2) + 1, x2, y1, Math.floorDiv(y2 + y1, 2) + 1);
    }
    if (y2 - (Math.floorDiv((y2 - y1), 2) + 1) >= 1 && y2 - y1 > 2 && x2 - x1 > 1) {
      // bottom left quadrant
      this.fractalWires(x1, Math.floorDiv(x2 + x1, 2), Math.floorDiv(y2 + y1, 2) + 1, y2);
    }
    if ((y2 - (Math.floorDiv((y2 - y1), 2) + 1) >= 1 && y2 - y1 > 2 && x2 - x1 > 2)
            && (x2 - ((Math.floorDiv((x2 - x1), 2)) + 1) >= 1 && x2 - x1 > 2 && y2 - y1 > 2)) {
      // bottom right quadrant
      this.fractalWires(Math.floorDiv(x2 + x1, 2) + 1, x2, Math.floorDiv(y2 + y1, 2) + 1, y2);
    }
  }

  // EFFECT: generates a random, acyclic wire pattern using Kruskal's algorithm
  void kruskalWires() {
    this.genEdges();
    while (this.edgesInTree.size() != this.nodes.size() - 1) {
      Edge current = this.worklist.remove(0);
      GamePiece fromNode = current.fromNode;
      GamePiece toNode = current.toNode;
      GamePiece fromRep = this.findRep(fromNode);
      GamePiece toRep = this.findRep(toNode);
      if (!fromRep.equals(toRep)) {
        this.edgesInTree.add(current);
        this.representatives.put(this.representatives.get(fromRep), toRep);
      }
    }
    for (int i = 0; i < this.edgesInTree.size(); i++) {
      Edge current = this.edgesInTree.get(i);
      GamePiece fromNode = current.fromNode;
      GamePiece toNode = current.toNode;
      // NOTE: Does not check for top neighbor because
      // Edges were generated sweeping towards the bottom right corner
      if (fromNode.neighborHash.containsKey("right")
              && fromNode.neighborHash.get("right").equals(toNode)) {
        fromNode.wireRight = true;
        toNode.wireLeft = true;
      }
      if (fromNode.neighborHash.containsKey("bottom")
              && fromNode.neighborHash.get("bottom").equals(toNode)) {
        fromNode.wireBot = true;
        toNode.wireTop = true;
      }
      if (fromNode.neighborHash.containsKey("left")
              && fromNode.neighborHash.get("left").equals(toNode)) {
        fromNode.wireLeft = true;
        toNode.wireRight = true;
      }
    }
    this.updateConnections();
    if (this.scramble) {
      for (int i = 0; i < this.nodes.size(); i++) {
        GamePiece current = this.nodes.get(i);
        int rotations = this.rand.nextInt(4);
        for (int j = 0; j < rotations; j++) {
          current.rotate("LeftButton");
        }
      }
    }
  }

  // to return the deepest GamePiece for a given key in the representatives HashMap
  GamePiece findRep(GamePiece key) {
    GamePiece currentRep = this.representatives.get(key);
    if (currentRep.equals(key)) {
      return key;
    }
    else {
      return this.findRep(currentRep);
    }
  }

  // EFFECT: generates all Edges in this graph sorted by randomly assigned weights
  void genEdges() {
    int maxWeight = this.nodes.size();
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        // target node
        GamePiece target = this.board.get(i).get(j);
        this.representatives.put(target, target);
        // GamePiece in last col connects to bottom neighbors
        if (i == this.width - 1 && j < this.height - 1) {
          this.worklist.add(new Edge(target,
                  this.board.get(i).get(j + 1), maxWeight, this.rand));
        }
        // GamePiece in last row connects to right neighbors
        else if (i < this.width - 1 && j == this.height - 1) {
          this.worklist.add(new Edge(target,
                  this.board.get(i + 1).get(j), maxWeight, this.rand));
        }
        // GamePieces remaining connect to bottom and right neighbors
        else if (i < this.width - 1 && j < this.height - 1) {
          this.worklist.add(new Edge(target,
                  this.board.get(i + 1).get(j), maxWeight, this.rand));
          this.worklist.add(new Edge(target,
                  this.board.get(i).get(j + 1), maxWeight, this.rand));
        }
        // NOTE: Bottom right corner does not generate edges
      }
    }
    // calls mergeSort to sort list of Edges by their weight
    new Utils().mergesort(this.worklist, new HeavierThan());
  }

  // EFFECT: updates connections between GamePieces after any operation
  void updateConnections() {
    for (int i = 0; i < this.nodes.size(); i++) {
      this.nodes.get(i).updateConnections();
      this.nodes.get(i).updatePowerStation(this.powerCol, this.powerRow);
    }
  }

  // EFFECT: powers down the board for safety before any operation (OnMouseClicked or OnKey)
  void powerDown() {
    for (int i = 0; i < this.nodes.size(); i++) {
      this.nodes.get(i).powerDown(this.powered);
    }
    this.powered = new ArrayList<>();
  }

  // EFFECT: updates the effective radius of the power station using BFS
  void findRadius() {
    int maxDepth = 0;
    HashMap<Integer, GamePiece> firstLast =
            this.findDeepest(this.board.get(powerCol).get(powerRow), 0, new ArrayList<>());
    for (int key : firstLast.keySet()) {
      maxDepth = key;
    }
    HashMap<Integer, GamePiece> secondLast =
            this.findDeepest(firstLast.get(maxDepth), 0, new ArrayList<>());
    for (int key : secondLast.keySet()) {
      maxDepth = key;
    }
    this.radius = maxDepth / 2 + 1;
  }

  // uses BFS to return a HashMap of length 1. The HashMap contains:
  //  - key: An int representing the maximum depth of the graph
  //  - value: The GamePiece farthest from the start point
  HashMap<Integer, GamePiece> findDeepest(GamePiece start,
                                          int depth,
                                          ArrayList<GamePiece> acc) {
    // Next group of nodes to be checked (if any)
    ArrayList<GamePiece> nextLevel = new ArrayList<>();
    // Loop to update nextLevel with currentLast's neighbors (if any)
    // updates accumulator accordingly
    for (String key : start.neighborHash.keySet()) {
      GamePiece toCheck = start.neighborHash.get(key);
      if (!acc.contains(toCheck) && toCheck.isConnected(start)) {
        nextLevel.add(toCheck);
      }
    }
    // initialize results variable to an empty HashMap
    HashMap<Integer, GamePiece> results = new HashMap<>();
    // If there are no more nodes to check, return HashMap with depth, start pair
    if (nextLevel.size() == 0) {
      results.put(depth, start);
    }
    // If there are more nodes to be check, update accumulator and call helper
    else {
      acc.add(start);
      results = this.findDeeper(nextLevel, depth + 1, acc);
    }
    return results;
  }

  // helper for findDeepest() to check ArrayLists of neighbors when their size > 1
  HashMap<Integer, GamePiece> findDeeper(ArrayList<GamePiece> prevLevel,
                                         int depth,
                                         ArrayList<GamePiece> acc) {
    // Next group of nodes to be checked (if any)
    ArrayList<GamePiece> nextLevel = new ArrayList<>();
    // An arbitrary node from this level to be returned if no deeper GamePiece is found
    GamePiece nowDeepest = prevLevel.get(0);
    // If there is only one node to check, update accumulator and call original method
    if (prevLevel.size() == 1) {
      acc.add(nowDeepest);
      return findDeepest(nowDeepest, depth, acc);
    }
    // If there is more than one node to check, check all their neighbors
    else {
      // First loop checks every node in prevLevel (if not in accumulator)
      for (int i = 0; i < prevLevel.size(); i++) {
        nowDeepest = prevLevel.get(i);
        if (!acc.contains(nowDeepest)) {
          // Second loop adds unchecked neighbors to nextLevel (if not in accumulator)
          for (String key : nowDeepest.neighborHash.keySet()) {
            GamePiece aNeighbor = nowDeepest.neighborHash.get(key);
            if (aNeighbor.isConnected(nowDeepest)
                    && (!acc.contains(aNeighbor) && !nextLevel.contains(aNeighbor))) {
              nextLevel.add(aNeighbor);
            }
          }
        }
        // Update the accumulator
        acc.add(nowDeepest);
      }
    }
    // initialize results variable to an empty HashMap
    HashMap<Integer, GamePiece> results = new HashMap<>();
    // If there are no more nodes to check, return HashMap with depth, nowDeepest pair
    if (nextLevel.size() == 0) {
      results.put(depth, nowDeepest);
    }
    // If there are more nodes to be checked, call this method recursively
    else {
      // NOTE: no need to update accumulator since it was updated in previous if-else statement
      results = this.findDeeper(nextLevel, depth + 1, acc);
    }
    return results;
  }

}
