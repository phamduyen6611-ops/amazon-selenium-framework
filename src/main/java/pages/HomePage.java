package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
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
    @FindBy(xpath = "//span[normalize-space()='No results for your search query.'] | //span[contains(text(),'No results for')] | //div[contains(@class, 's-no-results-info')]")
    private WebElement noResultMessage;
    @FindBy(xpath = "//h1//*[contains(text(), 'results')] | //h2/span[contains(text(),'results')] | //div[contains(@class, 'a-section')]//span[contains(text(), 'results')]")
    private WebElement resultsText;

    @FindBy(xpath = "//span[contains(@class, 'glow-toaster-button-dismiss')]//input")
    private WebElement dismissLocationPopup;

    public void dismissPopups() {
        try {
            if (dismissLocationPopup.isDisplayed()) {
                click(dismissLocationPopup);
            }
        } catch (Exception e) {
            // Popup not present
        }
    }

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
            String[] parts = text.split(" ");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].contains("result")) {
                    String numStr = parts[i-1].replace(",", "");
                    if (numStr.equalsIgnoreCase("over") || numStr.equalsIgnoreCase("of")) {
                        // try to find the actual number before "results"
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
    public boolean isNodata(){
        try {
            if (noResultMessage.isDisplayed()) return true;
        } catch (Exception e) {}
        return searchResults.isEmpty();
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
