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
public class ServerMain {
    private static final int PORT = 6000;
    private static int totalPlayers = 1;
    private static int players = 0;
    private  static boolean finish = false;


    public static void main(String[] args) {
        DataInputStream socketIn = null;
        DataOutputStream socketOut = null;

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Waiting for players......");

            List<Socket> service = new ArrayList<>(); //new Socket[totalPlayers];
            while (players < totalPlayers) {
                Socket sc = server.accept();
                service.add(sc);
                System.out.println("Connection established");
                try {
                    socketIn = new DataInputStream(sc.getInputStream());
                    socketOut = new DataOutputStream(sc.getOutputStream());
                    if (players == 0) {
                        socketOut.writeUTF("How many players will join the game?");
                        totalPlayers = socketIn.readInt();
                        System.out.println(totalPlayers);
                    } else {
                        socketOut.writeUTF("Welcome");
                        System.out.println("welcome");
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                players++;
            }
                while(!finish) {
                    if (players == totalPlayers) {
                        System.out.println("ya estamos todos");
                        List<ServerThread> list = new ArrayList<>();
                        for (Socket sr : service) {
                            ServerThread st = new ServerThread(sr);
                            System.out.println("lanzando thread");
                            list.add(st);
                            st.start();

                        }
                        int terminated = totalPlayers;

                        while (terminated > 0) {
                            Thread.sleep(1000);
                            terminated = (int) list.stream().filter(Thread::isAlive).count();

                        }
                        //if(fin.size() == players) {
                        System.out.println("sorting");
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

                        for (Socket sr : service) {
                            socketIn = new DataInputStream(sr.getInputStream());
                            socketOut = new DataOutputStream(sr.getOutputStream());
                            socketOut.writeUTF(list.get(0).getName() + ";" + status);
                            System.out.println(list.get(0).getName() + ";" + status);

                            Thread.sleep(2000);
                            socketOut.writeUTF("Do you wish to repeat the game?");
                            if (socketIn.readUTF().equals("no")) {
                                finish = true;
                                System.out.println("no");
                            }else{
                                System.out.println("yes");
                            }
                        }
                    }
                }
                socketOut.writeUTF("Game over");
                Thread.sleep(4000);
                for (Socket s : service) {
                    if(s != null) {
                        s.close();
                    }
                }
                if(socketOut != null) {
                    socketOut.close();
                }
                if(socketIn != null) {
                    socketIn.close();
                }

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}