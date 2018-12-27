package View;

import Controller.Controller;
import Model.Flights;
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
    private ArrayList<Flights> flights;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        setMessages();
    }

    public void setMessages() {
        ArrayList<Flights> pendingFlights = controller.readPendingVacations(controller.getUserName());
        this.flights = new ArrayList<>();
        this.buttonsListConfirm = new ArrayList<>();
        this.buttonsListCancel = new ArrayList<>();
        this.labelList = new ArrayList<>();
        for (Flights flights : pendingFlights) {
            String vacationID = String.valueOf(flights.getVacationId());
            String details = "שדה תעופה ביעד:"+ flights.getDestinationAirport() + " מס' כרטיסים: " + flights.getNumOfTickets() + "\n" +  " כבודה:"+ flights.getBaggage() + " סוג כרטיס: " + flights.getTicketsType() + "\n" + " מחיר: "+ flights.getPrice();
            Button buttonConfirm = new Button("אשר רכישה");
            buttonConfirm.setId(vacationID);
            buttonConfirm.setOnAction(this);
            buttonConfirm.setFont(new Font("Calibri Light",15));
            Button buttonCancel = new Button("סרב רכישה");
            buttonCancel.setId(vacationID);
            buttonCancel.setOnAction(this);
            buttonCancel.setFont(new Font("Calibri Light",15));
            buttonsListConfirm.add(buttonConfirm);
            buttonsListCancel.add(buttonCancel);
            Label label = new Label(details);
            label.setFont(new Font("Calibri Light",15));
            label.setPrefSize(500.0,38.0);
            this.flights.add(flights);
            labelList.add(label);
        }
        VB_buttonsConfirm.getChildren().clear();
        VB_buttonsConfirm.getChildren().addAll(buttonsListConfirm);
        VB_buttonsCancel.getChildren().clear();
        VB_buttonsCancel.getChildren().addAll(buttonsListCancel);
        VB_labels.getChildren().clear();
        VB_labels.getChildren().addAll(labelList);

    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getText().equals("אשר רכישה")) {
            int index = buttonsListConfirm.indexOf(button);
            String buyer = controller.readPendingVacationBuyer(Integer.valueOf(button.getId()));
            controller.deletePendingVacation(button.getId());
            Label label = labelList.get(index);
            String labelText = label.getText();
            Flights flights = this.flights.get(index);
            //String[] data = labelText.split(",");
            controller.insertConfirmedVacation(Integer.valueOf(button.getId()), controller.getUserName(),
                    buyer, flights.getOrigin(), flights.getDestination(), flights.getPrice(), flights.getDateOfDeparture(), flights.getDateOfArrival());
            button.setDisable(true);
            buttonsListCancel.get(index).setDisable(true);
            alert("הודעת אישור תועבר לקונה", Alert.AlertType.CONFIRMATION);
        }
        else{
            //למחוק מפנדיג
            // להוסיך לזמינים
            controller.deletePendingVacation(button.getId());
            int index = buttonsListCancel.indexOf(button);
            Label label = labelList.get(index);
            Flights flights = this.flights.get(index);
            controller.insertVacation(flights.getOrigin(), flights.getDestination(), flights.getPrice(), flights.getDestinationAirport(), flights.getDateOfDeparture(), flights.getDateOfArrival(), flights.getAirlineCompany(),
                    flights.getNumOfTickets(), flights.getBaggage(), flights.getTicketsType(), flights.getVacationStyle(), flights.getSeller(), flights.getOriginalPrice());
            button.setDisable(true);
            buttonsListConfirm.get(index).setDisable(true);

        }

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
