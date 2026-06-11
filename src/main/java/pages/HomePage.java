package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.Base.BasePage;
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
    @FindBy(xpath = "//span[normalize-space()='No results for your search query.'] | //span[contains(text(),'No results for')]")
    private WebElement noResultMessage;
    @FindBy(xpath = "//h2/span[contains(text(),'results')]")
    WebElement resultsText;

    public void searchProduct(String productNameText) {
        type(searchboxField, productNameText);
        click(searchButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void searchProductWithCategory(String productNameText, String category) {
        selectByVisibleText(categoryDropdown, category);
        type(searchboxField, productNameText);
        click(searchButton);
    }

    public void selectSortBy(String sortOption) {
        selectByVisibleText(sortByDropdown, sortOption);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isNegativeToSearchPage(String productNameText){
        String keyword = productNameText.replace(" ", "+");
        return driver.getCurrentUrl().contains("k=" + keyword);
    }

    public int QuantityOfProductInSearchResults(){
        return searchResults.size() ;
    }
    public boolean hasMultipleSearchResults(){
        return QuantityOfProductInSearchResults() > 1;
    }
    public boolean hasSearchResults(){
        return QuantityOfProductInSearchResults() > 0;
    }
    public String getSearchBoxPlaceholder(){
        return wait.waitForElementVisible(searchboxField).getAttribute("placeholder");
    }
    public boolean isSearchBoxPlaceholderDisplayed(){
        String placeholder = getSearchBoxPlaceholder();
        return placeholder != null && !placeholder.isEmpty();
    }
    public int getResultCount(){
        try {
            String text = resultsText.getText();
            String number = text.split(" ")[0].replace(",", "");
            return Integer.parseInt(number);
        } catch (Exception e) {
            return searchResults.size();
        }
    }
    public boolean isNodata(){
        try {
            return noResultMessage.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return searchResults.isEmpty();
        }
    }
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
