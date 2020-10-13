package library;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CommonHandler {

    boolean isConnected;
    boolean isAuthorised;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isAuthorised() {
        return isAuthorised;
    }

    public void setAuthorised(boolean authorised) {
        isAuthorised = authorised;
    }

    protected void message(String str) {
        System.out.println(str);
    }

    protected byte[] ByteBufToBytes(ByteBuffer buffer) {
//        byte[]  bytes = buffer.array();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return  bytes;
    }

//    protected ByteBuf stringToByteBuff(String str) {
//        return Unpooled.copiedBuffer(str, Charset.defaultCharset());
//    }
        protected ByteBuffer stringToByteBuff(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }


    protected String commandDecoder(byte[] bytes) {
        byte[] cndByte = new byte[Command.MAX_COMMAND_SIZE];
        System.arraycopy(bytes, 0, cndByte, 0, Command.MAX_COMMAND_SIZE);
        String cmd = new String(cndByte);
        return cmd;
    }

    protected String reponseDecoder(byte[] bytes) {

        byte[] cndByte = new byte[Command.MAX_COMMAND_SIZE];
        System.arraycopy(bytes, 0, cndByte, 0, Command.MAX_COMMAND_SIZE);
        String cmd = new String(cndByte);
        return cmd;
    }
}
