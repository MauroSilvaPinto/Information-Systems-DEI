package domino;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TableGame {

	public static void main(String args[]){

		// Creates the scanner object
		Scanner scan=new Scanner(System.in);

		String user_name="";

		// Getting the users name
		System.out.println("Welcome to Domino SI Game.");
		System.out.println("Please insert your player name: ");
		user_name=scan.nextLine();
		//user_name="mauro";

		// Creating all players
		Player user_player=new Player(user_name);
		Player bot_1=new Player("bot_1");
		Player bot_2=new Player("bot_2");
		Player bot_3=new Player("bot_3");

		// Creating the array list of players
		ArrayList<Player> players= new ArrayList<Player>();
		players.add(user_player);
		players.add(bot_1);
		players.add(bot_2);
		players.add(bot_3);

		// Creating the Table Game
		ArrayList<Piece> on_the_table_pieces=DominoAssets.createDominoPieces();

		// Assigning who starts the first match, at random
		Random random = new Random();

		int first_player_playing=random.nextInt(4);
		int first_player_distributing=random.nextInt(4);

		// First iteration: assigning random playing order and distribution order
		ArrayList<Player> playing_order=DominoAssets.orderPlayersStartingFrom(players,first_player_playing);
		ArrayList<Player> distribution_order=DominoAssets.orderPlayersStartingFrom(players,first_player_distributing);

		boolean game_on=true;
		
		while (game_on){

			System.out.println("----- New Match ------ ");
			System.out.println("");

			System.out.println("--- Players Order  ---- ");
			DominoAssets.printPlayersPoints(playing_order);

			System.out.println("--- Distributions Order  ---- ");			
			DominoAssets.printPlayersByOrder(distribution_order);
			System.out.println("");


			// shuffling the pieces
			DominoAssets.shufflePieces(on_the_table_pieces);
			System.out.println("Shuffling the pieces ...");
			System.out.println("");

			// distributing the pieces to all players
			for (Player player:distribution_order) {
				player.receivePieces(on_the_table_pieces);
				System.out.println("Player "+player.name+" receiving pieces ...");
			}
			System.out.println("");
			
			boolean is_match_on=true;
			
			// to avoid infinite loops
			int game_size_previous=0;
			
			// A NEW MATCH
			while(is_match_on) {
				// get the number of pieces on the table
				game_size_previous=on_the_table_pieces.size();
				
				// distributing the pieces to all players
				for (Player player:playing_order) {
					if (is_match_on) {
						if (player.equals(user_player)) {
							System.out.println("");
							DominoAssets.showTableGame(on_the_table_pieces);
							player.showHand();
							player.userPlay(on_the_table_pieces);
						} else {
							player.automaticPlay(on_the_table_pieces);
							System.out.println("Player "+player.name+" just played");
						}
						
					}
					if (player.hasFinishedThisMatch()){
						is_match_on=false;
						// this break is to stop more players from playing
						break;
					}
				}
				
				// If in a round of 4 players, no one plays a piece
				// it is because it stagnated, you can stop.
				if (game_size_previous==on_the_table_pieces.size()) {
					is_match_on=false;
				}
				System.out.println("");
			}
			System.out.println("Match is Over.");
			
			DominoAssets.declareMatchWinner(playing_order);
			DominoAssets.accumulatePointsInPlayers(playing_order);
			DominoAssets.printPoints(playing_order);
			
			game_on=DominoAssets.isGameNotOver(playing_order);
			if (game_on) {
				// return the non-used pieces to the table
				DominoAssets.giveAllPiecesToTable(players,on_the_table_pieces);
				
				// Rotating distribution
				// it rotates 1 step as it orders from the second position (index 1):
				distribution_order=DominoAssets.orderPlayersStartingFrom(distribution_order,1);

				// Assigning First Player
				int winning_player_index=DominoAssets.getMatchWinnerIndex(playing_order);
				playing_order=DominoAssets.orderPlayersStartingFrom(playing_order,winning_player_index);
			} else {
				System.out.println("------------ The Game Is Over ------------");
				DominoAssets.declareGameResults(playing_order);	
			}	
		}
		scan.close();
	}
}
