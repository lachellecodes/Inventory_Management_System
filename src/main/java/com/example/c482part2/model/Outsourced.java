package com.example.c482part2.model;

/** This class represents an outsourced part. */

public class Outsourced extends Part {



    private String companyName;

    /** The constructor that creates a new outsourced part object.
     * @param id The ID number of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The number of this part currently in stock.
     * @param min The minimum number of this part in inventory.
     * @param max The maximum number of this part in inventory.
     * @param companyName The company name of the outsourced part.
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Getter for the company name variable.
     * @return company name.
     * */
    public String getCompanyName(){
        return companyName;
    }

    /** Setter for the company name variable.
     * @param companyName accepts the company name.
     */

    public void setCompanyName(String companyName) {

        this.companyName=companyName;
    }
}
