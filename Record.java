package cn;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import cn.BitField;

public class Record {
	private final int ID;
	private final Socket downloadSocket;
	private final Socket uploadSocket;
	private final Socket haveSocket;
	private AtomicInteger download;
	//state = 0: choke
	//state = 1: unchoke
	//state = 2: optUnchoke
	private AtomicInteger state;
	private BitField bitField;
	
	public Record(int numPiece, int ID, Socket socket1, Socket socket2, Socket socket3) {
		
		this.ID = ID;
		downloadSocket = socket1;
		uploadSocket = socket2;
		haveSocket = socket3;
		download = new AtomicInteger(0);
		state = new AtomicInteger(0);
		bitField = new BitField(numPiece);
		
	}
	
	public boolean isFinished() {
		return bitField.finished();
	}

	public void turnOnBit(int which) {
		
		bitField.turnonbit(which);
	}

	/**
	 * @return the download
	 */
	public AtomicInteger getDownload() {
		return download;
	}

	/**
	 * @param download the download to set
	 */
	public void setDownload(AtomicInteger download) {
		this.download = download;
	}
	
	//increase the download data amount
	public void incDownload() {
		download.incrementAndGet();
	}
	
	public void clearDownload() {
		download.set(0);
	}

	/**
	 * @return the state
	 */
	public AtomicInteger getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(AtomicInteger state) {
		this.state = state;
	}

	/**
	 * @return the bitField
	 */
	public BitField getBitField() {
		return bitField;
	}

	/**
	 * @param bitField the bitField to set
	 */
	public void setBitField(BitField bitField) {
		this.bitField = bitField;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the downloadSocket
	 */
	public Socket getDownloadSocket() {
		return downloadSocket;
	}

	/**
	 * @return the uploadSocket
	 */
	public Socket getUploadSocket() {
		return uploadSocket;
	}

	/**
	 * @return the haveSocket
	 */
	public Socket getHaveSocket() {
		return haveSocket;
	}
	

}
