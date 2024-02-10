import org.junit.Test;

import cs3500.samegame.model.hw02.GameState;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Test for the GameState enumerator methods.
 */
public class TestGameState {

  @Test
  public void testGameOver() {
    assertTrue(GameState.GameOver.gameOver());
    assertFalse(GameState.InProgress.gameOver());
    assertFalse(GameState.NotStarted.gameOver());
  }

  @Test
  public void testGameStarted() {
    assertFalse(GameState.GameOver.gameInProgress());
    assertTrue(GameState.InProgress.gameInProgress());
    assertFalse(GameState.NotStarted.gameInProgress());
  }

}
