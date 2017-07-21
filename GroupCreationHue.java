package com.example.a310287808.onswitch_automation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 7/19/2017.
 */

public class GroupCreationHue {
    public int lightCounter=0;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    public List RoomList;
    public MobileElement RoomlistItem1;
    public String RoomName;

    public void GroupCreationHue (AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException  {

        driver.navigate().back();
        //Opening Hue applictaion
        driver.findElement(By.xpath("//android.widget.TextView[@bounds='[24,1380][216,1572]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on settings button
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1026,184][1074,232]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Selecting Room setup
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,408][152,536]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on plus button to add room
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[1048,1672][1160,1784]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Write the name for the new room
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).click();
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).clear();
        TimeUnit.SECONDS.sleep(2);
        //Entering new name for room
        Random rand = new Random();
        int  n = rand.nextInt() + 1;
        RoomName = "Room"+String.valueOf(n);
        System.out.println(RoomName);

        //Entering the new name in the text field
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).sendKeys(RoomName);
        TimeUnit.SECONDS.sleep(5);
        driver.hideKeyboard();

        //Click on saving
        driver.findElement(By.id("com.philips.lighting.hue2:id/save")).click();
        TimeUnit.SECONDS.sleep(5);

        //Going back from the application
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
        driver.navigate().back();
        TimeUnit.SECONDS.sleep(5);

        Runtime.getRuntime().exec("taskkill /F /FI \"WindowTitle eq OnSwitch\" /T");
       // driver.pressKeyCode(187);


        //Opening OnSwitch App
        System.out.println("Clicking application");
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(10);


        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //getting the list of rooms available in the application and inserting it in Hashmap
        HashMap<Object, Integer> Rooms = new HashMap<>();

        RoomList = driver.findElements(By.id("com.getonswitch.onswitch:id/groupName"));

        for(int i=1; i< RoomList.size()-1; i++)
        {
            RoomlistItem1 = (MobileElement) RoomList.get(i);
            Rooms.put(RoomlistItem1.getText(),i);
            Boolean result = RoomlistItem1.getText().equals(RoomName);
            if (result == true) {
                lightCounter++;

            } else {
                continue;
            }
        }
        if (lightCounter == 0)
        {

            Status = "0";
            ActualResult = "New Room: " + RoomName + " is not created in OnSwitch";
            Comments = "FAIL: No Room is created";
            ExpectedResult = "New Room: " + RoomName + " should be created in OnSwitch";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

        }
        else
        {
            Status = "1";
            ActualResult = "New Room: " + RoomName + " is created in OnSwitch";
            Comments = "NA";
            ExpectedResult = "New Room: " + RoomName + " should be created in OnSwitch";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

        }

        driver.navigate().back();
        driver.navigate().back();
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
    }

    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel(String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult
            ,String resultAPIVersion, String resultSWVersion) throws IOException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS aa");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber=workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell((short) 0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell((short) 1);
        r2c2.setCellValue("15");

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
        FileOutputStream out =
                new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();

    }
}
