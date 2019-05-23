package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FailureListener.class)
public class BicyclesTest extends TestSettings {
    @Test
    public void bicyclesTest(){
        FirstPage fpage = new FirstPage(driver);
        fpage.pathToBicyclesCatalog();
        BicyclesPage bpage = new BicyclesPage(driver);
        bpage.bicycles();
        bpage.prices("15500", "26399");
        bpage.allBicycles();
        bpage.displayedBicycles(15500, 26399);
        bpage.expensive();
        bpage.allBicycles();
        bpage.checkIfDecreases();
        bpage.chooseBrand("STELS");
        bpage.allBicycles();
        bpage.checkBrand("STELS");
        bpage.bicycleColor("синий");
        bpage.allBicycles();
        bpage.bicycleColorCheck("синий");
    }
}
