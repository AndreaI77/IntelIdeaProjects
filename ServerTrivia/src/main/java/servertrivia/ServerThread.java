package servertrivia;

import questionmodel.Card;
import questionmodel.Deck;
import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Class that handles the connection and communication with each client
 */
public class ServerThread extends Thread {
    Socket service;

    private int correct;
    private int incorrect;
    private static boolean finish;
    public static Deck deck;

    /**
     * constructor of the thread class
     * @param s socket with the clients connection
     */
    public ServerThread(Socket s)
    {
        service = s;
    }

    /**
     * main method of the thread, handles all the communication with the client
     */
    @Override
    public void run()
    {
        ObjectOutputStream objectOut = null;
        DataInputStream  socketIn = null;
        DataOutputStream socketOut = null;

        // if the deck is null, the first thread reates new deck
        if(deck == null) {
            deck = new Deck(Paths.get("cards.txt").toAbsolutePath());
        }

        Card card = null;
        correct = 0;
        incorrect=0;
        try
        {
            System.out.println("Dentro de run");
           //create  objects to comunicate with the client
            socketIn = new DataInputStream(service.getInputStream());
            socketOut = new DataOutputStream(service.getOutputStream());

            //sets the name of the thread
            String name = socketIn.readUTF();
            this.setName(name);

            objectOut = new ObjectOutputStream(service.getOutputStream());

            String line = "";
            finish = false;
            while (!finish) {
                System.out.println("dentro run");

                //socketOut.writeUTF("card");
                objectOut.writeObject("card");
                System.out.println("card");
                //get a card and send it to the client
                card = deck.getCard();
                objectOut.writeObject(card);

                // Read answer from the client
                line = socketIn.readUTF();

                if (line.equals("end")){
                    socketOut.writeUTF("end");
                    finish = true;
                }else if( line.equals("Pass")){
                    socketOut.writeUTF("passed");

                }else {

                    if(card.getAnswer().equals(line)){
                        socketOut.writeUTF("yes");
                        this.correct++;
                    }else{
                        socketOut.writeUTF("no");
                        this.incorrect ++;
                    }
                }
            }
            //socketOut.writeUTF("terminated");
            objectOut.writeObject("terminated");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * getter
     * @return the amount of correct answers
     */
    public int getCorrect() {
        return correct;
    }

    /**
     * getter
     * @return amount of incorrect answers
     */
    public int getIncorrect() {
        return incorrect;
    }

    /**
     * toString method
     * @return the information with the name and the results of the thread
     */
    @Override
    public String toString() {
        return "Name: "+this.getName()+", correct " + correct + ", incorrect " + incorrect ;
    }
}
