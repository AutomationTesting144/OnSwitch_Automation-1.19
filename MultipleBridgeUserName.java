package com.example.a310287808.onswitch_automation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Date;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 7/27/2017.
 */

public class MultipleBridgeUserName {
    public String whiteListString;
    public String whiteListStringSecond;
    public String [] parts ;
    public int counter=0;
    public String WLname;
    public String WLnameSecond;
    public String url;
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;
    public boolean ob3;

    public void MultipleBridgeUserName(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws JSONException, IOException, InterruptedException {
        driver.navigate().back();
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
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String DateToStr = format.format(curDate);

        JSONObject jsonObject = new JSONObject(output);
        Object whitelistObject = jsonObject.get("whitelist");
        whiteListString = whitelistObject.toString();

        parts = whiteListString.split("}");
        for(int i =0;i<parts.length;i++){
            if(parts[i].contains("OnSwitch")){
                counter++;
                WLname = parts[i].substring(2,42);URL url1 = new URL("http://192.168.86.21/api/6ziFpmJs-YJYyUVbN9sbe0FzRujC8AoUJ0NL2D-A/config/whitelist/"+WLname);
                System.out.println("WhiteList name: "+WLname);
            }
        }

        URL urlSecond = new URL("http://192.168.86.23/api/YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4/config");
        connection = (HttpURLConnection) urlSecond.openConnection();
        connection.connect();
        InputStream streamSecond = connection.getInputStream();
        BufferedReader readerSecond = new BufferedReader(new InputStreamReader(streamSecond));
        StringBuffer brSecond = new StringBuffer();
        String lineSecond = " ";
        while ((lineSecond = readerSecond.readLine()) != null) {
            brSecond.append(lineSecond);
        }
        String outputSecond = brSecond.toString();

        JSONObject jsonObjectSecond = new JSONObject(outputSecond);
        Object whitelistObjectSecond = jsonObjectSecond.get("whitelist");
        whiteListStringSecond = whitelistObjectSecond.toString();

        parts = whiteListStringSecond.split("}");
        for(int i =0;i<parts.length;i++){
            if(parts[i].contains("OnSwitch")){
                counter++;
                WLnameSecond = parts[i].substring(2,42);
            }
        }
        System.out.println("WhiteList Second name: "+WLnameSecond);
        driver.navigate().back();

        //System.out.print("First WL null or not :"+WLname!="NULL" +"\n");
        //System.out.print("Second WL null or not :" +WLnameSecond!= "NULL" +"\n");

        Boolean Final= WLname!="NULL" && WLnameSecond!= "NULL";
//        System.out.println(Final);
//        //System.out.print("Final result is null or not :"+Final);
//        System.out.println("Is she mad?:"+Final);
//        //System.exit(0);


        if(Final==true) {
            Status = "1";
            ActualResult = "Different User names are created on different bridges: \n 1. Bridge IP: 192.168.86.21 and User Name: "+WLname +"\n 2. Bridge IP: 192.168.86.23 and User Name: "+WLnameSecond;
            Comments = "NA";
            ExpectedResult = "Different username should be created for different bridges for the same application";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

        } else {
            Status = "0";
            ActualResult = "Different User names are not created on different bridges";
            Comments = "Fail: 1. Bridge IP: 192.168.86.21 and User Name: "+WLname +"\n2. Bridge IP: 192.168.86.23 and User Name:"+WLnameSecond;
            ExpectedResult = "Different username should be created for different bridges for the same application";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }


        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
    }
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel(String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult
            ,String resultAPIVersion, String resultSWVersion) throws IOException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber=workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell((short)0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell((short)1);
        r2c2.setCellValue("9");

        HSSFCell r2c3 = row2.createCell((short)2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell((short)3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell((short)4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell((short)5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell((short)6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =
                new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();


    }
}
