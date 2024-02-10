package cs3500.samegame.model.hw02;

import java.util.List;

/**
 * Represents a FourPieceSameGame using GamePiece as pieces.
 */
public class FourPieceSameGame implements SameGameModel<GamePiece> {

  private GamePiece[][] gameBoard;
  private int swapCount;
  private int score;
  private int column;
  private int row;
  private GameState state;

  public FourPieceSameGame() {
    this.score = 0;
    this.state = GameState.NotStarted;
  }

  /**
   * Swap method implication note.
   *
   * @implNote the swap must be either in the same column or same row
   */
  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    gameIsRunning();
    inBounds(fromRow, fromCol);
    inBounds(toRow, toCol);
    if (this.gameBoard[fromRow][fromCol] == null && this.gameBoard[toRow][toCol] == null) {
      throw new IllegalArgumentException("There are no valid pieces in these positions");
    } else if (fromRow == toRow && fromCol == toCol || fromCol != toCol && fromRow != toRow) {
      throw new IllegalStateException("Entered argument is invalid");
    }
    if (this.swapCount == 0) {
      throw new IllegalStateException("There are no swaps remaining");
    }
    GamePiece store = this.gameBoard[fromRow][fromCol];
    this.gameBoard[fromRow][fromCol] = this.gameBoard[toRow][toCol];
    this.gameBoard[toRow][toCol] = store;
    this.swapCount--;
    isGameOver();
  }

  @Override
  public void removeMatch(int row, int col) {
    gameIsRunning();
    inBounds(row, col);
    if (this.gameBoard[row][col] == null) {
      throw new IllegalStateException("No valid piece in this position");
    }
    if (!this.isTouching(row, col)) {
      throw new IllegalStateException("There is no match at this position");
    }
    if (!isTouchingTwo(row, col)) {
      if (!isTouchingTouching(row, col)) {
        throw new IllegalStateException("There is no match at this position");
      }
    }

    this.score -= 2;
    removeMatchHelper(row, col);
    isGameOver();
  }

  /**
   * Checks if any touching match piece of the given piece is touching 2 pieces.
   *
   * @param row the row of the given piece
   * @param col the column of the given piece
   * @return true iff a neighboring piece is touching at least 2
   */
  private boolean isTouchingTouching(int row, int col) {
    int count = 0;
    GamePiece match = this.gameBoard[row][col];
    if (row > 0) {
      if (gameBoard[row - 1][col] == match) {
        if (isTouchingTwo(row - 1, col)) {
          count++;
        }
      }
    }
    if (col > 0) {
      if (gameBoard[row][col - 1] == match) {
        if (isTouchingTwo(row, col - 1)) {
          count++;
        }
      }
    }
    if (row < width() - 1) {
      if (gameBoard[row + 1][col] == match) {
        if (isTouchingTwo(row + 1, col)) {
          count++;
        }
      }
    }
    if (col < length() - 1) {
      if (gameBoard[row][col + 1] == match) {
        if (isTouchingTwo(row, col + 1)) {
          count++;
        }
      }
    }
    return count > 0;
  }

  /**
   * Takes in a piece coordinates that has been verified to have a match. Adds 1 to the score
   * and goes through all touching tiles, if any are a match, it calls the method on them.
   *
   * @param row the given row
   * @param col the given column
   */
  private void removeMatchHelper(int row, int col) {
    inBounds(row, col);
    GamePiece match = gameBoard[row][col];
    score++;
    gameBoard[row][col] = null;

    if (row > 0) {
      if (gameBoard[row - 1][col] == match) {
        removeMatchHelper(row - 1, col);
      }
    }

    if (col > 0) {
      if (gameBoard[row][col - 1] == match) {
        removeMatchHelper(row, col - 1);
      }
    }

    if (row < width() - 1) {
      if (gameBoard[row + 1][col] == match) {
        removeMatchHelper(row + 1, col);
      }
    }

    if (col < length() - 1) {
      if (gameBoard[row][col + 1] == match) {
        removeMatchHelper(row, col + 1);
      }
    }
  }

  /**
   * Checks if the given piece is touching at least one of the same piece. If the given coords are
   * on the edge of the board, does not test for touching there.
   *
   * @param row the row value
   * @param col the column value
   * @return true iff this piece is touching the same piece
   */
  private boolean isTouching(int row, int col) {
    GamePiece match = gameBoard[row][col];
    if (match == null) {
      return false;
    }
    if (row > 0) {
      if (gameBoard[row - 1][col] == match) {
        return true;
      }
    }
    if (col > 0) {
      if (gameBoard[row][col - 1] == match) {
        return true;
      }
    }
    if (row < width() - 1) {
      if (gameBoard[row + 1][col] == match) {
        return true;
      }
    }
    if (col < length() - 1) {
      return gameBoard[row][col + 1] == match;
    }
    return false;
  }

  /**
   * Checks if the given piece is touching at least two of the same piece. If the given coords are
   * on the edge of the board, does not test for touching there.
   *
   * @param row the row value
   * @param col the column value
   * @return true iff this piece is touching two of the same piece
   */
  private boolean isTouchingTwo(int row, int col) {
    GamePiece match = gameBoard[row][col];
    int count = 0;
    if (match == null) {
      return false;
    }
    if (row > 0) {
      if (gameBoard[row - 1][col] == match) {
        count++;
      }
    }
    if (col > 0) {
      if (gameBoard[row][col - 1] == match) {
        count++;
      }
    }
    if (row < width() - 1) {
      if (gameBoard[row + 1][col] == match) {
        count++;
      }
    }
    if (col < length() - 1) {
      if (gameBoard[row][col + 1] == match) {
        count++;
      }
    }
    return count > 1;
  }

  @Override
  public int width() {
    gameStarted();
    return this.row;
  }

  @Override
  public int length() {
    gameStarted();
    return this.column;
  }

  /**
   * Throws an error iff the game is not running.
   */
  private void gameIsRunning() {
    if (!this.state.gameInProgress()) {
      throw new IllegalStateException("Game is not running.");
    }
  }

  /**
   * Throws an error iff the game has not started.
   */
  private void gameStarted() {
    if (this.state == GameState.NotStarted) {
      throw new IllegalStateException("Game has not started");
    }
  }


  @Override
  public GamePiece pieceAt(int row, int col) {
    gameStarted();
    inBounds(row, col);
    return this.gameBoard[row][col];
  }

  /**
   * checks if the entered arguments are in bounds.
   *
   * @param row the row
   * @param col the column
   */
  private void inBounds(int row, int col) {
    if (row >= this.row || col >= this.column || col < 0 || row < 0) {
      throw new IllegalArgumentException("Position is out of bounds");
    }
  }

  @Override
  public int score() {
    gameStarted();
    return this.score;
  }

  @Override
  public int remainingSwaps() {
    gameStarted();
    return this.swapCount;
  }


  //create is game over method -> make this just return the game state
  @Override
  public boolean gameOver() {
    gameStarted();
    return state.gameOver();
  }

  /**
   * Checks if the game is over, if so, updates the game state.
   */
  private void isGameOver() {
    int rcount = 0;
    int bcount = 0;
    int gcount = 0;
    int ycount = 0;
    //check is touching two for every tile
    //if yes for any, game continues -> return
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.column; col++) {
        if (isTouchingTwo(row, col)) {
          return;
        }
        if (gameBoard[row][col] == GamePiece.RED) {
          rcount++;
        } else if (gameBoard[row][col] == GamePiece.YELLOW) {
          ycount++;
        } else if (gameBoard[row][col] == GamePiece.BLUE) {
          bcount++;
        } else if (gameBoard[row][col] == GamePiece.GREEN) {
          gcount++;
        }
      }
    }
    if (remainingSwaps() > 0) {
      if (ycount > 2 || bcount > 2 || gcount > 2 || rcount > 2) {
        return;
      }
    }
    //if no touch && swaps -> return could touch on whole board
 
    //if no && no swaps -> update game state
    state = GameState.GameOver;
  }

  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    if (this.state.gameOver() || this.state.gameInProgress()) {
      throw new IllegalStateException("the game has already started");
    }
    this.initSwap(swaps);
    if (rows < 1 || cols < 1) {
      throw new IllegalArgumentException("rows and cols must be greater than 0");
    }
    this.state = GameState.InProgress;
    this.column = cols;
    this.row = rows;
    this.createEmptyBoard();
    if (random) {
      this.createRandomGameBoard();
    } else {
      this.createDeterministicGameBoard();
    }
    isGameOver();
  }

  /**
   * Implication notes.
   *
   * @implNote if any spaces are left as null, they will be run as null within the game.
   *     The checking of game started and swaps is delegated to the initStart helper
   */
  @Override
  public void startGame(List<List<GamePiece>> board, int swaps) {
    if (this.state.gameOver() || this.state.gameInProgress()) {
      throw new IllegalStateException("the game has already started");
    }
    if (board == null) {
      throw new IllegalArgumentException("Board cannot be null");
    } else if (board.isEmpty()) {
      throw new IllegalArgumentException("Board cannot be null");
    } else if (board.contains(null)) {
      throw new IllegalArgumentException("Board cannot be null");
    }
    for (List<GamePiece> check : board) {
      if (check.size() != board.get(0).size()) {
        throw new IllegalArgumentException("Board must be a valid rectangle");
      }
    }
    this.initSwap(swaps);
    this.state = GameState.InProgress;
    this.row = board.size();
    this.column = board.get(0).size();
    this.createEmptyBoard();
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        this.gameBoard[i][j] = board.get(i).get(j);
      }
    }
    isGameOver();
  }


  /**
   * Initializes the number of swaps and checks if they are negative.
   *
   * @param swaps the amount of swaps for a game
   * @throws IllegalArgumentException if the number of swaps is negative
   */
  private void initSwap(int swaps) {
    if (swaps < 0) {
      throw new IllegalArgumentException("swaps cannot be negative");
    }
    this.swapCount = swaps;
  }


  /**
   * Generates a deterministic game board.
   * To create the deterministic board, it goes through the list of pieces repeating itself.
   * i.e. a 3x3 board would be [[RED, GREEN, BLUE], [YELLOW, RED, GREEN], [BLUE, YELLOW, RED]]
   */
  private void createDeterministicGameBoard() {
    GamePiece[] source = this.createListOfPieces();
    int iter = 0;
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.column; col++) {
        this.gameBoard[row][col] = source[iter];
        iter++;
        if (iter == source.length) {
          iter = 0;
        }
      }
    }
  }


  /**
   * Creates a random game board, directly updating the gameBoard field.
   * Each call creates a new randomly generated board.
   */
  private void createRandomGameBoard() {
    GamePiece[] source = this.createListOfPieces();
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        this.gameBoard[i][j] = source[(int) (Math.random() * source.length)];
      }
    }
  }

  /**
   * Creates an empty game board according to the r and c fields.
   */
  private void createEmptyBoard() {
    this.gameBoard = new GamePiece[this.row][this.column];
    for (int row = 0; row < this.row; row++) {
      this.gameBoard[row] = new GamePiece[this.column];
    }
  }

  @Override
  public GamePiece[] createListOfPieces() {
    return new GamePiece[]{GamePiece.RED, GamePiece.GREEN, GamePiece.BLUE, GamePiece.YELLOW};
  }


}
