import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

public class ServerCoreHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

//        ByteBuf in = (ByteBuf) msg;
//        String str = in.toString(Charset.defaultCharset());
//
//        System.out.println("Client cjnnnected");
//        System.out.println(str);
//        try {
////            while (in.isReadable()) {
////                System.out.print((char) in.readByte());
////                System.out.flush();
////            }
//            System.out.println(str);
//
//
//
//            ctx.write(str);
//            ctx.flush();
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }

    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}
