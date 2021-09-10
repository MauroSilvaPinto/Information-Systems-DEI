package domino;

import java.util.ArrayList;
import java.util.Collections;

public class DominoAssets {

	/**
	 * Creates the set of Domino Pieces.
	 *
	 * @return: the full set of domino pieces.
	 */
	public static ArrayList<Piece> createDominoPieces(){
		ArrayList<Piece> domino_pieces=new ArrayList<Piece>();

		for (int left_value = 0; left_value < 6+1; left_value++) {
			for (int right_value = 0; right_value<left_value+1; right_value++) {
				domino_pieces.add(new Piece(left_value,right_value));
			}
		}
		return domino_pieces;
	}

	/**
	 * Decides if the game is over. A game is over if a player has reached 50 points.
	 *
	 * @return: a boolean. True if at least a player reached 50 points. False if otherwise.
	 */
	public static boolean isGameNotOver(ArrayList<Player> players) {
		int max_points=0;
		for (Player player:players) {
			if (player.points>max_points) {
				max_points=player.points;
			}
		}
		return (max_points<50);
	}
	
	public static void declareWinner(ArrayList<Player> players) {
		int index_winner=0;
		int points_winner=51;
		for (int i=0;i<players.size();i++) {
			if (players.get(i).points<points_winner) {
				index_winner=i;
				points_winner=players.get(i).points;
			}
		}
		System.out.println("The Winner is "+players.get(index_winner).name);
		System.out.println("");
	}
	
	public static void declareGameResults(ArrayList<Player> players) {
		declareWinner(players);
		printPlayersPoints(players);
	}

	/**
	 * Takes the players and the starting one, and puts them into the playing order.
	 *
	 * @return: an array list of Players. The first player will the first to play, and consecutively.
	 */
	public static ArrayList<Player> orderPlayersStartingFrom(ArrayList<Player> players, int index){
		ArrayList<Player> new_match_order=new ArrayList<Player>();

		// from index till end
		for (int i=index;i<players.size();i++) {
			new_match_order.add(players.get(i));
		}

		// from 0 till index
		for (int i=0;i<index;i++) {
			new_match_order.add(players.get(i));
		}
		
		return new_match_order;
	}
	
	public static void printPlayersByOrder(ArrayList<Player> players) {
		for (Player player:players) {
			System.out.println("Player "+player.name);
		}
		System.out.println("");
	}
	
	public static void printPlayersPoints(ArrayList<Player> players) {
		for (Player player:players) {
			System.out.println("Player "+player.name+": "+player.points+" points");
		}
		System.out.println("");
	}
	
	public static void shufflePieces(ArrayList<Piece> pieces) {
		Collections.shuffle(pieces);
	}
	
	public static void showTableGame(ArrayList<Piece> pieces) {
		System.out.println(" ---- Table Game ---- ");
		for (Piece piece:pieces) {
			piece.printPiece();
		}
		System.out.println(" -------------------- ");
		System.out.println("");
	}
	
	public static boolean isHandOrTableEmpty(ArrayList<Piece> pieces) {
		return pieces.size()==0;
	}
	
	public static void addPieceToEmptyTable(ArrayList<Piece> table_pieces,Piece piece) {
		table_pieces.add(piece);
		}
	
	public static void fitPieceInTable(ArrayList<Piece> table_pieces, Piece piece) {
		int[] table_possible_values=DominoAssets.getTablePossibleValues(table_pieces);
		if (piece.canFitIntoRightSide(table_possible_values)){
			piece.fitIntoRightSide(table_possible_values[1]);
		} else {
			piece.fitIntoLeftSide(table_possible_values[0]);
		}
	}
	
	public static void putPieceInRigtSideTable(ArrayList<Piece> table_pieces, Piece piece){
		table_pieces.add(piece);
	}
	
	public static void putPieceInLeftSideTable(ArrayList<Piece> table_pieces, Piece piece){
		table_pieces.add(0,piece);
	}
	
	public static void putPieceInTable(ArrayList<Piece> table_pieces, Piece piece) {
		if (DominoAssets.isHandOrTableEmpty(table_pieces)){
			putPieceInLeftSideTable(table_pieces, piece);
		} else {
			int[] table_possible_values=DominoAssets.getTablePossibleValues(table_pieces);
			if (piece.canFitIntoRightSide(table_possible_values)){
				putPieceInRigtSideTable(table_pieces, piece);
			} else {
				putPieceInLeftSideTable(table_pieces, piece);
			}
		}
		
	}
	
	public static void printPoints(ArrayList<Player> players) {
		System.out.println("Players Match Points: ");
		for (Player player:players) {
			System.out.println(player.name+" : "+player.points+" points");
	}
		System.out.println("");
	}
	
		
	
	public static boolean isItPossibleToPlay(ArrayList<Piece> hand, ArrayList<Piece> table_pieces){
		int[] table_possible_values=getTablePossibleValues(table_pieces);
		
		return canAnyPiecePlay(table_possible_values,hand);
	}
	
	public static boolean canAnyPiecePlay(int[] table_possible_values, ArrayList<Piece> hand) {
		for (Piece piece_in_hand:hand) {
			if (piece_in_hand.canFitInto(table_possible_values)) {
				return true;
			}
		}
		return false;
	}
	
	public static void declareMatchWinner(ArrayList<Player> players) {
		// a stupid quantity of points
		int min_points=500;
		int index_player=0;
		for (int i=0;i<players.size();i++) {
			if (players.get(i).calculateAccumulatedPoints()<min_points){
				min_points=players.get(i).calculateAccumulatedPoints();
				index_player=i;
			}
		}
		System.out.println("Winner of the match: "+players.get(index_player).name+
				" with "+min_points+" points");
		System.out.println("");
	}
	
	public static int getMatchWinnerIndex(ArrayList<Player> players) {
		// a stupid quantity of points
		int min_points=500;
		int index_player=0;
		for (int i=0;i<players.size();i++) {
			if (players.get(i).calculateAccumulatedPoints()<min_points){
				min_points=players.get(i).calculateAccumulatedPoints();
				index_player=i;
			}
		}
		return index_player;
	}
	
	public static void giveAllPiecesToTable(ArrayList<Player> players, ArrayList<Piece> on_the_table_pieces) {
		for (Player player:players) {
			player.giveAllPiecesToTable(on_the_table_pieces);
		}
	}
	
	public static void accumulatePointsInPlayers(ArrayList<Player> players){
		for (Player player:players) {
			player.accumulatePoints();
		}
	}
	
	public static int[] getTablePossibleValues(ArrayList<Piece> table_pieces) {
		int[] possible_values= new int[2];
		possible_values[0]=table_pieces.get(0).left;
		possible_values[1]=table_pieces.get(table_pieces.size()-1).right;
		
		return possible_values;
	}
			
	}
	

