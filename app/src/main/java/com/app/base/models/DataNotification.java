package com.app.base.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by luthfi on 24/02/2017.
 */

public class DataNotification {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("dataId")
    @Expose
    private String dataId;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("keyValue")
    @Expose
    private String keyValue;
    @SerializedName("typeValue")
    @Expose
    private String typeValue;
    @SerializedName("method")
    @Expose
    private String method;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
    public String getKeyValue() {
        return keyValue;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
