package servertrivia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

/**
 * Class to define server
 */
public class ServerMain {
    private static final int PORT = 6000;
    private static int totalPlayers = 1;
    private static int players = 0;
    private  static boolean finish = false;

    /**
     * Main method of the application. Creates server listenning for clients connections,
     * ask amount of players and create a thread for each of them. After finishing the game,
     * calculates the results and ask to repeat the game or to finish.
     * @param args String array passed to  main
     */
    public static void main(String[] args) {

        //create server
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Waiting for players......");

           //create list of players. The class player was added on the last moment, so I didn't have time anymore to implement it as I should.
            // I just use it to keep  the connections to don't have to make new ones every time I need to send a message to the players.
            List<Player> pl = new ArrayList<>();
            //accepts connections untill all the players are conected
            while (players < totalPlayers) {
                Socket sc = server.accept();
                Player player = new Player(sc);
                pl.add(player);
                System.out.println("Connection established");
                try {
                    player.setSocketIn(new DataInputStream(sc.getInputStream()));
                    player.setSocketOut(new DataOutputStream(sc.getOutputStream()));

                    //ask the first player the total amount of players
                    if (players == 0) {
                        player.getSocketOut().writeUTF("How many players will join the game?");
                        totalPlayers = player.getSocketIn().readInt();

                    } else {
                        player.getSocketOut().writeUTF("Welcome");
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                players++;
            }
            // creates thread for every connection and add it to the list
            List<ServerThread> list = new ArrayList<>();
            while (!finish) {
                if (players == totalPlayers) {
                    list.clear();
                    for (Player player : pl) {
                        player.getSocketOut().writeUTF("New Game");
                        ServerThread st = new ServerThread(player.getSc());
                        list.add(st);
                        st.start();

                    }
                    //checks every second if the game has finished
                    int terminated = totalPlayers;

                    while (terminated > 0) {
                        Thread.sleep(1000);
                        terminated = (int) list.stream().filter(Thread::isAlive).count();

                    }
                    //sortind the results of the game and gets the winner
                    list.sort((t1, t2) -> {
                        if (t1.getCorrect() > t2.getCorrect()) {
                            return -1;
                        } else if (t2.getCorrect() > t1.getCorrect()) {
                            return 1;
                        } else {
                            if (t1.getIncorrect() > t2.getIncorrect()) {
                                return -1;
                            } else if (t2.getIncorrect() > t1.getIncorrect()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });

                    String status = "Final score: \n" + list.stream().map(ServerThread::toString).collect(Collectors.joining("\n"));

                    // send the results to all players
                    for (Player player : pl) {
                        player.getSocketOut().writeUTF(list.get(0).getName() + ";" + status);
                        System.out.println(list.get(0).getName() + ";" + status);
                    }

                    Thread.sleep(2000);
                    //ask every player if he wants to repeat the game,
                    // in case one of them doesn't want, it finishes the game for everybody.
                    for (Player player : pl) {
                        player.getSocketOut().writeUTF("Do you wish to repeat the game?");
                        if (player.getSocketIn().readUTF().equals("no")) {
                            finish = true;
                           // player.getSocketOut().writeUTF("finish");
                        } /*else {
                            player.getSocketOut().writeUTF("New Game");
                        }*/
                    }
                }
            }
            //send message to every player
            for (Player player : pl) {
                player.getSocketOut().writeUTF("Game over");
            }
            //after 4 sec.  the sockets get closed
            Thread.sleep(4000);
            //for (Socket s : service) {
            for (Player player : pl) {
                if (player.getSc() != null) {
                    player.getSc().close();
                }
                if (player.getSocketOut() != null) {
                    player.getSocketOut().close();
                }
                if (player.getSocketIn() != null) {
                    player.getSocketIn().close();
                }
            }


        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}