package com.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class TestBase {
    protected WebDriver driver;
    protected String platform;
    protected String browserName;
    protected String browserVersion;
    
    public static final String LT_USERNAME = System.getenv("LT_USERNAME");
    public static final String LT_ACCESS_KEY = System.getenv("LT_ACCESS_KEY");
    public static final String GRID_URL = "hub.lambdatest.com/wd/hub";
    
    @BeforeMethod
    @Parameters({"platform", "browserName", "browserVersion"})
    public void setUp(String platform, String browserName, String browserVersion) 
            throws MalformedURLException {
        this.platform = platform;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", platform);
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("version", browserVersion);
        capabilities.setCapability("build", "LambdaTest Jenkins Integration");
        capabilities.setCapability("name", "Test on " + platform + " " + browserName);
        capabilities.setCapability("network", true);
        capabilities.setCapability("visual", true);
        capabilities.setCapability("video", true);
        capabilities.setCapability("console", true);
        
        // LambdaTest Capabilities
        capabilities.setCapability("user", LT_USERNAME);
        capabilities.setCapability("accessKey", LT_ACCESS_KEY);
        
        String gridURL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@" + GRID_URL;
        driver = new RemoteWebDriver(new URL(gridURL), capabilities);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}