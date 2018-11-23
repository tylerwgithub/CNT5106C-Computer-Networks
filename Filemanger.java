/**
 * 
 */
package cn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author 14596
 *
 */
public class Filemanger {
	private c config;
	private RandomAccessFile file;
	
	public Filemanger(c config, int peerid) throws FileNotFoundException {
		this.config=config;
		String path=peerid+"/";
		File peer=new File(path);
		if(!peer.exists()) peer.mkdir();
		file = new RandomAccessFile(peer + config.getFilename(), "rw");	
	}

	public synchronized void writepiece(Piece piece) throws IOException {
		int piecenum=0;
		int len=0;
		int num=piece.getNumpiece();
		byte[] bytes=piece.getPiecebyte();
		int start=config.getPieceSize()*(num);
		if(config.getFilesize()%config.getPieceSize()==0) {piecenum=config.getFilesize()/config.getPieceSize();}/////
		else {piecenum=config.getFilesize()/config.getPieceSize()+1;}///////////config
		file.seek(start);
		if(num+1!=piecenum) {
			len=config.getPieceSize();
			}
		else {
			len=config.getFilesize()-config.getPieceSize()*num;
		}
		for(int i=0;i<len;i++) {
			file.writeByte(bytes[i]);
		}
	}
	
	@SuppressWarnings("null")
	public synchronized Piece readpiece(int num) throws IOException {
		////num:the piece number of the piece that we want to read, piece number starts from 0
		int piecenum=0;
		int start=0;
		int len=0;
		num=num+1;
		byte[] bytes = null;
		if(config.getFilesize()%config.getPieceSize()==0) {piecenum=config.getFilesize()/config.getPieceSize();}/////
		else {piecenum=config.getFilesize()/config.getPieceSize()+1;}///////////config
		if(num!=piecenum) {
			start=config.getPieceSize()*(num-1);
			len=config.getPieceSize();}
		else {
			start=config.getPieceSize()*(num-1);
			len=config.getFilesize()-config.getPieceSize()*(num-1);}
		file.seek(start);
		for(int i=0;i<len;i++) {
			byte b=file.readByte();
			bytes[i]=b;
		}
		Piece piece=new Piece(num-1,bytes);
		return piece;
	}




}
