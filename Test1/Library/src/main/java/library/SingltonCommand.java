package library;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SingltonCommand {

    private static final SingltonCommand singltonCommand = new SingltonCommand();
    public Queue<ByteBuffer> commandList =  new  LinkedList<ByteBuffer>();

    private SingltonCommand() {

    }

    public static SingltonCommand getInstance() {
        return singltonCommand;
    }
}
