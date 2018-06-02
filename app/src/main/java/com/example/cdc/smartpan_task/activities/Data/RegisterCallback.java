package com.example.cdc.smartpan_task.activities.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterCallback {

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("CustomerId")
    @Expose
    private int customerID;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
