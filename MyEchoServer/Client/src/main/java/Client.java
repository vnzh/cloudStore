import java.io.*;
import java.net.Socket;


public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        InputStream inSocket = null;
        BufferedInputStream  streamFile = null;
        OutputStream outSocket = null;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            socket = new Socket("127.0.0.1", 8189);
             inSocket = socket.getInputStream();
             outSocket = socket.getOutputStream();
             streamFile = new BufferedInputStream(new FileInputStream("C:\\Practics\\Sockets\\MyEchoServer\\232.jpg"),
                     1028);
//             fileInputStream = new FileInputStream("232.jpg");
//             byte[] readed = new byte[1028];
             while (streamFile.available() > 0) {

                 outSocket.write(streamFile.read());
                 System.out.println("byte written");
             }
             streamFile.close();
             while (true) {}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
