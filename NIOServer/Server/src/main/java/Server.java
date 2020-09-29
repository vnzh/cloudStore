import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Server {

    private static int acceptedClientIndex = 1;
    static Selector serverSelector;
    static List<String> propeties = new ArrayList<>();

    public static void main(String[] args) {

//        ссоздание  каналов
        try {
            serverSelector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8189));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

            while (true) {

                int count = serverSelector.select();
                if (count == 0) {
                    continue;
                }

                Set<SelectionKey> selectedKeys = serverSelector.selectedKeys();

                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                SelectionKey key;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isConnectable()) {
                        handleConnect(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    if (key.isWritable()) {
                        handleWrite(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
//Я  так  и не  понял,  почему    передача    файла  в   Accept идет,  я   ситал  что в  Write доджна  идти
// и  сперва   сюда  код  написал
    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.configureBlocking(false);
        socketChannel.register(serverSelector, SelectionKey.OP_WRITE);

        Path file2 = Paths.get("src/main/resources/new.jpg");
        Files.deleteIfExists(file2);
        Files.createFile(file2);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8184);
        RandomAccessFile accessFile = new RandomAccessFile(file2.toString(), "rw");
        FileChannel fileChannel = accessFile.getChannel();
        int readed = socketChannel.read(byteBuffer);
        while (readed > 0) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                fileChannel.write(byteBuffer);
            }
            byteBuffer.clear();
            readed = socketChannel.read(byteBuffer);
        }
    }

    private static void handleRead(SelectionKey key) {
//        SocketChannel socketChannel = (SocketChannel) key.channel();
//        socketChannel.configureBlocking(false);
//        socketChannel.register(serverSelector, SelectionKey.OP_WRITE);
    }

    private static void handleConnect(SelectionKey key) {
    }

    private static void handleAccept(SelectionKey key) throws IOException {
//      Подготовка  канала  сокета
        SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
        String clientName = "Клиент #" + acceptedClientIndex;
        acceptedClientIndex++;
        sc.configureBlocking(false);
        sc.register(serverSelector, SelectionKey.OP_READ, clientName);

        System.out.println("Подключился новый клиент " + clientName);

//Прием  служебной  информации
        ByteBuffer bufferProper = ByteBuffer.allocate(60);
//       int readed;
        while (bufferProper.position() < bufferProper.capacity()) {
            int readed = sc.read(bufferProper);
            System.out.println("readed " + readed);
        }
//Вывод  служебной  информации   в   аррейлист
        properties(bufferProper);

//        создание    каталога  пользователя  и  файла
        Path path1 = Paths.get("C:\\Practics\\Sockets\\NIOServer\\Server\\src\\main\\resources\\").resolve(propeties.get(0));

        if (!Files.exists(path1)) {
            Files.createDirectory(path1);
        }
        Path path2 = Paths.get(path1.toString()).resolve(propeties.get(1));
//        Path path3 = Paths.get(path2.toString() + ".upld");
//        Files.deleteIfExists(path3);
//        Files.createFile(path3);
//        Files.deleteIfExists(path2);
        if (!Files.exists(path2)) {
            Files.createFile(path2);
        }
//Прием    файла
        ByteBuffer byteBuffer = ByteBuffer.allocate(8184);
        RandomAccessFile accessFile = new RandomAccessFile(path2.toString(), "rw");
        FileChannel fileChannel = accessFile.getChannel();
        System.out.println("Upload  starting  ");
        int readed = sc.read(byteBuffer);
        System.out.println("readed " + readed);
        while (readed > 0) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                int bytewrote = fileChannel.write(byteBuffer);
                System.out.println(" wrote  " + bytewrote);
            }
            byteBuffer.clear();
            readed = sc.read(byteBuffer);
            System.out.println("readed " + readed);
        }
//Проверка   ,  что  файл  принят  без  ошибок
        if (Files.exists(path1)) {
            System.out.println("Hashcode  " + hashCalc(path2));
//            if (propeties.get(2).equals(hashCalc(path2))) {
            System.out.println("Файл " + propeties.get(1) + "  загружен");
//            } else System.out.println("Хешкод   загруженного  файла  не  равен  мсходному");
        } else System.out.println("Файл не  загружен");




//        if(path3.toFile().renameTo(path2.toFile())){
//            System.out.println("Rename succesful");
//            System.out.println("Файл " + propeties.get(1) + "  загружен");
//        }else{
//            System.out.println("Rename failed");
//        }

    }
    //Вывод  служебной  информации   в   аррейлист
    public static void properties(ByteBuffer byteBuffer) {
//        byteBuffer.rewind();
        byteBuffer.flip();
        byte [] clientName = new byte[10];
        byteBuffer.get(clientName, 0, 10);
        propeties.add(new String(clientName).trim());
        byte[] filename = new byte[30];
        byteBuffer.get(filename, 0 ,30);
        propeties.add(new String(filename).trim());
        byte [] hashcode = new byte [20];
        byteBuffer.get(hashcode, 0, 20);
        propeties.add(new String(hashcode).trim());
        for (String s: propeties ) {
            System.out.println(s);
        }

//        System.out.println(byteBuffer.remaining());
//        byte[] bytes = new byte[byteBuffer.remaining()];
//        byteBuffer.get(bytes);
//        Scanner scanner = new Scanner(new ByteArrayInputStream(bytes));
//
//        while (scanner.hasNext()) {
//            propeties.add(scanner.next());
//            System.out.println(propeties.size());
//            System.out.println(propeties.get(propeties.size()-1));
//            }

     }
    //   здесь  будет  вычисляться  хешкод   передаваемого  файла. Сейчас   вычисляется хешкод  объекта
    public static String hashCalc(Path path) {
    return String.valueOf(path.hashCode());
    }

}

