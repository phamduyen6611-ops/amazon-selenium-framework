package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.Base.BasePage;

public class LoginPage extends BasePage
{
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nav-link-accountList")
    private WebElement signinPress;
    @FindBy(id = "ap_email_login")
    private WebElement emailTextBox;
    @FindBy(xpath = "//input[@aria-labelledby='continue-announce']")
    private WebElement continueButton;
    @FindBy(id = "ap_password")
    private WebElement passwordTextBox;
    @FindBy(id = "signInSubmit")
    private WebElement SigninButton;

    public HomePage Login(String email,String pass){
        click(signinPress);
        type(emailTextBox, email);
        click(continueButton);
        type(passwordTextBox, pass);
        click(SigninButton);
        return new HomePage(driver);
    }
}
