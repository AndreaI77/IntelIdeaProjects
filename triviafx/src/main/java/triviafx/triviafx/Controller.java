package triviafx.triviafx;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import questionmodel.Card;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class that controls the associated view and its functions
 */
public class Controller implements Initializable {
    @FXML
    private RadioButton rbpass;
    @FXML
    private RadioButton rb4;
    @FXML
    private RadioButton rb3;
    @FXML
    private RadioButton rb2;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnSend;
    @FXML
    private RadioButton rbOne;
    @FXML
    private Label lblQuestion;
    @FXML
    private TextField txtPlayer;
    @FXML
    private Button btnConnect;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtHost;

    private InetAddress server = null;
    private Socket mySocket = null;
    private DataInputStream socketIn = null;
    private DataOutputStream socketOut = null;
    private ObjectInputStream objectIn = null;
    private Card card;
    private ToggleGroup options;
    private String name;
    private int correct;
    private int incorrect;

    /**
     * method  that initialize the object in the view that needs to have come values. Sets the toggle group of the radioButtons
     * @param url Url object
     * @param resourceBundle ResourceBundle object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        options = new ToggleGroup();

        rbOne.setToggleGroup(options);
        rb2.setToggleGroup(options);
        rb3.setToggleGroup(options);
        rb4.setToggleGroup(options);
        rbpass.setToggleGroup(options);
    }

    /**
     * Method called by the connect button. Gets the values from fields and makes connection.
     * Ass the first player for the amount of players, gets the first card and sets visible all the elements needed
     * @param actionEvent
     */
    public void connect(ActionEvent actionEvent) {
        name = txtPlayer.getText().trim();
        if(name.isEmpty()){
            MessageUtils.showError("Write your name!","Players name is missing!");
        }else {
            int port = 0;
            try{
                port = Integer.parseInt(txtPort.getText().trim());
            }catch (Exception e){
                MessageUtils.showError("Error","The port must be a number");
            }

            String host = txtHost.getText().trim();
            if (host.isEmpty()) {
                MessageUtils.showError("Error","The host is missing");
            }

            if(port != 0 && !host.isEmpty()) {
                btnConnect.setDisable(true);
                try {
                    server = InetAddress.getByName(host);

                    try {
                        mySocket = new Socket(server, port);
                        socketIn = new DataInputStream(mySocket.getInputStream());
                        socketOut = new DataOutputStream(mySocket.getOutputStream());

                        String line;
                        // Read response from server

                        line = socketIn.readUTF();
                        //System.out.println("Response : " + line);
                        if (line.equals("How many players will join the game?")) {
                            boolean pl = false;
                            while (!pl) {
                                TextInputDialog dialog = new TextInputDialog();
                                dialog.setTitle("Players");
                                dialog.setHeaderText(line);
                                String result = "";
                                Optional<String> opt = dialog.showAndWait();
                                TextField rslt = dialog.getEditor();
                                result = rslt.getText().trim();
                                if (result.isEmpty()) {
                                    MessageUtils.showError("Error", "Write the amount of players.");
                                } else {
                                    try {

                                        int rs = Integer.parseInt(result);
                                        System.out.println(rs);
                                        if (rs > 0) {
                                            pl = true;
                                            try {
                                                socketOut.writeInt(rs);

                                            } catch (IOException e) {
                                                MessageUtils.showError("Error!", "Something went wrong.");
                                            }
                                        } else {
                                            MessageUtils.showError("Error!", "The number must be larger than 0");
                                        }
                                    } catch (Exception e) {
                                        MessageUtils.showError("Error!", "The text should be a number!");

                                    }
                                }
                            }
                        }
                        line = socketIn.readUTF();
                        //System.out.println("Response : " + line);
                        socketOut.writeUTF(name);
                        objectIn = new ObjectInputStream(mySocket.getInputStream());

                        objectIn.readObject(); //card
                        getCard();
                        ObservableList<Toggle> rb = options.getToggles();
                        for (Toggle t : rb) {
                            RadioButton rbt = (RadioButton) t;
                            rbt.setVisible(true);
                        }
                        btnSend.setVisible(true);
                        btnStop.setVisible(true);

                    } catch (IOException e) {
                        System.out.println(e);
                        btnConnect.setDisable(false);
                    } catch (ClassNotFoundException e) {
                        btnConnect.setDisable(false);
                        MessageUtils.showError("Error!", "something went wrong.");
                    }
                } catch (UnknownHostException ex) {
                    btnConnect.setDisable(false);
                    MessageUtils.showError("Error!", "Unknown host");
                    System.err.println("Unknown host: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * method that send an answer to the card, add the correct or incorrect answer and in case of finishing game, shows the status.
     * @param actionEvent
     */
    public void sendAnswer(ActionEvent actionEvent) {
        Toggle t = options.getSelectedToggle();
        if (t == null) {
            MessageUtils.showError("Error", "Select an option");
        }else{
            RadioButton rb = (RadioButton) t;
            String answer = rb.getText();

            try {
                socketOut.writeUTF(answer);
                String st = socketIn.readUTF();
                System.out.println(st);
                if (st.equals("yes")) {
                    lblStatus.setText("Correct!");
                    correct++;
                } else if (st.equals("no")) {
                    lblStatus.setText("Incorrect!");
                    incorrect++;
                } else if (st.equals("passed")) {
                    lblStatus.setText("Passed");
                }

                lblResult.setText("Correct: " + correct + ", Incorrect: " + incorrect);

                String line = (String) objectIn.readObject();
                System.out.println(line);
                if (line.equals("card")) {
                    getCard();
                    t.setSelected(false);
                } else {
                    btnSend.setDisable(true);
                    btnStop.setDisable(true);
                    String str = socketIn.readUTF();//status
                    //System.out.println(str);
                    getStatus(str);
                    repeatGame();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * method called by the button stop . gets the status and asks to repeat the game
     * @param actionEvent  Click event of the button
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void stopPlaying(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        lblStatus.setText("Waiting for other players to finish.....");
        btnSend.setDisable(true);
        btnStop.setDisable(true);
        socketOut.writeUTF("end");
        lblStatus.setText(socketIn.readUTF()); //end
        objectIn.readObject();
        String st =socketIn.readUTF(); //status
        getStatus(st);
        repeatGame();
    }

    /**
     * methodthat gets a nwe card and assign the values to the objects in the view.
     * @throws IOException
     * @throws ClassNotFoundException
     */
  public void getCard() throws IOException, ClassNotFoundException {

          card = (Card)(objectIn.readObject());
          if(card != null){
              lblQuestion.setText(card.getQuestion());
              String [] list = card.getOptions();
              ObservableList<Toggle> rb  =options.getToggles();
              for(int i=0; i<list.length; i++){
                  RadioButton rbt = (RadioButton)rb.get(i);
                  rbt.setText(list[i]);
              }
              rbpass.setText("Pass");
          }else{
              MessageUtils.showError("Error", " No se ha cargado ninguna tarjeta.");
          }
  }

    /**
     * method that gets the final status of the game
     * @param st String with the message received form the server
     */
  public void getStatus(String st){
      String [] parts = st.split(";");
      if(this.name.equals(parts[0])){
          lblStatus.setText("You win! \n"+parts[1]);
      }else{
          lblStatus.setText("You loose! \n"+parts[1]);
      }
  }

    /**
     * method that confirm if the player wants to repeat the game and  either gets a nes card or shows final message
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void repeatGame() throws IOException, ClassNotFoundException {
        String st =socketIn.readUTF();//repeat game?
        System.out.println(st);
        if(MessageUtils.askConfirmation("Repeat game",st)){
            socketOut.writeUTF("yes");
           st=socketIn.readUTF(); // new game or game over
            System.out.println(st);
            if(st.equals("New Game")) {
                btnSend.setDisable(false);
                btnStop.setDisable(false);
                correct = 0;
                incorrect = 0;
                lblResult.setText("");
                lblStatus.setText("");
                socketOut.writeUTF(name);

                socketIn = new DataInputStream(mySocket.getInputStream());
                socketOut = new DataOutputStream(mySocket.getOutputStream());
                objectIn = new ObjectInputStream(mySocket.getInputStream());
                String line = (String) objectIn.readObject();//card

                getCard();
                Toggle t = options.getSelectedToggle();
                if (t != null) {
                    t.setSelected(false);
                }
            }else{
                System.out.println(st);
                lblResult.setText(st);
                lblStatus.setText("");
            }

        }else{
            socketOut.writeUTF("no");
            st=socketIn.readUTF();
            System.out.println(st);
            lblResult.setText(st);
            lblStatus.setText("");
        }
    }
}