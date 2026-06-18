package model;

import common.Mark;

public class HashTagModel {

	private Mark[][] table = new Mark[3][3];
	
	public HashTagModel() {
		reset();
	}
	
	public void reset() {
		
		for(int lin=0; lin<table.length; lin++) {
			for(int col=0; col<table[lin].length; col++)
				table[lin][col] = Mark.BLANK;
		}
	}
	
	private boolean isInvalid(int index) {
	    return ( index < 0 || index > 2 );
	}

	
	private void checkBounds(int lin, int col) {
	    if( isInvalid(lin) || isInvalid(col) )
	        throw HashTagModelException.outOfBounds(lin, col);
	}
	
	private void checkBlank(int lin, int col) {
		
		if(table[lin][col] != Mark.BLANK)
			throw HashTagModelException.blockedCellException(lin, col);
	}
	
	public Mark getMark(int lin, int col) {
		
		checkBounds(lin, col);
		return table[lin][col];
	}
	
	public void setMark(int lin, int col, Mark mark) {
		
		checkBounds(lin, col);
		checkBlank(lin, col);
		
		table[lin][col] = mark;
	}
	
	public Mark[] getMarksOfLine(int lin) {
		
		if(isInvalid(lin))
			throw HashTagModelException.lineOutOfBounds(lin);
		
		Mark[] line = new Mark[3];
		for(int col=0; col<3; col++)
			line[col] = table[lin][col];
		
		return line;
	}
	
	public Mark[] getMarksOfColumn(int col) {
		
		if(isInvalid(col))
			throw HashTagModelException.columnOutOfBounds(col);
		
		Mark[] column = new Mark[3];
		column[0] = table[0][col];
		column[1] = table[1][col];
		column[2] = table[2][col];
		
		return column;
	}
	
	public Mark[] getMarksOfMainDiagonal() {
		
		Mark[] diagonal = new Mark[3];
		diagonal[0] = table[0][0];
		diagonal[1] = table[1][1];
		diagonal[2] = table[2][2];
		
		return diagonal;
	}
	
	public Mark[] getMarksOfSecondDiagonal() {
		
		Mark[] diagonal = new Mark[3];
		diagonal[0] = table[2][0];
		diagonal[1] = table[1][1];
		diagonal[2] = table[0][2];
		
		return diagonal;
	}
	



	public boolean isBlank(int lin, int col) {
	    checkBounds(lin, col);
	    return ( table[lin][col] == Mark.BLANK );
	}

	public boolean hasBlank() {
	    for(int lin=0; lin<3; lin++) {
	        for(int col=0; col<3; col++) {
	            if ( isBlank(lin, col) )
	                return true;
	        }
	    }
	    return false;
	}


	public boolean isAllBlank() {
	    for(int lin=0; lin<3; lin++) {
	        for(int col=0; col<3; col++) {
	            if ( !isBlank(lin, col) )
	                return false;
	        }
	    }
	    return true;
	}

}
