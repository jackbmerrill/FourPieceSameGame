package cs3500.samegame.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.view.SameGameTextView;

/**
 * Public class for the Same Game Text Controller.
 *
 * @param <T> the type of game piece for the game
 */

public class SameGameTextController<T> implements SameGameController<T> {

  private SameGameModel<T> model;
  private final Readable input;
  private final Appendable output;
  private Scanner scan;
  private boolean quit;

  /**
   * A constructor for the SameGameTextController. Takes in an Appendable
   * and a readable for inputs and outputs.
   *
   * @param rd An object of the readable interface.
   * @param ap An object of the appendable interface.
   * @throws IllegalArgumentException throws if either the readable or appendable is null.
   */
  public SameGameTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if ((rd == null) || (ap == null)) {
      throw new IllegalArgumentException("Readable or appendable is null");
    }
    this.input = rd;
    this.output = ap;
  }

  @Override
  public void playGame(SameGameModel<T> model,
                       int rows, int cols, int swaps, boolean isRandom)
          throws IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Provided model cannot be null");
    }
    this.model = model;
    try {
      model.startGame(rows, cols, swaps, isRandom);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Game cannot be started as inputs are invalid.");
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Game has already started.");
    }
    this.control();
  }

  @Override
  public void playGame(SameGameModel<T> model, List<List<T>> board, int swaps)
          throws IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Provided model cannot be null");
    }
    this.model = model;
    try {
      model.startGame(board, swaps);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Game cannot be started as inputs are invalid.");
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Game has already started.");
    }
    this.control();
  }

  /**
   * Runs the game. Creates a new view using the model and appendable. Using a switch statement,
   * delegates commands to helpers. If a quit message is ever entered, it quits the game,
   * else if the game ends, it returns a game over screen.
   *
   * @throws IllegalStateException if readable or appendable fails
   */
  private void control() throws IllegalStateException {
    scan = new Scanner(input);
    quit = false;
    SameGameTextView<T> view = new SameGameTextView<T>(this.model, this.output);
    if (model.gameOver()) {
      writeMessage("Game over.");
      gameState(view);
      return;
    }
    gameState(view);
    while (!quit && scan.hasNext()) {
      String userInput = scan.next();
      switch (userInput) {
        case "q":
        case "Q":
          quit = true;
          break;
        case "m":
          match();
          break;
        case "s":
          swap();
          break;
        default:
          writeMessage("Invalid command. Try again. Valid commands are Q, q, s, m.");
      }
      if (model.gameOver()) {
        writeMessage("Game over.");
        gameState(view);
        return;
      }
      if (quit) {
        writeMessage("Game quit!");
        writeMessage("State of game when quit:");
        gameState(view);
        return;
      }
      gameState(view);
    }
    throw new IllegalStateException("Readable has failed.");
  }

  /**
   * Runs swap on the board. Goes through and checks each input for validity.
   * If given input is not an Integer, keeps trying. If a quit message, returns and ends game.
   * If an invalid input for the game, returns a set message to the board.
   *
   * @throws IllegalStateException if readable or appendable fails
   */
  private void swap() {
    int row1 = checkInput();
    if (quit) {
      return;
    }
    int col1 = checkInput();
    if (quit) {
      return;
    }
    int row2 = checkInput();
    if (quit) {
      return;
    }
    int col2 = checkInput();
    if (quit) {
      return;
    }
    try {
      model.swap(row1, col1, row2, col2);
    } catch (IllegalStateException e) {
      writeMessage("Invalid move. Try again. There are no swaps remaining.");
    } catch (IllegalArgumentException e) {
      writeMessage("Invalid move. Try again. Swap coordinates are illegal.");
    }
  }

  /**
   * Runs match on the board. Goes through and checks each input for validity.
   * If given input is not an Integer, keeps trying. If a quit message, returns and ends game.
   * If an invalid input for the game, returns a set message to the board.
   *
   * @throws IllegalStateException if readable or appendable fails
   */
  private void match() {
    int row = checkInput();
    if (quit) {
      return;
    }
    int col = checkInput();
    if (quit) {
      return;
    }
    try {
      model.removeMatch(row, col);
    } catch (IllegalStateException e) {
      writeMessage("Invalid move. Try again. The chosen piece is invalid");
    } catch (IllegalArgumentException e) {
      writeMessage("Invalid move. Try again. At least one argument is out of bounds");
    }
  }

  /**
   * Renders the current game state, remaining swaps, and score, appending it to the appendable.
   *
   * @param view The view of the given model.
   */
  private void gameState(SameGameTextView<T> view) {
    view.render();
    writeMessage("\nRemaining swaps: " + model.remainingSwaps());
    writeMessage("Score: " + model.score());
  }

  /**
   * Appends a message to the appendable of this game controller.
   *
   * @param message The desired method to be output
   * @throws IllegalStateException if there is an IO exception
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      output.append(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable has failed.");
    }
  }

  /**
   * Checks the given input. If the input is a valid integer, it returns the integer.
   * If it is a "q" or "Q" to quit the game it returns a 0 and sets quit to true.
   * If it is not a valid integer or a message to quit, the method will recurse.
   * Negatives will cause the method to recurse.
   *
   * @return A valid integer or 0.
   * @throws IllegalStateException if there is nothing else to be read.
   */
  private int checkInput() throws IllegalStateException {
    try {
      String desired = scan.next();
      if (desired.equals("Q") || desired.equals("q")) {
        quit = true;
        return 0;
      }
      int num = Integer.parseInt(desired);
      if (num < 0) {
        return checkInput();
      } else {
        return num - 1;
      }
    } catch (NumberFormatException e) {
      return checkInput();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Readable has failed");
    }
  }

}
