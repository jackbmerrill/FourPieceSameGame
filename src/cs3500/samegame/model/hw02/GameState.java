package cs3500.samegame.model.hw02;

/**
 * Represents the game state.
 * Can be NotStarted, InProgress, or GameOver.
 */
public enum GameState {
  NotStarted, InProgress, GameOver;

  /**
   * Checks if the game state is currently running.
   *
   * @return true iff this equals InProgress
   */
  public boolean gameInProgress() {
    return this.equals(InProgress);
  }

  /**
   * Checks if the game state is over.
   *
   * @return true iff this equals GameOver
   */
  public boolean gameOver() {
    return this.equals(GameOver);
  }

}
