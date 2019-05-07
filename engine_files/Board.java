package amazons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static amazons.Piece.*;


/**
 * The state of an Amazons Game.
 *
 * @author Shirley Guo
 */
class Board {

    /**
     * The number of squares on a side of the board.
     */
    static final int SIZE = 10;

    /**
     * Initializes a game board with SIZE squares on a side in the
     * initial position.
     */
    Board() {
        init();
    }

    /**
     * Initializes a copy of MODEL.
     */
    Board(Board model) {
        copy(model);
    }

    /**
     * Copies MODEL into me.
     */
    void copy(Board model) {
        board = new Piece[model.SIZE][model.SIZE];
        for (int i = 0; i < model.SIZE; i++) {
            for (int j = 0; j < model.SIZE; j++) {
                if (model.get(j, i).equals(BLACK)) {
                    board[i][j] = BLACK;
                } else if (model.get(j, i).equals(WHITE)) {
                    board[i][j] = WHITE;
                }  else {
                    board[i][j] = EMPTY;
                }
            }
        }
        this._turn = model.turn();
        this._winner = model.winner();
        this.movecount = model.numMoves();

    }

    /**
     * Clears the board to the initial position.
     */
    void init() {
        board = new Piece[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        board[0][5] = BLACK;
        board[0][1] = WHITE;
        _turn = WHITE;
        _black = Square.sq(0);
        _white = Square.sq(0);
        _winner = EMPTY;
        this.movecount = 0;

    }

    /**
     * Return the Piece whose move it is (WHITE or BLACK).
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Return the number of moves (that have not been undone) for this
     * board.
     */
    int numMoves() {
        return movecount;
    }

    /**
     * Return the winner in the current position, or null if the game is
     * not yet finished.
     */
    Piece winner() {
        return _winner;
    }


    /**
     * Return the contents the square at S.
     */
    final Piece get(Square s) {
        return get(s.col(), s.row());
    }


    /**
     * Return the contents of the square at (COL, ROW), where
     * 0 <= COL, ROW <= 9.
     */
    final Piece get(int col, int row) {
        return board[row][col];
    }

    /**
     * Return the contents of the square at COL ROW.
     */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /**
     * Set square S to P.
     */
    final void put(Piece p, Square s) {
        board[s.row()][s.col()] = p;
    }

    /**
     * Set square (COL, ROW) to P.
     */
    final void put(Piece p, int col, int row) {
        board[row][col] = p;
    }

    /**
     * Set square COL ROW to P.
     */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }


    /**
     * Move FROM-TO, assuming this is a legal move.
     */
    void makeMove() {
        Square from;
        int random  = 2* (int)(Math.random() * 6 + 1);
        Square to;
        if (_turn == BLACK){
            from = _black;
            to = Square.sq(from.index()+random);
            _black = to;
        } else {
            from = _white;
            to = Square.sq(from.index()+random);
            _white = to;
        }


        if (to.index() >=99){
            to = Square.sq(99);
        }
        put(get(from), to);
        put(EMPTY, from);
        if (_turn == WHITE) {
            _turn = BLACK;
        } else {
            _turn = WHITE;
        }
        this.movecount += 1;

    }






    @Override
    public String toString() {
        String result = "";
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[0].length; j++) {
                if (j == 0) {
                    result = result + "   " + board[i][j].toString();
                } else if (j == SIZE - 1) {
                    result = result + " " + board[i][j].toString() + "\n";
                } else {
                    result = result + " " + board[i][j].toString();
                }
            }
        }
        return result;
    }

    /**
     * An empty iterator for initialization.
     */
    private static final Iterator<Square> NO_SQUARES =
            Collections.emptyIterator();

    /**
     * Piece whose turn it is (BLACK or WHITE).
     */
    private Piece _turn;

    /**
     * Position of Black.
     */
    public Square _black;

    /**
     * Position of White.
     */
    public Square _white;
    /**
     * Cached value of winner on this board, or EMPTY if it has not been
     * computed.
     */
    private Piece _winner;
    /**
     * The representation of board.
     */
    private Piece[][] board;
    /**
     * The number of moves.
     */
    private int movecount;

}
