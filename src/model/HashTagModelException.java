package model;

public class HashTagModelException extends RuntimeException{
	
	private HashTagModelException(String message) {
		super(message);
	}
	
	public static final
	HashTagModelException outOfBounds(int lin, int col) {
		
		return new HashTagModelException(String.format("""
				Index (%d, %d) out of bounds [0-2][0-2]""", lin, col));
	}
	
	public static final
	HashTagModelException lineOutOfBounds(int lin) {
		
		return new HashTagModelException(String.format("""
				Line index (%d) out of bounds, [0-2]""", lin));
	}
	
	public static final
	HashTagModelException columnOutOfBounds(int col) {
		
		return new HashTagModelException(String.format("""
				Column index (%d) out of bounds, [0-2]""", col));
	}

	public static final 
	HashTagModelException blockedCellException(int lin, int col) {

		return new HashTagModelException(String.format("""
				Cell(%d, %d) is already marked.
				""", lin, col));
	}
}
