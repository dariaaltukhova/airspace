import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AirspaceLoginChallengeTest {

    private WebDriver driver;

    @BeforeClass
    public void before() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void beforeTest() {
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
    }

   @Test
    public void loginAsValidUserWithValidPasswordTest() {
        WebElement userNameTextBox = driver.findElement(By.id("username"));
        userNameTextBox.sendKeys("tomsmith");

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys("SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("//i[contains(text(),'Login')]"));
        loginButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/secure");
    }

   @Test
    public void loginAsInvalidUserWithValidPasswordTest() {
        WebElement userNameTextBox = driver.findElement(By.id("username"));
        userNameTextBox.sendKeys("annasmith");

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys("SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("//i[contains(text(),'Login')]"));
        loginButton.click();

        WebElement errorMessageBox = driver.findElement(By.xpath("//div[@id='flash']"));
        Assert.assertTrue(errorMessageBox.isDisplayed());

        String errorMessage = errorMessageBox.getText();
        Assert.assertTrue(errorMessage.contains("Your username is invalid!"));
    }

    @Test
    public void loginAsValidUserWithInvalidPasswordTest() {
        WebElement userNameTextBox = driver.findElement(By.id("username"));
        userNameTextBox.sendKeys("tomsmith");

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys("SomePassword!");

        WebElement loginButton = driver.findElement(By.xpath("//i[contains(text(),'Login')]"));
        loginButton.click();

        WebElement errorMessageBox = driver.findElement(By.xpath("//div[@id='flash']"));
        Assert.assertTrue(errorMessageBox.isDisplayed());

        String errorMessage = errorMessageBox.getText();
        Assert.assertTrue(errorMessage.contains("Your password is invalid!"));
    }

    @Test
    public void loginAsEmptyUserFieldWithValidPasswordTest() {
        WebElement userNameTextBox = driver.findElement(By.id("username"));
        userNameTextBox.clear();

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys("SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("//i[contains(text(),'Login')]"));
        loginButton.click();

        WebElement errorMessageBox = driver.findElement(By.xpath("//div[@id='flash']"));
        Assert.assertTrue(errorMessageBox.isDisplayed());

        String errorMessage = errorMessageBox.getText();
        Assert.assertTrue(errorMessage.contains("Your username is invalid!"));
    }

    @Test
    public void loginAsValidUserWithEmptyPasswordFieldTest() {
        WebElement userNameTextBox = driver.findElement(By.id("username"));
        userNameTextBox.sendKeys("tomsmith");

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.clear();

        WebElement loginButton = driver.findElement(By.xpath("//i[contains(text(),'Login')]"));
        loginButton.click();

        WebElement errorMessageBox = driver.findElement(By.xpath("//div[@id='flash']"));
        Assert.assertTrue(errorMessageBox.isDisplayed());

        String errorMessage = errorMessageBox.getText();
        Assert.assertTrue(errorMessage.contains("Your password is invalid!"));
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }
}
