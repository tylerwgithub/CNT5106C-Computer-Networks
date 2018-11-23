// import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;


public class Config {
	
	//The common properties
	private final int NumberOfPreferredNeighbors;
	private final int UnchokingInterval;
	private final int OptimisticUnchokingInterval;
	private final String FileName;
	private final int FileSize;
	private final int PieceSize;	
	private final int numPieces;
	
	//The peer information
	private final ArrayList<Integer> ID;
	private final ArrayList<String> hostname;
	private final ArrayList<Integer> listeningport;
	private final ArrayList<Boolean> filebit;
	private final ArrayList<Integer> uploadPort;
	private final ArrayList<Integer> havePort;
	private final int numPeers;


	public Config(String common, String peerinfo) throws Exception {
		
		//read Common.cfg
		File file1 = new File(common); 
		BufferedReader br1 = new BufferedReader(new FileReader(file1)); 
		String line;
  		line = br1.readLine();
		this.NumberOfPreferredNeighbors = Integer.parseInt(line);
		line = br1.readLine();
		this.UnchokingInterval = Integer.parseInt(line);
		line = br1.readLine();
		this.OptimisticUnchokingInterval = Integer.parseInt(line);
		line = br1.readLine();
		this.FileName = line;
		line = br1.readLine();
		this.FileSize = Integer.parseInt(line);
		line = br1.readLine();
		this.PieceSize = Integer.parseInt(line);
		
// 		this.numPieces = this.FileSize/this.PieceSize;
		if (this.FileSize%this.PieceSize != 0) {
			this.numPieces = this.FileSize/this.PieceSize + 1;
 		} 
 		else{
 			this.numPieces = this.FileSize/this.PieceSize;
 		}
		
		
		//read PeerInfo.cfg
		File file2 = new File(peerinfo); 
		BufferedReader br2 = new BufferedReader(new FileReader(file2)); 
		
		ID = new ArrayList<Integer>();
		hostname = new ArrayList<String>();
		listeningport = new ArrayList<Integer>();
		filebit = new ArrayList<Boolean>();		
		uploadPort = new ArrayList<Integer>();
		havePort = new ArrayList<Integer>();
		
		int counter = 0;
		while ((line = br2.readLine()) != null) {

			String[] word = line.split(" ");
			this.ID.add(Integer.parseInt(word[0]));
			this.hostname.add(word[1]);
			this.listeningport.add(Integer.parseInt(word[2]));
			this.uploadPort.add(Integer.parseInt(word[2]) + 1);
			this.havePort.add(Integer.parseInt(word[2]) + 2);
			if (word[3].equals("0")) {
				this.filebit.add(false);
			} else {
				this.filebit.add(true);
			}
			counter++;
		}
		
		this.numPeers = counter;

	System.out.println("NumberOfPreferredNeighbors = " + this.NumberOfPreferredNeighbors);
	System.out.println("UnchokingInterval = " + this.UnchokingInterval);
	System.out.println("OptimisticUnchokingInterval = " + this.OptimisticUnchokingInterval );
	System.out.println("FileName = " + this.FileName);
	System.out.println("FileSize = " + this.FileSize);
	System.out.println("PieceSize = " + this.PieceSize);
	System.out.println("numPiece = " + this.numPieces);
	System.out.println("numPeers = " + this.numPeers);
	int i = 0;
	while (i < counter) {
		System.out.println("peer ID = " + this.ID.get(i));
		System.out.println("hostname = " + this.hostname.get(i));
		System.out.println("listening port = " + this.listeningport.get(i));
		System.out.println("upload port = " + this.uploadPort.get(i));
		System.out.println("have port = " + this.havePort.get(i));
		System.out.println("filebit = " + this.filebit.get(i));
	}
		
	}

	public int getFileSize() {
		return FileSize;
	}


	public int getNumPieces() {
		return numPieces;
	}


	public int getPieceSize() {
		return PieceSize;
	}


	public int getNumberOfPreferredNeighbors() {
		return NumberOfPreferredNeighbors;
	}


	public int getUnchokingInterval() {
		return UnchokingInterval;
	}


	public int getOptimisticUnchokingInterval() {
		return OptimisticUnchokingInterval;
	}


	public String getFileName() {
		return FileName;
	}


	public int getNumPeers() {
		return numPeers;
	}

	
	public int getDownloadPort(int index) {
		return listeningport.get(index);
	}


	public int getUploadPort(int index) {
		return uploadPort.get(index);
	}


	public int getHavePort(int index) {
		return havePort.get(index);
	}


	public ArrayList<Integer> getID() {
		return ID;
	}


	public ArrayList<String> gethostname() {
		return hostname;
	}


	public ArrayList<Boolean> getfilebit() {
		return filebit;
	}

	public static void main(String args[]) throws Exception {
	
		Config config = new Config("Common.cfg", "PeerInfo.cfg");
	}

}
