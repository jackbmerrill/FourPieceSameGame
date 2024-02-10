import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import cs3500.samegame.controller.SameGameController;
import cs3500.samegame.controller.SameGameTextController;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the controller inputs to the model using the mock model. Only model
 *    methods ran by the controller are tested, ie. piece at is not tested.
 */
public class TestSameGameControllerInputs {

  SameGameModel<GamePiece> model;
  StringBuilder userInput;

  @Before
  public void init() {
    userInput = new StringBuilder();
    model = new MockModel<GamePiece>(userInput);
  }

  @Test
  public void testStartGameInputsWithDeterministic() {
    Readable userInput = new StringReader("q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model, 2, 2, 2, false);
    assertEquals(this.userInput.toString(), "rows: 2, cols: 2, swaps: 2, random: false");
  }

  @Test
  public void testStartGameInputsWithBoard() {
    Readable userInput = new StringReader("q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model, null, 2);
    assertEquals(this.userInput.toString(), "Board: null, swaps: 2");
  }

  @Test
  public void testSwapInputs() {
    Readable userInput = new StringReader("s 1 1 3 3 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model, 4, 4, 2, false);
    String[] list = this.userInput.toString().split("\n");
    assertEquals(list[1], "fromRow: 0, fromCol: 0, toRow: 2, toCol: 2");
  }

  @Test
  public void testMatchInputs() {
    Readable userInput = new StringReader("m 3 2 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model, 4, 4, 2, false);
    String[] list = this.userInput.toString().split("\n");
    assertEquals(list[1], "row: 2, col: 1");
  }

  @Test
  public void testIncorrectInputsToMatch() {
    Readable userInput = new StringReader("m z wenofe 3 dnsa -3 fsd dq 3j2 2 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model, 4, 4, 2, false);
    String[] list = this.userInput.toString().split("\n");
    assertEquals(list[1], "row: 2, col: 1");
  }

  @Test
  public void testIncorrectInputsToSwap() {
    Readable userInput = new StringReader("s jdsjkds 1 -1 sjd 1 903sa 3 []ds 3 q");
    Appendable stringBuilder = new StringBuilder();
    SameGameController<GamePiece> controller
            = new SameGameTextController<>(userInput, stringBuilder);
    controller.playGame(model, 4, 4, 2, false);
    String[] list = this.userInput.toString().split("\n");
    assertEquals(list[1], "fromRow: 0, fromCol: 0, toRow: 2, toCol: 2");
  }
}

