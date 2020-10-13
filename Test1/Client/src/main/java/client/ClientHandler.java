package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import library.SingltonResponse;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    private ByteBuf bBuf;
    private ByteBuffer byteBuffer;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Client connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        bBuf = (ByteBuf) msg;
        byteBuffer = bBuf.nioBuffer();

        String str = byteBuffer.toString();

        System.out.println(str);
        SingltonResponse.getInstance().serverResponses.add(byteBuffer);
        ctx.writeAndFlush("Connnected".getBytes());
        bBuf.release();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}