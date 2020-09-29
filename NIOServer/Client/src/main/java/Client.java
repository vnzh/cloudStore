import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {


    public static void main(String[] args) {

        try {
            Selector clientSelector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8189));

            Path file1 = Paths.get("C:\\Practics\\Sockets\\NIOServer\\Client\\src\\main\\resources\\232.jpg");

//создаем   служебные  каналы
            RandomAccessFile accessFile = new RandomAccessFile(file1.toString(), "rw");
            FileChannel fileChannel = accessFile.getChannel();
            ByteBuffer bufferProperties = ByteBuffer.wrap(propeties());

//            для  отладки
            System.out.println("Buffer  position " + bufferProperties.position());
            System.out.println("Buffer  limit " + bufferProperties.limit());
            System.out.println("Buffer   capasity  " + bufferProperties.capacity());
//            передача  служебной информации?
        while (bufferProperties.hasRemaining()) {
                int bytewrote = socketChannel.write(bufferProperties);
                System.out.println(" wrote  " + bytewrote);
            }
//        передача   файла
            ByteBuffer byteBuffer = ByteBuffer.allocate(8184);
            int readed = fileChannel.read(byteBuffer);

            System.out.println("readed " + readed);
            while (readed > 0) {
                byteBuffer.flip();

                while (byteBuffer.hasRemaining()) {
                    int bytewrote = socketChannel.write(byteBuffer);
                    System.out.println(" wrote  " + bytewrote);
                }
                byteBuffer.clear();
                readed = fileChannel.read(byteBuffer);
                System.out.println("readed " + readed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//метод  для    формирования   к  передачи  служебной  информации -   именя  пользователя, хешкода   файла (пока не  реализовано)
    public static byte[] propeties() {
        Path file = Paths.get("C:\\Practics\\NIO\\Paths\\resource\\232.jpg");
        String user = "user";
        // Протокол      имя  10  символов    имя   файла 30  символов  хешкод  20  символов
        String fileName = file.getName(file.getNameCount() - 1).toString();

        StringBuilder stringBuilder1 = new StringBuilder(user);
        for (int i = stringBuilder1.length(); i < 10; i++) {
            stringBuilder1.append(" ");
        }
        stringBuilder1.append(fileName);
        for (int i = stringBuilder1.length(); i < 40; i++) {
            stringBuilder1.append(" ");
        }
        stringBuilder1.append(hashCalc(file));
        for (int i = stringBuilder1.length(); i < 60; i++) {
            stringBuilder1.append(" ");
        }

        System.out.println(stringBuilder1);
        System.out.println(stringBuilder1.length());
        byte[] bstr = stringBuilder1.toString().getBytes();
        System.out.println(bstr.length);
        return bstr;
    }
//   здесь  будет  вычисляться  хешкод   передаваемого  файла. Сейчас   вычисляется хешкод  объекта
    public static String hashCalc(Path path) {
        return String.valueOf(path.hashCode());
    }
}
