import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerCore {

    private int port;
    private  ServerController serverController;

    public ServerCore(int port, ServerController serverController) {
        this.port = port;
        this.serverController = serverController;
    }

    public void run() {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {

                        ch.pipeline().addLast(new  ServerCoreHandler());

                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);


        ChannelFuture f = b.bind(port).sync();
        System.out.println("Server starts");
        f.channel().closeFuture().sync();

    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }


}
}
