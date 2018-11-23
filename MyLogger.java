import java.io.IOException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.util.logging.*; 
import java.util.ArrayList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class MyFormatter extends Formatter {
    // Create a DateFormat to format the logger timestamp.
    private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date(record.getMillis()))).append(": ");
//         builder.append("[").append(record.getSourceClassName()).append(".");
//         builder.append(record.getSourceMethodName()).append("] - ");
//         builder.append("[").append(record.getLevel()).append("] - ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }
}

public class MyLogger {
	
	private int peerID1;
	private int numbpieces = 0;
	private Logger logger;	
	public MyLogger(int peerID1) throws SecurityException, IOException {
		this.peerID1 = peerID1;
		logger = Logger.getLogger("Peer" + peerID1);
// 		System.out.println(logger.getHandlers());
// 		logger.removeHandler(logger.getHandlers()[0]);
		FileHandler filelog = new FileHandler("log_peer_" + peerID1 + ".log", true);
		filelog.setFormatter(new MyFormatter());
		logger.addHandler(filelog);
		
// 		ConsoleHandler consolelog = new ConsoleHandler();
//         consolelog.setFormatter(new MyFormatter());
//         logger.addHandler(consolelog);
	}
	
	public void TCPClientLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " makes a connection to Peer " + peerID2);
	}
	public void TCPServerLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " is connected from Peer " + peerID2); 
	}
	
	public void PreferredNeighborsLog(ArrayList<Integer> prefNeighbors) {
		
		StringBuffer neighborstring = new StringBuffer();
		neighborstring.append("Peer " + peerID1 + " has the preferred neighbores ");
		int x = 0;
		while (x <= (prefNeighbors.size() - 1)){
			if (x == (prefNeighbors.size() - 1)) {
				neighborstring.append(prefNeighbors.get(x) + "\n");
			} else {
				neighborstring.append(prefNeighbors.get(x) + ", ");
			}
			x++; 
		}
		
		logger.log(Level.INFO, neighborstring.toString());
	}
	
	public void OptNeighborsLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " has the optimistically unchoked neighbor " + peerID2);
	}
	
	public void UnchokingLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " is unchoked by " + peerID2);
	}
	
	public void ChokingLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " is choked by " + peerID2);
	}
	
	public void HaveLog(int peerID2, int pieceindex) {
		logger.log(Level.INFO, "Peer " + peerID1 + " received the 'have' message from " + peerID2 + " for the piece " + pieceindex);
	}
	
	public void InterestedLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " received the 'interested' message from " + peerID2);
	}
	
	public void NotInterestedLog(int peerID2) {
		logger.log(Level.INFO, "Peer " + peerID1 + " received the 'not interested' message from " + peerID2);
	}
	
	public void DownloadLog(int peerID2, int pieceindex) {
		this.numbpieces ++;
		logger.log(Level.INFO, "Peer " + peerID1 + " has downloaded the piece " + pieceindex + " from " + peerID2 + ".\nNow the number of pieces it has is " + this.numbpieces); 
	}
	
	public void CompletionLog() {
		logger.log(Level.INFO, "Peer " + peerID1 + " has downloaded the complete file");
	}
	
// 	public static void main(String args[]) throws Exception {
// 	
// 		MyLogger log = new MyLogger(1001);
// 		log.NotInterestedLog(1002);
// 		log.NotInterestedLog(1003);
// 		log.DownloadLog(1002, 1);
// 		log.DownloadLog(1002, 2);
// 
// 	}
	
}
