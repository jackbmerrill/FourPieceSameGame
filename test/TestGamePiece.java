import org.junit.Test;

import cs3500.samegame.model.hw02.GamePiece;

import static org.junit.Assert.assertEquals;

/**
 * Test for the GamePiece enumerator methods.
 */

public class TestGamePiece {

  @Test
  public void testToString() {
    assertEquals("R", GamePiece.RED.toString());
    assertEquals("B", GamePiece.BLUE.toString());
    assertEquals("G", GamePiece.GREEN.toString());
    assertEquals("Y", GamePiece.YELLOW.toString());
  }



}
