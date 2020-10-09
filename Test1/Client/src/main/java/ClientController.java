import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClientController {

    ProtocolCommand protocolCommand;
    boolean isAuthorised;


   public static List<String> listCommand = Collections.synchronizedList(new ArrayList<String>());

    public static void main(String[] args) throws InterruptedException {


        ClientController clientController = new ClientController();
//        clientController.protocolCommand.authrCommand("asdfghjkl;", "1234567890");
//        clientController.listCommand.add(clientController.protocolCommand.authrCommandStr("asdfghjkl;", "1234567890"));
        ClientCore clientCore = new ClientCore("localhost", 8189, clientController);
        clientCore.run();

        while (!clientCore.isConnnected ){

        }

        clientCore.sendToServer(clientController.protocolCommand.authrCommand("asdfghjkl;", "1234567890"));
        while (true) {

            if (clientController.isAuthorised) {
                clientController.dialog();
            }
        }
    }

//диалоговое  окно  для теста в  консоли
    void dialog() {
        System.out.println("To upload file  press  1   to dounload file press 2");
        int number = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            number = scanner.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (number == 1) {
            listCommand.add(protocolCommand.newflCommandStr("filename"));
        }

    }

}
