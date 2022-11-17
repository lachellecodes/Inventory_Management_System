package com.example.c482part2;


import com.example.c482part2.model.Inventory;
import com.example.c482part2.model.Part;
import com.example.c482part2.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class that provides logic for the Modify Product controller. */

public class ModifyProductController implements Initializable {


    Product productToDelete;

    @FXML
    private TableColumn<Part, Integer> modifyProductBottomInvLevel;

    @FXML
    private TableColumn<Part, Integer> modifyProductBottomPartId;

    @FXML
    private TableColumn<Part, String> modifyProductBottomPartName;

    @FXML
    private TableColumn<Part, Double> modifyProductBottomPrice;

    @FXML
    private TableView<Part> modifyProductBottomTableView;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TableColumn<Part, Integer> modifyProductTopInvLevel;

    @FXML
    private TableColumn<Part, Integer> modifyProductTopPartId;

    @FXML
    private TableColumn<Part, String> modifyProductTopPartName;

    @FXML
    private TableColumn<Part, Double> modifyProductTopPrice;

    @FXML
    private TableView<Part> modifyProductTopTableView;

    private ObservableList <Part> associatedParts = FXCollections.observableArrayList();

    /** Allows user to cancel modify product and return to main screen.
     * @param event Cancel button action.
     */

    @FXML
    void onActionModProdCancel(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage= (Stage) (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    /** Method that searches for a part by name to add a list of parts to associate to a product.
     * Loops through the list of all parts in inventory to find a match.
     * @return part that matches query.
     */
    private ObservableList<Part> searchProductsPart (String partialName){
        ObservableList<Part> namedProductPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part np: allParts){
            if (np.getName().contains(partialName)){
                namedProductPart.add(np);
            }

        }

        return namedProductPart;
    }

    /** Method that searches for a part by ID number to add to a list of parts to associate with a product.
     *  Loops through list of all parts in inventory to find a match.
     *  @return part that matches query.
     */
    private Part getProductPartByNum (int partNum){
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part np = allParts.get(i);
            if (np.getId() == partNum){
                return np;
            }
        }

        return null;

    }

    /** Method that searches for a part to add to product by product ID number or name.
     * @param event Search action.
     * @return Table view with parts that match query.
     */
    @FXML
    void onActionModProdSearch(ActionEvent event) {

        String query = modifyProductSearchTxt.getText();
        ObservableList<Part> foundProduct = searchProductsPart(query);

        if (foundProduct.size() == 0) {
            int num = Integer.parseInt(query);
            Part np = getProductPartByNum(num);
            if (np != null) {
                foundProduct.add(np);
            }

        }

        modifyProductTopTableView.setItems(foundProduct);

    }

    /** Allows user to save a modified product.
     *Validates user input.
     *Shows warning alert if incorrect values entered.
     *If product is modified successfully, removes old product and updates inventory list with modified product.
     *Returns to main screen.
     * @param event Save button action.
     * */

    @FXML
    void onActionModProdSave(ActionEvent event) throws IOException {
        try{
            boolean validationError = false;
            int id = Integer.parseInt(modifyProductIdTxt.getText());
            String name = modifyProductNameTxt.getText();
            int stock = Integer.parseInt(modifyProductInvTxt.getText());
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
            boolean productModifiedSuccessfully = false;

            if (min > max) {
                validationError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                validationError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value should be between min and max values.");
                alert.showAndWait();
            }

            if(validationError==false){

            Product newProduct= new Product( id, name, price, stock, min, max);
            for (Part part : associatedParts){
                newProduct.addAssociatedPart(part);}

            Inventory.getAllProducts().add(newProduct);
            productModifiedSuccessfully= true;}

            Inventory.getAllProducts().remove(productToDelete);

            if(productModifiedSuccessfully){

            Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(parent);
            Stage stage= (Stage) (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();}

        }
        catch(NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter valid values for each field.");
            alert.show();

        }


    }

    /** Allows user to add a selected part to the product.
     * Shows a warning alert if no part is selected.
     * Adds an associated part to the bottom table view of parts associated with the product.
     * @param event Add button action.
     * */
    @FXML
    void onActionProductAdd(ActionEvent event) {

        Part selectedPart = modifyProductTopTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No part selected.");
            alert.show();

        }
        else {
            associatedParts.add(selectedPart);
            modifyProductBottomTableView.setItems(associatedParts);
        }


    }

    /** Allows user to remove a part associated with the product.
     * Shows a warning alert if no part is selected.
     * Shows a confirmation alert to confirm that the user wants to remove the associated part from the product.
     * Removes the associated part from the product but not from the parts list in inventory.
     * @param event Remove button action.
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {


        Part selectedPart = modifyProductBottomTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No part selected.");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {


                associatedParts.remove(selectedPart);
                modifyProductBottomTableView.setItems(associatedParts);
            }
        }

    }

    /** Method that gets the selected part from the Product list to modify.
     * @param productToModify */

    public void sendSelectedProduct (Product productToModify){

        productToDelete = productToModify;
        modifyProductIdTxt.setText(String.valueOf(productToModify.getId()));
        modifyProductNameTxt.setText(productToModify.getName());
        modifyProductInvTxt.setText(String.valueOf(productToModify.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(productToModify.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(productToModify.getMax()));
        modifyProductMinTxt.setText(String.valueOf(productToModify.getMin()));
        modifyProductBottomTableView.setItems(productToModify.getAllAssociatedParts());

    }


    /** Initializes the Modify Part controller and populates top table view with parts. 
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductTopTableView.setItems(Inventory.getAllParts());
        modifyProductTopPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductTopPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductTopInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductTopPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        //modifyProductBottomTableView.setItems(associatedParts);
        modifyProductBottomPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductBottomPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductBottomInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductBottomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
