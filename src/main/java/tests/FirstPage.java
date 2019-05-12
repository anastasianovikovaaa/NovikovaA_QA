package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.security.Key;

public class FirstPage {
    private static WebDriver driver;

    private WebElement getAccount() {
        return driver.findElement(By.cssSelector(".header2-nav__user"));
    }

    public FirstPage(WebDriver dr) {
        driver = dr;
    }

    @Step("Кликаем на \"Войти в аккаунт\"")
    public void clickOnAccount() {
        getAccount().click();
    }

    @Step("Убедиться, что кнопка \"Войти в аккаунт\" сменилась на \"Мой профиль\"")
    public void myProfile() {
        Assert.assertEquals(getAccount().getAttribute("textContent"), "Мой профиль");
    }

    @Step("После авторизации проверить, что на главной странице отображается логин")
    public void checkLogin() {
        getAccount().click();
        Assert.assertEquals(driver.findElement(By
                .cssSelector("[class = 'header2-user-menu__email']"))
                .getAttribute("textContent"), "peter.parkerrr@yandex.ru");
    }

    @Step("Находясь на главной странице beru.ru, сменить город с Саратова на Хвалынск")
    public void changeCity(String city) {
        driver.findElement(By.cssSelector(".unique-selling-proposition-line__region [class = 'link__inner']")).click();
        WebElement cityForm = driver.findElement(By.cssSelector("[class = 'header2-region-popup']"));
        WebElement newCity = cityForm.findElement(By
                .cssSelector("[class*='region-suggest'] [class*='input__control']"));
        newCity.click();
        newCity.sendKeys(city);
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.visibilityOf(newCity.findElement(By
                        .xpath("//strong[text() = '" + city + "']"))));
        newCity.sendKeys(Keys.ENTER);

        (new Actions(driver)).moveToElement(cityForm.findElement(By
                .cssSelector("[class*='button2__text']"))).build().perform();

        cityForm.findElement(By
                .cssSelector("[class *= 'region-select-form__continue-with-new i-bem button2']")).click();
        driver.navigate().refresh(); // перезагрузить страницу
    }

    @Step("Проверить, что значение города изменилось")
    public void checkCity(String city) {
        Assert.assertEquals(driver.findElement(By
                .cssSelector(".unique-selling-proposition-line__region [class = 'link__inner']"))
                .getAttribute("textContent"), city);
    }

    @Step("Перейти в 'Настройки'")
    public void pathToMyProfile() {
        getAccount().click();
        driver.findElement(By.xpath("//a[text()='Мои адреса доставки']/..")).click();
    }
}