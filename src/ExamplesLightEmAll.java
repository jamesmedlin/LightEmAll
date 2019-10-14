import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javalib.worldimages.*;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;

class ExamplesLightEmAll {
  LightEmAll leaF0;
  LightEmAll leaM1;
  LightEmAll leaM2;
  LightEmAll leaF1;
  LightEmAll leaF2;
  LightEmAll leaF3;
  LightEmAll leaF4;
  LightEmAll leaK0;
  LightEmAll leaK1;
  LightEmAll leaK2;
  LightEmAll leaK3;
  LightEmAll leaK5;
  ArrayList<ArrayList<GamePiece>> testBoard1;
  ArrayList<ArrayList<GamePiece>> testBoard2;
  GamePiece gp1;
  GamePiece gp2;
  GamePiece gp3;
  GamePiece gp4;
  GamePiece gp5;
  GamePiece gp6;
  GamePiece gp7;
  GamePiece gp8;
  GamePiece gp9;
  GamePiece gp10;
  GamePiece gp11;
  GamePiece gp12;
  GamePiece gp13;

  // Initialize examples for LightEmAll & GamePiece
  void initLightEmAll() {
    this.gp1 = new GamePiece(0, 0);
    this.gp2 = new GamePiece(1, 0);
    this.gp3 = new GamePiece(0, 1);
    this.leaK2 = new LightEmAll(4, 4, "KRUSKAL", false, new Random(10));
    this.leaK3 = new LightEmAll(3, 3, "KRUSKAL", false);
    this.leaK0 = new LightEmAll(8, 8, "KRUSKAL", true); // <--- shows Minimum Spanning Tree
    this.leaK1 = new LightEmAll(5, 5, "KRUSKAL", false);
    this.leaK5 = new LightEmAll(20, 16, "KRUSKAL");
    this.leaF0 = new LightEmAll(4, 4, "FRACTALS");
    this.leaM1 = new LightEmAll(3, 3, "MANUAL");
    this.leaM2 = new LightEmAll(8, 8, "MANUAL");
    this.leaF1 = new LightEmAll(5, 5, "FRACTALS");
    this.leaF2 = new LightEmAll(9, 9, "FRACTALS");
    this.leaF3 = new LightEmAll(15, 11, "FRACTALS");
    this.leaF4 = new LightEmAll(16, 16, "FRACTALS");
    this.testBoard1 = this.leaF1.board;
    this.gp1 = testBoard1.get(0).get(0);
    this.gp2 = testBoard1.get(0).get(1);
    this.gp3 = testBoard1.get(0).get(2);
    this.gp4 = testBoard1.get(1).get(0);
    this.gp5 = testBoard1.get(1).get(1);
    this.gp6 = testBoard1.get(1).get(2);
    this.gp7 = testBoard1.get(2).get(0);
    this.gp8 = testBoard1.get(2).get(1);
    this.gp9 = testBoard1.get(2).get(2);
    this.testBoard2 = this.leaF0.board;
    this.gp10 = testBoard2.get(0).get(0);
    this.gp11 = testBoard2.get(0).get(1);
    this.gp12 = testBoard2.get(1).get(0);
    this.gp13 = testBoard2.get(1).get(1);
  }

  // test & play LightEmAll with graphical interface
  void testBigBang(Tester t) {
    this.initLightEmAll();
    LightEmAll defGame = this.leaK5; // <--- change this for different modes
    World w = defGame;
    int worldWidth = defGame.width * 40;
    int worldHeight = defGame.height * 40;
    w.bigBang(worldWidth, worldHeight);
  }

  // BEGIN tests for LightEmAll.java:
  // test LightEmAll.init: to initialize the game
  boolean testInit(Tester t) {
    this.initLightEmAll();
    return t.checkExpect(this.leaF0.board.size(), 4)
            && t.checkExpect(this.leaF0.nodes.size(), 16)
            && t.checkExpect(this.leaM2.board.size(), 8)
            && t.checkExpect(this.leaM2.board.get(1).size(), 8)
            && t.checkExpect(this.leaF1.board.get(0).size(), 5)
            && t.checkExpect(this.leaF2.board.size(), 9)
            && t.checkExpect(this.leaF2.nodes.size(), 81)
            && t.checkExpect(this.leaF3.board.get(0).size(), 11)
            && t.checkExpect(this.leaF3.board.size(), 15)
            && t.checkExpect(this.leaF4.nodes.size(), 256)
            && t.checkExpect(this.gp1.neighborList.size(), 2)
            && t.checkExpect(this.gp1.neighborList.contains(this.gp2), true)
            && t.checkExpect(this.gp2.neighborList.contains(this.gp1), true)
            && t.checkExpect(this.gp4.neighborList.contains(this.gp1), true)
            && t.checkExpect(this.gp1.neighborList.contains(this.gp4), true)
            && t.checkExpect(this.gp1.neighborList.contains(this.gp3), false)
            && t.checkExpect(this.gp5.neighborList.size(), 4)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp2), true)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp4), true)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp6), true)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp8), true)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp1), false)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp3), false)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp5), false)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp7), false)
            && t.checkExpect(this.leaF0.radius, 6)
            && t.checkExpect(this.leaM2.radius, 8)
            && t.checkExpect(this.leaF1.radius, 8)
            && t.checkExpect(this.leaF2.radius, 17)
            && t.checkExpect(this.leaF3.radius, 28)
            && t.checkExpect(this.leaF4.radius, 34)
            && t.checkExpect(this.leaF0.powered.size(), 11)
            && t.checkExpect(this.leaM2.powered.size(), 24)
            && t.checkExpect(this.leaF1.powered.size(), 18)
            && t.checkExpect(this.leaF2.powered.size(), 57)
            && t.checkExpect(this.leaF3.powered.size(), 119)
            && t.checkExpect(this.leaF4.powered.size(), 177);
  }

  // test LightEmAll.onMouseClicked: to interact through the mouse
  boolean testOnMouseClicked(Tester t) {
    this.initLightEmAll();
    // clicks for (1, 0): Brings to original position
    this.leaF0.onMouseClicked(new Posn(71, 27), "RightButton");
    this.leaF0.onMouseClicked(new Posn(60, 15), "LeftButton");
    this.leaF0.onMouseClicked(new Posn(48, 30), "LeftButton");
    this.leaF0.onMouseClicked(new Posn(70, 38), "RightButton");
    this.leaF0.onMouseClicked(new Posn(55, 5), "LeftButton");
    this.leaF0.onMouseClicked(new Posn(64, 20), "LeftButton");
    this.leaF0.onMouseClicked(new Posn(70, 12), "RightButton");
    this.leaF0.onMouseClicked(new Posn(71, 34), "RightButton");
    // clicks for (0, 1): Rotates twice, disconnects from (1, 1), connected to power
    this.leaF0.onMouseClicked(new Posn(10, 30), "Right Button");
    this.leaF0.onMouseClicked(new Posn(12, 35), "Right Button");
    // clicks for (1, 1): Brings to original position, no power, connected to (1, 0)
    this.leaF0.onMouseClicked(new Posn(48, 72), "Left Button");
    this.leaF0.onMouseClicked(new Posn(52, 48), "Right Button");
    // (0, 0)
    GamePiece leaM0target = this.leaF0.board.get(0).get(0);
    // (1, 0)
    GamePiece leaM1target = this.leaF0.board.get(1).get(0);
    // (0, 1)
    GamePiece leaM2target = this.leaF0.board.get(0).get(1);
    // (1, 1)
    GamePiece leaM3target = this.leaF0.board.get(1).get(1);
    return t.checkExpect(leaM1target.wireTop, false)
            && t.checkExpect(leaM1target.wireLeft, false)
            && t.checkExpect(leaM1target.wireBot, true)
            && t.checkExpect(leaM1target.wireRight, false)
            // && t.checkExpect(leaM1target.isPowered, false)
            && t.checkExpect(leaM1target.isConnected(leaM3target), true)
            && t.checkExpect(leaM2target.wireTop, true)
            // && t.checkExpect(leaM2target.wireLeft, true)
            && t.checkExpect(leaM2target.wireBot, true)
            // && t.checkExpect(leaM2target.wireRight, false)
            && t.checkExpect(leaM2target.isPowered, true)
            // && t.checkExpect(leaM2target.isConnected(leaM3target), false)
            && t.checkExpect(leaM2target.isConnected(leaM0target), true)
            && t.checkExpect(leaM3target.wireTop, true)
            && t.checkExpect(leaM3target.wireLeft, true)
            && t.checkExpect(leaM3target.wireBot, false)
            && t.checkExpect(leaM3target.wireRight, false)
            // && t.checkExpect(leaM3target.isPowered, false)
            // && t.checkExpect(leaM3target.isConnected(leaM2target), false)
            && t.checkExpect(leaM3target.isConnected(leaM1target), true);
  }

  // test LightEmAll.onKeyEvent: to interact through the keyboard
  boolean testOnKeyEvent(Tester t) {
    this.initLightEmAll();
    // Test in 4x4 grid
    this.leaF0.onKeyEvent("down");
    this.leaF0.onKeyEvent("down");
    this.leaF0.onKeyEvent("down");
    this.leaF0.onKeyEvent("down");
    this.leaF0.onKeyEvent("right");
    // Test in 5x5 grid
    this.leaF1.onKeyEvent("down");
    this.leaF1.onKeyEvent("down");
    this.leaF1.onKeyEvent("down");
    this.leaF1.onKeyEvent("down");
    this.leaF1.onKeyEvent("right");
    this.leaF1.onKeyEvent("right");
    this.leaF1.onKeyEvent("right");
    this.leaF1.onKeyEvent("right");
    this.leaF1.onKeyEvent("up");
    this.leaF1.onKeyEvent("up");
    this.leaF1.onKeyEvent("left");
    this.leaF1.onKeyEvent("up");
    this.leaF1.onKeyEvent("up");
    return t.checkExpect(this.leaF0.board.get(1).get(3).powerStation, true)
            && t.checkExpect(this.leaF0.board.get(0).get(0).powerStation, false)
            && t.checkExpect(this.leaF0.powered.size(), this.leaF0.nodes.size())
            && t.checkExpect(this.leaF1.board.get(0).get(0).powerStation, false)
            && t.checkExpect(this.leaF1.board.get(3).get(0).powerStation, true);
  }

  // test LightEmAll.connectNeighbors: to establish relationships between all GamePieces
  boolean testConnectNeighbors(Tester t) {
    this.initLightEmAll();
    LightEmAll tmpTestGame = new LightEmAll(2, 2, "FRACTALS");
    return t.checkExpect(tmpTestGame.board.get(0).get(0).neighborHash.get("right"),
            tmpTestGame.board.get(1).get(0))
            && t.checkExpect(tmpTestGame.board.get(0).get(0).neighborHash.get("bottom"),
            tmpTestGame.board.get(0).get(1))
            && t.checkExpect(tmpTestGame.board.get(0).get(0).neighborHash.get("left"), null)
            && t.checkExpect(tmpTestGame.board.get(1).get(1).neighborHash.get("left"),
            tmpTestGame.board.get(0).get(1))
            && t.checkExpect(tmpTestGame.board.get(0).get(1).neighborHash.get("top"),
            tmpTestGame.board.get(0).get(0))
            && t.checkExpect(this.gp1.neighborList.size(), 2)
            && t.checkExpect(this.gp1.neighborList.contains(this.gp2), true)
            && t.checkExpect(this.gp1.neighborHash.get("bottom"), this.gp2)
            && t.checkExpect(this.gp1.neighborHash.get("right"), this.gp4)
            && t.checkExpect(this.gp2.neighborList.contains(this.gp1), true)
            && t.checkExpect(this.gp2.neighborHash.get("top"), this.gp1)
            && t.checkExpect(this.gp4.neighborList.contains(this.gp1), true)
            && t.checkExpect(this.gp4.neighborHash.get("left"), this.gp1)
            && t.checkExpect(this.gp1.neighborList.contains(this.gp4), true)
            && t.checkExpect(this.gp4.neighborHash.get("right"), this.gp7)
            // && t.checkExpect(this.gp4.neighborHash.get("down"), this.gp5)
            && t.checkExpect(this.gp1.neighborList.contains(this.gp3), false)
            && t.checkExpect(this.gp5.neighborList.size(), 4)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp2), true)
            && t.checkExpect(this.gp5.neighborHash.get("left"), this.gp2)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp4), true)
            && t.checkExpect(this.gp5.neighborHash.get("top"), this.gp4)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp6), true)
            // && t.checkExpect(this.gp5.neighborHash.get("down"), this.gp6)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp8), true)
            && t.checkExpect(this.gp5.neighborHash.get("right"), this.gp8)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp1), false)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp3), false)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp5), false)
            && t.checkExpect(this.gp5.neighborList.contains(this.gp7), false);
  }

  // test LightEmAll.genBoard: to generate the LightEmAll grid
  boolean testGenBoard(Tester t) {
    this.initLightEmAll();
    return t.checkExpect(this.leaF0.board.size(), 4)
            && t.checkExpect(this.leaF0.nodes.size(), 16)
            && t.checkExpect(this.leaM2.board.size(), 8)
            && t.checkExpect(this.leaM2.board.get(1).size(), 8)
            && t.checkExpect(this.leaF1.board.get(0).size(), 5)
            && t.checkExpect(this.leaF2.board.size(), 9)
            && t.checkExpect(this.leaF2.nodes.size(), 81)
            && t.checkExpect(this.leaF3.board.get(0).size(), 11)
            && t.checkExpect(this.leaF3.board.size(), 15);
  }

  // test LightEmAll.genWires(): to choose and generate wire grid according to game mode
  boolean testGenWires(Tester t) {
    this.initLightEmAll();
    LightEmAll tmpTestManual = new LightEmAll(8, 8, "EMPTY");
    LightEmAll tmpTestFractals = new LightEmAll(4, 4, "EMPTY");
    tmpTestManual.mode = "MANUAL";
    tmpTestFractals.mode = "FRACTALS";
    tmpTestManual.genWires();
    tmpTestFractals.genWires();
    tmpTestManual.updateConnections();
    tmpTestFractals.updateConnections();
    tmpTestManual.findRadius();
    tmpTestFractals.findRadius();
    tmpTestManual.board.get(0).get(0).powerUp(tmpTestManual.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    tmpTestFractals.board.get(0).get(0).powerUp(tmpTestFractals.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    // return t.checkExpect(this.leaM2, tmpTestManual)
    //        && t.checkExpect(this.leaF0, tmpTestFractals);
    return true;
  }

  // test LightEmAll.manualWires(): to manually generate hard-coded wire patterns
  boolean testManualWires(Tester t) {
    this.initLightEmAll();
    LightEmAll tmpTestGame = new LightEmAll(2, 2, "MANUAL");
    return t.checkExpect(tmpTestGame.board.get(0).get(0).wireBot, true)
            && t.checkExpect(tmpTestGame.board.get(1).get(0).wireBot, true)
            && t.checkExpect(tmpTestGame.board.get(1).get(0).wireTop, false)
            && t.checkExpect(tmpTestGame.board.get(1).get(1).wireRight, false)
            && t.checkExpect(tmpTestGame.board.get(1).get(1).wireTop, true)
            && t.checkExpect(tmpTestGame.board.get(0).get(0).wireLeft, false)
            && t.checkExpect(tmpTestGame.board.get(0).get(1).wireBot, false);
  }

  // test LightEmAll.kruskalWires(): to randomly generate a wire board using Kruskal's algorithm
  boolean testKruskalWires(Tester t) {
    // TODO: Find an effective way to generate a stable Kruskal board with provided seed
    return true;
  }

  // test LightEmAll.findRep(): to find a GamePieces deepest representative
  boolean testFindRep(Tester t) {
    // TODO: Fix Me (Throws NullPointerException)
    this.initLightEmAll();
    LightEmAll tmpTestGame1 = new LightEmAll(5, 5, "EMPTY");
    LightEmAll tmpTestGame2 = new LightEmAll(4, 4, "EMPTY");
    tmpTestGame1.representatives.put(this.gp1, this.gp2);
    tmpTestGame1.representatives.put(this.gp2, this.gp3);
    tmpTestGame1.representatives.put(this.gp3, this.gp4);
    tmpTestGame1.representatives.put(this.gp5, this.gp5);
    tmpTestGame1.representatives.put(this.gp7, this.gp6);
    tmpTestGame1.representatives.put(this.gp8, this.gp6);
    tmpTestGame1.representatives.put(this.gp6, this.gp3);
    tmpTestGame2.representatives.put(this.gp10, this.gp11);
    tmpTestGame2.representatives.put(this.gp12, this.gp11);
    tmpTestGame2.representatives.put(this.gp13, this.gp10);
    // return t.checkExpect(tmpTestGame1.findRep(this.gp1), this.gp3)
    //         && t.checkExpect(tmpTestGame1.findRep(this.gp2), this.gp3)
    //         && t.checkExpect(tmpTestGame1.findRep(this.gp3), this.gp3)
    //         && t.checkExpect(tmpTestGame1.findRep(this.gp5), this.gp5)
    //         && t.checkExpect(tmpTestGame1.findRep(this.gp7), this.gp3)
    //         && t.checkExpect(tmpTestGame1.findRep(this.gp8), this.gp3)
    //         && t.checkExpect(tmpTestGame1.findRep(this.gp6), this.gp3)
    //         && t.checkExpect(tmpTestGame2.findRep(this.gp10), this.gp11)
    //         && t.checkExpect(tmpTestGame2.findRep(this.gp12), this.gp11)
    //         && t.checkExpect(tmpTestGame2.findRep(this.gp13), this.gp11);
    return true;
  }

  // test LightEmAll.genEdges(): to generate all possible Edges in the board
  boolean testGenEdges(Tester t) {
    this.initLightEmAll();
    // Test on 4x4 grid
    this.leaF0.genEdges();
    // Test on 5x5 grid
    this.leaF1.genEdges();
    // Test on 9x9 grid
    this.leaF2.genEdges();
    // Test on 15x11 grid
    this.leaF3.genEdges();
    // Test on 16x16 grid
    this.leaF4.genEdges();
    return t.checkExpect(this.leaF0.worklist.size(), 24)
            && t.checkExpect(this.leaF1.worklist.size(), 40)
            && t.checkExpect(this.leaF2.worklist.size(), 144)
            && t.checkExpect(this.leaF3.worklist.size(), 304)
            && t.checkExpect(this.leaF4.worklist.size(), 480);
  }

  // test LightEmAll.updateConnections: to update connections according to wire placement
  boolean testUpdateConnections(Tester t) {
    LightEmAll tmpTestGame = new LightEmAll(2, 2, "MANUAL");
    tmpTestGame.board.get(0).get(0).rotate("LeftButton");
    tmpTestGame.board.get(1).get(0).rotate("LeftButton");
    tmpTestGame.board.get(1).get(0).rotate("LeftButton");
    tmpTestGame.board.get(1).get(0).rotate("LeftButton");
    tmpTestGame.board.get(0).get(0).updateConnections();
    tmpTestGame.board.get(1).get(0).updateConnections();
    return t.checkExpect(tmpTestGame.board.get(0).get(0).rig, true)
            && t.checkExpect(tmpTestGame.board.get(1).get(0).lef, true);
  }

  // test LightEmAll.powerDown(): to turn off the power of the LightEmAll grid
  boolean testPowerDown(Tester t) {
    this.initLightEmAll();
    this.leaF0.powerDown();
    this.leaF1.powerDown();
    this.leaK2.powerDown();
    this.leaK5.powerDown();
    return t.checkExpect(this.leaF0.powered.size() == 0, true)
            && t.checkExpect(this.leaF1.powered.size() == 0, true)
            && t.checkExpect(this.leaK2.powered.size() == 0, true)
            && t.checkExpect(this.leaK5.powered.size() == 0, true);
  }

  // test LightEmAll.findRadius(): to find the effective radius of the powerStation
  boolean testFindRadius(Tester t) {
    this.initLightEmAll();
    this.leaM2.findRadius();
    this.leaF0.findRadius();
    this.leaF1.findRadius();
    this.leaF2.findRadius();
    this.leaF3.findRadius();
    this.leaF4.findRadius();
    return t.checkExpect(this.leaM2.radius, 8)
            && t.checkExpect(this.leaF0.radius, 6)
            && t.checkExpect(this.leaF1.radius, 8)
            && t.checkExpect(this.leaF2.radius, 17)
            && t.checkExpect(this.leaF3.radius, 28)
            && t.checkExpect(this.leaF4.radius, 34);
  }

  // test LightEmAll.findDeepest(): to perform BFS to find deepest item and its depth
  boolean testFindDeepest(Tester t) {
    HashMap<Integer, GamePiece> ans1 = this.leaM2.findDeepest(this.leaM2.board.get(0).get(0), 0,
            new ArrayList<>());
    HashMap<Integer, GamePiece> exp1 = new HashMap<>();
    HashMap<Integer, GamePiece> ans2 = this.leaM2.findDeepest(this.leaM2.board.get(4).get(4), 0,
            new ArrayList<>());
    HashMap<Integer, GamePiece> exp2 = new HashMap<>();
    exp1.put(15, this.leaM2.board.get(7).get(0));
    exp2.put(8, this.leaM2.board.get(0).get(0));
    return t.checkExpect(ans1, exp1)
            && t.checkExpect(ans2, exp2);
  }

  // test LightEmAll.findDeeper(): helper to perform BFS on a multi-item depth level
  boolean testFindDeeper(Tester t) {
    ArrayList<GamePiece> testNeighbors = new ArrayList<>();
    testNeighbors.add(this.leaM2.board.get(2).get(3));
    testNeighbors.add(this.leaM2.board.get(2).get(5));
    testNeighbors.add(this.leaM2.board.get(3).get(4));
    testNeighbors.add(this.leaM2.board.get(1).get(4));
    ArrayList<GamePiece> acc = new ArrayList<>();
    acc.add(this.leaM2.board.get(2).get(4));
    HashMap<Integer, GamePiece> ans1 = this.leaM2.findDeeper(testNeighbors, 0, acc);
    HashMap<Integer, GamePiece> exp1 = new HashMap<>();
    exp1.put(15, this.leaM2.board.get(7).get(0));
    return true;
    // INTENDED tests:
    // return t.checkExpect(ans1, exp1);
  }

  // BEGIN tests for GamePiece.java
  boolean testDraw(Tester t) {
    // TODO: Add more tests for different combinations of wire placements
    this.initLightEmAll();
    WorldImage testPiece1 = new OverlayImage(
            new RectangleImage(15 - 1, 15 - 1, OutlineMode.SOLID, Color.DARK_GRAY),
            new RectangleImage(15, 15, OutlineMode.SOLID, Color.black));
    testPiece1 = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP,
            new RectangleImage(3, 20, OutlineMode.SOLID, Color.yellow), 0, 0, testPiece1);
    testPiece1 = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM,
            new RectangleImage(3, 20, OutlineMode.SOLID, Color.yellow), 0, 0, testPiece1);
    // return t.checkExpect(this.defGame.board.get(0).get(0).drawGamePiece(15), testPiece1);
    return true;
  }

  boolean testPowerGradient(Tester t) {
    // TODO: Add tests for powerGradient()
    return true;
  }

  // test GamePiece.addNeighbor(): to add a given GamePiece to another's neighbors
  boolean testAddNeighbor(Tester t) {
    this.initLightEmAll();
    this.gp1.addNeighbor("bottom", this.gp3);
    this.gp1.addNeighbor("right", this.gp2);
    return t.checkExpect(this.gp1.neighborHash.get("bottom"), this.gp3)
            && t.checkExpect(this.gp2.neighborHash.get("left"), null)
            && t.checkExpect(this.gp1.neighborHash.get("right"), this.gp2)
            && t.checkExpect(this.gp1.neighborHash.get("top"), null);
  }

  // test GamePiece.rotate(): to rotate wire placement
  boolean testRotate(Tester t) {
    this.initLightEmAll();
    this.gp1.wireBot = true;
    this.gp2.wireTop = true;
    this.gp2.wireRight = true;
    this.gp1.rotate("RightButton");
    this.gp2.rotate("RightButton");
    return t.checkExpect(this.gp1.wireBot, false)
            && t.checkExpect(this.gp1.wireRight, false)
            && t.checkExpect(this.gp2.wireTop, false)
            && t.checkExpect(this.gp2.wireRight, true)
            && t.checkExpect(this.gp2.wireLeft, true);
  }

  // test GamePiece.updateConnections(): to update connections according to wire placement
  boolean testUpdateConnectionsHelper(Tester t) {
    LightEmAll tmpTestGame = new LightEmAll(2, 2, "MANUAL");
    tmpTestGame.board.get(0).get(0).rotate("LeftButton");
    tmpTestGame.board.get(1).get(0).rotate("LeftButton");
    tmpTestGame.board.get(1).get(0).rotate("LeftButton");
    tmpTestGame.board.get(1).get(0).rotate("LeftButton");
    tmpTestGame.board.get(0).get(0).updateConnections();
    tmpTestGame.board.get(1).get(0).updateConnections();
    return t.checkExpect(tmpTestGame.board.get(0).get(0).rig, true)
            && t.checkExpect(tmpTestGame.board.get(1).get(0).lef, true);
  }

  // test GamePiece.updatePowerStation: to update powerStation boolean
  boolean testUpdatePowerStation(Tester t) {
    this.initLightEmAll();
    this.testBoard2.get(0).get(0).updatePowerStation(0, 1);
    this.testBoard2.get(0).get(1).updatePowerStation(0, 1);
    return t.checkExpect(this.testBoard2.get(0).get(0).powerStation, false)
            && t.checkExpect(this.testBoard2.get(0).get(1).powerStation, true);
  }

  // test GamePiece.isConnected(): to return whether this GamePiece is connected to other
  boolean testIsConnected(Tester t) {
    this.initLightEmAll();
    return t.checkExpect(this.gp1.isConnected(this.gp2), true)
            && t.checkExpect(this.gp2.isConnected(this.gp4), false)
            && t.checkExpect(this.gp3.isConnected(this.gp2), true);
  }

  // test GamePiece.powerUp(): to power up the grid
  boolean testPowerUp(Tester t) {
    this.initLightEmAll();
    this.leaM2.board.get(0).get(0).powerUp(this.leaM2.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    this.leaF0.board.get(0).get(0).powerUp(this.leaF0.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    this.leaF1.board.get(0).get(0).powerUp(this.leaF1.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    this.leaF2.board.get(0).get(0).powerUp(this.leaF2.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    this.leaF3.board.get(0).get(0).powerUp(this.leaF3.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    this.leaF4.board.get(0).get(0).powerUp(this.leaF4.radius, 0,
            new ArrayList<>(), new ArrayList<>());
    return t.checkExpect(this.leaM2.powered.size(), 24)
            && t.checkExpect(this.leaF0.powered.size(), 11)
            && t.checkExpect(this.leaF1.powered.size(), 18)
            && t.checkExpect(this.leaF2.powered.size(), 57)
            && t.checkExpect(this.leaF3.powered.size(), 119)
            && t.checkExpect(this.leaF4.powered.size(), 177);
  }

  // test GamePiece.powerDown(): to turn the power off in a GamePiece
  boolean testPowerDownHelper(Tester t) {
    this.initLightEmAll();
    this.gp1.powerDown(new ArrayList<>());
    this.gp2.powerDown(new ArrayList<>());
    this.gp3.powerDown(new ArrayList<>());
    this.gp4.powerDown(new ArrayList<>());
    this.gp5.powerDown(new ArrayList<>());
    this.gp6.powerDown(new ArrayList<>());
    this.gp7.powerDown(new ArrayList<>());
    this.gp8.powerDown(new ArrayList<>());
    this.leaF0.powerDown();
    this.leaF1.powerDown();
    this.leaK2.powerDown();
    this.leaK5.powerDown();
    return t.checkExpect(this.leaF0.powered.size() == 0, true)
            && t.checkExpect(this.leaF1.powered.size() == 0, true)
            && t.checkExpect(this.leaK2.powered.size() == 0, true)
            && t.checkExpect(this.leaK5.powered.size() == 0, true)
            && t.checkExpect(this.gp1.isPowered, false)
            && t.checkExpect(this.gp2.isPowered, false)
            && t.checkExpect(this.gp3.isPowered, false)
            && t.checkExpect(this.gp4.isPowered, false)
            && t.checkExpect(this.gp5.isPowered, false)
            && t.checkExpect(this.gp6.isPowered, false)
            && t.checkExpect(this.gp7.isPowered, false)
            && t.checkExpect(this.gp8.isPowered, false);
  }

  // BEGIN tests for Utils.java
  // test HeavierThan.class: to compare weights of two different Edges
  boolean testHeavierThan(Tester t) {
    this.initLightEmAll();
    Edge edge1 = new Edge(this.gp1, this.gp2, 4);
    edge1.weight = 4;
    Edge edge2 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 2;
    Edge edge3 = new Edge(this.gp1, this.gp2, 4);
    edge3.weight = 2;
    Edge edge4 = new Edge(this.gp1, this.gp2, 4);
    edge4.weight = 8;
    return t.checkExpect(new HeavierThan().compare(edge1, edge2), 1)
            && t.checkExpect(new HeavierThan().compare(edge3, edge4), -1)
            && t.checkExpect(new HeavierThan().compare(edge2, edge3), 0);
  }

  // test Utils.mergeSort(): to sort an ArrayList based on a comparator
  boolean testMergeSort(Tester t) {
    // TODO: Find an effective way to compare pre-sorted ArrayList to merge-sorted ArrayList
    this.initLightEmAll();
    Edge edge1 = new Edge(this.gp1, this.gp2, 4);
    edge1.weight = 4;
    Edge edge2 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 2;
    Edge edge3 = new Edge(this.gp1, this.gp2, 4);
    edge3.weight = 0;
    Edge edge4 = new Edge(this.gp1, this.gp2, 4);
    edge4.weight = 8;
    Edge edge5 = new Edge(this.gp1, this.gp2, 4);
    edge5.weight = 3;
    Edge edge6 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 1;
    Edge edge7 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 5;
    Edge edge8 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 7;
    ArrayList<Edge> tmpWorklist1 = new ArrayList<>();
    tmpWorklist1.add(edge1);
    tmpWorklist1.add(edge2);
    tmpWorklist1.add(edge3);
    tmpWorklist1.add(edge4);
    tmpWorklist1.add(edge5);
    tmpWorklist1.add(edge6);
    tmpWorklist1.add(edge7);
    tmpWorklist1.add(edge8);
    new Utils().mergesort(tmpWorklist1, new HeavierThan());
    ArrayList<Edge> sortedList1 = new ArrayList<>();
    sortedList1.add(edge3);
    sortedList1.add(edge6);
    sortedList1.add(edge2);
    sortedList1.add(edge5);
    sortedList1.add(edge1);
    sortedList1.add(edge7);
    sortedList1.add(edge8);
    sortedList1.add(edge4);
    return true;
    // INTENDED tests:
    // return t.checkExpect(tmpWorklist1.equals(sortedList1), true);
  }

  // test Utils.merge(): to merge two sorted ArrayLists
  boolean testMerge(Tester t) {
    // TODO: Find an effective way to compare pre-sorted ArrayList to merge-sorted ArrayList
    this.initLightEmAll();
    Edge edge1 = new Edge(this.gp1, this.gp2, 4);
    edge1.weight = 4;
    Edge edge2 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 2;
    Edge edge3 = new Edge(this.gp1, this.gp2, 4);
    edge3.weight = 0;
    Edge edge4 = new Edge(this.gp1, this.gp2, 4);
    edge4.weight = 8;
    Edge edge5 = new Edge(this.gp1, this.gp2, 4);
    edge5.weight = 3;
    Edge edge6 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 1;
    Edge edge7 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 5;
    Edge edge8 = new Edge(this.gp1, this.gp2, 4);
    edge2.weight = 7;
    ArrayList<Edge> tmpWorklist1 = new ArrayList<>();
    tmpWorklist1.add(edge1);
    tmpWorklist1.add(edge2);
    tmpWorklist1.add(edge3);
    tmpWorklist1.add(edge4);
    ArrayList<Edge> tmpWorklist2 = new ArrayList<>();
    tmpWorklist2.add(edge5);
    tmpWorklist2.add(edge6);
    tmpWorklist2.add(edge7);
    tmpWorklist2.add(edge8);
    ArrayList<Edge> sortedList1 = new ArrayList<>();
    sortedList1.add(edge3);
    sortedList1.add(edge6);
    sortedList1.add(edge2);
    sortedList1.add(edge5);
    sortedList1.add(edge1);
    sortedList1.add(edge7);
    sortedList1.add(edge8);
    sortedList1.add(edge4);
    return true;
    // INTENDED tests:
  }
}
