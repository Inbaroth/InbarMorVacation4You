package View;

import Controller.Controller;
import Model.Vacation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observer;

public class DisplayVacations extends HomePage implements Observer {

    private Controller controller;
    private Stage stage;
    ArrayList<Vacation> matchVacations;
    public VBox vb_Buttons;
    public VBox vb_details;
    public Label l_originAndDestination;
    public Label l_dates;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        matchVacations = new ArrayList<>();
        matchVacations = controller.getMatchesVacations();
        if(matchVacations == null)
            alert("מתנצלים אך אין חופשה שתואמת את החיפוש שלך", Alert.AlertType.INFORMATION);
        else
            offerVacations();
    }
             //(ActionEvent event)
    void offerVacations() {
        //check this is the right order HERE
        l_originAndDestination.setText("מ"+matchVacations.get(0).getOrigin() + "אל "+matchVacations.get(0).getDestination() );
        l_dates.setText(matchVacations.get(0).getDateOfDeparture() + "-" +matchVacations.get(0).getDateOfArrival() );
        ArrayList<Button> buttonlist = new ArrayList<Button>(); //our Collection to hold newly created Buttons
        String buttonTitle = "רכוש חופשה"; //extract button text, adapt the String to the columnname that you are interested in
        ArrayList<Label> detailsLabels = new ArrayList<Label>();
        //change this to flight details
        for (Vacation vacation: matchVacations) {
            Button btn = new Button(buttonTitle);
            btn.setFont(new Font("Calibri Light", 15));
            btn.setPrefHeight(38.0);
           // btn.setTextFill();
            buttonlist.add(btn);
            String deatails = "שדה תעופה ביעד:"+vacation.getDestinationAirport() + "מס' כרטיסים:" + vacation.getNumOfTickets() + "כבודה:"+ vacation.getBaggage() + "סוג כרטיס:" + vacation.getTicketsType() + "מחיר:"+ vacation.getPrice();
            Label lbl = new Label();
            lbl.setText(deatails);
            lbl.setFont(new Font("Calibri Light", 15));
            lbl.setPrefSize(500.0,38.0);
            detailsLabels.add(lbl);
        }
        vb_Buttons.getChildren().clear();
        vb_Buttons.getChildren().addAll(buttonlist);

        vb_details.getChildren().clear();
        vb_details.getChildren().addAll(detailsLabels);
    }


}
