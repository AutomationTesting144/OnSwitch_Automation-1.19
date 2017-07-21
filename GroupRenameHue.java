package com.example.a310287808.onswitch_automation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

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

public class GroupRenameHue {
    public int lightCounter=0;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    Dimension size;
    public MobileElement RoomlistItem1;
    public String RoomName;
    public String DeletedRoom;

    public void GroupRenameHue (AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();
        //Opening Hue application
        driver.findElement(By.xpath("//android.widget.TextView[@bounds='[24,1380][216,1572]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on settings button
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1026,184][1074,232]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Selecting Room setup
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,408][152,536]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //getting the list of rooms available in the application
        driver.findElement(By.id("com.philips.lighting.hue2:id/hue_play_toolbar")).isDisplayed();

        List RoomList = driver.findElements(By.id("com.philips.lighting.hue2:id/list_item_title"));
        int Total = RoomList.size();
        int TotalRooms = Total - 1;
        DeletedRoom = ((MobileElement) RoomList.get(TotalRooms)).getText();


        int bound = 192 * TotalRooms;
        int bound1 = 196 + bound;
        int bound2 = 316 + bound;

        String FinalBound = "[1080," + bound1 + "][1200," + bound2 + "]";

        //Click on the i bubble in front of room to be deleted
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='" + FinalBound + "']")).click();
        TimeUnit.SECONDS.sleep(2);

        //getting the name of the room to be deleted
        String DeletedToBeRoom = driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).getText();
        System.out.println(DeletedRoom);

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

        //Going back from the application
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
        driver.navigate().back();
        TimeUnit.SECONDS.sleep(5);

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

            }
            else
            {
                continue;
            }
        }
        if (lightCounter == 0)
        {
            Status = "0";
            ActualResult = "Room: " +DeletedRoom+" is not renamed to : "+ RoomName ;
            Comments = "FAIL: Room is not renamed";
            ExpectedResult = "Room: " +DeletedRoom+" should be renamed to : "+ RoomName ;
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }
        else
        {
            Status = "1";
            ActualResult = "Room: " +DeletedRoom+" is renamed to : "+ RoomName ;
            Comments = "NA";
            ExpectedResult = "Room: " +DeletedRoom+" should be renamed to : "+ RoomName ;
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
        r2c2.setCellValue("16");

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


