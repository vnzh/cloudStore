
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientCore implements Runnable {

    private Callback onRecieved;
    private int port;
    private ClientController clientController;
    private String host;
    private SocketChannel channel;
    boolean isConnnected;

    public ClientCore(String host, int port, ClientController clientController) {
        this.port = port;
        this.clientController = clientController;
        this.host = host;
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {


            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline().addLast(new ClientHandler());
                }
            });
//           socketChannel.pipeline().addLast(new ClientHandler());
//        }
//    });
            ChannelFuture f = b.connect(host, port).sync();
            isConnnected = true;
            System.out.println("Clients started");
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


    void sendToServer(byte[] cmd) {
        channel.writeAndFlush(cmd);
        for (int i = 0; i < cmd.length; i++) {
            System.out.format(" $s  ", cmd[i]);
        }
    }

}


