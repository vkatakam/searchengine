package com.qa.web.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.qa.web.pages.BasePage;

/**
 * @author Venkat Katakam
 * PageObject This page contains all the Web Elements, User Actions and Business processes functions of the Google Search page
 */
public class GoogleSearchPage extends BasePage {
	private WebDriver driver;

	public GoogleSearchPage(WebDriver driver) {
		this.driver = driver;
	}

	public void open() {
		driver.get("https://www.google.com");
	}

	// ==============================================================
	// Page Objects
	// ==============================================================
	@FindAll({ @FindBy(name = "q") })
	@CacheLookup
	List<WebElement> searchBoxList;
	WebElement searchBox;

	@FindAll({ @FindBy(xpath = "//ul[@role='listbox']/li/descendant::div[@class='wM6W7d']") })
	@CacheLookup
	List<WebElement> searchBoxTextList;
	WebElement searchBoxText;

	@FindAll({
			@FindBy(xpath = "//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div/div[1]/div/span/a/div/div/div/div[1]/span") })
	@CacheLookup
	List<WebElement> firstResultTextList;
	WebElement firstResultText;

	// ==============================================================
	// User Actions
	// ==============================================================
	public void searchTermInput(String searchInputTerm) {
		System.out.println("Inside searchTermInput method");
		searchBox = listElement(searchBoxList);
		searchBox.sendKeys(searchInputTerm);
	}

	public void searchInputData(String searchData) {
		System.out.println("Inside searchInputData");
		searchBoxText = listElement(searchBoxTextList);
		searchBoxText.sendKeys(searchData);
	}

	/**
	 * Search the value in the Google Search page
	 */
	public void search(String searchTerm) throws InterruptedException {
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys(searchTerm);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
		Thread.sleep(5000);
		List<WebElement> list = driver
				.findElements(By.xpath("//ul[@role='listbox']/li/descendant::div[@class='wM6W7d']"));
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			String listItem = list.get(i).getText();
			System.out.println(listItem);
			if (listItem.contains("java")) {
				driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
				Thread.sleep(5000);
				list.get(i).click();
				break;
			}
		}
	}

	/**
	 * Read first return value
	 * @throws InterruptedException 
	 */
	public String getFirstResult() throws InterruptedException {
		String expectedResult = "Java";
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
		Thread.sleep(5000);
		WebElement firstResult = driver.findElement(By.xpath(
				"//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div/div[1]/div/span/a/div/div/div/div[1]/span"));
		String actualResult = firstResult.getText();
		System.out.println("Read first value from search result page:" + actualResult);
		// Assert that the first result matches the expected result
		Assert.assertEquals(actualResult, expectedResult);
		System.out.println("Done");
		return actualResult;
	}

	/*
	 * public String getFirstResult() {
	 * System.out.println("Read first value from search result page");
	 * firstResultText = listElement(firstResultTextList); 
	 * return firstResultText.getText(); }
	 */

}
