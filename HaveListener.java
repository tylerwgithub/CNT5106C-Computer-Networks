import java.io.IOException;
import java.util.concurrent.Callable;
import java.net.Socket;

public class HaveListener implements Callable<Object>{

	private final int CHOKE = 0;
	private final int UNCHOKE = 1;
	private final int INTERESTED = 2;
	private final int NOTINTERESTED = 3;
	private final int HAVE = 4;
	private final int BITFIELD = 5;
	private final int REQUEST = 6;
	private final int PIECE = 7;
	private final int STOP = 8;	
	private int type;
	private int haveindex;
	private int peerID1;
	private Record record;
	private MyLogger logger;

	
	public HaveListener(int arg0, Record arg1, MyLogger arg2) {
		this.peerID1 = arg0;
		this.record = arg1;
		this.logger = arg2;
	}
	
	public Object call() throws IOException {
		Socket socket = record.getHaveSocket();
		Message message = new Message();

		for (;;) {
			message.read(socket);
			type = message.getType();
			if (type == this.HAVE) {		
				haveindex = Conversion.BytesToInt(message.getPayLoad());
				record.getBitField().turnonbit(haveindex);
				logger.HaveLog(record.getID(), haveindex);
			}
			else if (type == this.STOP) {
				break;
			} 
		}
		return new Object();
	}
	public static void main(String args[]) throws Exception {
	
	}
	
}
