package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import library.Command;
import library.CommonHandler;
import library.SingltonCommand;
import library.SingltonToServer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static library.Command.*;

class ClientControllerHandler extends CommonHandler {


    private ByteBuf byteBuf;

    void commandHandler(ByteBuffer buffer) {
        byte[] bytes = ByteBufToBytes(buffer);
        String cmd = commandDecoder(bytes);

        switch (cmd) {
            case Command.AUTHR:
                message("auth start");
                SingltonToServer.getInstance().toServer.add(authrCommand("valera", "1234"));
                message("add to server");
                System.out.println(SingltonToServer.getInstance().toServer.element());
                break;
            default:
                break;

        }
    }


    void reponseHandler(ByteBuffer buffer) {
        byte[] bytes = ByteBufToBytes(buffer);
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        int readerIndex = 0;
//        byteBuf.getBytes(readerIndex, bytes);

        String cmd = reponseDecoder(bytes);

        switch (cmd) {
//   "/CONNE";
//                String CONNECTFAIL = "/CONFL";
//                String AUTTRUE = "/AUTRU";
//                String AUTFAIL = "/AUTFL";
//                String FAILTREE = "/FTREE";
            case "/CONNE":    //    if  connnection is  sucsess,  send command   for   authorisation
                message(Command.CONNECTmsg);
                setConnected(true);
                SingltonCommand.getInstance().commandList.add(stringToByteBuff(Command.AUTHR));

                message("send");
                break;
            default:
                break;
            case "/CONFL":
                message(Command.CONNECTFAIL);
                break;

        }
    }

    public ByteBuffer authrCommand(String username, String psw) {
        byte[] cmd = new byte[MAX_COMMAND_SIZE + MAX_USERNAME_SIZE + MAX_PASSWORD_SIZE];

        System.arraycopy(AUTHR.getBytes(), 0, cmd, 0, AUTHR.length());
        System.arraycopy(username.getBytes(), 0, cmd, MAX_COMMAND_SIZE, username.length());
        System.arraycopy(psw.getBytes(), 0, cmd, MAX_COMMAND_SIZE + MAX_USERNAME_SIZE,
                psw.length());
        String str = new String(cmd);
        message(str);
        for (int i = 0; i < cmd.length; i++) {
            System.out.print(cmd[i]);
        };
        System.out.println();

        return ByteBuffer.wrap(cmd);
    }

}
