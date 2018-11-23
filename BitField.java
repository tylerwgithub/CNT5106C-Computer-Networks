/**
 * 
 */
package cn;

import cn.BitField;

/**
 * @author 14596
 *
 */
public class BitField {
	private boolean finish;
	private int numpiecedl;
	private boolean[] bitfield;
	private final int numpiece;
	
	public BitField(int numpiece) {
		this.numpiece=numpiece;
		this.finish=false;
		this.numpiecedl=0;
		this.bitfield=new boolean[numpiece];
		for(int i=0;i<numpiece;i++) {
			bitfield[i]=false;
		}
	}
	
	public synchronized void turnonbit(int num) {
		if(bitfield[num]==false) {
			bitfield[num]=true;
			numpiecedl++;
			if(numpiecedl==numpiece) finish=true;
		}
	}
	
	public synchronized void turnonall() {
		for(int i=0;i<numpiece;i++) bitfield[i]=true;
		finish=true;
		numpiecedl=numpiece;
	}
	
	public synchronized boolean finished() {
		return finish;	
	}
	
	public synchronized byte[][] bytes() {
		int numfield=0;
		byte[][] bytes=new byte[numfield][8];
		if(numpiece%8==0)  numfield=numpiece/8;
		else  numfield=numpiece/8+1;
		for(int i=0;i<numfield;i++) {
			for(int j=0;j<8;j++) {
				bytes[i][j]=(byte)0;
			}
		}
		for(int i=0;i<numpiece;i++) {
			int numbyte=i/8;
			int numbit=i%8;
			for(int j=0;j<8;j++) {
				if(bitfield[i]==true) {
					bytes[numbyte][j]=1;		
				} 
////////////////////////////////////	bytes[numbyte] = (byte) (bytes[numbyte] | (1 << numbit));	
			}
		}
		return bytes;
	}
	
	public synchronized void setbitfield(byte[][] bytes) {
		numpiecedl=0;
		for(int i=0;i<numpiece;i++) {
			int numbyte=i/8;
			for(int j=0;j<8;j++) {
				if(bytes[numbyte][j]==0) {
					bitfield[i]=false;
				}
				else {
					bitfield[i]=true;
					numpiecedl++;
				}
			}		
//			int numbyte=i/8;
//			int numbit=i%8;
//			if(((bytes[numbyte]) & (1 << numbit))==1){
//				bitfield[i]=true;
//				numpiecedl++;
//			}
//			else {bitfield[i]=false;}
		}
		if (numpiecedl == numpiece) {
			finish = true;
		}
		
	}
	
	public synchronized int getInterestingIndex(BitField bitfield) {
		int index = -1 ;
		for (int i = 0; i < numpiece; i++) {
			if ((this.bitfield[i] == false) && bitfield.bitfield[i] == true) {
				return i;
			} 
		}
		return index;
	}
	
//	public String getText() {
//		StringBuffer text = new StringBuffer();
//		for (int i = 0 ; i < this.numpiece; i++) {
//			if (bitfield[i]) {
//				text.append("1");
//			} else {
//				text.append("0");
//			}
//		}
//		return text.toString();
//	}
}
