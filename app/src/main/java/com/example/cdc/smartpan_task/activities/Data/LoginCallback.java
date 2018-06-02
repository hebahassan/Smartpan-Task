package com.example.cdc.smartpan_task.activities.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCallback {

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("Id")
    @Expose
    private int userID;

    @SerializedName("UserName")
    @Expose
    private String userName;

    @SerializedName("Image")
    @Expose
    private String image;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
