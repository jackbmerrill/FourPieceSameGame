package cs3500.samegame.model.hw02;

/**
 * Represents a game piece on the board.
 * Equals and hashcode do not need to be declared as
 * enum already handles those methods.
 */
public enum GamePiece {

  RED, BLUE, YELLOW, GREEN;

  @Override
  public String toString() {
    switch (this) {
      case RED:
        return "R";
      case BLUE:
        return "B";
      case YELLOW:
        return "Y";
      case GREEN:
        return "G";
      default:
        return "X";
    }
  }

}
