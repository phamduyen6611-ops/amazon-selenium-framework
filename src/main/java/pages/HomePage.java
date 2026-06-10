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

    @FindBy(xpath = "//div[@data-component-type='s-search-result']")
    private List<WebElement> searchResults;
    @FindBy(xpath = "//div[@data-component-type='s-search-result']//h2//span")
    private List<WebElement> productTitles;
    @FindBy(xpath = "//span[normalize-space()='No results for your search query.']")
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
    public boolean isNegativeToSearchPage(String productNameText){
        String keyword = productNameText.replace(" ", "+");
        return driver.getCurrentUrl().contains("amazon.com/s?k=" + keyword);
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
        return "Search Amazon".equals(getSearchBoxPlaceholder());
    }
    public int getResultCount(){

        String text = resultsText.getText();
        // ví dụ: "4 results for"

        String number = text.split(" ")[0].replace(",", "");

        return Integer.parseInt(number);
    }
    public boolean isNodata(){
        return noResultMessage.isDisplayed();
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
