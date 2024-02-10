package cs3500.samegame;

import java.io.InputStreamReader;

import cs3500.samegame.controller.SameGameTextController;
import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.SameGameModel;

/**
 * Main class for actually running and playing the game.
 */
public class Main {

  /**
   * Main method for running the game.
   * @param args Arguments to be entered
   */
  public static void main(String[] args) {
    SameGameModel model = new FourPieceSameGame();
    new SameGameTextController<>(new InputStreamReader(System.in),
            System.out).playGame(model, 4, 4, 1, true);
  }
}
