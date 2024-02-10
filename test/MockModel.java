import java.util.List;

import cs3500.samegame.model.hw02.SameGameModel;

/**
 * The mock model for testing controller inputs. Only works for this purpose.
 * @param <T> The type of the game piece.
 */
public class MockModel<T> implements SameGameModel<T> {

  private final StringBuilder reader;

  /**
   * The constructor for the mock model for testing controller inputs. Takes in an appendable
   *    in order to check the inputs are correct.
   * @param stringBuilder the given string builder to confirm inputs.
   */
  public MockModel(StringBuilder stringBuilder) {
    this.reader = stringBuilder;
  }

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    this.reader.append("\nfromRow: " + fromRow + ", fromCol: " + fromCol
            + ", toRow: " + toRow + ", toCol: " + toCol);
  }

  @Override
  public void removeMatch(int row, int col) {
    this.reader.append("\nrow: " + row + ", col: " + col);
  }

  @Override
  public int width() {
    return 0;
  }

  @Override
  public int length() {
    return 0;
  }

  @Override
  public T pieceAt(int row, int col) {
    return null;
  }

  @Override
  public int score() {
    return 0;
  }

  @Override
  public int remainingSwaps() {
    return 0;
  }

  @Override
  public boolean gameOver() {
    return false;
  }

  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    this.reader.append("rows: " + rows + ", cols: " + cols
            + ", swaps: " + swaps + ", random: " + random);
  }

  @Override
  public void startGame(List board, int swaps) {
    this.reader.append("Board: " + board + ", swaps: " + swaps);
  }

  @Override
  public T[] createListOfPieces() {
    return null;
  }

}
