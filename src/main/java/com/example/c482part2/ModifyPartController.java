package com.example.c482part2;

import com.example.c482part2.model.InHouse;
import com.example.c482part2.model.Inventory;
import com.example.c482part2.model.Outsourced;
import com.example.c482part2.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller class that provides logic for the Modify Part controller. */

public class ModifyPartController implements Initializable {


        Part partToDelete;

        public Text modifyMachineIdLabel;
        public RadioButton modifyPartInhouseButton;
        public RadioButton modifyPartOutSourcedButton;
        @FXML
        private TextField modifyPartIdTxt;

        @FXML
        private TextField modifyPartInvTxt;

        @FXML
        private TextField modifyPartMachineIdTxt;

        @FXML
        private TextField modifyPartMaxTxt;

        @FXML
        private TextField modifyPartMinTxt;

        @FXML
        private TextField modifyPartNameTxt;

        @FXML
        private TextField modifyPartPriceTxt;

        @FXML
        private ToggleGroup togglegroup;

        /** Allows user to cancel modify part and returns to the main screen.
         * @param event Cancel button action.
         */

        @FXML
        void onActionCancelModifyPart(ActionEvent event) throws IOException {
                Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene scene = new Scene(parent);
                Stage stage= (Stage) (Stage) ((Button)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
        }


        /** Allows user to save a modified part.
         * Updates an existing part with new values.
         * Validates user input.
         * Displays error message if valid values are not entered.
         * If part is modified successfully , removes old part and adds modified part to inventory list.
         * Returns to main screen.
         * @param event Save button action.
         */
        @FXML
        void onActionSaveModifyPart(ActionEvent event) throws IOException {
                try {
                        boolean validationError =false;
                        int id = Integer.parseInt(modifyPartIdTxt.getText());
                        String name = modifyPartNameTxt.getText();
                        int stock = Integer.parseInt(modifyPartInvTxt.getText());
                        double price = Double.parseDouble(modifyPartPriceTxt.getText());
                        int min = Integer.parseInt(modifyPartMinTxt.getText());
                        int max = Integer.parseInt(modifyPartMaxTxt.getText());

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
                        boolean partModifiedSuccessfully = false;
                        if (validationError==false) {



                                if (modifyPartInhouseButton.isSelected()) {
                                        machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
                                        InHouse newInhouse = new InHouse(id, name, price, stock, min, max, machineId);
                                        //newInhouse.setId(Inventory.getUniquePartId());
                                        Inventory.getAllParts().add(newInhouse);
                                        partModifiedSuccessfully = true;

                                } else {
                                        companyName = modifyPartMachineIdTxt.getText();
                                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                                        //newOutsourcedPart.setId(Inventory.getUniquePartId());
                                        Inventory.getAllParts().add(newOutsourcedPart);
                                        partModifiedSuccessfully = true;

                                }

                                Inventory.getAllParts().remove(partToDelete);
                        }



                        if (partModifiedSuccessfully) {
                                Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
                                Scene scene = new Scene(parent);
                                Stage stage = (Stage) (Stage) ((Button) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                        }

                } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter valid values for each field.");
                        alert.showAndWait();

                }
        }

        /** Sets part Machine ID label to Machine ID if selected part is an In House part. */

        public void onActionModifyPartInhouse(ActionEvent event) {
               modifyMachineIdLabel.setText("Machine ID");
        }

        /** Sets part Machine ID label to Company Name if selected part is an Outsourced part.  */
        public void onActionModifyPartOutsourced(ActionEvent event) {
               modifyMachineIdLabel.setText("Company Name");
        }

        /** Method that gets the part to modify.
         * @param partToModify */
        public void sendSelectedPart(Part partToModify, int index){


                partToDelete = partToModify;
                modifyPartIdTxt.setText(String.valueOf(partToModify.getId()));
                modifyPartNameTxt.setText(partToModify.getName());
                modifyPartInvTxt.setText(String.valueOf(partToModify.getStock()));
                modifyPartPriceTxt.setText(String.valueOf(partToModify.getPrice()));
                modifyPartMaxTxt.setText(String.valueOf(partToModify.getMax()));
                modifyPartMinTxt.setText(String.valueOf(partToModify.getMin()));


                if (partToModify instanceof InHouse){
                        modifyPartInhouseButton.setSelected(true);
                        modifyMachineIdLabel.setText("Machine ID");
                        modifyPartMachineIdTxt.setText(String.valueOf(((InHouse)partToModify).getMachineId()));
                }
                else {
                        modifyPartOutSourcedButton.setSelected(true);
                        modifyMachineIdLabel.setText("Company Name");
                        modifyPartMachineIdTxt.setText( ((Outsourced) partToModify).getCompanyName());

                }


        }


        /** Initializes modify part controller.
         * @param url
         * @param resourceBundle
         */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }


}
