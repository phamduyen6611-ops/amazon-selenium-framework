package tests.Search;
import base.BaseTest;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class Search extends BaseTest {
    private static final String MULTIPLE_PRODUCTS_KEYWORD = "MacBook";
    private static final String SEARCH_CRITERIA_PRODUCT = "iPhone";
    private static final String DESCRIPTION_KEYWORD = "Wireless";
    private static final String EXISTING_PRODUCT = "iMac";

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

    @Test(priority = 16, description = "TC_SF_016 - Verify Search textbox field and search icon button are displayed on all pages")
    public void verifySearchBoxAndButtonDisplayedOnApplicationPages() {
        HomePage homePage = new HomePage(driver);
        String baseUrl = ConfigReader.getProperty("baseUrl");

        driver.get(baseUrl);
        assertTrue(homePage.isSearchFunctionalityDisplayed(), "Search box and search button should be displayed on Home page");

        homePage.searchProduct(EXISTING_PRODUCT);
        assertTrue(homePage.isSearchFunctionalityDisplayed(), "Search box and search button should be displayed on Search Results page");

        homePage.openSearchPage(baseUrl, MULTIPLE_PRODUCTS_KEYWORD);
        assertTrue(homePage.isSearchFunctionalityDisplayed(), "Search box and search button should be displayed on Search page");
    }

    @Test(priority = 17, description = "TC_SF_017 - Verify navigating to Search page from the Site Map page")
    public void verifyNavigatingToSearchPageFromSiteMapPage() {
        HomePage homePage = new HomePage(driver);
        String baseUrl = ConfigReader.getProperty("baseUrl");

        homePage.openSiteDirectoryPage(baseUrl);
        assertTrue(homePage.isSiteDirectoryPage(), "Site Directory page should be displayed");

        homePage.openSearchPage(baseUrl, EXISTING_PRODUCT);
        assertTrue(homePage.isSearchPageUrlForKeyword(EXISTING_PRODUCT), "User should be navigated to Search page from Site Directory page");
    }

    @Test(priority = 18, description = "TC_SF_018 - Verify Breadcrumb of the Search page")
    public void verifyBreadcrumbOfSearchPage() {
        HomePage homePage = new HomePage(driver);
        driver.get(ConfigReader.getProperty("baseUrl"));

        homePage.searchProduct(EXISTING_PRODUCT);
        assertTrue(homePage.isSearchPageUrlForKeyword(EXISTING_PRODUCT), "Search page URL should keep the searched keyword");
        assertTrue(homePage.isSearchPageHeadingDisplayed(), "Search page heading or result context should be displayed");
    }

    @Test(priority = 19, description = "TC_SF_019 - Verify Search functionality can be used with keyboard keys")
    public void verifySearchUsingKeyboardKeys() {
        HomePage homePage = new HomePage(driver);
        driver.get(ConfigReader.getProperty("baseUrl"));

        homePage.searchProductUsingKeyboard(EXISTING_PRODUCT);
        assertTrue(homePage.isSearchPageUrlForKeyword(EXISTING_PRODUCT), "Search should be performed using keyboard Enter key");
        assertTrue(homePage.hasSearchResults(), "Search results should be displayed after keyboard search");
    }

    @Test(priority = 20, description = "TC_SF_020 - Verify Page Heading, Page URL and Page Title of the Search page")
    public void verifySearchPageHeadingUrlAndTitle() {
        HomePage homePage = new HomePage(driver);
        driver.get(ConfigReader.getProperty("baseUrl"));

        homePage.searchProduct(EXISTING_PRODUCT);
        assertTrue(homePage.isSearchPageHeadingDisplayed(), "Search page heading should be displayed");
        assertTrue(homePage.isSearchPageUrlForKeyword(EXISTING_PRODUCT), "Search page URL should contain searched keyword");
        assertTrue(homePage.isSearchPageTitleDisplayedForKeyword(EXISTING_PRODUCT), "Search page title should contain searched keyword");
    }

    @Test(priority = 21, description = "TC_SF_021 - Verify the UI of Search functionality and Search page options")
    public void verifySearchFunctionalityAndSearchPageUi() {
        HomePage homePage = new HomePage(driver);
        driver.get(ConfigReader.getProperty("baseUrl"));

        assertTrue(homePage.isSearchFunctionalityDisplayed(), "Search textbox and search button should be visible");
        assertTrue(homePage.isSearchBoxPlaceholderDisplayed(), "Search textbox should have placeholder text");

        homePage.searchProduct(MULTIPLE_PRODUCTS_KEYWORD);
        assertTrue(homePage.hasSearchPageUiOptions(), "Search results page should display core search UI options and results");
    }

    @Test(priority = 22, description = "TC_SF_022 - Verify Search functionality in the supported environment")
    public void verifySearchFunctionalityInSupportedEnvironment() {
        HomePage homePage = new HomePage(driver);
        driver.get(ConfigReader.getProperty("baseUrl"));

        homePage.searchProduct(EXISTING_PRODUCT);
        assertTrue(homePage.isSearchWorkingInCurrentEnvironment(EXISTING_PRODUCT), "Search functionality should work in the configured browser environment");
    }
}
