package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestMain {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.com/");
        
        // Use the driver for your automation tasks
        
        driver.quit();
    }
}




