package tests;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FailureListener extends TestListenerAdapter {

    @Attachment(value = "{0}", type = "image/png")
    public byte[] saveScreen(String name) {
        // скриншот возвращается в виде массива байтов и привязывается к отчету
        return ((TakesScreenshot) TestSettings.getDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        Date dat = new Date();
        DateFormat formatForDateNow = new SimpleDateFormat("yyyy-mm-dd hh.mm.ss");
        saveScreen("ERROR " + formatForDateNow.format(dat));
    }
}
