package com.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestBase {
	protected String platform;
	protected String browserName;
	protected String browserVersion;

	public static String hubURL = "https://hub.lambdatest.com/wd/hub";
	protected WebDriver driver;

	@BeforeMethod
	@Parameters({ "platform", "browserName", "browserVersion" })
	public void setUp(String platform, String browserName, String browserVersion) throws MalformedURLException {
		this.platform = platform;
		this.browserName = browserName;
		this.browserVersion = browserVersion;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "139");
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", "rashyamgupta");
        ltOptions.put("accessKey", "LT_uNi2oRzGfunSRz22T01EqD9GR7EzOPJ3Pdha3hBWw0AneQ8");
        ltOptions.put("build", "Selenium 4");
        ltOptions.put("name", this.getClass().getName());
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("seCdp", true);
        ltOptions.put("selenium_version", "4.23.0");
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        System.out.println(driver);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}