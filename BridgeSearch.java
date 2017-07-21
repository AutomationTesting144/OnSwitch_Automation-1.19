package com.example.a310287808.onswitch_automation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;

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
 * Created by 310287808 on 7/18/2017.
 */

public class BridgeSearch {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "groups/0";
    public String finalURL;
    public String ActualResult;
    public String Comments;
    public String ExpectedResult;
    public String Status;
    public void BridgeSearch(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {
        //Checking the state of lights from API and turning them OFf if they are ON
        driver.navigate().back();
        //Opening OnSwitch App
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(2);
        //Clicking on Search button to look for new IPs
        System.out.println("About to search");
        driver.findElement(By.id("com.getonswitch.onswitch:id/searchAgainButton")).click();
        System.out.println("Clicked");
        TimeUnit.SECONDS.sleep(30);

        //Choosing the correct bridge
        driver.findElement(By.xpath("//android.widget.TextView[@text='192.168.86.21']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);
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

        //If the lights in the group are already ON then turn them OFF
        if (ob1.toString()=="true")
        {
            URL url1 = new URL("http://192.168.86.21/api/i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo/groups/0/action");
            String content = "{"+"\"on\""+":"+"false"+"}";
            HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(content);
            out.close();
            httpCon.getInputStream();
            System.out.println(httpCon.getResponseCode());

        }


        //Clicking on All Lights to check whether bridge is connected or not
        driver.findElement(By.xpath("//android.widget.Button[@bounds='[1039,304][1199,400]']")).click();
        TimeUnit.SECONDS.sleep(5);
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        //getting the status from API to make sure that lights are turned ON
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

        String output2 = br1.toString();
        JSONObject jsonObject2 = new JSONObject(output2);

        Object ob2 = jsonObject2.get("state");
        String newString1 = ob2.toString();
        JSONObject jsonObject3 = new JSONObject(newString1);
        Object ob3 = jsonObject3.get("all_on");


        if (ob3.toString().equals("true"))

        {
            Status = "1";
            ActualResult = "Application is connected to the bridge";
            Comments = "NA";
            ExpectedResult= "Application should be able to connect with the selected bridge";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        } else {
            Status = "0";
            ActualResult = "Application is not connected to the bridge";
            Comments = "FAIL: Application is not connected to the bridge";
            ExpectedResult= "Application should be able to connect with the selected bridge";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }
        //CALLING THE FUNCTION FOR WRITING THE CODE IN EXCEL FILE
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);

    }
    //WRITING THE RESULT IN EXCEL FILE
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel (String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult, String resultAPIVersion, String resultSWVersion) throws IOException
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS aa");
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
        r2c2.setCellValue("1");

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
