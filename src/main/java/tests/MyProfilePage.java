package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class MyProfilePage {
    private static WebDriver driver;

    public MyProfilePage(WebDriver dr) {
        driver = dr;
    }

    @Step("Сравниваем значение города в верхнем углу и город доставки")
    public void comparisonOfCities(){
        WebElement topLeftCity = driver.findElement(By
                .cssSelector(".unique-selling-proposition-line__region [class = 'link__inner']"));
        WebElement bottomCity = driver.findElement(By
                .cssSelector("[class = 'settings-list__title'] [class = 'link__inner']"));
        Assert.assertEquals(topLeftCity.getAttribute("textContent"), bottomCity.getAttribute("textContent"));
    }
}
