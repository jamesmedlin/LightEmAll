import java.util.Random;

// to represent an Edge in a LightEmAll game
class Edge {
  // the two GamePieces connected by this Edge
  GamePiece fromNode;
  GamePiece toNode;
  // the weight of this Edge
  int weight;

  // Constructor for an Edge with a randomly assigned weight
  Edge(GamePiece fromNode, GamePiece toNode, int maxWeight) {
    this(fromNode, toNode, maxWeight, new Random());
  }

  // Constructor for an Edge with a specified Random object
  Edge(GamePiece fromNode, GamePiece toNode, int maxWeight, Random rand) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.weight = rand.nextInt(maxWeight);
  }
}