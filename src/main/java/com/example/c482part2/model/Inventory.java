package com.example.c482part2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class represents the inventory of all parts and products. */

public class Inventory {


    /** A static variable that is used to generate unique part IDs. */
    private static int uniquePartId=3;

    /** A static variable that is used to generate unique product IDs. */
    private static int uniqueProductId = 3;

    /** A list that holds all parts in inventory. */
    private static ObservableList<Part> allParts= FXCollections.observableArrayList();

    /** A list that holds all products in inventory. */
    private static ObservableList<Product> allProducts=FXCollections.observableArrayList();

    /** Adds a new part to the list of all parts in inventory.
     * @param newPart The new part to add.
     */
    public static void addPart(Part newPart){

        allParts.add(newPart);
    }

    /** Creates a unique part ID for the new part.
     * @return a unique part ID.
     */
    public static int getUniquePartId(){

        return ++uniquePartId;

    }

    /** Creates a unique product ID for the new product.
     * @return a unique product ID.
     */

    public static int getUniqueProductId(){

        return ++uniqueProductId;
    }

    /** Adds a new product to the list of all products.
     * @param newProduct The new product to add.
     */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /** Gets a list of all parts in inventory.
     * @return a list of all parts currently in inventory.
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /** Gets a list of all products in inventory.
     * @return a list of all products currently in inventory.
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /** Searches for a part by Part ID.
     *@param partID takes in a part ID number.
     *@return part if there is a part ID number match, otherwise null.
     */
    public static Part lookupPart(int partID){

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0;  i < allParts.size(); i++) {
            Part np = allParts.get(i);
            if (np.getId() == partID){
                return np;
            }
        }
        return null;

    }

    /** Searches for product by id number.
     * @param productId accepts a product number.
     * @returns product if there is a product ID number match, otherwise null.
     */

    public static Product lookupProduct(int productId){

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product np = allProducts.get(i);
            if (np.getId() == productId){
                return np;
            }
        }

        return null;

    }

    /** Searches for a part by name.
     * @param partName accepts the name or partial name of the part.
     * @return part(s) results that match the input from search, either a whole name or partial.
     */

    public static ObservableList<Part> lookupPart(String partName){

        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part np: allParts){
            if (np.getName().contains(partName)){
                namedPart.add(np);
            }

        }

        return namedPart;
    }

    /** Searches for a product by name.
     * @param productName accepts the name or partial name of the product.
     * @return product(s) that match the input from search , either a whole name or partial.
     */

    public static ObservableList<Product> lookupProduct (String productName){

        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product np: allProducts){
            if (np.getName().contains(productName)){
                namedProduct.add(np);
            }

        }

        return namedProduct;

    }

    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);

    }

    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);

    }

    public static boolean deletePart(Part selectedPart){

        return true;
    }

    public static boolean deleteProduct(Product selectedProduct){

        return true;
    }

}
