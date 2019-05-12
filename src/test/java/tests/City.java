package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class City extends TestSettings {
    // метод, который подготавливает данные для теста
    @DataProvider(name="cityProvider")
    public Object[][] getCity(){
        return new Object[][]
                {
                        { "Хвалынск" },
                        { "Санкт-Петербург" },
                        { "Казань" },
                        { "Москва" }
                };
    }

    @Test(dataProvider="cityProvider")
    public void cityTest(String cityName){
        FirstPage fpage = new FirstPage(driver);
        fpage.changeCity(cityName);
        fpage.checkCity(cityName);
        fpage.clickOnAccount();
        LoginPage lpage = new LoginPage(driver);
        lpage.enteringLogin("peter.parkerrr");
        lpage.enteringPassword("FriendlyNeighborhoodSpiderMan");
        fpage.pathToMyProfile();
        MyProfilePage mppage = new MyProfilePage(driver);
        mppage.comparisonOfCities();
    }
}
