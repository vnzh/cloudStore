package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import library.SingltonToServer;

public class ClientOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        if (!SingltonToServer.getInstance().toServer.isEmpty()) {
            System.out.println("has command");

            ctx.writeAndFlush(SingltonToServer.getInstance().toServer.poll().array());
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        if (!SingltonToServer.getInstance().toServer.isEmpty()) {
//            System.out.println("has command");
//            msg  = SingltonToServer.getInstance().toServer.poll().array();
//            ctx.writeAndFlush(msg);
//        }

    }
}
