package domino;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	
	Scanner scan=new Scanner(System.in);
	public String name;
	public int points;
	public ArrayList<Piece> hand;


	/**
	 * Creates a Player with the given name.
	 * Each player starts with 0 points and an empty hand.
	 *
	 *@return: a Player object with the given name, 0 points and an empty hand.
	 */
	public Player(String name) {
		this.name = name; 
		this.points= 0;
		this.hand= new ArrayList<Piece>();
	}

	public void receivePieces(ArrayList<Piece> pieces) {
		int size_index=pieces.size()-1;
		for (int i=0;i<7;i++) {
			this.hand.add(pieces.get(size_index-i));
			pieces.remove(size_index-i);
		}
	}

	public void showHand(){
		System.out.println(" ---- Player "+this.name+" hand ---- ");
		System.out.println("");
		for (int i=0;i<this.hand.size();i++) {
			System.out.println(i+"): ");
			this.hand.get(i).printPiece();
			System.out.println("");
		}
		System.out.println(this.hand.size()+"): ");
		System.out.println(" Pass ");

		System.out.println("");
	}
	
	public int calculateAccumulatedPoints() {
		int accumulated_points=0;
		for (Piece piece:this.hand) {
			accumulated_points=accumulated_points+piece.getPieceTotalPoints();
		}
		return accumulated_points;
	}
	
	public void accumulatePoints() {
		for (Piece piece:this.hand) {
			this.points=this.points+piece.getPieceTotalPoints();
		}
	}

	public void automaticPlay(ArrayList<Piece> table_pieces){
		if (DominoAssets.isHandOrTableEmpty(table_pieces)) {
			Piece piece=this.playHighestPiece();
			DominoAssets.addPieceToEmptyTable(table_pieces,piece);
		} else {
			if (DominoAssets.isItPossibleToPlay(this.hand, table_pieces)) {
				Piece piece=this.playHighestPieceThatFits(table_pieces);
				DominoAssets.fitPieceInTable(table_pieces,piece);
				DominoAssets.putPieceInTable(table_pieces,piece);
			} else {
				this.pass();
			}
		}
	}
	
	public void giveAllPiecesToTable(ArrayList<Piece> pieces_on_table) {
		for (Piece piece:this.hand) {
			pieces_on_table.add(piece);
		}
		this.hand.clear();
	}

	public void pass(){
		;
	}

	public Piece playHighestPiece() {
		int max_value=0;
		int index_piece=0;
		Piece piece_to_play;
		for (int i=0;i<this.hand.size();i++) {
			if (this.hand.get(i).getPieceTotalPoints()>max_value) {
				max_value=this.hand.get(i).getPieceTotalPoints();
				index_piece=i;
			}
		}
		piece_to_play=this.hand.get(index_piece);
		this.hand.remove(index_piece);
		return piece_to_play;
	}

	public Piece playHighestPieceThatFits(ArrayList<Piece> table_pieces) {
		int max_value=0;
		int index_piece=0;
		Piece piece_to_play;
		int[] table_possible_values=DominoAssets.getTablePossibleValues(table_pieces);
		for (int i=0;i<this.hand.size();i++) {
			if (this.hand.get(i).canFitInto(table_possible_values)) {
				if (this.hand.get(i).getPieceTotalPoints()>max_value) {
					max_value=this.hand.get(i).getPieceTotalPoints();
					index_piece=i;
				}
			}	
		}
		piece_to_play=this.hand.get(index_piece);
		this.hand.remove(index_piece);
		return piece_to_play;
	}

	public boolean isMoveValidInteger(int move) {
		return move>=0 && move<=this.hand.size();
	}

	public boolean canMoveBePerformed(int move, ArrayList<Piece> table_pieces) {
		if (DominoAssets.isHandOrTableEmpty(table_pieces)){
			return true;
		} else {
			int[] table_possible_values=DominoAssets.getTablePossibleValues(table_pieces);
			return this.hand.get(move).canFitInto(table_possible_values);	
		}
	}

	public boolean isMoveToPass(int move) {
		return move==this.hand.size();
	}
	
	public Piece playPiece(int move) {
		Piece piece_to_play=this.hand.get(move);
		this.hand.remove(move);
		return piece_to_play;
	}
	
	public boolean hasFinishedThisMatch() {
		return this.hand.size()==0;
	}
	
	public void userPlay(ArrayList<Piece> on_the_table_pieces){
		boolean is_move_not_performed=true;

		while (is_move_not_performed) {
			System.out.println("Choose your move: ");
			if (!scan.hasNextInt()) {
				scan.nextLine();
				System.out.println("Input is not an integer.");
				System.out.println("");
			} else {
				int move = scan.nextInt();
				scan.nextLine();
				if (this.isMoveValidInteger(move)) {
					if (this.isMoveToPass(move)) {
						System.out.println("You passed.");
						is_move_not_performed=false;
						System.out.println("");
					}
					else if (canMoveBePerformed(move,on_the_table_pieces)) {
						Piece piece=this.playPiece(move);
						if (!DominoAssets.isHandOrTableEmpty(on_the_table_pieces)) {
							DominoAssets.fitPieceInTable(on_the_table_pieces,piece);
						}
						DominoAssets.putPieceInTable(on_the_table_pieces,piece);
						is_move_not_performed=false;
						} else {
						System.out.println("That move cannot be performed.");
					}
				} else {
					System.out.println("Invalid input.");
					System.out.println("");
				}
			}
		}
	}
}
