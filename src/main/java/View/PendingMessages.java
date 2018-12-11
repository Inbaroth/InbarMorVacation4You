package View;

import Controller.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.ArrayList;

public class PendingMessages extends HomePage implements EventHandler<ActionEvent>{

    public VBox VB_buttons;
    public VBox VB_labels;
    private Controller controller;
    private Stage stage;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        setMessages();
    }

    public void setMessages() {
        ArrayList<String> pendingVacations = controller.readPendingVacations(controller.getUserName());
        this.buttonsList = new ArrayList<>();
        this.labelList = new ArrayList<>();
        for (String message : pendingVacations) {
            String[] messageDetails = message.split(",");
            Button button = new Button("אשר רכישה");
            button.setId(messageDetails[0]);
            button.setOnAction(this);
            button.setFont(new Font("Calibri Light",15));
            buttonsList.add(new Button(messageDetails[0]));
            Label label = new Label(message.substring(messageDetails.length));
            label.setFont(new Font("Calibri Light",15));
            labelList.add(label);
        }
        VB_buttons.getChildren().clear();
        VB_buttons.getChildren().addAll(buttonsList);
        VB_labels.getChildren().clear();
        VB_labels.getChildren().addAll(labelList);

    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = buttonsList.indexOf(button);
        controller.deletePendingVacation(button.getId());
        Label label = labelList.get(index);
        String labelText = label.getText();
        String [] data = labelText.split(",");
        String buyer = controller.readPendingVacationBuyer(Integer.valueOf(button.getId()));
        controller.insertConfirmedVacation(Integer.valueOf(button.getId()),controller.getUserName(),
                buyer,data[1],data[2],Integer.valueOf(data[3]),data[4],data[5]);
        button.setDisable(true);
        alert("הודעת אישור תעבור לקונה", Alert.AlertType.CONFIRMATION);

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
