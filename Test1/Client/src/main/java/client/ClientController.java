package client;

import library.ProtocolCommand;
import library.SingltonCommand;
import library.SingltonResponse;

public class ClientController extends ClientControllerHandler implements Runnable{

    ProtocolCommand protocolCommand;
    boolean isConnected;
    boolean isAuthorised;


;

    public static void main(String[] args) throws InterruptedException {




        new  ClientController().run();




//        clientCore.sendToServer(clientController.protocolCommand.authrCommand("asdfghjkl;", "1234567890"));
//        while (true) {
//
//            if (clientController.isAuthorised) {
//                clientController.dialog();
//            }
//        }
    }


    @Override
    public void run() {



        ClientCore clientCore = new ClientCore("localhost", 8189, this);
        clientCore.run();

        while (true) {
            if (!SingltonCommand.getInstance().commandList.isEmpty()) {
//                String str = SingltonCommand.getInstance().bufList.poll().toString(Charset.defaultCharset());
//                System.out.println(SingltonCommand.getInstance().bufList.poll().toString(Charset.defaultCharset()));
                commandHandler(SingltonCommand.getInstance().commandList.poll());
            }
            if (!SingltonResponse.getInstance().serverResponses.isEmpty()) {

                reponseHandler(SingltonResponse.getInstance().serverResponses.poll());
            }

        }


    }




//диалоговое  окно  для теста в  консоли
//    void dialog() {
//        System.out.println("To upload file  press  1   to dounload file press 2");
//        int number = 0;
//        try (Scanner scanner = new Scanner(System.in)) {
//            number = scanner.nextInt();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (number == 1) {
//            listCommand.add(protocolCommand.newflCommandStr("filename"));
//        }
//
//    }

}
