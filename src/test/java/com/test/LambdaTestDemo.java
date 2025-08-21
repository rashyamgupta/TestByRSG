package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LambdaTestDemo extends TestBase {

	@Test
	public void testSimpleFormDemo() throws InterruptedException {
		// Open Selenium Playground
		driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");

		// Verify page title
		Assert.assertTrue(driver.getTitle().contains("Selenium Playground"));

		// Enter message
		WebElement messageField = driver.findElement(By.id("user-message"));
		messageField.clear();
		messageField.sendKeys("Welcome to LambdaTest");

		// Click Get Checked Value button
		WebElement showMessageBtn = driver.findElement(By.id("showInput"));
		showMessageBtn.click();

		// Verify message
		WebElement message = driver.findElement(By.id("message"));
		Assert.assertEquals(message.getText(), "Welcome to LambdaTest");

		// Mark test status on LambdaTest
		markTestStatus("passed", "Test completed successfully");
	}

	@Test
	public void testDragAndDrop() throws InterruptedException {
		// Open Drag & Drop Sliders
		driver.get("https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo");

		// Verify page title
		Assert.assertTrue(driver.getTitle().contains("Drag & Drop Sliders"));

		// Find the slider and drag it
		WebElement slider = driver.findElement(By.cssSelector("input[value='15']"));

		// Using JavaScript to set the slider value
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '95'; arguments[0].dispatchEvent(new Event('change'))", slider);

		// Verify output
		WebElement output = driver.findElement(By.id("rangeSuccess"));
		Assert.assertTrue(output.getText().contains("95"));

		// Mark test status on LambdaTest
		markTestStatus("passed", "Drag and drop test completed");
	}

	private void markTestStatus(String status, String reason) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("lambda-status=" + status);
	}
}