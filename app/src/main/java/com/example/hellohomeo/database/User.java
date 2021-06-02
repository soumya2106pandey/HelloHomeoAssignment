package com.example.hellohomeo.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int User_Id;
    private String name;
    private String agency;
    private String image;
    private String link;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String name, String agency, String image, String link, String status) {
        this.name = name;
        this.agency = agency;
        this.image = image;
        this.link = link;
        this.status = status;
    }


}
