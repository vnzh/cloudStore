package library;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class SingltonResponse {

    private  static  SingltonResponse singltonResponse = new SingltonResponse();

   private SingltonResponse() {

    }

    public Queue<ByteBuffer>  serverResponses = new LinkedList<ByteBuffer>();

    public static SingltonResponse getInstance() {
        return singltonResponse;
    }
}
