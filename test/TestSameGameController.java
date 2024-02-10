import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.samegame.controller.SameGameController;
import cs3500.samegame.controller.SameGameTextController;
import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * A class for testing the same game controller. This document tests all controller outputs
 *    assuming all inputs are read correctly, tested by the test controller input class.
 */

public class TestSameGameController {

  Appendable output1;
  Readable input1;
  SameGameController<GamePiece> control1;
  SameGameModel<GamePiece> model1;
  SameGameModel<GamePiece> model2;
  List<List<GamePiece>> threeByThreeBoard;


  @Before
  public void init() {
    output1 = new StringBuilder();
    input1 = new StringReader("q");
    control1 = new SameGameTextController<GamePiece>(input1, output1);
    model1 = new FourPieceSameGame();
    model2 = new FourPieceSameGame();
    model2.startGame(2, 2, 2, false);
    threeByThreeBoard = new ArrayList<>(List.of(
            new ArrayList<>(List.of(GamePiece.RED, GamePiece.BLUE, GamePiece.BLUE)),
            new ArrayList<>(List.of(GamePiece.RED, GamePiece.BLUE, GamePiece.YELLOW)),
            new ArrayList<>(List.of(GamePiece.RED, GamePiece.YELLOW, GamePiece.YELLOW))));
  }

  @Test
  public void testSameGameTextControllerConstructor() {
    assertThrows("Readable or appendable is null", IllegalArgumentException.class, () ->
            new SameGameTextController<GamePiece>(null, null));
    assertThrows("Readable or appendable is null", IllegalArgumentException.class, () ->
            new SameGameTextController<GamePiece>(input1, null));
    assertThrows("Readable or appendable is null", IllegalArgumentException.class, () ->
            new SameGameTextController<GamePiece>(null, output1));
    new SameGameTextController<GamePiece>(input1, output1);
  }

  @Test
  public void testPlayGameStartModelGenerated() {
    assertThrows("Provided model cannot be null", IllegalArgumentException.class, () ->
            control1.playGame(null, 1, 1, 1, false));
    assertThrows("Game cannot be started as inputs are invalid.",
            IllegalArgumentException.class, () -> control1.playGame(
                    model1, -1, 1, 1, false));
    assertThrows("Game cannot be started as inputs are invalid.",
            IllegalArgumentException.class, () -> control1.playGame(
                    model1, 1, -1, 1, false));
    assertThrows("Game cannot be started as inputs are invalid.",
            IllegalArgumentException.class, () -> control1.playGame(
                    model1, 1, 1, -1, false));
    assertThrows("Game has already started.",
            IllegalStateException.class, () -> control1.playGame(
                    model2, 1, 1, 1, false));
  }

  @Test
  public void testPlayGameStartModelBoard() {
    assertThrows("Provided model cannot be null", IllegalArgumentException.class, () ->
            control1.playGame(null, threeByThreeBoard, 1));
    assertThrows("Game cannot be started as inputs are invalid.",
            IllegalArgumentException.class, () -> control1.playGame(
                    model1, threeByThreeBoard, -1));
    assertThrows("Game cannot be started as inputs are invalid.",
            IllegalArgumentException.class, () -> control1.playGame(
                    model1, null, 1));
    assertThrows("Game has already started.",
            IllegalStateException.class, () -> control1.playGame(
                    model2, null, 1));
  }

  @Test
  public void testControllerOutputsWithGameOverFromStart() {
    Readable userInput = new StringReader("m 3 2 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, 2, 2, 2, false);
    assertEquals("Game over.\n" + "R G\n" + "B Y\n" + "Remaining swaps: 2\n"
            + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithInvalidCommand() {
    Readable userInput = new StringReader("e 3 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, 4, 4, 2, false);
    assertEquals("R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Invalid command. Try again. Valid commands are Q, q, s, m.\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Invalid command. Try again. Valid commands are Q, q, s, m.\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n" + "Game quit!\n"
            + "State of game when quit:\n" + "R G B Y\n" + "R G B Y\n"
            + "R G B Y\n" + "R G B Y\n" + "Remaining swaps: 2\n"
            + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithInvalidBoundsMatchInputs() {
    Readable userInput = new StringReader("m 0 2 m 1 6 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput,
            stringBuilder);
    controller.playGame(model1, 4, 4, 2, false);
    assertEquals("R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Invalid move. Try again. At least one argument is out of bounds\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Invalid move. Try again. At least one argument is out of bounds\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n" + "Game quit!\n"
            + "State of game when quit:\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "R G B Y\n" + "Remaining swaps: 2\n" + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithInvalidPieceMatchInputs() {
    Readable userInput = new StringReader("m 1 1 m 2 2 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput,
            stringBuilder);
    controller.playGame(model1, 3, 3, 2, false);
    assertEquals("R G B\n" + "Y R G\n" + "B Y R\n" + "Remaining swaps: 2\n"
            + "Score: 0\n" + "Invalid move. Try again. The chosen piece is invalid\n"
            + "R G B\n" + "Y R G\n" + "B Y R\n" + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Invalid move. Try again. The chosen piece is invalid\n" + "R G B\n"
            + "Y R G\n" + "B Y R\n" + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Game quit!\n" + "State of game when quit:\n" + "R G B\n" + "Y R G\n"
            + "B Y R\n" + "Remaining swaps: 2\n" + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithInvalidBoundsSwap() {
    Readable userInput = new StringReader("s 6 3 5 1 s 3 4 1 10 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, 4, 4, 2, false);
    assertEquals("R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n"
            + "Remaining swaps: 2\n" + "Score: 0\n"
            + "Invalid move. Try again. Swap coordinates are illegal.\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "Remaining swaps: 2\n"
            + "Score: 0\n" + "Invalid move. Try again. Swap coordinates are illegal.\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "Remaining swaps: 2\n"
            + "Score: 0\n" + "Game quit!\n" + "State of game when quit:\n" + "R G B Y\n"
            + "R G B Y\n" + "R G B Y\n" + "R G B Y\n" + "Remaining swaps: 2\n"
            + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithInvalidPieceSwapSame() {
    Readable userInput = new StringReader("s 1 1 1 1 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, threeByThreeBoard, 1);
    assertEquals("R B B\n" + "R B Y\n" + "R Y Y\n" + "Remaining swaps: 1\n"
            + "Score: 0\n" + "Invalid move. Try again. There are no swaps remaining.\n"
            + "R B B\n" + "R B Y\n" + "R Y Y\n" + "Remaining swaps: 1\n" + "Score: 0\n"
            + "Game quit!\n" + "State of game when quit:\n" + "R B B\n" + "R B Y\n"
            + "R Y Y\n" + "Remaining swaps: 1\n" + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithInvalidPieceSwapNull() {
    Readable userInput = new StringReader("m 1 1 s 1 1 2 1 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, threeByThreeBoard, 1);
    assertEquals("R B B\n" + "R B Y\n" + "R Y Y\n" + "Remaining swaps: 1\n"
            + "Score: 0\n" + "X B B\n" + "X B Y\n" + "X Y Y\n" + "Remaining swaps: 1\n"
            + "Score: 1\n" + "Invalid move. Try again. Swap coordinates are illegal.\n"
            + "X B B\n" + "X B Y\n" + "X Y Y\n" + "Remaining swaps: 1\n" + "Score: 1\n"
            + "Game quit!\n" + "State of game when quit:\n" + "X B B\n" + "X B Y\n"
            + "X Y Y\n" + "Remaining swaps: 1\n" + "Score: 1\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithNoSwapRemaining() {
    Readable userInput = new StringReader("s 1 1 2 2 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, threeByThreeBoard, 0);
    assertEquals("R B B\n" + "R B Y\n" + "R Y Y\n" + "Remaining swaps: 0\n"
            + "Score: 0\n" + "Invalid move. Try again. There are no swaps remaining.\n"
            + "R B B\n" + "R B Y\n" + "R Y Y\n" + "Remaining swaps: 0\n" + "Score: 0\n"
            + "Game quit!\n" + "State of game when quit:\n" + "R B B\n" + "R B Y\n"
            + "R Y Y\n" + "Remaining swaps: 0\n" + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithGameOverAfterSwap() {
    Readable userInput = new StringReader("s 1 1 2 1 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, 3, 3, 1, false);
    assertEquals("R G B\nY R G\nB Y R\nRemaining swaps: 1\n"
            + "Score: 0\nGame over.\n" + "Y G B\n" + "R R G\n" + "B Y R\n"
            + "Remaining swaps: 0\n" + "Score: 0\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithGameOverAfterMatch() {
    List<List<GamePiece>> board = new ArrayList<>(List.of(
            new ArrayList<>(List.of(GamePiece.BLUE, GamePiece.BLUE)),
            new ArrayList<>(List.of(GamePiece.BLUE, GamePiece.BLUE))));
    Readable userInput = new StringReader("m 1 1 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model1, board, 0);
    assertEquals("B B\nB B\nRemaining swaps: 0\nScore: 0"
            + "\nGame over.\nX X\nX X\nRemaining swaps: 0\n"
            + "Score: 2\n", stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithNoMoreInputsInMatch() {
    Readable userInput = new StringReader("m");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
//    assertThrows("Readable has failed", IllegalStateException.class, () ->
//            controller.playGame(model1, 3, 3, 1, false));
    controller.playGame(model1, 3, 3, 1, false);
    System.out.println(stringBuilder.toString());
  }

  @Test
  public void testControllerOutputsWithNoMoreInputsInSwap() {
    Readable userInput = new StringReader("s 2 2 ");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    assertThrows("Readable has failed", IllegalStateException.class, () ->
            controller.playGame(model1, 3, 3, 1, false));
  }

  @Test
  public void testControllerOutputsWithNoMoreInputs() {
    Readable userInput = new StringReader("");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    assertThrows("Readable has failed", IllegalStateException.class, () ->
            controller.playGame(model1, 3, 3, 1, false));
  }

  @Test
  public void testControllerOutputsWithFailingAppendable() {
    Readable userInput = new StringReader("");
    Appendable failBuilder = new MockFailAppendable();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, failBuilder);
    assertThrows("Appendable has failed", IllegalStateException.class, () ->
            controller.playGame(model1, 3, 3, 1, false));
  }

}
