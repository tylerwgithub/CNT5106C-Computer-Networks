/**
 * 
 */
package cn;

/**
 * @author 14596
 *
 */
public class Piece {
	private final int numpiece;
	private final byte[] piecebyte;
	
	public Piece(int num, byte[] piecebyte) {
		numpiece=num;
		this.piecebyte=piecebyte;
	}

	/**
	 * @return the numpiece
	 */
	public int getNumpiece() {
		return numpiece;
	}

	/**
	 * @return the piecebyte
	 */
	public byte[] getPiecebyte() {
		return piecebyte;
	}
	

}
