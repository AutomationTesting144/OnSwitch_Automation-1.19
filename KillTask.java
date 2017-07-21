package com.example.a310287808.onswitch_automation;

/**
 * Created by 310287808 on 7/20/2017.
 */

public class KillTask {


    public static void main (String args[])
    {
        try
        {
            Runtime.getRuntime().exec("taskkill /F /IM OnSwitch.exe");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
