package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private static WebDriver driver;

    public LoginPage(WebDriver dr) {
        driver = dr;
    }

    @Step("Ввод логина")
    public void enteringLogin(String log) {
        WebElement loginForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".passp-login-form"))));
        WebElement field = loginForm.findElement(By.cssSelector("[id = 'passp-field-login']"));
        field.sendKeys(log);
        loginForm.findElement(By.cssSelector("[class *= 'sign-in-button']")).click();
    }

    @Step("Ввод пароля")
    public void enteringPassword(String passw) {
        WebElement passwordForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.cssSelector("[class *= 'passp-welcome-page']"))));
        WebElement field = passwordForm.findElement(By.cssSelector("[id = 'passp-field-passwd']"));
        field.sendKeys(passw);
        passwordForm.findElement(By.cssSelector("[class *= 'control button2']")).click();
    }
}
