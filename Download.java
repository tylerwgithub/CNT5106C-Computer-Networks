import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Download implements Callable<Object>
{
  private Record record;
  private BitField bitfield;
  private Socket socket;
  private Filemanger filemnger;
  private Record[] neighbors;
  private MyLogger logger;
  private int peerID1;
  
public Download(int peerID1, Record record, Record[] neighbors, BitField bitfield, Filemanger filemnger, MyLogger logger) {
		
	this.peerID1 = peerID1;
	this.neighbors = neighbors;
	this.record = record;
	this.bitfield = bitfield;
	this.socket = record.getDownloadSocket();
	this.filemnger = filemnger;
	this.logger = logger;
		
	}
  
  public Object call()
    throws IOException
  {
    Message localMessage = new Message();
    
    int i;
    do
    {
      localMessage.read(socket);
      //when receiving STOP message, break
      if (localMessage.getType() == 8) {
        for (i = 0; i < neighbors.length; i++) {
          localMessage.send(neighbors[i].getHaveSocket());
        }
        break;
      }
      
    } while (localMessage.getType() != 1);
    //when receiving CHOKE message or not interests to the sender
    logger.UnchokingLog(record.getID());
    for (;;) {
      i = bitfield.getInterestingIndex(record.getBitField());
      // send out not interested message
      if (i == -1) {
        localMessage.setType(3);
        localMessage.setPayLoad(null);
        localMessage.send(socket);
        break;
      }
      //send out request message
      localMessage.setType(6);
      localMessage.setPayLoad(Conversion.IntToBytes(i));
      localMessage.send(socket);
      

      localMessage.read(socket);
      

      if (localMessage.getType() == 0) {
        logger.ChokingLog(record.getID());
        break;
      }
      
      Piece localPiece = new Piece(i, localMessage.getPayLoad());
      filemnger.writepiece(localPiece);
      bitfield.turnonbit(i);
      
      record.incDownload();
      logger.DownloadLog(record.getID(), i);
      if (bitfield.finished()) {
        logger.CompletionLog();
      }
      
	//send out have message to all the neighbors
      localMessage.setType(4);
      localMessage.setPayLoad(Conversion.IntToBytes(i));
      for (int j = 0; j < neighbors.length; j++) {
        localMessage.send(neighbors[j].getHaveSocket());
      }
    }
    




    return new Object();
  }
}
