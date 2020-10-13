package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import library.SingltonToServer;

import java.nio.ByteBuffer;

public class ClientCore implements Runnable {

    private ByteBuf buf;
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
//           socketChannel.pipeline().addLast(new client.ClientHandler());?
//        }
//    });
            ChannelFuture f = b.connect(host, port).sync();

//            System.out.println("Clients started");
            f.channel().closeFuture().sync();
            //  при  наличии   информации  для  передачи  на  сервер  передает  на сервер   информацию
            new Thread() {
                public void run() {
                    while (true) {
                        if (!SingltonToServer.getInstance().toServer.isEmpty()) {
                            System.out.println("send auth");
                            sendToServer(SingltonToServer.getInstance().toServer.poll());

                            System.out.println("send auth");
                        }
//                        System.out.println("test");
                    }
                }
            }.start();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


    void sendToServer(ByteBuffer buffer) {


        channel.writeAndFlush(buffer);
//        channel.writeAndFlush(buffer.array());

//        UnpooledDirectByteBuf(buffer);
//        channel.writeAndFlush(buf);
        System.out.println("message  sended");
    }

}


