package pt.ipbeja.po2.sokoban.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/16/2018
 */

class GameBoardTest {
    private GameBoard model;
    private Keeper keeper = Keeper.getInstance();
    private List<Box> box;
    private List<Cell> map;

    @BeforeEach
    void setUp() {
        this.model = Maps.buildSampleLevel();
    }

    @AfterEach
    void tearDown() {
        System.out.println("TEARDOWN");
    }

    @Test
    void moveKeeper() {
        boolean validMove = this.model.moveKeeper(2, 3);

        assertTrue(validMove);
        assertEquals(2, keeper.getLine());
        assertEquals(3, keeper.getCol());
    }

    @Test
    void moveKeeperToInvalidPosition() {
        boolean invalidMove = this.model.moveKeeper(2, 2);

        assertFalse(invalidMove);
        assertEquals(3, keeper.getLine());
        assertEquals(3, keeper.getCol());
    }

    @Test
    void moveKeeperOffBoard() {
        this.model.moveKeeper(3, 2);
        this.model.moveKeeper(3, 1);
        this.model.moveKeeper(3, 0);
        boolean invalidMove = this.model.moveKeeper(3, -1);

        assertFalse(invalidMove);
        assertEquals(3, keeper.getLine());
        assertEquals(0, keeper.getCol());
    }

    @Test
    void moveKeeperIntoWall() {
        this.model.moveKeeper(3, 4);
        this.model.moveKeeper(3, 5);
        boolean invalidMove = this.model.moveKeeper(3, 6);

        assertFalse(invalidMove);
        assertEquals(3, keeper.getLine());
        assertEquals(5, keeper.getCol());
    }


    @Test
    void moveBox() {
        box = model.getBoxes();
        box.get(0).goToNextPosition(2, 2);
        keeper.goToPosition(2, 1);

        model.moveKeeper(2, 2);
        //right
        assertEquals(2, box.get(0).getLine());
        assertEquals(3, box.get(0).getCol());

        //left
        box.get(0).goToNextPosition(2, 2);
        keeper.goToPosition(2, 3);
        model.moveKeeper(2, 2);
        assertEquals(2, box.get(0).getLine());
        assertEquals(1, box.get(0).getCol());

        //up
        box.get(0).goToNextPosition(2, 2);
        keeper.goToPosition(3, 2);
        model.moveKeeper(2, 2);
        assertEquals(1, box.get(0).getLine());
        assertEquals(2, box.get(0).getCol());

        //down
        box.get(0).goToNextPosition(2, 2);
        keeper.goToPosition(1, 2);
        model.moveKeeper(2, 2);
        assertEquals(3, box.get(0).getLine());
        assertEquals(2, box.get(0).getCol());


    }

    @Test
    void moveBoxToBox() {
        box = model.getBoxes();

        box.get(0).goToNextPosition(2, 2);
        box.get(1).goToNextPosition(2, 3);
        keeper.goToPosition(2, 1);
        assertFalse(model.moveKeeper(2, 2));
    }


    @Test
    void checkNextPosition() {
        // 3,3 -> 3,4 ---> 3,5
        // 3,4 -> 4,4 ---> 5,4
        // 4,4 -> 3,4 ---> 2,4
        // 2,4 -> 3,3 ---> 3,2

        Position source = new Position(3, 3);
        Position target = new Position(3, 4);
        Position expected = new Position(3, 5);

        assertEquals(expected, Position.getNextPosition(source, target));

        source = target;
        target = new Position(4, 4);
        expected = new Position(5, 4);
        assertEquals(expected, Position.getNextPosition(source, target));


        source = target;
        target = new Position(3, 4);
        expected = new Position(2, 4);
        assertEquals(expected, Position.getNextPosition(source, target));

        source = target;
        target = new Position(3, 3);
        expected = new Position(3, 2);
        assertEquals(expected, Position.getNextPosition(source, target));

    }

    @Test
    void CheckWinning() {
        box = model.getBoxes();
        keeper.goToPosition(1, 3);
        box.get(0).goToNextPosition(1, 2);
        model.moveKeeper(1, 2);


        keeper.goToPosition(5, 3);
        box.get(1).goToNextPosition(5, 4);
        model.moveKeeper(5, 4);

        assertTrue(model.isWinnigPosition());

    }

    @Test
    void Undo() {
        keeper.goToPosition(3, 3);

        box = model.getBoxes();
        box.get(0).goToNextPosition(3, 4);


        model.moveKeeper(3, 4);

        assertEquals(3, keeper.getLine());
        assertEquals(4, keeper.getCol());

        assertEquals(3, box.get(0).getLine());
        assertEquals(5, box.get(0).getCol());

        model.undoLastMove();

        assertEquals(3, keeper.getLine());
        assertEquals(3, keeper.getCol());


        assertEquals(3, box.get(0).getLine());
        assertEquals(4, box.get(0).getCol());


    }

}