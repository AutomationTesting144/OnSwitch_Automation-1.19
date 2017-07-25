package com.example.a310287808.onswitch_automation;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 7/25/2017.
 */

public class WhiteListDeletion {

    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "groups/2";
    public String finalURL;
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;

    public void WhiteListDeletion(AndroidDriver driver) throws IOException, JSONException, InterruptedException {

            driver.navigate().back();

            URL url1 = new URL("http://192.168.86.21/api/6ziFpmJs-YJYyUVbN9sbe0FzRujC8AoUJ0NL2D-A/config/whitelist/i357MlUbdqtsWcE-uMKaHQadbATPwlEaNAnnIn75");
         //   String content = "{" + "\"on\"" + ":" + "false" + "}";
            HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("DELETE");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            System.out.println(httpCon.getResponseCode());
            System.out.println(httpCon.getResponseMessage());
            out.close();



    }
}
