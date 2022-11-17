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

/** Controller class that provides logic for the Add Product controller. */

public class AddProductController implements Initializable {


    @FXML
    private TableView<Part> addProductBottomTable;

    @FXML
    private TableColumn<Part, Integer> addProductBottomTableInventoryCol;

    @FXML
    private TableColumn<Part, Integer> addProductBottomTablePartIdCol;

    @FXML
    private TableColumn<Part, String> addProductBottomTablePartNameCol;

    @FXML
    private TableColumn<Part, Double> addProductBottomTablePriceCol;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableColumn<Part, Integer> addProductTopTableInventoryCol;

    @FXML
    private TableColumn<Part, Integer> addProductTopTablePartCol;

    @FXML
    private TableColumn<Part, String> addProductTopTablePartNameCol;

    @FXML
    private TableColumn<Part, Double> addProductTopTablePriceCol;

    @FXML
    private TableView<Part> addProductTopTableView;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Method that searches for a part by name to add a list of parts to associate to a new product.
     * Loops through the list of all parts in inventory to find a match.
     * @return part that matches query.
     */
    private ObservableList<Part> searchParts(String partialName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part np : allParts) {
            if (np.getName().contains(partialName)) {
                namedPart.add(np);
            }

        }

        return namedPart;
    }
    /** Method that searches for a part by ID number to add to a list of parts to associate with a product.
     * Loops through list of all parts in inventory to find a match.
     * @return part that matches query.
     */
    private Part getPartByNum(int partNum) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part np = allParts.get(i);
            if (np.getId() == partNum) {
                return np;
            }
        }

        return null;

    }

    /** Allows user to search for a part by ID number or name to associate with a product.
     * @param actionEvent Search action.
     * @return Table view with parts that match query.
     */

    public void onActionAddProductSearchPart(ActionEvent actionEvent) {

        String query = addProductSearchTxt.getText();
        ObservableList<Part> foundPart = searchParts(query);

        if (foundPart.size() == 0) {
            int num = Integer.parseInt(query);
            Part np = getPartByNum(num);
            if (np != null) {
                foundPart.add(np);
            }

        }

        addProductTopTableView.setItems(foundPart);

    }

    /** Allows user to cancel adding a product and return to the main screen.
     * @param event Cancel button action.
     */

    @FXML
    void onActionCancelAddProduct(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Allows user to remove a selected associated part from a product.
     * Displays warning alert message if no part is selected.
     * Displays confirmation alert to verify user wants to remove selected part.
     * Removes part from the product but not from inventory parts list.
     */

    @FXML
    void onActionRemove(ActionEvent event) {

        Part selectedPart = addProductBottomTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No part selected.");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                addProductBottomTable.setItems(associatedParts);

            }
        }
    }

    /** Allows user to save a new product with associated parts.
     * Validates user input.
     * Shows warning alert if incorrect values entered.
     * If new product created successfully adds a new product to inventory list.
     * Returns to main screen once product successfully created.
     * @param event Save product button action.
     */


    @FXML
    void onActionSaveAddProduct(ActionEvent event) throws IOException {
        try {
            boolean validationError = false;
            int id = 0;
            String name = addProductNameTxt.getText();
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int stock = Integer.parseInt(addProductInvTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            boolean productAddedSuccessfully = false;

            if (min > max) {
                validationError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                validationError = true;
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value should be between min and max values.");
                alert.showAndWait();
            }

            if (validationError == false) {

                Product newProduct = new Product(id, name, price, stock, min, max);
                newProduct.setId(Inventory.getUniquePartId());
                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);}

                Inventory.addProduct(newProduct);
                productAddedSuccessfully=true;


            }

            if (productAddedSuccessfully) {
                Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }


            catch(NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter valid values for each field.");
            alert.show();

        }

        }

    /** Allows user to add a part to associate with a new product.
     * Gets selected part from the top parts table view.
     * Displays warning alert if no product is selected.
     * Adds part to associated parts of a new product and displays in bottom table view.
     * @param actionEvent Add button action.
     */

    public void onActionAddPart(ActionEvent actionEvent) {

        Part selectedPart = addProductTopTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
        Alert alert = new Alert(Alert.AlertType.WARNING,"No Part Selected");
        alert.show();

        }
        else {
            associatedParts.add(selectedPart);
            addProductBottomTable.setItems(associatedParts);
        }


    }

    /** Initializes the add product controller with top table view populated with parts.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTopTableView.setItems(Inventory.getAllParts());
        addProductTopTablePartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductTopTablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductTopTableInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductTopTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //addProductBottomTable.setItems(Inventory.getAllParts());
        addProductBottomTablePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductBottomTablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductBottomTableInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductBottomTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


}
