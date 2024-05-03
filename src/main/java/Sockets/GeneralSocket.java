package Sockets;

public class GeneralSocket {

    private ClientSocket clientSocket;
    private ServersSocket serverSocket;
    private boolean isLaunched = false;


    public GeneralSocket(String ip, String port)
    {
        this.clientSocket = new ClientSocket(ip, port);
        this.serverSocket = new ServersSocket(port);
        isLaunched = true;
    }

    public void startClient() throws InterruptedException {
        this.clientSocket.start();
    }

    public void startServer()
    {
        this.serverSocket.start();
        this.isLaunched = true;
    }

    public void closeClient() throws InterruptedException {
        Thread.sleep(2000);
        this.clientSocket.clientClose();
    }

    public void closeServer()
    {
        this.serverSocket.serverClose();
        this.isLaunched = false;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ServersSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServersSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public void setLaunched(boolean isLaunched) {
        this.isLaunched = isLaunched;
    }
}
