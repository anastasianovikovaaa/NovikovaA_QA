package tests;

import org.testng.annotations.Test;

public class Login extends TestSettings {

    @Test
    public void test() {
        FirstPage fpage = new FirstPage(driver);
        fpage.clickOnAccount();

        LoginPage lpage = new LoginPage(driver);
        lpage.enteringLogin("peter.parkerrr");
        lpage.enteringPassword("FriendlyNeighborhoodSpiderMan");

        fpage.myProfile();
        fpage.checkLogin();

    }
}

