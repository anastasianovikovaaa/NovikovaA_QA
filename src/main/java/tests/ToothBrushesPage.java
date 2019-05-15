package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class ToothBrushesPage {
    public static WebDriver driver;
    private List<WebElement> toothBrushesList;


    public ToothBrushesPage(WebDriver dr) {
        driver = dr;
    }

    @Step("Выполнить поиск с диапазоном цен от 999 до 1999 рублей")
    public void prices(String l, String h){
        WebElement low = driver.findElement(By.cssSelector("[id = 'glpricefrom']"));
        WebElement high = driver.findElement(By.cssSelector("[id = 'glpriceto']"));
        low.sendKeys(l);
        high.sendKeys(h);
        // проверяем, высвечивается ли рамка "Найдено .. товаров"
        WebElement frame = driver.findElement(By.cssSelector("[class*='_1PQIIOelRL']"));
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(frame));
    }

    @Step("Найти все щетки")
    public void allBrushes() {
        // Нажимаем на кнопку "показать ещё", пока это возможно, когда станет невозможно, будет выкинуто исключение
        WebElement btnShowMore = driver.findElement(By.cssSelector("[class *= 'button2_size_ml button2_theme_gray']"));
        while (true) {
            try {
                btnShowMore.click();
            } catch (Exception e) {
                break;
            }
        }
        // получаем список всех щёток
        toothBrushesList = driver.findElements(By.cssSelector("[class*='grid-snippet_react']"));
    }

    @Step("Проверяем, что в результате отобразились щетки с ценами в нужном диапазоне")
    public void displayedToothBrushes(int l, int h) {
        // Идём по списку всех щёток и проверяем, что у каждой цена в нужном диапазоне
        for (int i = 0; i < toothBrushesList.size(); i++) {
            String price = toothBrushesList.get(i).findElement(By
                            .cssSelector("[data-tid='c3eaad93']")).getAttribute("textContent")
                            .replace(" ", "");
            int intPrice = Integer.parseInt(price);
            Assert.assertTrue(intPrice >= l && intPrice <= h);
        }
    }

    @Step("Добавить товар в корзину")
    public void addToShoppingCart() {
        // Берём предпоследнюю щётку и нажимаем "добавить в корзину"
        toothBrushesList.get(toothBrushesList.size() - 2).findElement(By.cssSelector("[class*='_2w0qPDYwej']")).click();
        // Ожидание появления всплывающего окна с информацией о том, что товар добавлен в корзину
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .cssSelector("[class*='_1sjxYfIabK _26mXJDBxtH']")));

        // после чего нажимаем на кнопку ещё раз и переходим в корзину
        toothBrushesList.get(toothBrushesList.size() - 2).findElement(By.cssSelector("[class*='_2w0qPDYwej']")).click();
    }
}
