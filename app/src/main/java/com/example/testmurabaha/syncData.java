package com.example.testmurabaha;

import com.google.gson.annotations.SerializedName;

public class syncData {

    @SerializedName("Код") private String code;
    @SerializedName("Ссылка") private String link;
    @SerializedName("Наименование") private String name;
    @SerializedName("ИНН") private String INN;
    @SerializedName("КонтактныеДанные") private String contactData;

    public syncData(String code, String link, String name, String INN, String contactData) {
        this.code = code;
        this.link = link;
        this.name = name;
        this.INN = INN;
        this.contactData = contactData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        this.contactData = contactData;
    }
}

