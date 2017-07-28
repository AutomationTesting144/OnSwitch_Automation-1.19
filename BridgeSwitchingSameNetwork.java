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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 7/27/2017.
 */

public class BridgeSwitchingSameNetwork {
    public String IPAddress1 = "192.168.86.21/api";
    public String HueUserName1 = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String IPAddress2 = "192.168.86.23/api";
    public String HueUserName2 = "YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4";
    public String HueBridgeParameterType = "groups/2";
    public String finalURL;
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;
    Dimension size;


    public void BridgeSwitchingSameNetwork(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();
        //Checking whether the group light is ON/OFF for First bridge
        HttpURLConnection connection;
        finalURL = "http://" + IPAddress1 + "/" + HueUserName1 + "/" + HueBridgeParameterType;
        URL url = new URL(finalURL);
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer br = new StringBuffer();
        String line = " ";
        while ((line = reader.readLine()) != null) {
            br.append(line);
        }

        String output1 = br.toString();
        JSONObject jsonObject = new JSONObject(output1);

        Object ob = jsonObject.get("state");
        String newString = ob.toString();
        JSONObject jsonObject1 = new JSONObject(newString);
        Object ob1 = jsonObject1.get("all_on");

        //If the lights in the group are already ON then turn them off
        if (ob1.toString()=="true")
        {
            URL url1 = new URL("http://192.168.86.21/api/FgwTGpJneMTWtudw0G1VMBPKXbLZCk5Q8Trwuved/groups/2/action");
            String content = "{"+"\"on\""+":"+"false"+"}";
            HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(content);
            out.close();
            httpCon.getInputStream();
            System.out.println(httpCon.getResponseCode());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Lights are switched off");
            TimeUnit.SECONDS.sleep(5);

        }

        //Checking whether the group light is ON/OFF for Second bridge

        finalURL = "http://" + IPAddress2 + "/" + HueUserName2 + "/" + HueBridgeParameterType;
        URL url2 = new URL(finalURL);
        connection = (HttpURLConnection) url2.openConnection();
        connection.connect();
        InputStream stream2 = connection.getInputStream();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(stream2));
        StringBuffer br2 = new StringBuffer();
        String line2 = " ";
        while ((line2 = reader2.readLine()) != null) {
            br2.append(line2);
        }

        String output2 = br2.toString();
        JSONObject jsonObject2 = new JSONObject(output2);

        Object ob2 = jsonObject2.get("state");
        String newString2 = ob2.toString();
        JSONObject jsonObject3 = new JSONObject(newString2);
        Object ob3 = jsonObject3.get("all_on");

        //If the lights in the group are already ON then turn them off
        if (ob3.toString()=="true")
        {
            URL url1 = new URL("http://192.168.86.23/api/YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4/groups/2/action");
            String content = "{"+"\"on\""+":"+"false"+"}";
            HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(content);
            out.close();
            httpCon.getInputStream();
            System.out.println(httpCon.getResponseCode());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Lights are switched off");
            TimeUnit.SECONDS.sleep(5);

        }
        //Opening OnSwitch App
        System.out.println("Clicking application");
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(15);

        //Go to the Info tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='INFO']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on Find Bridge button
        driver.findElement(By.id("com.getonswitch.onswitch:id/hueFindBridgeButton")).click();
        TimeUnit.SECONDS.sleep(15);

        //Choosing IP 1
        driver.findElement(By.xpath("//android.widget.TextView[@text='192.168.86.21']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Go to the Group tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on the toggle switch for bedroom to turn it ON
        driver.findElement(By.xpath("//android.widget.Button[@bounds='[1039,466][1199,562]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Going back from the application
        driver.navigate().back();
        driver.navigate().back();

        //getting the status of  group from API
        finalURL = "http://" + IPAddress1 + "/" + HueUserName1 + "/" + HueBridgeParameterType;
        URL urlstatus = new URL(finalURL);
        connection = (HttpURLConnection) urlstatus.openConnection();
        connection.connect();
        InputStream streamStatus = connection.getInputStream();
        BufferedReader readerStatus = new BufferedReader(new InputStreamReader(streamStatus));
        StringBuffer brStatus = new StringBuffer();
        String lineStatus = " ";
        while ((lineStatus = readerStatus.readLine()) != null) {
            brStatus.append(lineStatus);
        }

        String outputStatus = brStatus.toString();
        JSONObject jsonObjectStatus = new JSONObject(outputStatus);
        Object RoomName =  jsonObjectStatus.get("name");
        String FirstRoom= RoomName.toString();
        System.out.println("First Room is: "+FirstRoom);
        Object obStatus = jsonObjectStatus.get("state");
        String newStringStatus = obStatus.toString();
        JSONObject jsonObject1Status = new JSONObject(newStringStatus);
        Object ob1Status = jsonObject1Status.get("all_on");
        String Network1=ob1Status.toString();
        System.out.println("Status of first bridge all on: "+Network1);

        //Switching to 2nd network

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
        TimeUnit.SECONDS.sleep(15);

        //Go to the Info tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='INFO']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on Find Bridge button
        driver.findElement(By.id("com.getonswitch.onswitch:id/hueFindBridgeButton")).click();
        TimeUnit.SECONDS.sleep(15);

        //Choosing IP 1
        driver.findElement(By.xpath("//android.widget.TextView[@text='192.168.86.23']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Go to the Group tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on the toggle switch for bedroom to turn it ON
        driver.findElement(By.xpath("//android.widget.Button[@bounds='[1039,466][1199,562]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Going back from the application
        driver.navigate().back();
        driver.navigate().back();

        //getting the status of  group from API
        finalURL = "http://" + IPAddress2 + "/" + HueUserName2 + "/" + HueBridgeParameterType;
        URL urlstatus1 = new URL(finalURL);
        connection = (HttpURLConnection) urlstatus1.openConnection();
        connection.connect();
        InputStream streamStatus1 = connection.getInputStream();
        BufferedReader readerStatus1 = new BufferedReader(new InputStreamReader(streamStatus1));
        StringBuffer brStatus1 = new StringBuffer();
        String lineStatus1 = " ";
        while ((lineStatus1 = readerStatus1.readLine()) != null) {
            brStatus1.append(lineStatus1);
        }

        String outputStatus1 = brStatus1.toString();
        JSONObject jsonObjectStatus1 = new JSONObject(outputStatus1);
        Object RoomName1 =  jsonObjectStatus1.get("name");
        String SecondRoom= RoomName1.toString();
        System.out.println("Room Name of second bridge: "+SecondRoom);
        Object obStatus1 = jsonObjectStatus1.get("state");
        String newStringStatus1 = obStatus1.toString();
        JSONObject jsonObject1Status1 = new JSONObject(newStringStatus1);
        Object ob1Status1 = jsonObject1Status1.get("all_on");
        String Network2=ob1Status1.toString();
        System.out.println("Status of second bridge group: "+Network2);

        Boolean FinalResult= Network1=="true" && Network2 =="true";
        System.out.println("Final Result: "+FinalResult);



        if(FinalResult.equals(true))
        {
            Status = "1";
            ActualResult = "Application is able to switch between 2 bridges and control their rooms";
            Comments = "NA";
            ExpectedResult = "Application should be able to switch between 2 bridges and control their rooms";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }
        else{
            Status = "0";
            ActualResult = "Application is not able to switch between 2 bridges and control their rooms";
            Comments = "FAIL: Group Status of " + RoomName + " is : " + outputStatus.toString();
            ExpectedResult = "Application should be able to switch between 2 bridges and control their rooms";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }


        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
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
        r2c2.setCellValue("8");

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
