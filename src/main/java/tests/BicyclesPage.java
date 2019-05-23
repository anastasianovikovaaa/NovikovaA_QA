package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BicyclesPage {
    public static WebDriver driver;
    List<WebElement> allBicyclesList;

    public BicyclesPage(WebDriver dr) {
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

    @Step("Переходим в раздел 'Велосипеды'")
    public void bicycles() {
        driver.findElement(By.xpath("//a[text()='Велосипеды']")).click();
    }



    @Step("Получить список всех велосипедов")
    public void allBicycles() {
        // Нажимаем на кнопку "показать ещё", пока это возможно, когда станет невозможно, будет выкинуто исключение
        WebElement btnShowMore = driver.findElement(By.cssSelector("[class *= 'button2_size_ml button2_theme_gray']"));
        while (true) {
            try {
                btnShowMore.click();
            } catch (Exception e) {
                break;
            }
        }
        final int countElement = stringToInteger(
                driver.findElement(By.cssSelector("[class *= '_1PQIIOelRL']"))
                        .getAttribute("textContent"));

        (new WebDriverWait(driver,30)).until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver) {
                return driver.findElements(By
                        .cssSelector("[class*='grid-snippet_react']")).size() == countElement;
            }
        });

        // получаем список всех велосипедов
        allBicyclesList = driver.findElements(By.cssSelector("[class*='grid-snippet_react']"));
    }

    @Step("Выполнить поиск с диапазоном цен от 15500 до 26399 рублей")
    public void prices(String l, String h) {
        WebElement low = driver.findElement(By.cssSelector("[id = 'glpricefrom']"));
        WebElement high = driver.findElement(By.cssSelector("[id = 'glpriceto']"));
        low.sendKeys(l);
        high.sendKeys(h);
        // проверяем, высвечивается ли рамка "Найдено .. товаров"
        WebElement frame = driver.findElement(By.cssSelector("[class*='_1PQIIOelRL']"));
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(frame));

    }

    @Step("Проверяем, что в результате отобразились велосипеды с ценами в нужном диапазоне")
    public void displayedBicycles(int l, int h) {
        // Идём по списку всех велосипедов и проверяем, что у каждой цена в нужном диапазоне
        for (int i = 0; i < allBicyclesList.size(); i++) {
            String price = allBicyclesList.get(i).findElement(By
                    .cssSelector("[data-tid='c3eaad93']")).getAttribute("textContent");
            int intPrice = stringToInteger(price);
            Assert.assertTrue(intPrice >= l && intPrice <= h);
        }
    }

    @Step("Сортируем так, чтобы сначала были велосипеды подороже")
    public void expensive() {
        WebElement btn = driver.findElement(By.xpath("//span[contains(text(),'сначала популярное')]"));
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(btn));
        btn.click();
        driver.findElement(By.xpath("//span[contains(text(),'сначала подороже')]")).click();
    }

    @Step("Проверяем, что велосипеды показаны в порядке убывания цены")
    public void checkIfDecreases(){
        int currentPrice = stringToInteger(allBicyclesList.get(0).findElement(By
                .cssSelector("[data-tid='c3eaad93']")).getAttribute("textContent"));

        for (int i = 1; i < allBicyclesList.size(); i++) {
            String price = allBicyclesList.get(i).findElement(By
                    .cssSelector("[data-tid='c3eaad93']")).getAttribute("textContent");
            int intPrice = stringToInteger(price);
            Assert.assertTrue(intPrice <= currentPrice);
            currentPrice = intPrice;
        }
    }

    @Step("Выбрать производителя велосипедов STELS")
    public void chooseBrand(String brand) {
        driver.findElement(By.xpath("//input[contains(@name, '" + brand + "')]/..")).click();
    }

    @Step("Проверить, тот ли производитель велосипедов был выбран")
    public void checkBrand(String brand) {
        for (int i = 0; i < allBicyclesList.size(); i++) {
            String text = allBicyclesList.get(i).findElement(By
                    .cssSelector("[class*='_3l-uEDOaBN tdrs43E7Xn _3HJsMt3YC']")).getAttribute("textContent");
            Assert.assertTrue(text.contains(brand));
        }
    }

    @Step("Выбрать синий цвет велосипедов")
    public void bicycleColor(String color) {
        driver.findElement(By.xpath("//span[contains(text(), '" + color +"')]/..")).click();
    }

    @Step("Проверить, что был выбран синий цвет велосипедов")
    public void bicycleColorCheck(String color) {
        for (int i = 0; i < allBicyclesList.size(); i++) {
            String text = allBicyclesList.get(i).findElement(By
                    .cssSelector("[class*='_3l-uEDOaBN tdrs43E7Xn _3HJsMt3YC']")).getAttribute("textContent");
            Assert.assertTrue(text.contains(color));
        }
    }
}