import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A class to represent a player in the game
 */
public class Player {

    /**
     * The stage of a player
     */
    private Stage stage = new Stage();

    /**
     * The grid of tiles for a player's hand
     */
    private GridPane gridPane = new GridPane();

    /**
     * The border pane of a player
     */
    private BorderPane borderPane = new BorderPane(gridPane);

    /**
     * The scene of a player
     */
    private Scene scene = new Scene(borderPane);

    /**
     * The position of a player
     */
    private int position = -1;

    /**
     * The array of tiles for a player's hand
     */
    private TsuroButton[] handArray = null;

    /**
     * The player's tile position in the x axis
     */
    private int tilePositionI = -1;

    /**
     * The player's tile position in the y axis
     */
    private int tilePositionJ = -1;

    /**
     * The player's color
     */
    private Color color = null;

    /**
     * A boolean flag that determines whether it's the first turn of a player
     */
    private boolean firstMove = true;

    /**
     * A boolean flag that determines whether a player has lost
     */
    private boolean hasLost = false;

    /**
     * The event handler of a player
     */
    private EventHandler<ActionEvent> playerAction = new PlayerAction();

    /**
     * A constructor that creates a player
     * @param color the player's color
     */
    public Player(Color color) {
        this.color = color;
    }

    /**
     * Gets the player's stage
     * @return the player's stage
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Gets the player's grid pane
     * @return the player's grid pane
     */
    public GridPane getGridPane() {
        return this.gridPane;
    }

    /**
     * Gets the player's scene
     * @return the player's scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Gets the player's position
     * @return the player's position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Gets the player's hand array
     * @return the player's hand array
     */
    public TsuroButton[] getHandArray() {
        return this.handArray;
    }

    /**
     * Gets the player's row position
     * @return the player's row position
     */
    public int getTilePositionI() {
        return this.tilePositionI;
    }

    /**
     * Gets the player's column position
     * @return the player's column position
     */
    public int getTilePositionJ() {
        return this.tilePositionJ;
    }

    /**
     * Gets the player's color
     * @return the player's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets the player's boolean loss flag
     * @return the player's boolean loss flag
     */
    public boolean getHasLost() {
        return this.hasLost;
    }

    /**
     * Gets the player's event handler
     * @return the player's event handler
     */
    public EventHandler<ActionEvent> getPlayerAction() {
        return this.playerAction;
    }   

    /**
     * Sets the player's position
     * @param position the player's position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets the player's hand array
     * @param handArray the player's hand array
     */
    public void setHandArray(TsuroButton[] handArray) {
        this.handArray = handArray;
    }

    /**
     * Sets the player's row position
     * @param tilePositionI the player's row position
     */
    public void setTilePositionI(int tilePositionI) {
        this.tilePositionI = tilePositionI;
    }

    /**
     * Sets the player's column position
     * @param tilePositionJ the player's column position
     */
    public void setTilePositionJ(int tilePositionJ) {
        this.tilePositionJ = tilePositionJ;
    }

    /**
     * A class to represent an action performed by the p;ayer
     */
    public class PlayerAction implements EventHandler<ActionEvent> {
        
        /**
         * The handle method for player's action
         * @param e the action event from player's action
         */
        public void handle(ActionEvent e) {
            TsuroButton tsuroButton = (TsuroButton) e.getSource();
            if (tsuroButton.getBackgroundColor().equals(Color.WHITE)) {
                for (int i = 0; i < getHandArray().length; i++) {
                    if (handArray[i] != tsuroButton) {
                        handArray[i].setBackgroundColor(Color.WHITE);
                    } else {
                        tsuroButton.setBackgroundColor(Color.YELLOW);
                    }
                }
            } else {
                tsuroButton.setConnections(rotateTile(tsuroButton.getConnections()));
            }
        }

    }

    /**
     * Rotates a tile in a player's hand
     * @param currentConnections the connections of the tile that is being rotated
     * @return the rotated connections of the tile
     */
    public static int[] rotateTile(int[] currentConnections) {
        int[] newIndices = {2, 3, 5, 4, 6, 7, 1, 0};
        int[] rotatedConnections = new int[8];
        for (int i = 0; i < 8; i++) {
            rotatedConnections[newIndices[i]] = newIndices[currentConnections[i]];
        }
        return rotatedConnections;
    }

    /**
     * Returns the button that is highlighted in a player's hand
     * @param tsuroButtonArray the array of tsuro buttons in a player's hand
     * @return the tsuro button array of a player
     */
    public TsuroButton getHighlightedTile(TsuroButton[] tsuroButtonArray) {
        for (int i = 0; i < this.handArray.length; i++) {
            if (this.handArray[i].getBackgroundColor().equals(Color.YELLOW)) {
                return tsuroButtonArray[i];
            }
        }
        return null;
    }

    /**
     * Checks if the player has a highlighted tile in their hand array
     * @return true if there is a highlighted tile in the player's hand array and false otherwise
     */
    public boolean hasHighlightedTile() {
        for (int i = 0; i < this.handArray.length; i++) {
            if (this.handArray[i].getBackgroundColor().equals(Color.YELLOW)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the new position of a stone after it has traversed through the tile it is on for the player
     * @param tsuroButton the tsuro button the stone is on
     * @return the new position of a stone after it has traverses through the tile it is on for the player
     */
    public int getTileTraversalPosition(TsuroButton tsuroButton) {
        for (int i = 0; i < this.handArray.length; i++) {
            this.handArray[i].removeStone(this.position);
        }
        int[] positions = tsuroButton.getConnections();
        this.position = positions[this.position];
        //this.setPosition(positions[this.position]);
        return this.position;
    }

    /**
     * Sets the new position of the stone on its next tile
     */
    public void setNewPosition() {
        switch (this.position) {
            case 0: this.position = 4; break;
            case 1: this.position = 5; break;
            case 2: this.position = 6; break;
            case 3: this.position = 7; break;
            case 4: this.position = 0; break;
            case 5: this.position = 1; break;
            case 6: this.position = 2; break;
            case 7: this.position = 3; break;
        }
        for (int i = 0; i < this.handArray.length; i++) {
            this.handArray[i].addStone(this.color, this.position);
        }
    }

    /**
     * Returns the player's position on the previous tile to check if if it has collided with another player's stone
     * @param position the current current position of the player's stone
     * @return the player's position on the previous tile to check if if it has collided with another player's stone
     */
    public int getPreviousPosition(int position) {
        switch (position) {
            case 0: return 4;
            case 1: return 5;
            case 2: return 6;
            case 3: return 7;
            case 4: return 0;
            case 5: return 1;
            case 6: return 2;
            case 7: return 3;
        }
        return -1;
    }

    /**
     * Traverses both player's stones
     */
    public void traverse() {
        while (Tsuro.getPlayers()[Tsuro.getTurn()].getNextTile() != null) {
            Tsuro.getPlayers()[Tsuro.getTurn()].traversePlayer();
        }
        while (Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].getNextTile() != null) {
            Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].traversePlayer();
        }
    }

    /**
     * Places a player's tile on the game board
     * @param tsuroButton the tile to be place
     */
    public void placeTile(TsuroButton tsuroButton) {
        tsuroButton.setConnections(getHighlightedTile(this.handArray).getConnections());
        this.getHighlightedTile(this.handArray).setConnections(this.getHighlightedTile(this.handArray).makeRandomConnectionArray());
        this.getHighlightedTile(this.handArray).setBackgroundColor(Color.WHITE);
    }

    /**
     * Moves a player's stone through the tile
     * @param tsuroButton the tile to move through
     */
    public void moveThroughTile(TsuroButton tsuroButton) {
        Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ].removeStone(this.getPreviousPosition(this.position));
        tsuroButton.addStone(this.getColor(), this.getTileTraversalPosition(tsuroButton));
        setNewPosition();
    }

    /**
     * Gets the next tile in the player's path
     * @return the next tile in the player's path
     */
    public TsuroButton getNextTile() {
        if (this.hasLost) {
            return null;
        }
        try {
            if ((this.position == 0 || this.position == 1) && Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ + 1].getConnections() != null) {
                return Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ + 1];
            }
            else if ((this.position == 2 || this.position == 3) && Tsuro.getGameBoardArray()[this.tilePositionI - 1][this.tilePositionJ].getConnections() != null) {
                return Tsuro.getGameBoardArray()[this.tilePositionI - 1][this.tilePositionJ];
            }
            else if ((this.position == 4 || this.position == 5) && Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ - 1].getConnections() != null) {
                return Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ - 1];
            }
            else if ((this.position == 6 || this.position == 7) && Tsuro.getGameBoardArray()[this.tilePositionI + 1][this.tilePositionJ].getConnections() != null) {
                return Tsuro.getGameBoardArray()[this.tilePositionI + 1][this.tilePositionJ];
            }
        }
        catch (IndexOutOfBoundsException e) {
            this.hasLost = true;
        }
        return null;
    }

    /**
     * Traverses the player through the tiles
     */
    public void traversePlayer() {
        TsuroButton tsuroButton = this.getNextTile(); 
        if (this.position == 0 || this.position == 1) {
            this.moveThroughTile(tsuroButton);
            this.tilePositionJ++;
        }
        else if (this.position == 2 || this.position == 3) {
            this.moveThroughTile(tsuroButton);
            this.tilePositionI--;
        }
        else if (this.position == 4 || this.position == 5) {
            this.moveThroughTile(tsuroButton);
            this.tilePositionJ--;
        }
        else if (this.position == 6 || this.position == 7) {
            this.moveThroughTile(tsuroButton);
            this.tilePositionI++;
        }
    }

    /**
     * Checks if the player's collided with each other
     * @return true if the player's have collided with each other and false otherwise
     */
    public boolean checkTieCondition() {
        if (Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionI == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionI && Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionJ == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionJ + 1) {
            return true;
        }
        else if (Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionJ == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionJ && Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionI == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionI - 1) {
            return true;
        }
        else if (Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionI == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionI && Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionJ == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionJ - 1) {
            return true;
        }
        else if (Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionJ == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionJ && Tsuro.getPlayers()[Tsuro.getTurn()].tilePositionI == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].tilePositionI + 1) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a tie
     */
    public void checkIfTie() {
        if (checkTieCondition() && Tsuro.getPlayers()[Tsuro.getTurn()].getPreviousPosition(Tsuro.getPlayers()[Tsuro.getTurn()].position) == Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].position) {
            Tsuro.getPlayers()[Tsuro.getTurn()].hasLost = true;
            Tsuro.getPlayers()[(Tsuro.getTurn() + 1)%Tsuro.getPlayers().length].hasLost = true;
            Tsuro.setGameHasEnded(true);
        }
    }   

    /**
     * The player's first turn
     * @param tsuroButton the tile the stone is on
     * @return true if the first turn was successful and false otherwise
     */
    public boolean firstTurn(TsuroButton tsuroButton) {
        if (tsuroButton.getBackgroundColor().equals(this.color)) {
            tsuroButton.setBackgroundColor(Color.WHITE);
            this.placeTile(tsuroButton);
            this.moveThroughTile(tsuroButton);
            checkIfTie();
            traverse();
            return true;
        }
        return false;
    }

    /**
     * Moves the player's stone through the tile it is on
     * @param tsuroButton the tile the stone is on
     */
    public boolean turn(TsuroButton tsuroButton) {
        if (!Tsuro.getGameHasEnded() && this.hasHighlightedTile() && tsuroButton.getConnections() == null) {
            if (this.firstMove) {
                this.firstMove = false;
                return this.firstTurn(tsuroButton);
            }
            if ((this.position == 0 || this.position == 1) && tsuroButton == Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ + 1]) {
                this.placeTile(tsuroButton);
                this.moveThroughTile(tsuroButton);
                this.tilePositionJ++;
            }
            else if ((this.position == 2 || this.position == 3) && tsuroButton == Tsuro.getGameBoardArray()[this.tilePositionI - 1][this.tilePositionJ]) {
                this.placeTile(tsuroButton);
                this.moveThroughTile(tsuroButton);
                this.tilePositionI--;
            }
            else if ((this.position == 4 || this.position == 5) && tsuroButton == Tsuro.getGameBoardArray()[this.tilePositionI][this.tilePositionJ - 1]) {
                this.placeTile(tsuroButton);
                this.moveThroughTile(tsuroButton);
                this.tilePositionJ--;
            }
            else if ((this.position == 6 || this.position == 7) && tsuroButton == Tsuro.getGameBoardArray()[this.tilePositionI + 1][this.tilePositionJ]) {
                this.placeTile(tsuroButton);
                this.moveThroughTile(tsuroButton);
                this.tilePositionI++;
            }
            checkIfTie();
            this.traverse();
            if (Tsuro.getPlayers()[0].hasLost || Tsuro.getPlayers()[1].hasLost) {
                Tsuro.setGameHasEnded(true);
            }
            return true;
        }
        return false;
    }

}
