package lab4.data;

import lab4.client.GomokuClient;

import java.util.Observable;
import java.util.Observer;

public class GomokuGameState extends Observable implements Observer {

    private final int DEFAULT_SIZE = 17;

    private GameGrid gameGrid;

    private State currentState;

    private String message;

    private GomokuClient client;


    public GomokuGameState(GomokuClient client) {
        this.client = client;
        this.gameGrid = new GameGrid(DEFAULT_SIZE);
        this.currentState = State.NOT_STARTED;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("update called in GumokoGameState.java");
    }

    public void receivedMove(int x, int y) {
        gameGrid.move(x, y, GameGrid.TileState.OTHER);

        if (gameGrid.isWinner()) {
            setMessage("You lost!");
            currentState = State.FINISHED;
        } else {
            setMessage("Other player moved to (" + x + ", " + y + ")");
            currentState = State.MY_TURN;
        }
        setChanged();
        notifyObservers();
    }

    public void disconnect() {
        setMessage("Disconnected!");
        currentState = State.NOT_STARTED;
        gameGrid.clearGrid();
        client.disconnect();
        setChanged();
        notifyObservers();
    }

    public void move(int x, int y) {
        switch (currentState) {
            case FINISHED:
                setMessage("Game already finished!");
                return;
            case NOT_STARTED:
                setMessage("Game have not started!");
                return;
            case OTHERS_TURN:
                setMessage("Not your turn!");
                return;
        }

        if (!gameGrid.move(x, y, GameGrid.TileState.ME)) {
            setMessage("Illegal move!");
            return;
        }

        client.sendMoveMessage(x, y);

        if (gameGrid.isWinner()) {
            currentState = State.FINISHED;
            setMessage("You won!");
        } else {
            currentState = State.OTHERS_TURN;
            setMessage("You moved to (" + x + ", " + y + ")");
        }

        setChanged();
        notifyObservers();
    }

    public String getMessageString() {
        return message;
    }

    public GameGrid getGameGrid() {
        return gameGrid;
    }

    public void newGame() {
        gameGrid.clearGrid();
        currentState = State.OTHERS_TURN;
        setMessage("New game, others turn!");
        client.sendNewGameMessage();
        setChanged();
        notifyObservers();

    }

    public void receivedNewGame() {
        gameGrid.clearGrid();
        currentState = State.MY_TURN;
        setMessage("New game and your turn!");
        setChanged();
        notifyObservers();
    }


    public void otherGuyLeft() {
        gameGrid.clearGrid();
        setMessage("Opponent left!");
        currentState = State.NOT_STARTED;
        setChanged();
        notifyObservers();
    }

    private void setMessage(String message) {
        System.out.println(message);
        this.message = message;

        // notify?
    }

    private enum State {
        NOT_STARTED, MY_TURN, OTHERS_TURN, FINISHED;
    }
}
