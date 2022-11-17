package com.example.c482part2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class represents a product. */
public class Product {

    /** This is a list that holds all parts associated with a product. */
    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();

    private int id;

    private String name;

    private double price;

    private int stock;

    private int min;

    private int max;

    /** This constructor creates a new product object.
     * @param id The product ID.
     * @param name The product name.
     * @param price The product price.
     * @param stock The amount of this product currently stock.
     * @param min  The minimum number of this product in inventory.
     * @param max The maximum number of this product in inventory.
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** The getter for the product ID.
     * @return product ID.
     */

    public int getId(){

        return id;
    }

    /** The setter for the product ID.
     * @param id the product ID.
     */

    public void setId(int id){

        this.id=id;
    }

    /** The getter for the product name.
     * @return product name.
     */

    public String getName() {

        return name;
    }

    /** The setter for the product name.
     * @param name of the product.
     */

    public void setName(String name){

        this.name=name;
    }

    /** The getter for the stock amount.
     * @return stock of the product.
     */
    public int getStock() {

        return stock;
    }

    /** The setter for the stock amount.
     * @param stock the amount of product in stock.
     */

    public void setStock(int stock){

        this.stock=stock;
    }

    /** The getter for the price amount.
     * @return price of the product.
     */

    public double getPrice() {
        return price;
    }

    /** The setter for the price amount.
     * @param price the price of the product.
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /** The getter for the minimum amount of the product.
     * @return minimum amount of the product.
     */

    public int getMin(){

        return min;
    }
    /** The setter for the minimum amount of the product.
     * @param min minimum amount of the product.
     */
    public void setMin(int min){

        this.min=min;
    }

    /** The getter for the maximum amount of the product.
     * @return maximum amount of the product.
     */
    public int getMax(){

        return max;
    }
    /** The setter for the maximum amount of the product.
     * @param max the maximum amount of the product.
     */
    public void setMax(int max){

        this.max=max;
    }
    /** Adds an associated part(s) to the product in an observable list.
     * @param part takes a new part to be added to the product.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);

    }

    /** Returns a list of all associated parts associated with a product.
     * @return an observable list of associated parts with a product.
     */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }
}
