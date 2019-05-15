package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FailureListener.class)
public class ToothBrushes extends TestSettings{
    @Test
    public void toothBrushesTest(){
        FirstPage fpage = new FirstPage(driver);
        fpage.pathToCatalog();
        ToothBrushesPage tbpage = new ToothBrushesPage(driver);
        tbpage.prices("999", "1999");
        tbpage.allBrushes();
        tbpage.displayedToothBrushes(999, 1999);
        tbpage.addToShoppingCart();
        ShoppingCart shoppingCart = new ShoppingCart(driver);
        shoppingCart.freeShippingMessageCheck("бесплатной доставки осталось");
        shoppingCart.priceCheck();
        shoppingCart.pressPlus(2999);
        shoppingCart.freeShippingTitleCheck("Поздравляем!");
        shoppingCart.freeShippingCheck();
        shoppingCart.priceCheck();
    }
}
