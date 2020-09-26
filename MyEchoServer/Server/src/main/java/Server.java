import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        InputStream socketIn = null;
        BufferedOutputStream outputStream  = null;

        try {
            serverSocket = new ServerSocket(8189);
            Socket socket = serverSocket.accept();
//            outputStream = new BufferedOutputStream(new FileOutputStream("C:\\Practics\\Sockets\\MyEchoServer\\new.jpg"),
//                    1028);
            outputStream = new BufferedOutputStream(new FileOutputStream("new.jpg"),
                    1028);
            socketIn = socket.getInputStream();
//            byte[] readed = new byte[1028];
            while (socket.isConnected() | (socketIn.available() > 0)) {
                outputStream.write(socketIn.read());
                System.out.println("Byte readed");

            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
