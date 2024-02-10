package cs3500.samegame.view;

import java.io.IOException;

import cs3500.samegame.model.hw02.SameGameModel;

/**
 * Represents the view of the Same Game.
 *
 * @param <GamePiece> the game piece
 */
public class SameGameTextView<GamePiece> implements SameGameView<GamePiece> {
  private final SameGameModel<GamePiece> model;

  private final Appendable appendable;

  /**
   * Constructor for the view that takes in only a model. Generates a new appendable.
   *
   * @param model the model of the game
   */
  public SameGameTextView(SameGameModel<GamePiece> model) {
    this.model = model;
    this.appendable = new StringBuilder();
  }

  /**
   * Constructor for the view that takes in both a model and an appendable.
   * @param model the model of the game
   * @param appendable the given appendable for the game
   */
  public SameGameTextView(SameGameModel<GamePiece> model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }


  @Override
  public String toString() {
    int modelRow = this.model.width();
    int modelColumn = this.model.length();

    String print = "";
    for (int row = 0; row < modelRow; row++) {
      for (int col = 0; col < modelColumn; col++) {
        if (this.model.pieceAt(row, col) == null) {
          print += "X";
        } else {
          print += this.model.pieceAt(row, col).toString();
        }
        if (col < modelColumn - 1) {
          print += " ";
        }
      }
      if (row < modelRow - 1) {
        print += "\n";
      }
    }
    return print;
  }

  @Override
  public void render() {
    try {
      appendable.append(this.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Appendable has failed");
    }
  }
}
