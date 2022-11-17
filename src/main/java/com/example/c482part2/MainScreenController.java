package com.example.c482part2;

import com.example.c482part2.model.Inventory;
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
import com.example.c482part2.model.*;

/** A controller class that sends data and gets data for the Main Screen view of the inventory application. */



public class MainScreenController implements Initializable {

    /** The search field for the part table. */
    public TextField partSearchTxt;

    /** The search field for the product table. */
    public TextField productSearchTxt;
    Stage stage;
    Parent scene;

    /** The table view for all parts on the main screen. */
    @FXML
    private TableView<Part> partsTableView;

    /** The part ID column for the part table. */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /** The part inventory column for the part table. */
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    /** The part name column for the part table. */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /** The price column for the part table.  */
    @FXML
    private TableColumn<Part, Double> partPricePerUnitCol;

    /** The products table view on the main screen. */
    @FXML
    private TableView<Product> productsTableView;

    /** The product ID column in the product table. */
    @FXML
    private TableColumn<Inventory, Integer> productIdCol;

    /** The product inventory column in the product table. */
    @FXML
    private TableColumn<Inventory, Integer> productInventoryCol;

    /** The product name column in the product table. */
    @FXML
    private TableColumn<Inventory, String> productNameCol;

    /** The product price column in the product table. */
    @FXML
    private TableColumn<Inventory, Double> productPricePerUnitCol;

    /** Loads the add part controller.
     * @param event Add Button action to add a new part
     */

    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addpart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Loads the add product controller.
     * @param event Add button to add a new product.
     */

    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addproduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /** Searches for a part when user enters values in the search part text field. Returns table view with results.
     * Parts can be searched by part ID or name.
     * Creates a list of parts in inventory and loops through the list to find parts matching the value in the query.
     * @return  Parts table view with the list of matching parts.
     * */

    public void partTableSearch(ActionEvent actionEvent) {
        String query = partSearchTxt.getText();

        ObservableList<Part> parts = Inventory.lookupPart(query);

        if (parts.size() == 0) {

            int partID = Integer.parseInt(query);
            Part np = Inventory.lookupPart(partID);
            if (np != null)
                parts.add(np);
        }


        partsTableView.setItems(parts);
        partSearchTxt.setText("");

    }

    /** Searches for a product when user enters values in the search product text field. Returns table view with results.
     * Products can be searched by product ID or name.
     * Creates a list of products in Inventory and loops through the list to find products matching the query.
     * @return Products table view with list of matching product.
     * */
    public void productTableSearch(ActionEvent actionEvent) {

        String query = productSearchTxt.getText();

        ObservableList<Product> products = Inventory.lookupProduct(query);

        if (products.size() == 0) {

            int productId = Integer.parseInt(query);
            Product np = Inventory.lookupProduct(productId);
            if (np != null)
                products.add(np);
        }

        productsTableView.setItems(products);
        productSearchTxt.setText("");

    }

    /** Allows user to delete a selected part in the part table.
     * Displays a warning message if no part is selected.
     * Displays a confirmation message when part is selected to confirm user wants to delete the part.
     * @param event Delete part button action.
     * */

    @FXML
    void onActionDeleteParts(ActionEvent event) throws IOException {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();


        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "There is no part selected.");
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                Inventory.getAllParts().remove(selectedPart);

                Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        }


    }

    /** Allows user to delete a selected product in the product table.
     * Displays a warning message if no product is selected.
     * Displays a confirmation message if user wants to delete the selected product.
     *Displays a warning message if the product selected to delete has associated parts that user has not removed.
     * @param event Delete product button action.
     */

    @FXML
    void onActionDeleteProducts(ActionEvent event) throws IOException {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "There is no product selected.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
                if (associatedParts.size() >= 1) {
                    alert = new Alert(Alert.AlertType.WARNING, "There are parts associated with this product, please remove the associated parts before continuing.");
                    alert.showAndWait();

                } else {
                    Inventory.getAllProducts().remove(selectedProduct);
                }

                Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        }


    }

    /** Allows user to exit the program and close the window. */

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);

    }

    /** Allows user to select a part to modify and loads modify part controller.
     * Gets current selected part from the part table view.
     * Shows an alert if no product is selected.
     * Loads modify part screen.
     * @param event Modify button action on the parts table.
     * */

    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {

        Part partToModify = partsTableView.getSelectionModel().getSelectedItem();
        int index = partsTableView.getSelectionModel().getSelectedIndex();
        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part selected, please try again.");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifypart.fxml"));
        Parent root = loader.load();

        ModifyPartController modPartController = loader.getController();
        modPartController.sendSelectedPart(partToModify, index);


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //Parent scene = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();

    }

    /** Allows user to select a product to modify and loads modify part controller.
     * Gets current selected product from the product table view.
     * Shows an error alert if no product is selected.
     * Loads product controller screen.
     * @param event Modify product button action.
     * */

    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {

        Product productToModify = productsTableView.getSelectionModel().getSelectedItem();
        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No product selected, please try again.");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyproduct.fxml"));
        Parent root = loader.load();

        ModifyProductController modProductController = loader.getController();
        modProductController.sendSelectedProduct(productToModify);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();


    }
    /** Initializes the main screen controller and populates part and product table views.
     * @param url
     * @param resourceBundle
     * */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}






