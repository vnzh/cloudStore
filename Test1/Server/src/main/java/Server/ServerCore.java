package Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;
import java.util.Vector;

public class ServerCore {

    boolean userAuth;
    Channel channel;
    private String username;
    private int port;
    private ServerController serverController;
    //    Map<Channel, String> usersAuthor = new HashMap<Channel, String>();
    List<String> usersAuthor = new Vector<String>();

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
                            channel = ch;
                            ch.pipeline().addLast(new ServerCoreHandler());

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture f = b.bind(port).sync();
            System.out.println("Server starts");
            f.channel().closeFuture().sync();
            //при  авторизации  клиента   клиент добавляется  в   мапу
            new Thread() {
                public void run() {
                    while (true) {
                        if (userAuth) {
//                            usersAuthor.put(channel, username);
                            usersAuthor.add(username);
                            break;
                        }
                    }
                }
            }.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            usersAuthor.remove(username);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }
}
