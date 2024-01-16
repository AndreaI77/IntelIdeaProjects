package servertrivia;

import questionmodel.Card;
import questionmodel.Deck;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

public class ServerThread extends Thread {
    Socket service;

    private int correct = 0;
    private int incorrect = 0;
    private static boolean finish = false;
    public static Deck deck;

    public ServerThread(Socket s)
    {
        service = s;
    }

    @Override
    public void run()
    {
        ObjectOutputStream objectOut = null;
        DataInputStream  socketIn = null;
        DataOutputStream socketOut = null;

        if(deck == null) {
            deck = new Deck(Paths.get("cards.txt").toAbsolutePath());
        }
        Card card = null;

        try
        {
            System.out.println("dentro de try");
            socketIn = new DataInputStream(service.getInputStream());
            socketOut = new DataOutputStream(service.getOutputStream());

            String name = socketIn.readUTF();
            this.setName(name);
            System.out.println(this.getName());
            objectOut = new ObjectOutputStream(service.getOutputStream());
            System.out.println("creado object output");
            String line = "";

            while (!finish) {
                System.out.println("dentro finish");
                System.out.println("enviado card");
                socketOut.writeUTF("card");
                System.out.println("tarjeta enviada");
                card = deck.getCard();
                objectOut.writeObject(card);
                // Read message from the client
                line = socketIn.readUTF();
                System.out.println("Read: " + line);

                if (line.equals("end")){
                    socketOut.writeUTF("end");
                    finish = true;
                }else if( line.equals("Pass")){
                    socketOut.writeUTF("passed");

                }else {
                    //System.out.println(card.getAnswer()+": "+line);
                    if(card.getAnswer().equals(line)){
                        socketOut.writeUTF("yes");
                        this.correct++;
                    }else{
                        socketOut.writeUTF("no");
                        this.incorrect ++;
                    }
                }

            }
            System.out.println("terminated");
            socketOut.writeUTF("terminated");


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } /*finally {
            try {
                if (objectOut != null)
                    objectOut.close();
            } catch (IOException ex) {}
            try {
                if (socketOut != null)
                    socketOut.close();
            } catch (IOException ex) {}
            try {
                if (socketIn != null)
                    socketIn.close();

            } catch (IOException ex) {}
        }*/

    }

    public int getCorrect() {
        return correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    @Override
    public String toString() {
        return "Name: "+this.getName()+", correct " + correct + ", incorrect " + incorrect ;
    }
}
