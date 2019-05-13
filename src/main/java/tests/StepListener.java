package tests;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Attachment;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.Augmenter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StepListener implements StepLifecycleListener {

    private String createScreen() {
        // Скрин записывается во временный файл в памяти
        File screenshot = ((TakesScreenshot) TestSettings.getDriver())
                .getScreenshotAs(OutputType.FILE);

        DateFormat formatForDateNow = new SimpleDateFormat("yyyy-mm-dd hh.mm.ss");
        String fileName = formatForDateNow.format(new Date());
        try {
            BufferedImage img = ImageIO.read(screenshot);
            File to = new File(TestSettings.getSaveScreenFolder()
                    + "\\" + fileName  + ".png");
            ImageIO.write(img, "png", to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public void beforeStepStart(final StepResult result) {
        Attachment att = new Attachment();
        att.setType("image/png");
        att.setSource(TestSettings.getSaveScreenFolder() + "\\" + createScreen() + ".png");
        result.withAttachments(att);
    }

    @Override
    public void beforeStepStop(final StepResult result) {
        Attachment att = new Attachment();
        att.setType("image/png");
        att.setSource(TestSettings.getSaveScreenFolder() + "\\" + createScreen() + ".png");
        result.withAttachments(att);
    }
}
