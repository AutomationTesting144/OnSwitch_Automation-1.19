package com.example.a310287808.onswitch_automation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 310287808 on 7/18/2017.
 */

public class SWVersion {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "config";
    //public String HueBridgeIndLightType = "lights";
    public String finalURL;
    public String SWversion;

    public String getSWVersion() throws IOException, JSONException {
        finalURL= "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterType;
        URL url1 = new URL(finalURL);
        HttpURLConnection connection;
        connection = (HttpURLConnection) url1.openConnection();
        connection.connect();

        InputStream stream1 = connection.getInputStream();

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));

        StringBuffer br1 = new StringBuffer();

        String line1 = " ";
        String change = null;
        while ((line1 = reader1.readLine()) != null) {
            //change = line1.replace("[", "").replace("]", "");
            br1.append(line1);
        }
        String output1 = br1.toString();

        JSONObject SWObject = new JSONObject(output1);
        Object SWObjectValue = SWObject.get("swversion");
        SWversion = SWObjectValue.toString();
        return SWversion;
    }
}

