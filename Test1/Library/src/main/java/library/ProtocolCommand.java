package library;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ProtocolCommand implements Command {

    private String FL_NM;  //   file  or directory name
    private String FL_SZ;  //    file  size

    private String USER_NAME = "anton";
    private String PASSWORD = "password";



    // 5  byte   for   command  15 byte  fo   file  name  10 byte for   file   size


    public String getAUTHR() {
        return AUTHR;
    }

    public String getGET_TR() {
        return GET_TR;
    }

    public String getNEWFL() {
        return NEWFL;
    }

    public String getDELFL() {
        return DELFL;
    }

    public String getRENFL() {
        return RENFL;
    }

    public String getWR_FL() {
        return WR_FL;
    }


    // 5  byte   for   command  15 byte  fo   file  name  10 byte for   file   size
//  5  byte   for command 10 byte   for   username 10 byte  for  password

    public byte[] authrCommand(String username, String psw) {
        byte[] cmd = new byte[MAX_COMMAND_SIZE + MAX_USERNAME_SIZE + MAX_PASSWORD_SIZE];

        System.arraycopy(AUTHR.getBytes(), 0, cmd, 0, AUTHR.length());
        System.arraycopy(username.getBytes(), 0, cmd, MAX_COMMAND_SIZE, username.length());
        System.arraycopy(psw.getBytes(), 0, cmd, MAX_COMMAND_SIZE + MAX_USERNAME_SIZE,
                psw.length());

        return cmd;
    }

    protected String authrCommandStr(String username, String psw) {
        byte[] cmd = authrCommand(username, psw);

        return String.valueOf(cmd);
    }

    protected byte[] get_trCommand() {
        return GET_TR.getBytes();
    }

    protected byte[] newflCommand(String name) {
        if (name.length() > 10) {
            try {
                throw new SizeNameFileExeption();
            } catch (SizeNameFileExeption sizeNameFileExeption) {
                sizeNameFileExeption.printStackTrace();
                System.out.println("Too long filename");
            }
        }
//        вариант с  ограничением  на   размер  файла  в   3  ГБ
        Path path = Paths.get(name);
        long filesize = 0;
        try {
            filesize = Files.size(path);

            if (filesize > 3 * 1024 * 1024 * 1024) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Too big  file");
        }
        String filesizeStr = String.valueOf(filesize);

        byte[] cmd = new byte[MAX_COMMAND_SIZE + MAX_FILE_NAME_SIZE + MAX_FILE_SIZE_STRING];

        System.arraycopy(NEWFL.getBytes(), 0, cmd, 0, NEWFL.length());
        System.arraycopy(name.getBytes(), 0, cmd, MAX_FILE_NAME_SIZE, name.length());
        System.arraycopy(filesizeStr.getBytes(), 0, cmd, MAX_COMMAND_SIZE + MAX_FILE_NAME_SIZE,
                filesizeStr.length());

        return cmd;
    }

    public String newflCommandStr(String name) {

        byte[] cmd = newflCommand(name);
        return String.valueOf(cmd);
    }

    protected byte[] delflCommand() {
        return DELFL.getBytes();
    }

    //
//    protected   byte [] authrCommand () {
//        return AUTHR.getBytes();
//    }
//
    protected byte[] wr_flCommand(String name) {
        return (WR_FL + name).getBytes();
    }
}
