package library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})

public @interface CommmandLength {
    // 5  byte   for   command  15 byte  fo   file  name  10 byte for   file   size
    //  5  byte   for command 10 byte   for   username 10 byte  for  password

    int MAX_COMMAND_SIZE = 6;
    int MAX_FILE_NAME_SIZE = 15;
    int MAX_FILE_SIZE_STRING = 10;
    int MAX_USERNAME_SIZE = 10;
    int MAX_PASSWORD_SIZE = 10;

}
