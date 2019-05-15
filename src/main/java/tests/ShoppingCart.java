package tests;

import io.qameta.allure.Step;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingCart {
    public static WebDriver driver;
    private List<WebElement> priceList;

    public ShoppingCart(WebDriver dr) {
        driver = dr;
    }

    private int stringToInteger(String str) {
        // СОздаём шаблон регулярного выражения (всё, кроме цифр)
        Pattern pat = Pattern.compile("[^\\d]");
        // Создаём объект матчера
        Matcher matcher = pat.matcher(str);
        // заменяем всё, что не цифры, на пустую строку и переводим в int
        return Integer.parseInt(matcher.replaceAll(""));
    }

    @Step("Проверка корректности цены")
    public void priceCheck() {
        // получаем в массив все 3 (4) суммы которые определяют цену
        // 0 - настоящая цена, 1 - доставка, 2 -скидка (если есть), 3 - итоговая цена
        priceList = driver.findElements(By.cssSelector("[class *= '_1Q9ASvPbPN']"));

        String tempText = priceList.get(0).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent");

        int startingPrice = stringToInteger(tempText);

        tempText = priceList.get(1).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent");

        int deliveryPrice = 0;
        if (!tempText.contains("бесплатно")) {
            deliveryPrice = stringToInteger(tempText);
        }

        int discount = 0;
        int index = 2;
        if (priceList.size() == 4) {
            tempText = priceList.get(2).findElement(
                    By.xpath("//span[text()[contains(., 'Скидка')]]/following-sibling::span"))
                    .getAttribute("textContent");
            discount = stringToInteger(tempText);
            index = 3;
        }
        // Получаем суммарную цену
        tempText = priceList.get(index).findElement(
                By.cssSelector("[class*='_1oBlNqVHPq']"))
                .getAttribute("textContent");
        // Проверяем, что суммарная цена рассчитана правильно как стартовая + цена доставки - скидка
        Assert.assertEquals(startingPrice + deliveryPrice - discount, stringToInteger(tempText));
    }


    @Step("Проверяем, что доставка стала бесплатной")
    public void freeShippingCheck() {
        String priceStr = priceList.get(1).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");
        Assert.assertTrue(priceStr.contains("бесплатно"));
    }

    @Step("Добавляем ещё один экземпляр продукта")
    public void pressPlus(int priceLimit) {
        String priceStr = driver.findElement(By.xpath("//div[@data-auto='CartOfferPrice']/span/span/span"))
                .getAttribute("textContent");

        int commonPrice = stringToInteger(priceStr);

        while(commonPrice < priceLimit) {
            driver.findElement(By.xpath("//button//span[text()='+']")).click();
            priceStr = driver.findElement(By.xpath("//div[@data-auto='CartOfferPrice']/span/span/span"))
                    .getAttribute("textContent");
            commonPrice = stringToInteger(priceStr);
        }
    }

    public void freeShippingTitleCheck(String title) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.attributeContains(
                        By.cssSelector("[class*='_3EX9adn_xp']"), "textContent", title));
        freeShippingMessageCheck(title);
    }

    @Step("Проверяем, что появилось сообщение о бесплатной доставке")
    public void freeShippingMessageCheck(String textDelivery) {
        WebElement freeShip = driver.findElement(By.cssSelector("[class *= '_3EX9adn_xp']"));
        Assert.assertTrue(freeShip.getAttribute("textContent").contains(textDelivery));
    }
}
