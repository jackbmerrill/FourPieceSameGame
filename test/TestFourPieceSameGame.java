import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.GamePiece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for a four piece same game.
 */
public class TestFourPieceSameGame {

  FourPieceSameGame fiver;
  FourPieceSameGame twoByTwo;
  FourPieceSameGame twoByTwoRun;
  FourPieceSameGame sixBySeven;
  FourPieceSameGame tenByTwelve;
  FourPieceSameGame neverRun;
  FourPieceSameGame nullBoard;
  List<List<GamePiece>> threeByThreeNull;

  List<List<GamePiece>> fiveByFiveBoard;

  @Before
  public void initData() {
    fiver = new FourPieceSameGame();
    twoByTwo = new FourPieceSameGame();
    twoByTwoRun = new FourPieceSameGame();
    sixBySeven = new FourPieceSameGame();
    tenByTwelve = new FourPieceSameGame();
    neverRun = new FourPieceSameGame();
    nullBoard = new FourPieceSameGame();
    fiveByFiveBoard = new ArrayList<>(List.of(
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
    List<List<GamePiece>> twox2Run = new ArrayList<>(List.of(
            new ArrayList<>(List.of(GamePiece.RED, GamePiece.YELLOW)),
            new ArrayList<>(List.of(GamePiece.YELLOW, GamePiece.YELLOW))));
    threeByThreeNull = new ArrayList<>(List.of(nullArray, nullArray, nullArray));

    fiver.startGame(fiveByFiveBoard, 5);
    sixBySeven.startGame(6, 7, 3, false);
    twoByTwo.startGame(2, 2, 2, false);
    twoByTwoRun.startGame(twox2Run, 2);
  }

  @Test
  public void testRemoveMatch() {
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.removeMatch(0, 0));
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            twoByTwo.removeMatch(0, 0));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwoRun.removeMatch(2, 2));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwoRun.removeMatch(1, 2));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwoRun.removeMatch(2, 1));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwoRun.removeMatch(-1, 2));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwoRun.removeMatch(2, -1));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwoRun.removeMatch(-2, -1));
    assertThrows("No valid piece in this position", IllegalStateException.class, () ->
            nullBoard.removeMatch(2, 2));
    assertThrows("There is no match at this position", IllegalStateException.class, () ->
            fiver.removeMatch(0, 3));
    assertThrows("There is no match at this position", IllegalStateException.class, () ->
            twoByTwoRun.removeMatch(0, 0));
    assertEquals(GamePiece.GREEN, fiver.pieceAt(0, 0));
    assertEquals(GamePiece.GREEN, fiver.pieceAt(1, 0));
    assertEquals(GamePiece.GREEN, fiver.pieceAt(2, 0));
    fiver.removeMatch(1, 0);
    assertNull(fiver.pieceAt(0, 0));
    assertNull(fiver.pieceAt(1, 0));
    assertNull(fiver.pieceAt(2, 0));
    assertEquals(1, fiver.score());
    assertEquals(GamePiece.RED, fiver.pieceAt(4, 1));
    assertEquals(GamePiece.RED, fiver.pieceAt(4, 2));
    assertEquals(GamePiece.RED, fiver.pieceAt(4, 3));
    assertEquals(GamePiece.RED, fiver.pieceAt(4, 4));
    fiver.removeMatch(4, 4);
    assertNull(fiver.pieceAt(4, 1));
    assertNull(fiver.pieceAt(4, 2));
    assertNull(fiver.pieceAt(4, 3));
    assertNull(fiver.pieceAt(4, 4));
    assertEquals(3, fiver.score());
  }

  @Test
  public void testCreateListOfPieces() {
    assertEquals(new GamePiece[]{GamePiece.RED, GamePiece.GREEN, GamePiece.BLUE, GamePiece.YELLOW},
            fiver.createListOfPieces());
    assertEquals(new GamePiece[]{GamePiece.RED, GamePiece.GREEN, GamePiece.BLUE, GamePiece.YELLOW},
            sixBySeven.createListOfPieces());
  }

  @Test
  public void testLength() {
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.length());
    assertEquals(5, this.fiver.length());
    assertEquals(2, this.twoByTwo.length());
    assertEquals(7, this.sixBySeven.length());
  }

  @Test
  public void testWidth() {
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.width());
    assertEquals(5, this.fiver.width());
    assertEquals(2, this.twoByTwo.width());
    assertEquals(6, this.sixBySeven.width());
  }

  @Test
  public void testRemainingSwaps() {
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.remainingSwaps());
    assertEquals(5, this.fiver.remainingSwaps());
    assertEquals(2, this.twoByTwo.remainingSwaps());
    assertEquals(3, this.sixBySeven.remainingSwaps());
  }

  @Test
  public void testPieceAt() {
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.pieceAt(1, 1));
    assertEquals(GamePiece.YELLOW, this.twoByTwo.pieceAt(1, 1));
    assertEquals(GamePiece.RED, this.sixBySeven.pieceAt(4, 4));
    assertEquals(GamePiece.BLUE, this.fiver.pieceAt(0, 3));
    assertEquals(GamePiece.GREEN, this.sixBySeven.pieceAt(4, 5));
    fiver.removeMatch(0,0);
    assertNull(this.fiver.pieceAt(0, 0));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwo.pieceAt(2, 2));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwo.pieceAt(1, 2));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwo.pieceAt(2, 1));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwo.pieceAt(-1, 2));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwo.pieceAt(2, -1));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            twoByTwo.pieceAt(-2, -1));
  }

  @Test
  public void testStartGameWithList() {
    List<List<GamePiece>> empty = new ArrayList<>(3);
    assertThrows("Board cannot be null", IllegalArgumentException.class, () ->
            tenByTwelve.startGame(empty, 5));
    FourPieceSameGame test = new FourPieceSameGame();
    assertThrows("Board cannot be null", IllegalArgumentException.class, () ->
            test.startGame(null, 5));
    List<List<GamePiece>> invalidBoard = new ArrayList<>(List.of(
            new ArrayList<>(List.of(GamePiece.BLUE, GamePiece.RED)),
            new ArrayList<>(List.of(GamePiece.BLUE))));
    FourPieceSameGame test1 = new FourPieceSameGame();
    assertThrows("Board must be a valid rectangle", IllegalArgumentException.class, () ->
            test1.startGame(invalidBoard, 5));
    assertThrows("swaps cannot be negative", IllegalArgumentException.class, () ->
            neverRun.startGame(threeByThreeNull, -1));
    assertThrows("the game has already started", IllegalStateException.class, () ->
            fiver.startGame(threeByThreeNull, 2));
    assertThrows("the game has already started", IllegalStateException.class, () ->
            twoByTwo.startGame(threeByThreeNull, 2));
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        assertEquals(fiver.pieceAt(row, col), fiveByFiveBoard.get(row).get(col));
      }
    }
  }

  @Test
  public void testStartGameWithRandomOrDeterministic() {
    assertThrows("rows and cols must be greater than 0", IllegalArgumentException.class, () ->
            tenByTwelve.startGame(0, 0, 1, true));
    assertThrows("rows and cols must be greater than 0", IllegalArgumentException.class, () ->
            tenByTwelve.startGame(1, 0, 1, true));
    assertThrows("rows and cols must be greater than 0", IllegalArgumentException.class, () ->
            tenByTwelve.startGame(0, 1, 1, true));
    assertThrows("swaps cannot be negative", IllegalArgumentException.class, () ->
            neverRun.startGame(2, 2, -1, false));
    assertThrows("the game has already started", IllegalStateException.class, () ->
            twoByTwo.startGame(2, 2, 2, true));
    assertThrows("the game has already started", IllegalStateException.class, () ->
            fiver.startGame(2, 2, 2, true));
    //more tests for actually creating random and determ boards
    //for determ: use a 2x2
    FourPieceSameGame test2x2 = new FourPieceSameGame();
    test2x2.startGame(2, 2, 2, false);
    assertEquals(GamePiece.RED, test2x2.pieceAt(0, 0));
    assertEquals(GamePiece.GREEN, test2x2.pieceAt(0, 1));
    assertEquals(GamePiece.BLUE, test2x2.pieceAt(1, 0));
    assertEquals(GamePiece.YELLOW, test2x2.pieceAt(1, 1));
    //test random by creating two massive boards and showing they arent equal
    FourPieceSameGame rand1 = new FourPieceSameGame();
    FourPieceSameGame rand2 = new FourPieceSameGame();
    rand1.startGame(5, 5, 0, true);
    rand2.startGame(5, 5, 0, true);
    assertTrue(rand1.pieceAt(1, 1) != rand2.pieceAt(1, 1)
            || rand1.pieceAt(2, 2) != rand2.pieceAt(2, 2)
            || rand1.pieceAt(3, 3) != rand2.pieceAt(3, 3)
            || rand1.pieceAt(4, 4) != rand2.pieceAt(4, 4));
  }


  @Test
  public void testSwap() {
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.swap(1, 1, 1, 1));
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            twoByTwo.swap(0, 0, 1, 1));
    neverRun.startGame(2, 2, 0, false);
    assertThrows("There are no swaps remaining", IllegalStateException.class, () ->
            neverRun.swap(1, 1, 0, 0));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            fiver.swap(1, 1, 1, 5));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            fiver.swap(1, 5, 1, 1));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            fiver.swap(1, 1, 5, 1));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            fiver.swap(5, 1, 1, 5));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            fiver.swap(-1, 1, 3, 3));
    assertThrows("Entered argument is invalid", IllegalArgumentException.class, () ->
            fiver.swap(1, 1, -3, 3));
    assertThrows("Entered argument is invalid", IllegalStateException.class, () ->
            fiver.swap(3, 3, 3, 3));
    assertThrows("Entered argument is invalid", IllegalStateException.class, () ->
            fiver.swap(3, 3, 1, 1));
    assertEquals(GamePiece.RED, twoByTwoRun.pieceAt(0, 0));
    assertEquals(GamePiece.YELLOW, twoByTwoRun.pieceAt(0, 1));
    twoByTwoRun.swap(0, 0, 0, 1);
    assertEquals(GamePiece.YELLOW, twoByTwoRun.pieceAt(0, 0));
    assertEquals(GamePiece.RED, twoByTwoRun.pieceAt(0, 1));
    assertEquals(1, twoByTwoRun.remainingSwaps());
    fiver.removeMatch(0,0);
    assertThrows("There are no valid pieces in these positions",
            IllegalArgumentException.class, () ->
                    fiver.swap(0, 0, 1, 0));
  }

  @Test
  public void testGameOver() {
    assertTrue(twoByTwo.gameOver());
    assertFalse(twoByTwoRun.gameOver());
    assertFalse(fiver.gameOver());
    assertThrows("The game is not running.", IllegalStateException.class, () ->
            neverRun.gameOver());
  }

}
