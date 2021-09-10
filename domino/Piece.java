package domino;

public class Piece {

	public int right;
	public int left;

	/**
	 * Creates a Domino piece with the given values. Each piece has 2 values.
	 *
	 * @return: a Domino Piece object with the given values.
	 */
	public Piece(int left, int right) {
		this.left = left;
		this.right = right;
	}

	public void printVerticalPiece() {
		System.out.println(" ---");
		System.out.println(" |" + this.left + "|");
		System.out.println(" ---");
		System.out.println(" |" + this.right + "|");
		System.out.println(" ---");
	}

	public void printHorizontalPiece() {
		System.out.println("-----");
		System.out.println("|" + this.left + "|" + this.right + "|");
		System.out.println("-----");
	}

	public void printPiece() {
		if (this.isPieceDouble()) {
			this.printHorizontalPiece();
		} else {
			this.printVerticalPiece();
		}
	}

	public boolean isPieceDouble() {
		return this.right == this.left;
	}

	public int getPieceMaxValue() {
		return Math.max(this.right, this.left);
	}
	
	public int getPieceTotalPoints() {
		return (this.right+this.left);
	}

	public boolean canFitInto(int[] table_possible_values) {
		return (this.canFitIntoRightSide(table_possible_values) ||
				this.canFitIntoLeftSide(table_possible_values));
	}

	public boolean canFitIntoRightSide(int[] table_possible_values) {
		return (this.left == table_possible_values[1] || this.right == table_possible_values[1]);
	}

	public boolean canFitIntoLeftSide(int[] table_possible_values) {
		return (this.left == table_possible_values[0] || this.right == table_possible_values[0]);
	}
	
	public void fitIntoRightSide(int right_side_value) {
		if (this.left!=right_side_value) {
			this.rotate();
		}
	}
	
	public void fitIntoLeftSide(int left_side_value) {
		if (this.right!=left_side_value) {
			this.rotate();
		}
	}
	
	public void rotate() {
		int past_right=this.right;
		int past_left=this.left;
		
		this.right=past_left;
		this.left=past_right;
	}

}
