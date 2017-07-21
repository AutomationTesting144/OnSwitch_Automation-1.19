package com.example.a310287808.onswitch_automation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 310287808 on 7/19/2017.
 */

public class LightIdsFromGroup0 {
    public String lightsIDs;

    public String LightIdsFromGroup0(String input) throws JSONException {

        JSONObject jsonObject = new JSONObject(input);
        Object ob2 = jsonObject.get("lights");
        lightsIDs = ob2.toString();
        //       System.out.println(lightsIDs);

        return lightsIDs;

    }
}
