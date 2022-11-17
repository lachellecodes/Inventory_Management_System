package com.example.c482part2.model;

/** This class represents an In House part. */

public class InHouse extends Part{


    private int machineId;

    /** The constructor that creates a new In House part object.
        @param id The ID number for the part.
        @param name The name for the part.
        @param price The price of the part.
        @param stock The number of parts currently in inventory.
        @param min The minimum number of this part that is in inventory.
        @param max The maximum number of this part that is in inventory.
        @param machineId The machine ID for this In House part.
      */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId=machineId;
    }

    /** The getter for Machine ID.
        @return  The Machine ID for the In House Part.
     */
    public int getMachineId() {

        return machineId;
    }

    /** The setter for Machine ID.
        @param machineId the Machine ID for the In House Part.
      */

    public void setMachineId(int machineId){

        this.machineId=machineId;
    }

}
