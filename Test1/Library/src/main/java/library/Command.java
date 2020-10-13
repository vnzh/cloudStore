package library;
//  commmand   for  action

public interface Command {

    // Commands
    String AUTHR = "/AUTHR";   //authorisation
    String GET_TR = "/GET_TR";  //  show   file tree from  server
    String NEWFL = "/NEWFL";   //  create new  file  or  directory
    String DELFL = "/DELFL";    // delete  file  jr   directory
    String RENFL = "/RENFL";   // rename  file
    String WR_FL = "/WR_FL";  //  write  to   file


    //responses
    String CONNECT = "/CONNE";
    String CONNECTFAIL = "/CONFL";
    String AUTTRUE = "/AUTRU";
    String AUTFAIL = "/AUTFL";
    String FAILTREE = "/FTREE";

//to message
    String CONNECTmsg = "Соединение   установлено";
    String CONNECTFAILmsg = "Соединение  отсутствует";
    String AUTTRUEmsg= "Авторизация   успешна";
    String AUTFAILmsg = "Неверное  имя пользователя  или  пароль";


    // 5  byte   for   command  15 byte  fo   file  name  10 byte for   file   size

    // сcommand's  elements   size

    int MAX_COMMAND_SIZE = 6;
    int MAX_FILE_NAME_SIZE = 15;
    int MAX_FILE_SIZE_STRING = 10;
    int MAX_USERNAME_SIZE = 10;
    int MAX_PASSWORD_SIZE = 10;


}
