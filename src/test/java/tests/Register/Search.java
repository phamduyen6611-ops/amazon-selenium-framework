package tests.Register;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;

public class Search extends BaseTest {

    @Test
    public void search() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        String existingProduct = ConfigReader.getProperty("existingProduct");
        homePage.searchProduct(existingProduct);
        Assert.assertTrue(homePage.isNegativeToSearchPage(existingProduct));
        Assert.assertTrue(homePage.isSearchResultRelevant(existingProduct));
        Thread.sleep(3000);
    }
}
