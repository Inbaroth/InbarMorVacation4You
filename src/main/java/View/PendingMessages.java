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

    public VBox VB_buttonsConfirm;
    public VBox VB_buttonsCancel;
    public VBox VB_labels;
    private Controller controller;
    private Stage stage;
    private ArrayList<Button> buttonsListConfirm;
    private ArrayList<Button> buttonsListCancel;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        setMessages();
    }

    public void setMessages() {
        ArrayList<String> pendingVacations = controller.readPendingVacations(controller.getUserName());
        this.buttonsListConfirm = new ArrayList<>();
        this.buttonsListCancel = new ArrayList<>();
        this.labelList = new ArrayList<>();
        for (String message : pendingVacations) {
            String[] messageDetails = message.split(",");
            Button buttonConfirm = new Button("אשר רכישה");
            buttonConfirm.setId(messageDetails[0]);
            buttonConfirm.setOnAction(this);
            buttonConfirm.setFont(new Font("Calibri Light",15));
            Button buttonCancel = new Button("סרב רכישה");
            buttonCancel.setId(messageDetails[0]);
            buttonCancel.setOnAction(this);
            buttonCancel.setFont(new Font("Calibri Light",15));
            buttonsListConfirm.add(buttonConfirm);
            buttonsListCancel.add(buttonCancel);
            Label label = new Label(message.substring(messageDetails.length));
            label.setFont(new Font("Calibri Light",15));
            labelList.add(label);
        }
        VB_buttonsConfirm.getChildren().clear();
        VB_buttonsConfirm.getChildren().addAll(buttonsListConfirm);
        VB_labels.getChildren().clear();
        VB_labels.getChildren().addAll(labelList);

    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getText().equals("אשר רכישה")) {
            int index = buttonsListConfirm.indexOf(button);
            controller.deletePendingVacation(button.getId());
            Label label = labelList.get(index);
            String labelText = label.getText();
            String[] data = labelText.split(",");
            String buyer = controller.readPendingVacationBuyer(Integer.valueOf(button.getId()));
            controller.insertConfirmedVacation(Integer.valueOf(button.getId()), controller.getUserName(),
                    buyer, data[1], data[2], Integer.valueOf(data[3]), data[4], data[5]);
            button.setDisable(true);
            alert("הודעת אישור תועבר לקונה", Alert.AlertType.CONFIRMATION);
        }
        else{
            //למחוק מפנדיג
            // להוסיך לזמינים
            controller.deletePendingVacation(button.getId());
            int index = buttonsListCancel.indexOf(button);
            Label label = labelList.get(index);
            String labelText = label.getText();
            String[] data = labelText.split(",");


        }

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
