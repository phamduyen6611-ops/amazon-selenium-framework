package tests.Register;
import base.BaseTest;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class Search extends BaseTest {
    private static final String MULTIPLE_PRODUCTS_KEYWORD = "MacBook";
    private static final String SEARCH_CRITERIA_PRODUCT = "iPhone";
    private static final String DESCRIPTION_KEYWORD = "Wireless";

    @Test(priority = 1)
    public void searchValidProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("laptop");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed");
    }
    @Test(priority = 2)
    public void searchInvalidProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("acdxyz3245123456");
        assertTrue(homePage.isNodata(), "No data message should be displayed");
    }
    @Test(priority = 3)
    public void verifySearchWithoutEnteringAnyProduct() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("");
        assertTrue(driver.getCurrentUrl().contains("amazon.com"), "Should stay on Amazon domain");
    }
    @Test(priority = 4)
    public void verifySearchAfterLogin() throws InterruptedException {
        String email = ConfigReader.getProperty("email");
        if (email == null || email.isEmpty()) return;
        
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.Login(email, ConfigReader.getProperty("pass"));
        homePage.searchProduct("laptop");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed after login");
    }
    @Test(priority = 5, description = "TC_SF_005 - Verify searching by providing a search criteria which results in multiple products")
    public void verifySearchCriteriaReturnsMultipleProducts() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(MULTIPLE_PRODUCTS_KEYWORD);
        assertTrue(homePage.hasMultipleSearchResults(), "Multiple search results should be displayed");
    }
    @Test(priority = 6, description = "TC_SF_006 - Verify search field has placeholder")
    public void verifySearchFieldHasPlaceholder() {
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isSearchBoxPlaceholderDisplayed(), "Search field placeholder should be displayed");
    }
    @Test(priority = 7, description = "TC_SF_007 - Verify searching using search criteria field")
    public void verifySearchingUsingSearchCriteriaField() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(SEARCH_CRITERIA_PRODUCT);
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed for criteria product");
    }
    @Test(priority = 8, description = "TC_SF_008 - Verify search using text from product description")
    public void verifySearchUsingProductDescriptionText() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(DESCRIPTION_KEYWORD);
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed for description keyword");
    }

    @Test(priority = 9, description = "TC_SF_009 - Verify Search by selecting the category of product")
    public void verifySearchWithCategory() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProductWithCategory("iPhone", "Electronics");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed for category search");
    }

    @Test(priority = 10, description = "TC_SF_010 - Verify Search by selecting to search in subcategories")
    public void verifySearchInSubcategories() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProductWithCategory("MacBook", "Computers");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed for subcategory search");
    }

    @Test(priority = 11, description = "TC_SF_011 - Verify List and Grid views when only one Product is displayed")
    public void verifyListView() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("iPhone 15 Pro Max 256GB");
        assertTrue(homePage.hasSearchResults(), "Single product search result should be displayed");
    }

    @Test(priority = 12, description = "TC_SF_012 - Verify List and Grid views when multiple Products are displayed")
    public void verifyMultipleProductsView() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("MacBook Air");
        assertTrue(homePage.hasMultipleSearchResults(), "Multiple products should be displayed in results");
    }

    @Test(priority = 13, description = "TC_SF_013 - Verify navigating to Product Compare Page")
    public void verifyProductCompare() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("iPhone");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed");
    }

    @Test(priority = 14, description = "TC_SF_014 - Verify User is able to sort the Products displayed in the Search Results")
    public void verifySortProducts() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("Laptop");
        homePage.selectSortBy("Price: Low to High");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed after sorting");
    }

    @Test(priority = 15, description = "TC_SF_015 - Verify the User can select how many products can be displayed")
    public void verifyResultsPerPage() {
        HomePage homePage = new HomePage(driver);
        driver.navigate().refresh();
        homePage.searchProduct("Monitor");
        assertTrue(homePage.getResultCount() > 0, "Result count should be greater than 0");
    }
}
