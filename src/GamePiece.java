import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import javalib.worldimages.*;

// to represent a GamePiece in a LightEmAll game
class GamePiece {
  // x value of this GamePiece in its parent LightEmAll board
  int col;
  // y value of this GamePiece in its parent LightEmAll board
  int row;
  // does this GamePiece have outgoing wires?
  boolean wireLeft;
  boolean wireRight;
  boolean wireTop;
  boolean wireBot;
  // does this GamePiece connect to its neighbors through wires?
  boolean lef;
  boolean rig;
  boolean top;
  boolean bot;
  // does this GamePiece contain a powerStation?
  boolean powerStation;
  // a list of all neighbors of this GamePiece (independent of wire connection)
  ArrayList<GamePiece> neighborList;
  // a HashMap of all neighbors of this GamePiece. HashMap contains:
  //  - key: String representing the location in which a neighbor is located
  //  - value: a neighboring GamePiece
  HashMap<String, GamePiece> neighborHash;
  // is this GamePiece receiving power?
  boolean isPowered;
  // how far away is this GamePiece from the powerStation?
  int distFromPower;

  // Constructor for a GamePiece
  GamePiece(int i, int j) {
    this.row = j;
    this.col = i;
    this.lef = false;
    this.rig = false;
    this.top = false;
    this.bot = false;
    this.wireLeft = false;
    this.wireRight = false;
    this.wireTop = false;
    this.wireBot = false;
    this.powerStation = false;
    this.neighborList = new ArrayList<>();
    this.neighborHash = new HashMap<>();
    this.distFromPower = 2147483647;
  }

  // to draw this piece given its wire specifications
  WorldImage draw(int size, int radius) {
    Color power = this.powerGradient(radius);
    WorldImage wi = new OverlayImage(
            new RectangleImage(size - 1, size - 1, OutlineMode.SOLID, Color.DARK_GRAY),
            new RectangleImage(size, size, OutlineMode.SOLID, Color.black));
    if (this.wireRight) { // if there is a right wire
      wi = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE,
              new RectangleImage(20, 3, OutlineMode.SOLID, power), 0, 0, wi);
    }
    if (this.wireLeft) { // if there is a left wire
      wi = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE,
              new RectangleImage(20, 3, OutlineMode.SOLID, power), 0, 0, wi);
    }
    if (this.wireTop) { // if there is a top wire
      wi = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP,
              new RectangleImage(3, 20, OutlineMode.SOLID, power), 0, 0, wi);
    }
    if (this.wireBot) { // if there is a bottom wire
      wi = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM,
              new RectangleImage(3, 20, OutlineMode.SOLID, power), 0, 0, wi);
    }
    return wi;
  }

  // returns a color according to the distFromPower
  Color powerGradient(int radius) {
    if (this.distFromPower <= 0.010 * radius) {
      return new Color(255, 255, 204);
    }
    if (this.distFromPower <= 0.025 * radius) {
      return new Color(255, 255, 153);
    }
    if (this.distFromPower <= 0.050 * radius) {
      return new Color(255, 255, 102);
    }
    if (this.distFromPower <= 0.100 * radius) {
      return new Color(255, 255, 51);
    }
    if (this.distFromPower <= 0.125 * radius) {
      return new Color(240, 230, 0);
    }
    if (this.distFromPower <= 0.50 * radius) {
      return new Color(204, 204, 0);
    }
    if (this.distFromPower <= 0.75 * radius) {
      return new Color(153, 153, 0);
    }
    if (this.distFromPower <= 0.95 * radius) {
      return new Color(102, 102, 0);
    }
    if (this.distFromPower <= radius) {
      return new Color(75, 75, 0);
    }
    return Color.black;
  }

  // EFFECT: adds the given GamePiece to this GamePiece's neighbors
  void addNeighbor(String str, GamePiece neighbor) {
    if (str.equals("top")) {
      this.neighborList.add(neighbor);
      this.neighborHash.put("top", neighbor);
    }
    if (str.equals("right")) {
      this.neighborList.add(neighbor);
      this.neighborHash.put("right", neighbor);
    }
    if (str.equals("bottom")) {
      this.neighborList.add(neighbor);
      this.neighborHash.put("bottom", neighbor);
    }
    if (str.equals("left")) {
      this.neighborList.add(neighbor);
      this.neighborHash.put("left", neighbor);
    }
  }

  // EFFECT: rotates the piece 90 degrees counter clockwise
  void rotate(String direction) {
    if (direction.equals("LeftButton")) {
      boolean temp = this.wireRight;
      this.wireRight = this.wireBot;
      this.wireBot = this.wireLeft;
      this.wireLeft = this.wireTop;
      this.wireTop = temp;
    }
    if (direction.equals("RightButton")) {
      boolean temp = this.wireRight;
      this.wireRight = this.wireTop;
      this.wireTop = this.wireLeft;
      this.wireLeft = this.wireBot;
      this.wireBot = temp;
    }
    this.updateConnections();
  }

  // EFFECT: updates this GamePiece's connections according to new wire locations
  void updateConnections() {
    this.top = false;
    this.rig = false;
    this.bot = false;
    this.lef = false;
    if (this.wireTop && this.neighborHash.containsKey("top")
            && this.neighborHash.get("top").wireBot) {
      this.top = true;
    }
    if (this.wireRight && this.neighborHash.containsKey("right")
            && this.neighborHash.get("right").wireLeft) {
      this.rig = true;
    }
    if (this.wireBot && this.neighborHash.containsKey("bottom")
            && this.neighborHash.get("bottom").wireTop) {
      this.bot = true;
    }
    if (this.wireLeft && this.neighborHash.containsKey("left")
            && this.neighborHash.get("left").wireRight) {
      this.lef = true;
    }
  }

  // EFFECT: updates whether or not this GamePiece contains a powerStation
  void updatePowerStation(int x, int y) {
    this.powerStation = x == this.col && y == this.row;
  }

  // is this GamePiece directly connected to the given GamePiece?
  boolean isConnected(GamePiece curr) {
    if (this.row + 1 == curr.row && this.col == curr.col) {
      return this.wireBot && curr.wireTop;
    }
    if (this.row == curr.row && this.col + 1 == curr.col) {
      return this.wireRight && curr.wireLeft;
    }
    if (this.row - 1 == curr.row && this.col == curr.col) {
      return this.wireTop && curr.wireBot;
    }
    if (this.row == curr.row && this.col - 1 == curr.col) {
      return this.wireLeft && curr.wireRight;
    }
    else {
      return false;
    }
  }

  // to return an ArrayList of GamePieces that are powered
  // EFFECT: updates how much power is received by the board using BFS
  ArrayList<GamePiece> powerUp(int radius,
                               int distFromPower,
                               ArrayList<GamePiece> acc,
                               ArrayList<GamePiece> powered) {
    acc.add(this);
    this.distFromPower = distFromPower;
    if (distFromPower <= radius) {
      this.isPowered = true;
      powered.add(this);
    }
    else {
      this.isPowered = false;
    }
    ArrayList<GamePiece> toCheck = new ArrayList<>();
    if (this.top) {
      toCheck.add(this.neighborHash.get("top"));
    }
    if (this.rig) {
      toCheck.add(this.neighborHash.get("right"));
    }
    if (this.bot) {
      toCheck.add(this.neighborHash.get("bottom"));
    }
    if (this.lef) {
      toCheck.add(this.neighborHash.get("left"));
    }
    if (toCheck.size() != 0) {
      for (int i = 0; i < toCheck.size(); i++) {
        GamePiece node = toCheck.get(i);
        if (!acc.contains(node) && this.isConnected(node)) {
          node.powerUp(radius, distFromPower + 1, acc, powered);
        }
      }
    }
    return powered;
  }

  // EFFECT: shuts down the power in this GamePiece
  void powerDown(ArrayList<GamePiece> powered) {
    this.isPowered = false;
    this.distFromPower = 2147483647;
    this.updateConnections();
  }
}
