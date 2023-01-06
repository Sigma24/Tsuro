package Tsuro;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

/**
 * A class to represent the Tsuro game
 */
public class Tsuro extends Application {

	/**
	 * The number of columns in the gameboard
	 */
	private static int gridI;

	/**
	 * The number of rows in the gameboard
	 */
	private static int gridJ;

	/**
	 * The number of tiles in a player's hand
	 */
	private static int handSize;

	/**
	 * The array of tiles for the gameboard
	 */
	private static TsuroButton[][] gameBoardArray = null;
    
    /**
     * The array of players
     */
    private static Player[] players = new Player[2];

	/**
	 * A counter that determines the turn of the player
	 */
	private static int turn = 0;

    /**
     * A boolean flag to check if the game has ended
     */
    private static boolean gameHasEnded = false;

	/**
	 * Sets the number of columns in the gamebaord
	 * @param gridIInput the number of columns in the gameboard
	 */
	public static void setGridI(int gridIInput) {
		gridI = gridIInput;
	}

	/**
	 * Sets the number of rows in the gamebaord
	 * @param gridJInput the number of rows in the gameboard
	 */
	public static void setGridJ(int gridJInput) {
		gridJ = gridJInput;
	}

	/**
	 * Sets the number of tiles in the player's hand
	 * @param handSizeInput the number of tiles in the player's hand
	 */
	public static void setHandSize(int handSizeInput) {
		handSize = handSizeInput;
	}

	/**
	 * Gets number of columns in the gameboard
	 * @return the number of columns in the gameboard
	 */
	public static int getGridI() {
		return gridI;
	}

	/**
	 * Gets number of rows in the gameboard
	 * @return the number of rows in the gameboard
	 */
	public static int getGridJ() {
		return gridJ;
	}

	/**
	 * Gets number of tiles in a player's hand
	 * @return the number of tiles in a player's hand
	 */
	public static int getHandSize() {
		return handSize;
	}

    /**
     * Gets the game board array
     * @return the game board array
     */
    public static TsuroButton[][] getGameBoardArray() {
        return gameBoardArray;
    }

    /**
     * Gets the player array
     * @return the player array
     */
    public static Player[] getPlayers() {
        return players;
    }

    /**
     * Gets the turn of the game
     * @return the turn of the game
     */
    public static int getTurn() {
        return turn;
    }

    /**
     * Gets whether the game has ended or not
     * @return whether the game has ended or not
     */
    public static boolean getGameHasEnded() {
        return gameHasEnded;
    }

    /**
     * Sets whether the game has ended or not
     * @param gameEnd the end condition of the game
     */
    public static void setGameHasEnded(boolean gameEnd) {
        gameHasEnded = gameEnd;
    }

	/**
     * Sets the grid and hand size of the game
	 * @param gridIInput the number of columns in the gameboard
	 * @param gridJInput the number of rows in the gameboard
	 * @param handSizeInput the number of tiles in the player's hand
	 */
	public void setParameters(int gridIInput, int gridJInput, int handSizeInput) {
		gameBoardArray = new TsuroButton[gridIInput][gridJInput];
        for (int i = 0; i < players.length; i++) {
            players[i].setHandArray(new TsuroButton[handSizeInput]);
        }
	}

	/**
	 * Runs the Tsuro game
	 */
	public void start(Stage primaryStage) {

        players[0] = new Player(Color.RED);
        players[1] = new Player(Color.BLUE);

		setParameters(gridI, gridJ, handSize);

		/**
		 * The grid pane of the gameboard
		 */
		GridPane gameBoardGP = new GridPane();

		/**
		 * The event handler for an action on the gameboard
		 */
		EventHandler<ActionEvent> GameBoardAction = new GameBoardAction();

		for (int i = 0; i < gridI; i++) {
			for (int j = 0; j < gridJ; j++) {
				gameBoardArray[i][j] = new TsuroButton(100, 100);
				gameBoardArray[i][j].setOnAction(GameBoardAction);
				gameBoardGP.add(gameBoardArray[i][j], i + 1, j + 1);
			}
		}

        Random startingPositionGenerator = new Random();
        
        players[0].setPosition(startingPositionGenerator.nextInt(8));
        if (players[0].getPosition() == 0 || players[0].getPosition() == 1) {
            players[0].setTilePositionI(startingPositionGenerator.nextInt(gridI));
            players[0].setTilePositionJ(0);
        }
        else if (players[0].getPosition() == 2 || players[0].getPosition() == 3) {
            players[0].setTilePositionI(gridI - 1);
            players[0].setTilePositionJ(startingPositionGenerator.nextInt(gridJ));
        }
        else if (players[0].getPosition() == 4 || players[0].getPosition() == 5) {
            players[0].setTilePositionI(startingPositionGenerator.nextInt(gridI));
            players[0].setTilePositionJ(gridJ - 1);
        }
        else if (players[0].getPosition() == 6 || players[0].getPosition() == 7) {
            players[0].setTilePositionI(0);
            players[0].setTilePositionJ(startingPositionGenerator.nextInt(gridJ));
        }
        gameBoardArray[players[0].getTilePositionI()][players[0].getTilePositionJ()].setBackgroundColor(players[0].getColor());
        
        while ((players[1].getTilePositionI() == -1 && players[1].getTilePositionJ() == -1) || (players[1].getTilePositionI() == players[0].getTilePositionI() && players[1].getTilePositionJ() == players[0].getTilePositionJ())) {
            players[1].setPosition(startingPositionGenerator.nextInt(8));
            if (players[1].getPosition() == 0 || players[1].getPosition() == 1) {
                players[1].setTilePositionI(startingPositionGenerator.nextInt(gridI));
                players[1].setTilePositionJ(0);
            }
            else if (players[1].getPosition() == 2 || players[1].getPosition() == 3) {
                players[1].setTilePositionI(gridI - 1);
                players[1].setTilePositionJ(startingPositionGenerator.nextInt(gridJ));
            }
            else if (players[1].getPosition() == 4 || players[1].getPosition() == 5) {
                players[1].setTilePositionI(startingPositionGenerator.nextInt(gridI));
                players[1].setTilePositionJ(gridJ - 1);
            }
            else if (players[1].getPosition() == 6 || players[1].getPosition() == 7) {
                players[1].setTilePositionI(0);
                players[1].setTilePositionJ(startingPositionGenerator.nextInt(gridJ));
            }
        }
        gameBoardArray[players[1].getTilePositionI()][players[1].getTilePositionJ()].setBackgroundColor(players[1].getColor());

        for (int i = 0; i < handSize; i++) {
            players[0].getHandArray()[i] = new TsuroButton(100, 100);
            players[0].getHandArray()[i].setConnections(players[0].getHandArray()[i].makeRandomConnectionArray());
            players[0].getHandArray()[i].setOnAction(players[0].getPlayerAction());
            players[0].getHandArray()[i].addStone(players[0].getColor(), players[0].getPosition());
            players[0].getGridPane().add(players[0].getHandArray()[i], i + 1, 1);
        }

        for (int i = 0; i < handSize; i++) {
            players[1].getHandArray()[i] = new TsuroButton(100, 100);
            players[1].getHandArray()[i].setConnections(players[1].getHandArray()[i].makeRandomConnectionArray());
            players[1].getHandArray()[i].setOnAction(players[1].getPlayerAction());
            players[1].getHandArray()[i].addStone(players[1].getColor(), players[1].getPosition());
            players[1].getGridPane().add(players[1].getHandArray()[i], i + 1, 1);
        }

        for (int i = 0; i < players.length; i++) {
            players[i].getStage().setTitle("Player " + (i + 1));
            players[i].getStage().setScene(players[i].getScene());
            players[i].getStage().show();
        }

		/**
		 * The border pane of the gameboard
		 */
		BorderPane gameBoardBP = new BorderPane();
		gameBoardBP.setCenter(gameBoardGP);

		/**
		 * The scene of the gameboard
		 */
		Scene gameBoardScene = new Scene(gameBoardBP);

		primaryStage.setTitle("Tsuro");
		primaryStage.setScene(gameBoardScene);
		primaryStage.show();
	}

	/**
	 * A class that represents an action performed on the gameboard
	 */
	public class GameBoardAction implements EventHandler<ActionEvent> {
		
        /**
		 * The handle method for a gameboard action
		 * @param e the action event from the gameboard
		 */
		public void handle(ActionEvent e) {
			TsuroButton tsuroButton = (TsuroButton) e.getSource();
            if (!players[turn].turn(tsuroButton)) {
                return;
            }
            turn = (turn + 1)%players.length;
            if (gameHasEnded) {
                if (players[0].getHasLost() && players[1].getHasLost()) {
                    System.out.println("Game Over");
                    System.out.println("Tie!");
                }
                else if (players[0].getHasLost()) {
                    System.out.println("Game Over");
                    System.out.println("Player 2 Wins!");
                }
                else if (players[1].getHasLost()) {
                    System.out.println("Game Over");
                    System.out.println("Player 1 Wins!");
                }
            }
		}

	}

	/**
	 * The main method of the Tsuro game
	 */
	public static void main(String[] args) {
        Scanner settings = new Scanner(System.in);
        boolean isValidOption = false;
        boolean isValidSize = false;
        System.out.println("Welcome to Tsuro!");
        do {
            System.out.println("If you want to play the game, type 'Play'. If you want to use custom settings, type 'Custom'.");
            String option = settings.nextLine();
            if (option.equals("Play")) {
                isValidOption = true;
                setGridI(6);
				setGridJ(6);
				setHandSize(3);
            }
            else if (option.equals("Custom")) {
                isValidOption = true;
                System.out.println("Please enter the grid size and hand size.");
                do {
                    try {
                        System.out.println("Columns:");
                        int i = settings.nextInt();
                        System.out.println("Rows:");
                        int j = settings.nextInt();
                        System.out.println("Hand Size:");
                        int hand = settings.nextInt();
                        if (i >= 2 && j >= 2 && hand > 0) {
                            isValidSize = true;
                            setGridI(i);
                            setGridJ(j);
                            setHandSize(hand);
                        }
                        else {
                            System.out.println("Please enter a larger grid size or hand size. The minimum grid size is 2 x 2 and the minimum handsize is 1.");
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Please enter the size in a valid format.");
                        settings.next();
                    }
                } while(!isValidSize);
            }
        } while(!isValidOption);
        settings.close();
        Application.launch(args);
	}

}