package Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import library.Command;
import library.SingltonResponse;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ServerCoreHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf bBuf;
    private ByteBuffer buffer;


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        String str = Command.CONNECT;
//        byteBuf.alloc().buffer(str.length());
//        byteBuf.writeBytes(str.getBytes())
        bBuf = Unpooled.copiedBuffer(str, Charset.defaultCharset());
//        System.out.println(byteBuf.toString(Charset.defaultCharset()));
//       ctx.writeAndFlush(bBuf);
        final ChannelFuture f = ctx.writeAndFlush(bBuf);
        f.addListener(new ChannelFutureListener() {


            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                assert f == channelFuture;
                ctx.close();
            }
        });
        System.out.println(str);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        bBuf = (ByteBuf) msg;

        buffer = bBuf.nioBuffer();
        buffer = (ByteBuffer) msg;
        String str = buffer.toString();

        System.out.println(str);
        bBuf.release();


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}
