
//  commmand   for  action

public interface Command {

    String   AUTHR = "AUTHR" ;   //authorisation
    String   GET_TR = "GET_TR";  //  show   file tree from  server
    String   NEWFL = "NEWFL";   //  create new  file  or  directory
    String   DELFL = "DELFL";    // delete  file  jr   directory
    String   RENFL = "RENFL";   // rename  file
    String   WR_FL = "WR_FL";  //  write  to   file



    // 5  byte   for   command  15 byte  fo   file  name  10 byte for   file   size

}
