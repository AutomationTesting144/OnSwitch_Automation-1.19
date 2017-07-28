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
 * Created by 310287808 on 7/26/2017.
 */

public class ScenesOnMultipleRooms {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "groups/2";
    public String HueBridgeParameterTypeLivingrRoom= "groups/1";
    public String finalURL;
    public String lightStatusReturned;
    public String lightStatusReturnedGreen;
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;
    Dimension size;

    public void ScenesOnMultipleRooms(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();

        //Checking whether the group light is ON/OFF
        HttpURLConnection connection;
        finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterType;
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
        if (ob1.toString()=="false")
        {
            URL url1 = new URL("http://192.168.86.21/api/FgwTGpJneMTWtudw0G1VMBPKXbLZCk5Q8Trwuved/groups/2/action");
            String content = "{"+"\"on\""+":"+"true"+"}";
            HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(content);
            out.close();
            httpCon.getInputStream();
            System.out.println(httpCon.getResponseCode());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Lights are switched on");
            TimeUnit.SECONDS.sleep(5);


        }
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
        int starty = (int) (size.height * 0.80);
        //Find endy point which is at top side of screen.
        int endy = (int) (size.height * 0.20);
        //Find horizontal point where you wants to swipe. It is in middle of screen width.
        int startx = size.width / 2;

        //Swipe from Bottom to Top.
        driver.swipe(startx, starty, startx, endy, 3000);
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);

        //Choosing the scene for the group
        driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Scene Album']")).click();

        //Choosing RED color
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[99,586][301,919]']")).click();
        TimeUnit.SECONDS.sleep(10);

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

        JSONObject jsonObjectBedroom = new JSONObject(output);

        Object obBedroom = jsonObjectBedroom.get("name");
        String newStringBedroom = obBedroom.toString();
        System.out.println("Name1: "+newStringBedroom);

        ColorChangeAllStatus SingleStatus = new ColorChangeAllStatus();
        lightStatusReturned = SingleStatus.ColorChangeAllStatus(output);
        System.out.println("output for red:"+lightStatusReturned);

        String Xval=lightStatusReturned.substring(1,5);
        String Yval=lightStatusReturned.substring(7,11);
        System.out.println(Xval);
        System.out.println(Yval);
        String Xred="0.67";
        String Yred="0.32";

        boolean BedRoom=(Xval.equals(Xred)) && (Yval.equals(Yred));

// *********************switching to Living room for applying scenes**************

        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on the bedroom
        driver.findElement(By.xpath("//android.widget.TextView[@text='Living room']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Choosing Green color
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[99,999][301,1332]']")).click();
        TimeUnit.SECONDS.sleep(10);

        //Fetching results from API

        finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterTypeLivingrRoom;
        URL urlLiving = new URL(finalURL);
        connection = (HttpURLConnection) urlLiving.openConnection();
        connection.connect();

        InputStream streamLiving = connection.getInputStream();

        BufferedReader readerLiving = new BufferedReader(new InputStreamReader(streamLiving));

        br = new StringBuffer();

        String lineLiving = " ";
        while ((lineLiving = readerLiving.readLine()) != null) {
            br.append(lineLiving);
        }
        String outputLiving = br.toString();
        JSONObject jsonObjectLiving = new JSONObject(outputLiving);

        Object obLiving = jsonObjectLiving.get("name");
        String newStringLiving = obLiving.toString();
        System.out.println("Name2: "+newStringLiving);

        ColorChangeAllStatus SingleStatusLiving = new ColorChangeAllStatus();
        lightStatusReturnedGreen = SingleStatusLiving.ColorChangeAllStatus(outputLiving);
        System.out.println("output for green:"+lightStatusReturnedGreen);
        String XvalGreen=lightStatusReturnedGreen.substring(1,5);
        String Yvalgreen=lightStatusReturnedGreen.substring(8,12);
        System.out.println(XvalGreen);
        System.out.println(Yvalgreen);
        String Xgreen="0.42";
        String Ygreen="0.50";
        boolean LivingRoom=(XvalGreen.equals(Xgreen)) && (Yvalgreen.equals(Ygreen));
        Boolean FinalResult= BedRoom==true && LivingRoom==true;
        if (FinalResult==true){
            Status = "1";
            ActualResult = "New scenes are applied on following rooms: "+newStringBedroom+ " and" +newStringLiving;
            Comments = "NA";
            ExpectedResult= "New scene should be applied on the room";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }else
        {
            Status = "0";
            ActualResult ="Scene is not applied for the rooms: "+newStringBedroom+ " and" +newStringLiving;
            Comments = "FAIL:Application is failed to apply scenes";
            ExpectedResult= "New scene should be applied on the room";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }


        //Going back from the application
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

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
        r2c2.setCellValue("26");

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
