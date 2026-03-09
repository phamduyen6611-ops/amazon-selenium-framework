package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.Base.BasePage;
import java.util.*;

public class HomePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchboxField;
    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;
//    @FindBy(xpath ="//span[contains(text(),'of Vietnam')]")
//    private WebElement existingProduct;
    @FindBy(xpath = "//div[@data-component-type='s-search-result']")
    private List<WebElement> searchResults;
    @FindBy(xpath = "//div[@data-component-type='s-search-result']//h2//span")
    private List<WebElement> productTitles;

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

    public boolean isEqualsQuantityOfProductInSearchResults(){
        return searchResults.size() > 0;
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