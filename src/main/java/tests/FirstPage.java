package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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
                .cssSelector("[class='header2-user-menu__email']"))
                .getAttribute("textContent"), "peter.parkerrr@yandex.ru");
    }
}