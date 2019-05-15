package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FailureListener.class)
public class Login extends TestSettings {

    @Test
    public void test() {
        FirstPage fpage = new FirstPage(driver);
        fpage.clickOnAccount();

        LoginPage lpage = new LoginPage(driver);
        lpage.enteringLogin("peter.parkerrr");

        // тут пример для того, чтобы тест упал
        // Assert.assertTrue(false);

        lpage.enteringPassword("FriendlyNeighborhoodSpiderMan");
        fpage.myProfile();
        fpage.checkLogin();
    }
}

