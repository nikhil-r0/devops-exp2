    package org.test.Webpage;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.testng.Assert;
    import org.testng.annotations.AfterTest;
    import org.testng.annotations.BeforeTest;
    import org.testng.annotations.Test;

    import static org.testng.Assert.assertTrue;

    public class WebpageTest {
        private static WebDriver driver;

        @BeforeTest
        public void openBrowser() throws InterruptedException {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            Thread.sleep(2000);
            driver.get("https://nikhil-r0.github.io/devops-exp2/");
        }

        @Test
        public void titleValidationTest(){
            String actualTitle = driver.getTitle();
            String expectedTitle = "Ecommerce Demo";
            Assert.assertEquals(actualTitle, expectedTitle);

        }

        @AfterTest
        public void closeBrowser() throws InterruptedException {
            Thread.sleep(10000);
            driver.quit();
        }


    }