package com.klym.kogito.shared.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;  


public class CustomResult implements Serializable {

    private int responseCode;
    private Map<String, Object> data;
    private JSONObject jsonData;
    private String jsonString;

    public CustomResult() {
        this.data = new TreeMap<>();
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public Object getValue(String path) {
        return JsonPath.read(jsonString, path);
    }

    @Override
    public String toString() {
        return "CustomResult [responseCode=" + responseCode + ", data=" + data + "]";
    }

    
}
