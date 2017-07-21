package com.example.a310287808.onswitch_automation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;

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
 * Created by 310287808 on 7/19/2017.
 */

public class GroupBrightness {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "groups/2";
    public String finalURL;
    public String lightStatusReturned;
    public String AllLightIDs;
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;

    public void GroupBrightness(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();
        //Opening OnSwitch App
        System.out.println("Clicking application");
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(10);

        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on the toggle switch for bedroom to turn it ON
        driver.findElement(By.xpath("//android.widget.Button[@bounds='[1039,466][1199,562]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on the bedroom
        driver.findElement(By.xpath("//android.widget.TextView[@text='Bedroom']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Choosing scene
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[99,999][301,1332]']")).click();
        TimeUnit.SECONDS.sleep(2);
        //Setting brightness for Bedroom
        driver.findElement(By.xpath("//android.widget.SeekBar[@bounds='[181,359][1080,395]']")).click();
        TimeUnit.SECONDS.sleep(10);

        //Going back from the application
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        //getting the status of  group from API
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
        String output = br.toString();

        // getting the brightness status from other class
        DimmingLightStatus DimStatus=new DimmingLightStatus();
        lightStatusReturned = DimStatus.DimmingLightStatus(output);

        StringBuffer sb = new StringBuffer();
        Boolean result= lightStatusReturned.equals("254");

        if (result==true) {
            Status = "0";
            ActualResult ="Brightness is not reduced for the group";
            Comments = "FAIL:Brightness is not reduced for the group";
            ExpectedResult= "Brightness of the group should be reduced";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);


        } else {
            Status = "1";
            ActualResult ="Brightness is reduced for the group";
            Comments = "N/A";
            ExpectedResult= "Brightness of the group should be reduced";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);

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
        r2c2.setCellValue("14");

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
