package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base.BasePage;
import java.time.Duration;
import java.util.*;

public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchboxField;
    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;
    @FindBy(id = "searchDropdownBox")
    private WebElement categoryDropdown;
    @FindBy(id = "s-result-sort-select")
    private WebElement sortByDropdown;

    @FindBy(xpath = "//div[@data-component-type='s-search-result']")
    private List<WebElement> searchResults;
    @FindBy(xpath = "//div[@data-component-type='s-search-result']//h2//span")
    private List<WebElement> productTitles;
    @FindBy(xpath = "//span[normalize-space()='No results for your search query.'] | //span[contains(text(),'No results for')] | //div[contains(@class, 's-no-results-info')]")
    private WebElement noResultMessage;
    @FindBy(xpath = "//h1//*[contains(text(), 'results')] | //h2/span[contains(text(),'results')] | //div[contains(@class, 'a-section')]//span[contains(text(), 'results')]")
    private WebElement resultsText;

    @FindBy(xpath = "//span[contains(@class, 'glow-toaster-button-dismiss')]//input")
    private WebElement dismissLocationPopup;

    @Step("Dismiss any popups if present")
    public void dismissPopups() {
        try {
            if (dismissLocationPopup.isDisplayed()) {
                click(dismissLocationPopup);
            }
        } catch (Exception e) {
            // Popup not present
        }
    }

    @Step("Search for product: {productNameText}")
    public void searchProduct(String productNameText) {
        dismissPopups();
        type(searchboxField, productNameText);
        click(searchButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Step("Search for product using keyboard: {productNameText}")
    public void searchProductUsingKeyboard(String productNameText) {
        dismissPopups();
        WebElement searchBox = wait.waitForElementVisible(searchboxField);
        searchBox.clear();
        searchBox.sendKeys(productNameText);
        searchBox.sendKeys(Keys.ENTER);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/s?"));
    }

    @Step("Search for product '{productNameText}' in category '{category}'")
    public void searchProductWithCategory(String productNameText, String category) {
        dismissPopups();
        try {
            Select select = new Select(wait.waitForElementVisible(categoryDropdown));
            boolean found = false;
            for (WebElement option : select.getOptions()) {
                if (option.getText().equalsIgnoreCase(category) || option.getText().contains(category)) {
                    select.selectByVisibleText(option.getText());
                    found = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error selecting category: " + e.getMessage());
        }
        type(searchboxField, productNameText);
        click(searchButton);
    }

    @Step("Select sort option: {sortOption}")
    public void selectSortBy(String sortOption) {
        try {
            Select select = new Select(wait.waitForElementVisible(sortByDropdown));
            for (WebElement option : select.getOptions()) {
                if (option.getText().toLowerCase().contains(sortOption.toLowerCase())) {
                    select.selectByVisibleText(option.getText());
                    break;
                }
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Sort option " + sortOption + " not found");
        }
    }

    @Step("Verify if navigated to search page for keyword: {productNameText}")
    public boolean isNegativeToSearchPage(String productNameText){
        String keyword = productNameText.replace(" ", "+");
        return driver.getCurrentUrl().contains("k=" + keyword);
    }

    public int QuantityOfProductInSearchResults(){
        return searchResults.size() ;
    }

    @Step("Verify if there are multiple search results")
    public boolean hasMultipleSearchResults(){
        return QuantityOfProductInSearchResults() > 1;
    }

    @Step("Verify if there are any search results")
    public boolean hasSearchResults(){
        return QuantityOfProductInSearchResults() > 0;
    }

    @Step("Verify search textbox is displayed")
    public boolean isSearchBoxDisplayed(){
        try {
            return wait.waitForElementVisible(searchboxField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Verify search icon button is displayed")
    public boolean isSearchButtonDisplayed(){
        try {
            return wait.waitForElementVisible(searchButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Verify search functionality is displayed")
    public boolean isSearchFunctionalityDisplayed(){
        return isSearchBoxDisplayed() && isSearchButtonDisplayed();
    }

    public String getSearchBoxPlaceholder(){
        return wait.waitForElementVisible(searchboxField).getAttribute("placeholder");
    }

    @Step("Verify if search box placeholder is displayed")
    public boolean isSearchBoxPlaceholderDisplayed(){
        String placeholder = getSearchBoxPlaceholder();
        return placeholder != null && !placeholder.isEmpty();
    }

    @Step("Open Amazon site directory page")
    public void openSiteDirectoryPage(String baseUrl){
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        driver.get(normalizedBaseUrl + "gp/site-directory");
    }

    @Step("Open Amazon search page from current page for keyword: {keyword}")
    public void openSearchPage(String baseUrl, String keyword){
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        driver.get(normalizedBaseUrl + "s?k=" + keyword.replace(" ", "+"));
    }

    @Step("Verify current page is Amazon site directory")
    public boolean isSiteDirectoryPage(){
        String currentUrl = driver.getCurrentUrl().toLowerCase(Locale.ROOT);
        String pageTitle = driver.getTitle().toLowerCase(Locale.ROOT);
        return currentUrl.contains("site-directory") || pageTitle.contains("site directory");
    }

    @Step("Verify current page is search results page for keyword: {keyword}")
    public boolean isSearchPageUrlForKeyword(String keyword){
        String currentUrl = driver.getCurrentUrl().toLowerCase(Locale.ROOT);
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT).replace(" ", "+");
        return currentUrl.contains("/s?") && currentUrl.contains("k=" + normalizedKeyword);
    }

    @Step("Verify search page heading is displayed")
    public boolean isSearchPageHeadingDisplayed(){
        try {
            return wait.waitForElementVisible(resultsText).isDisplayed();
        } catch (Exception e) {
            return hasSearchResults();
        }
    }

    @Step("Verify search page title is displayed for keyword: {keyword}")
    public boolean isSearchPageTitleDisplayedForKeyword(String keyword){
        String title = driver.getTitle();
        return title != null
                && !title.isBlank()
                && title.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    @Step("Verify search result page has core UI options")
    public boolean hasSearchPageUiOptions(){
        return isSearchFunctionalityDisplayed() && hasSearchResults();
    }

    @Step("Verify search works in current browser environment for keyword: {keyword}")
    public boolean isSearchWorkingInCurrentEnvironment(String keyword){
        return isSearchPageUrlForKeyword(keyword) && hasSearchResults();
    }
    
    @Step("Get result count from the page")
    public int getResultCount(){
        try {
            String text = resultsText.getText();
            String[] parts = text.split(" ");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].contains("result")) {
                    String numStr = parts[i-1].replace(",", "");
                    if (numStr.equalsIgnoreCase("over") || numStr.equalsIgnoreCase("of")) {
                        for(int j=i-1; j>=0; j--) {
                            String s = parts[j].replace(",", "");
                            if(s.matches("\\d+")) return Integer.parseInt(s);
                        }
                    }
                    if (numStr.matches("\\d+")) return Integer.parseInt(numStr);
                }
            }
            return searchResults.size();
        } catch (Exception e) {
            return searchResults.size();
        }
    }
    
    @Step("Check if no results message is displayed")
    public boolean isNodata(){
        try {
            if (noResultMessage.isDisplayed()) return true;
        } catch (Exception e) {}
        return searchResults.isEmpty();
    }

    @Step("Verify if search result is relevant to keyword: {keyword}")
    public boolean isSearchResultRelevant(String keyword){
        List<String> words = normalizeKeywords(keyword);
        for(WebElement title : productTitles){
            String text = title.getText().toLowerCase();
            for(String word : words){
                if(text.contains(word)){
                    return true;
                }
            }
        }
        return false;
    }
    private List<String> normalizeKeywords(String keyword){
        keyword = keyword.toLowerCase();
        keyword = keyword.replace("-", " ");
        return Arrays.asList(keyword.split("\\s+"));
    }
}
