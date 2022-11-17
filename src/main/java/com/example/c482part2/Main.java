package com.example.c482part2;

import com.example.c482part2.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Javadoc documentation can be found after unzipping the files at /javadoc.
 *
 *  Runtime error correction:
 *
 * When a part was not selected from the main screen to modify, the program would take the user to the modify part screen anyway.
 * The code in the Main Screen controller was not checking to see if a there was a selected part to modify before transferring control to the Modify Part Controller.
 * Changed the code in the Main Screen controller in the OnActionModifyParts to an if() statement to check if a part was selected.
 * If part was not selected, user gets an alert to select a part to modify before continuing to modify part screen.
 *
 *
 * Future enhancements:
 * This inventory management program needs access control by requiring the user to have a login.
 * This will allow the company to know who has been using the system and track the transactions made by a user.
 * The inventory system could also be a cloud based software to allow for automated backups and real time updates.
 * The inventory system should have alerts when a part or product is low or drops below a given quantity.
 */

/**  The main class creates a starting point for the inventory app that manages parts and products. */
public class Main extends Application {


    /** The start method creates the stage and loads the initial app view.
     @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    //** The main method is the entry point to this application. It launches the application with test data. */

    public static void main(String[] args) {

        Part part1 = new InHouse(1, "headboard", 199.99, 17, 5, 105, 15);
        Part part2 = new InHouse(2,"mirror", 75.99, 37, 3, 100, 35);
        Part part3 = new InHouse(3, "pillow", 159.99, 80, 7, 80, 40);
        Product product1 = new Product(1, "bed", 479.99, 50, 10, 100);
        Product product2 = new Product(2, "dresser", 329.99, 13, 17, 35);
        Product product3 = new Product (3, "couch", 29.99, 45, 25, 90);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);


        launch(args );
    }
}