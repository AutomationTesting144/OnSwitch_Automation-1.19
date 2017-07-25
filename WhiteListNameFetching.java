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
 * Created by 310287808 on 7/25/2017.
 */

public class WhiteListNameFetching {
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;

    public void WhiteListNameFetching(String fileName, String APIVersion, String SWVersion) throws JSONException, IOException {
        HttpURLConnection connection;
        URL url = new URL("http://192.168.86.21/api/FgwTGpJneMTWtudw0G1VMBPKXbLZCk5Q8Trwuved/config");
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer br = new StringBuffer();
        String line = " ";
        while ((line = reader.readLine()) != null) {
            br.append(line);
        }
        String output = br.toString();

        JSONObject jsonObject = new JSONObject(output);
        Object whitelistObject = jsonObject.get("whitelist");
        String whitelistString = whitelistObject.toString();

        if (whitelistString.contains("OnSwitch")) {
            Status = "1";
            ActualResult = "Bridge has User Friendly whitelist name for OnSwitch";
            Comments = "NA";
            ExpectedResult = "Bridge should have User Friendly whitelist name for OnSwitch";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

        } else {
            Status = "0";
            ActualResult = "Bridge does not have User Friendly whitelist name for OnSwitch";
            Comments = "FAIL: User name is not friendly";
            ExpectedResult = "Bridge should have User Friendly whitelist name for OnSwitch";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

        }
    }
}
