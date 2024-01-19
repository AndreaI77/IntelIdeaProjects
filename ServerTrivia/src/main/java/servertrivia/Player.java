package servertrivia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class Player {
    Socket sc;

    DataInputStream socketIn;
    DataOutputStream socketOut;

    public Socket getSc() {
        return sc;
    }

    public DataInputStream getSocketIn() {
        return socketIn;
    }

    public DataOutputStream getSocketOut() {
        return socketOut;
    }

    public void setSc(Socket sc) {
        this.sc = sc;
    }

    public void setSocketIn(DataInputStream socketIn) {
        this.socketIn = socketIn;
    }

    public void setSocketOut(DataOutputStream socketOut) {
        this.socketOut = socketOut;
    }

    public Player(Socket sc) {
        this.sc = sc;

    }
}
