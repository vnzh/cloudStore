package Server;

public   class   ServerController {



    public ServerController() {

//        this.serverStart(this);
    }

    public static void main(String[] args) {

        ServerController serverController = new ServerController();
        serverController.serverStart(serverController);

    }


    protected void  serverStart (ServerController controller) {
        new ServerCore(8189, this).run();
    }

}
