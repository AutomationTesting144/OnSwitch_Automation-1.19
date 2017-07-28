package com.example.a310287808.onswitch_automation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 7/27/2017.
 */

public class DeleteLightSceneHandling {
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "lights/31";
    public String HueBridgeParameterTypeGroup = "groups/2";
    public String lightStatusReturned;
    public String finalURL;
    Dimension size;

    public void DeleteLightSceneHandling(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();
        HttpURLConnection connection;

        //Checking whether the group light is ON/OFF
//        finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterTypeGroup;
//        URL url = new URL(finalURL);
//        connection = (HttpURLConnection) url.openConnection();
//        connection.connect();
//        InputStream stream = connection.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        StringBuffer br = new StringBuffer();
//        String line = " ";
//        while ((line = reader.readLine()) != null) {
//            br.append(line);
//        }
//
//        String output1 = br.toString();
//        JSONObject jsonObject = new JSONObject(output1);
//
//        Object ob = jsonObject.get("state");
//        String newString = ob.toString();
//        JSONObject jsonObject1 = new JSONObject(newString);
//        Object ob1 = jsonObject1.get("all_on");
//
//        //If the lights in the group are already ON then turn them off
//        if (ob1.toString()=="true")
//        {
//            URL url1 = new URL("http://192.168.86.21/api/FgwTGpJneMTWtudw0G1VMBPKXbLZCk5Q8Trwuved/groups/2/action");
//            String content = "{"+"\"on\""+":"+"false"+"}";
//            HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
//            httpCon.setDoOutput(true);
//            httpCon.setRequestMethod("PUT");
//            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
//            out.write(content);
//            out.close();
//            httpCon.getInputStream();
//            System.out.println(httpCon.getResponseCode());
//            TimeUnit.SECONDS.sleep(5);
//            System.out.println("Lights are switched off");
//            TimeUnit.SECONDS.sleep(5);
//
//        }
        //Opening OnSwitch App
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(10);

        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //clicking on Edit button
        driver.findElement(By.id("com.getonswitch.onswitch:id/action_edit")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on group to add light
        driver.findElement(By.xpath("//*[@text='Edit' and ./following-sibling::*[@text='Bedroom']]")).click();
        TimeUnit.SECONDS.sleep(5);

        //Selecting the Light to delete in group
        driver.findElement(By.xpath("//android.widget.TextView[@text='Hue color lamp 57']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Saving the group name
        driver.findElement(By.id("com.getonswitch.onswitch:id/action_save")).click();
        TimeUnit.SECONDS.sleep(5);

        //going back from the application
        driver.navigate().back();
        driver.navigate().back();
        Runtime.getRuntime().exec("taskkill /F /FI \"WindowTitle eq OnSwitch\" /T");
        driver.pressKeyCode(187);
        TimeUnit.SECONDS.sleep(2);

        //Get the size of screen.
        size = driver.manage().window().getSize();

        //Find swipe start and end point from screen's with and height.
        //Find startx point which is at right side of screen.
        int startx = (int) (size.width * 0.70);
        //Find endx point which is at left side of screen.
        int endx = (int) (size.width * 0.30);
        //Find vertical point where you wants to swipe. It is in middle of screen height.
        int starty = size.height / 2;

        //Swipe from Right to Left.
        driver.swipe(startx, starty, endx, starty, 3000);
        Thread.sleep(2000);

        driver.swipe(startx, starty, endx, starty, 3000);
        Thread.sleep(2000);

        driver.swipe(startx, starty, endx, starty, 3000);
        Thread.sleep(2000);

        //Opening OnSwitch App
        System.out.println("Clicking application");
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(10);

        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on the bedroom
        driver.findElement(By.xpath("//android.widget.TextView[@text='Bedroom']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on Albums
        driver.findElement(By.xpath("//android.widget.TextView[@text='ALBUMS']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Swiping for different scenes
        size = driver.manage().window().getSize();

        //Find swipe start and end point from screen's with and height.
        //Find starty point which is at bottom side of screen.
        int starty1 = (int) (size.height * 0.80);
        //Find endy point which is at top side of screen.
        int endy = (int) (size.height * 0.20);
        //Find horizontal point where you wants to swipe. It is in middle of screen width.
        int startx1 = size.width / 2;

        //Swipe from Bottom to Top.
        driver.swipe(startx, starty1, startx1, endy, 3000);
        driver.swipe(startx, starty1, startx1, endy, 3000);
        Thread.sleep(2000);

        //Choosing the scene for the group
        driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Scene Album']")).click();

        //Choosing Green color
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[99,999][301,1332]']")).click();

        //Going back from the application
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();


        //getting the status of  group from API

        finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterType;
        URL url1 = new URL(finalURL);
        connection = (HttpURLConnection) url1.openConnection();
        connection.connect();

        InputStream stream1 = connection.getInputStream();

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));

        StringBuffer br1 = new StringBuffer();

        String line1 = " ";
        while ((line1 = reader1.readLine()) != null) {
            br1.append(line1);
        }
        String output = br1.toString();

        JSONObject jsonObjectLight = new JSONObject(output);
        //System.out.println(jsonObject.toString());

        Object obLight =  jsonObjectLight.get("name");
        String newStringLight = obLight.toString();

        ColorChangeSingleStatus SingleStatus = new ColorChangeSingleStatus();
        lightStatusReturned = SingleStatus.ColorChangeSingleStatus(output);

        String Xval=lightStatusReturned.substring(1,5);
        String Yval=lightStatusReturned.substring(8,12);
        System.out.println(Xval);
        System.out.println(Yval);
        String Xgreen="0.42";
        String Ygreen="0.50";

        boolean finalResult=(Xval.equals(Xgreen)) && (Yval.equals(Ygreen));
        if (finalResult==false){
            Status = "1";
            ActualResult = "light: "+newStringLight+ " deleted from the group and scene is not applied";
            Comments = "NA";
            ExpectedResult=  "light: "+newStringLight+ " should be deleted from the group and scene should not be applied";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }else
        {
            Status = "0";
            ActualResult = "light: "+newStringLight+ " is not deleted from the group and scene is applied";
            Comments = "FAIL: Light "+newStringLight+ " is failed to be deleted from the group";
            ExpectedResult=  "light: "+newStringLight+ " should be deleted from the group and scene should not be applied";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }


        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult, APIVersion, SWVersion);

    }
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel (String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult, String resultAPIVersion, String resultSWVersion) throws IOException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber = workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell((short) 0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell((short) 1);
        r2c2.setCellValue("28");

        HSSFCell r2c3 = row2.createCell((short) 2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell((short) 3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell((short) 4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell((short) 5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell((short) 6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();

    }

}
