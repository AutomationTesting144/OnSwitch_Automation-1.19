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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 7/18/2017.
 */

public class GroupVerification {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "i5ZxyqYoq6dmpjFXwKxw3ovCWLvF9arQdcBx8oLo";
    public String HueBridgeParameterType = "groups";
    public List RoomList;
    public MobileElement RoomlistItem1;
    public String finalURLIndLight;
    public String Status;
    public String Comments;
    public int lightCounter=0;
    public String ActualResult;
    public String ExpectedResult;

    public void GroupVerification(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();
        //Opening OnSwitch App
        driver.findElement(By.xpath("//android.widget.TextView[@text='OnSwitch']")).click();
        TimeUnit.SECONDS.sleep(10);

        //Go to the groups tab.
        driver.findElement(By.xpath("//android.widget.TextView[@text='GROUPS']")).click();
        TimeUnit.SECONDS.sleep(5);

        //getting the list of rooms available in the application and inserting it in Hashmap
     //   driver.findElement(By.xpath("//android.widget.LinearLayout[]@bounds='[0,272][1200,432]'")).isDisplayed();
        HashMap<Object, Integer> Rooms = new HashMap<>();

        RoomList = driver.findElements(By.id("com.getonswitch.onswitch:id/groupName"));

        for(int i=1; i< RoomList.size()-1; i++){
            RoomlistItem1 = (MobileElement) RoomList.get(i);
            Rooms.put(RoomlistItem1.getText(),i);

        }
        int Total=(RoomList.size())-2;

        HashMap<String,Integer> RoomIDs = new HashMap<>();
        RoomIDs.put("1",1);
        RoomIDs.put("2",2);



            for(Map.Entry<String,Integer> Room : RoomIDs.entrySet()){
                HttpURLConnection connection;


                finalURLIndLight = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterType
                        +"/"+Room.getKey();


                URL url1 = new URL(finalURLIndLight);
                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();

                InputStream stream1 = connection.getInputStream();

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));

                StringBuffer br1 = new StringBuffer();

                String line1 = " ";
                String change = null;
                while ((line1 = reader1.readLine()) != null) {
                                    br1.append(line1);
                }
                String output1 = br1.toString();
                JSONObject jsonObject = new JSONObject(output1);
                Object RoomName =  jsonObject.get("name");
                String newString = RoomName.toString();

                for( Map.Entry<Object, Integer> RoomNameInApp : Rooms.entrySet()) {
                    Boolean result = RoomNameInApp.getKey().toString().equals(RoomlistItem1.getText());
                    if (result==true) {
                        lightCounter++;
                    } else {
                        continue;
                    }
                }

            }

        if(Total==lightCounter){
            Status = "1";
            ActualResult = "Same rooms are available in Hue applications and OnSwitch";
            Comments = "NA";
            ExpectedResult = "Same rooms should be available in Hue applications and OnSwitch";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }
        else{
            Status = "0";
            ActualResult = "Same rooms are  not available in Hue applications and OnSwitch";
            Comments = "Fail: Same rooms are not available";
            ExpectedResult = "Same rooms should be available in Hue applications and OnSwitch";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }
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
        r2c2.setCellValue("11");

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
