package tests.Register;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class Search extends BaseTest {
    @Test(priority = 1)
    public void searchValidProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        String existingProduct = ConfigReader.getProperty("existingProduct");
        homePage.searchProduct(existingProduct);
        Assert.assertTrue(homePage.isNegativeToSearchPage(existingProduct));
        Assert.assertTrue(homePage.isSearchResultRelevant(existingProduct));
    }
    @Test(priority = 2)
    public void searchInvalidProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        String nonEexistingProduct = ConfigReader.getProperty("nonExistingProduct");
        homePage.searchProduct(nonEexistingProduct);
        Assert.assertTrue(homePage.isNegativeToSearchPage(nonEexistingProduct));
        Assert.assertTrue(homePage.isNodata());

    }
    @Test(priority = 3)
    public void verifySearchWithoutEnteringAnyProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("");
        String actualURL = ConfigReader.getProperty("baseUrl");
        String expectURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL,expectURL);

    }
    @Test(priority = 4)
    public void verifySearchAfterLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.Login(ConfigReader.getProperty("email"),ConfigReader.getProperty("pass"));
        String existingProduct = ConfigReader.getProperty("existingProduct");
        homePage.searchProduct(existingProduct);
        Assert.assertTrue(homePage.isNegativeToSearchPage(existingProduct));
        Assert.assertTrue(homePage.isSearchResultRelevant(existingProduct));

    }
    @Test(priority = 5)
    public void verifySearchMultiProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        String existingProduct = ConfigReader.getProperty("existingProduct");
        homePage.searchProduct(existingProduct);
        Assert.assertTrue(homePage.isNegativeToSearchPage(existingProduct));
        int actualResult = homePage.getResultCount();
        int ExpectResult = homePage.QuantityOfProductInSearchResults();
        Assert.assertEquals(actualResult,ExpectResult);

    }

}
