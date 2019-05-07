package amazons;

import ucb.gui2.Pad;

import java.io.IOException;

import java.util.concurrent.ArrayBlockingQueue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static amazons.Piece.*;
import static amazons.Square.sq;

/**
 * A widget that displays an Amazons game.
 *
 * @author Shirley Guo
 */
class BoardWidget extends Pad {

    /* Parameters controlling sizes, speeds, colors, and fonts. */

    /**
     * Colors of empty squares and grid lines.
     */
    static final Color
            LIGHT_SQUARE_COLOR = new Color(238, 207, 161),
            DARK_SQUARE_COLOR = new Color(205, 133, 63),
            CLICK_COLOR = new Color(100, 100, 60);

    /**
     * Locations of images of white and black queens.
     */
    private static final String
            WHITE_IMAGE = "wq4.png",
            BLACK_IMAGE = "bq4.png";


    /**
     * Size parameters.
     */
    private static final int
            SQUARE_SIDE = 30,
            BOARD_SIDE = SQUARE_SIDE * 10;

    /**
     * A graphical representation of an Amazons board that sends commands
     * derived from mouse clicks to COMMANDS.
     */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        _clicknum = 0;
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);

        try {
            _whiteQueen = ImageIO.read(Utils.getResource(WHITE_IMAGE));
            _blackQueen = ImageIO.read(Utils.getResource(BLACK_IMAGE));
        } catch (IOException excp) {
            System.err.println("Could not read queen images.");
            System.exit(1);
        }
        _acceptingMoves = false;
    }

    /**
     * Draw the bare board G.
     */
    private void drawGrid(Graphics2D g) {
        g.setColor(LIGHT_SQUARE_COLOR);
        g.fillRect(0, 0, BOARD_SIDE, BOARD_SIDE);
        g.setColor(DARK_SQUARE_COLOR);
        for (int k = 0; k <= BOARD_SIDE - 2 * SQUARE_SIDE;
             k = k + 4 * SQUARE_SIDE) {
            for (int j = 0; j <= BOARD_SIDE - 2 * SQUARE_SIDE;
                 j = j + 2 * SQUARE_SIDE) {
                g.fillRect(k, j, 2*SQUARE_SIDE, SQUARE_SIDE);
            }
        }
        for (int k = 2*SQUARE_SIDE; k <= BOARD_SIDE - 2*SQUARE_SIDE;
             k = k + 4 * SQUARE_SIDE) {
            for (int j = SQUARE_SIDE; j <= BOARD_SIDE - SQUARE_SIDE;
                 j = j + 2 * SQUARE_SIDE) {
                g.fillRect(k, j, 2*SQUARE_SIDE, SQUARE_SIDE);
            }
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        drawGrid(g);
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 10; n++) {
                Square sq = Square.sq(m, n);
                if (_board.get(sq).equals(BLACK)) {
                    drawQueen(g, sq, BLACK);
                } else if (_board.get(sq).equals(WHITE)) {
                    drawQueen(g, sq, WHITE);
                }
            }
        }


    }





    /**
     * Draw a queen for side PIECE at square S on G.
     */
    private void drawQueen(Graphics2D g, Square s, Piece piece) {
        if(piece == WHITE){
        g.drawImage( _whiteQueen ,
                cx(s.col()) + 2, cy(s.row()) + 4, null);}
        if(piece == BLACK){
            g.drawImage( _blackQueen,
                cx(s.col()) + 2, cy(s.row()) + 4, null);}
    }


    /**
     * Revise the displayed board according to BOARD.
     */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /**
     * Turn on move collection iff COLLECTING, and clear any current
     * partial selection.   When move collection is off, ignore clicks on
     * the board.
     */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /**
     * Return x-pixel coordinate of the left corners of column X
     * relative to the upper-left corner of the board.
     */
    private int cx(int x) {
        return x * SQUARE_SIDE;
    }

    /**
     * Return y-pixel coordinate of the upper corners of row Y
     * relative to the upper-left corner of the board.
     */
    private int cy(int y) {
        return (Board.SIZE - y - 1) * SQUARE_SIDE;
    }

    /**
     * Return x-pixel coordinate of the left corner of S
     * relative to the upper-left corner of the board.
     */
    private int cx(Square s) {
        return cx(s.col());
    }

    /**
     * Return y-pixel coordinate of the upper corner of S
     * relative to the upper-left corner of the board.
     */
    private int cy(Square s) {
        return cy(s.row());
    }

    /**
     * Queue on which to post move commands (from mouse clicks).
     */
    private ArrayBlockingQueue<String> _commands;
    /**
     * Board being displayed.
     */
    private final Board _board = new Board();

    /**
     * Image of white queen.
     */
    private BufferedImage _whiteQueen;
    /**
     * Image of black queen.
     */
    private BufferedImage _blackQueen;

    /**
     * True iff accepting moves from user.
     */
    private boolean _acceptingMoves;

    /**
     * The number of clicks.
     */
    private int _clicknum;
    /**
     * Stores the 3 clicks as a move.
     */
    private String _clickcommand;
}
