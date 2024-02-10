import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.view.SameGameTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for the view in same game.
 */

public class TestSameGameTextView {

  SameGameTextView<GamePiece> fives;
  SameGameTextView<GamePiece> twos;
  SameGameTextView<GamePiece> empty;
  StringBuilder fivesAppend;
  StringBuilder emptyAppend;
  FourPieceSameGame twoByTwo;

  @Before
  public void initData() {
    FourPieceSameGame fiver = new FourPieceSameGame();
    twoByTwo = new FourPieceSameGame();
    FourPieceSameGame nullBoard = new FourPieceSameGame();

    List<List<GamePiece>> fiveByFiveBoard = new ArrayList<>(List.of(
            new ArrayList<>(List.of(GamePiece.GREEN, GamePiece.RED,
                    GamePiece.RED, GamePiece.BLUE, GamePiece.YELLOW)),
            new ArrayList<>(List.of(GamePiece.GREEN, GamePiece.BLUE,
                    GamePiece.BLUE, GamePiece.YELLOW, GamePiece.YELLOW)),
            new ArrayList<>(List.of(GamePiece.GREEN, GamePiece.BLUE,
                    GamePiece.RED, GamePiece.BLUE, GamePiece.GREEN)),
            new ArrayList<>(List.of(GamePiece.YELLOW, GamePiece.GREEN,
                    GamePiece.BLUE, GamePiece.BLUE, GamePiece.YELLOW)),
            new ArrayList<>(List.of(GamePiece.GREEN, GamePiece.RED,
                    GamePiece.RED, GamePiece.RED, GamePiece.RED))));

    ArrayList<GamePiece> nullArray = new ArrayList<GamePiece>();
    nullArray.add(null);
    nullArray.add(null);
    nullArray.add(null);

    List<List<GamePiece>> threeByThreeNull = new ArrayList<>(
            List.of(nullArray, nullArray, nullArray));

    fiver.startGame(fiveByFiveBoard, 5);
    twoByTwo.startGame(2,2,2,false);
    nullBoard.startGame(threeByThreeNull, 3);

    fivesAppend = new StringBuilder();
    emptyAppend = new StringBuilder();
    fives = new SameGameTextView<>(fiver, fivesAppend);
    twos = new SameGameTextView<>(twoByTwo);
    empty = new SameGameTextView<>(nullBoard, emptyAppend);

  }

  @Test
  public void testToString() {
    assertEquals("R G\nB Y", twos.toString());
    assertEquals("G R R B Y\nG B B Y Y\nG B R B G\nY G B B Y\nG R R R R",
            fives.toString());
    assertEquals("X X X\nX X X\nX X X", empty.toString());
  }

  @Test
  public void testViewAppendableFailure() {
    twos = new SameGameTextView<>(twoByTwo, new MockFailAppendable());
    assertThrows("Appendable has failed", IllegalStateException.class, () ->
            twos.render());
  }

  @Test
  public void testRender() {
    fives.render();
    assertEquals("G R R B Y\nG B B Y Y\nG B R B G\nY G B B Y\nG R R R R",
            fivesAppend.toString());
    empty.render();
    assertEquals("X X X\nX X X\nX X X", emptyAppend.toString());
  }
}
