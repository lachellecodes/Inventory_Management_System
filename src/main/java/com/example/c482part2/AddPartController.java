package com.example.c482part2;

import com.example.c482part2.model.Inventory;
import com.example.c482part2.model.Outsourced;
import com.example.c482part2.model.Part;
import com.example.c482part2.model.InHouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** A controller class that provides logic for the Add Part controller. */

public class AddPartController implements Initializable {
    public Label machineIdLabel;

/** Initializes the Add Part Controller.
 * @param url
 * @param resourceBundle
 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button addPartCancelButton;

    @FXML
    private RadioButton addPartInHouseButton;

    @FXML
    private RadioButton addPartOutsourcedButton;

    @FXML
    private Button addPartSaveButton;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMachineTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private ToggleGroup togglegroup;



    /** Allows user to save a new part to inventory and returns to main screen once successfully added.
     * Validates user input into text fields.
     * @param event Save button action.
     */
    @FXML
    void addPartSaveButton(ActionEvent event) throws IOException {
        try{
            boolean validationError = false;

            int id = 0;
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());


            if (min > max) {
                validationError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                validationError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value should be between min and max values.");
                alert.showAndWait();
            }


            int machineId;
            String companyName;
            boolean partAddedSuccessfully =false;

            if (validationError == false) {


                if (addPartInHouseButton.isSelected()) {
                    machineId = Integer.parseInt(partMachineTxt.getText());
                    InHouse newInhouse = new InHouse(id, name, price, stock, min, max, machineId);
                    newInhouse.setId(Inventory.getUniquePartId());
                    Inventory.addPart(newInhouse);
                    partAddedSuccessfully= true;

                } else {
                    companyName = machineIdLabel.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    newOutsourcedPart.setId(Inventory.getUniquePartId());
                    Inventory.addPart(newOutsourcedPart);
                    partAddedSuccessfully=true;

                }
            }

            if (partAddedSuccessfully) {
                Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        }
        catch(NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter valid values for each field.");
            alert.showAndWait();

        }

    }


    /** Sets the part machine ID label text to Company Name if Outsourced product is selected. */
    @FXML
    void outsourcedClicked(ActionEvent event) {
        machineIdLabel.setText("Company Name");
    }

    /** Sets the part machine label ID text to Machine ID if In House product is selected.  */
    @FXML
    void inHouseButtonSelected(ActionEvent event) {
        machineIdLabel.setText("Machine ID");
    }

    /** Allows user to cancel adding a new part and returns to the main screen.
     * @param actionEvent Cancel Button action.
     */
    public void addPartCancelButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage= (Stage) (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
