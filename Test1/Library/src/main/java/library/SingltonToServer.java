package library;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public  class SingltonToServer {

    private static SingltonToServer singltonToServer = new SingltonToServer();

   public Queue<ByteBuffer>   toServer = new LinkedList<ByteBuffer>();

    private SingltonToServer() {

    }

    public static SingltonToServer getInstance() {
        return singltonToServer;
    }
}
