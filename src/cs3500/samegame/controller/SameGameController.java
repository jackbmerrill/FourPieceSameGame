package cs3500.samegame.controller;

import java.util.List;

import cs3500.samegame.model.hw02.SameGameModel;

/**
 * Controller for any type of this game. Assumes any inputs given
 * are natural numbers (0+) and the game indexes starting from 1.
 *
 * @param <T> the type representing the pieces in the game
 */
public interface SameGameController<T> {

  /**
   * Plays the game with the given the model.
   * The game is started with the given customization as well.
   * <p> Assumes all indices are 1-based.</p>
   *
   * @param model model of the current game
   * @param rows number of rows in the board
   * @param cols number of cols in the board
   * @param swaps number of swaps allowed in the game
   * @param isRandom true if the board should be setup randomly
   * @throws IllegalArgumentException if model is null or game cannot be started
   *     with the given arguments
   * @throws IllegalStateException if I/O fails for any reason
   */
  void playGame(SameGameModel<T> model, int rows, int cols, int swaps, boolean isRandom)
      throws IllegalStateException;


  /**
   * Plays the game with the given the model and the given board.
   * <p> Assumes all indices are 1-based.</p>
   *
   * @param model model of the current game
   * @param board the board to start the game with
   * @param swaps  the number of swaps allowed in the game
   * @throws IllegalArgumentException if model is null or game cannot be started with
   *     the given arguments
   * @throws IllegalStateException if I/O fails for any reason
   */
  void playGame(SameGameModel<T> model, List<List<T>> board, int swaps)
      throws IllegalStateException;
}
