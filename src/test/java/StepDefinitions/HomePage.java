package StepDefinitions;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    @Parameters("browser")
    public void setup(@Optional("firefox") String browser) {
        // Initialize the report
        Reporter.initialize();

        // Browser setup logic (using Firefox for example)
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver(); // Set up for Chrome
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://westfloridaahec.org/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Create a test log in the report
        Reporter.createTest("HomePage Test");

        System.out.println("Setup complete for " + browser);
    }

    // Verify if the home page loads successfully
    @Test(priority = 1)
    public void verifyHomePageLoaded() {
        WebElement home = driver.findElement(By.id("menu-item-207"));
        home.click();
        Assert.assertTrue(home.isDisplayed(), "Home Page did not load correctly.");
        takeScreenshot("HomePageLoaded");
        Reporter.pass("Home Page loaded successfully");
        Reporter.flush();
        System.out.println("Home Page loaded successfully");
    }

    // Perform a search and capture the search results page
    @Test(priority = 2, dependsOnMethods = "verifyHomePageLoaded")
    public void performSearchAndTakeScreenshot() {
        WebElement searchBox = driver.findElement(By.className("s"));
        searchBox.sendKeys("health programs");
        searchBox.submit();
        wait.until(ExpectedConditions.titleContains("You searched for"));
        takeScreenshot("SearchResults");
        Reporter.pass("Search performed");
        Reporter.flush();
        System.out.println("Search performed");
        driver.navigate().back();
    }

    // Verify the links under the "About" page menu
    @Test(priority = 3, dependsOnMethods = "performSearchAndTakeScreenshot")
    public void verifyAboutPageLinks() {
        verifyMenuLinks("menu-item-616", new String[]{"menu-item-594", "menu-item-593", "menu-item-305", "menu-item-644", "menu-item-571"});
        Reporter.pass("About Page links verified");
        Reporter.flush();
        System.out.println("About Page links verified");
    }

    // Verify the links under the "Programs" page menu
    @Test(priority = 4, dependsOnMethods = "verifyAboutPageLinks")
    public void verifyProgramsPageLinks() {
        verifyMenuLinks("menu-item-264", new String[]{"menu-item-344", "menu-item-280", "menu-item-534", "menu-item-1572"});
        Reporter.pass("Programs Page links verified");
        Reporter.flush();
        System.out.println("Programs Page links verified");
    }

    // Verify the links under the "Services" page menu
    @Test(priority = 5, dependsOnMethods = "verifyProgramsPageLinks")
    public void verifyServicesPageLinks() {
        verifyMenuLinks("menu-item-331", new String[]{"menu-item-440", "menu-item-330"});
        Reporter.pass("Services Page links verified");
        Reporter.flush();
        System.out.println("Services Page links verified");
    }

    // Verify the links under the "CPR" page menu
    @Test(priority = 6, dependsOnMethods = "verifyServicesPageLinks")
    public void verifyCPRPageLinks() {
        verifyMenuLinks("menu-item-467", new String[]{"menu-item-693", "menu-item-694", "menu-item-695", "menu-item-696", "menu-item-747"});
        Reporter.pass("CPR Page links verified");
        Reporter.flush();
        System.out.println("CPR Page links verified");
    }

    // Verify the "Contact Us" page
    @Test(priority = 7, dependsOnMethods = "verifyCPRPageLinks")
    public void verifyContactUsPage() {
        WebElement contactUs = driver.findElement(By.id("menu-item-209"));
        contactUs.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        takeScreenshot("ContactUs");
        Reporter.pass("Contact Us page verified");
        Reporter.flush();
        System.out.println("Contact Us page verified");
        driver.navigate().back();
    }

    // Verify the "News" page
    @Test(priority = 8, dependsOnMethods = "verifyContactUsPage")
    public void verifyNewsPage() {
        WebElement news = driver.findElement(By.id("menu-item-1097"));
        news.click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            takeScreenshot("News");
            Reporter.pass("News page verified");
            Reporter.flush();
            System.out.println("News page verified");
        } catch (Exception e) {
            System.out.println("Error verifying News page - " + e.getMessage());
        }
        driver.navigate().back();
    }

    // Verify the "My Account" page and perform account-related actions
    @Test(priority = 9, dependsOnMethods = "verifyNewsPage")
    public void verifyMyAccountPage() {
        WebElement myAccount = driver.findElement(By.xpath("//*[@id=\"menu-main-menu\"]/li[8]/a/span[1]"));
        myAccount.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        takeScreenshot("MyAccount");
        Reporter.pass("My Account page verified");
        Reporter.flush();
        System.out.println("My Account page verified");

        // Register a new account
        WebElement username = driver.findElement(By.id("reg_username"));
        WebElement email = driver.findElement(By.id("reg_email"));
        WebElement password = driver.findElement(By.id("reg_password"));
        WebElement registerButton = driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[2]/form/p[4]/button"));

        username.sendKeys("VijeeeepppppppwwayKk");
        email.sendKeys("VijaKweeeppppppeKy@example.com");
        password.sendKeys("Pa@@SSW272D$%");
        registerButton.click();

        // Verify registration success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        takeScreenshot("RegistrationSuccess");
        Reporter.pass("New account registered successfully");
        Reporter.flush();
        System.out.println("New account registered successfully");

        // Log out
        WebElement logout = driver.findElement(By.xpath("/html/body/div[8]/div/main/div/section/div/div/div/nav/ul/li[7]/a"));
        logout.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        takeScreenshot("LogoutSuccess");
        Reporter.pass("Logged out successfully");
        Reporter.flush();
        System.out.println("Logged out successfully");

        // Log back in with the new account
        myAccount = driver.findElement(By.xpath("//*[@id=\"menu-main-menu\"]/li[8]/a/span[1]"));
        myAccount.click();
        WebElement loginUsername = driver.findElement(By.id("username"));
        WebElement loginPassword = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[1]/form/p[3]/button"));

        loginUsername.sendKeys("VijeewwayKk");
        loginPassword.sendKeys("Pa@@SSW272D$%");
        loginButton.click();

        // Verify login success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        takeScreenshot("LoginSuccess");
        Reporter.pass("Logged in successfully with new account");
        Reporter.flush();
        System.out.println("Logged in successfully with new account");

        // Log out again
        logout = driver.findElement(By.xpath("/html/body/div[8]/div/main/div/section/div/div/div/nav/ul/li[7]/a"));
        logout.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        takeScreenshot("LogoutAgainSuccess");
        Reporter.pass("Logged out again successfully");
        Reporter.flush();
        System.out.println("Logged out again successfully");

        // Navigate to login page and click on "Lost your password?"
        myAccount = driver.findElement(By.xpath("//*[@id=\"menu-main-menu\"]/li[8]/a/span[1]"));
        myAccount.click();
        WebElement lostPassword = driver.findElement(By.xpath("/html/body/div[8]/div/main/div/section/div/div/div/div[2]/div[1]/form/p[4]/a"));
        lostPassword.click();

        // Enter username and click on "Reset Password"
        WebElement resetUsername = driver.findElement(By.id("user_login"));
        resetUsername.sendKeys("VijeewwayKk");
        WebElement resetButton = driver.findElement(By.xpath("/html/body/div[8]/div/main/div/section/div/div/div/form/p[3]/button"));
        resetButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[8]/div/main/div/section/div/div/div/div/div")));
        takeScreenshot("PasswordResetEmailSent");
        Reporter.pass("Password reset email has been sent.");
        Reporter.flush();
        System.out.println("Password reset email has been sent.");
    }

    // Tear down method to close the browser and cleanup resources
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        Reporter.flush(); // Ensure that the report is written at the end
        System.out.println("Tear down complete");
    }

    // Helper method to verify menu links
    private void verifyMenuLinks(String parentMenuId, String[] linkIds) {
        WebElement parentMenu = driver.findElement(By.id(parentMenuId));
        Actions actions = new Actions(driver);

        for (String linkId : linkIds) {
            try {
                actions.moveToElement(parentMenu).perform();
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.id(linkId)));
                String linkText = link.getText();
                link.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
                takeScreenshot(linkText);
                Reporter.pass("Link verified: " + linkText);
                Reporter.flush();
                System.out.println("Link verified: " + linkText);
                driver.navigate().back();
                Thread.sleep(5000); // Added delay for navigation
                parentMenu = driver.findElement(By.id(parentMenuId));
            } catch (Exception e) {
                WebElement link = driver.findElement(By.id(linkId));
                String linkText = link.getText();
                Reporter.fail("Error verifying link: " + linkText + " - " + e.getMessage());
                Reporter.flush();
                System.out.println("Error verifying link: " + linkText + " - " + e.getMessage());
            }
        }
    }

    // Helper method to capture screenshots
    private void takeScreenshot(String testName) {
        try {
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotDir, testName + ".png");
            Files.copy(srcFile.toPath(), destFile.toPath());
            System.out.println("Screenshot taken: " + testName);
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}
