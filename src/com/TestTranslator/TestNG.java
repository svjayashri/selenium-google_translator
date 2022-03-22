package com.TestTranslator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;

public class TestNG {
	WebDriver driver;

	@BeforeMethod

	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	@Parameters({ "sourceLanguage", "translationLanguage", "sourceText", "translatedText", "swapText", "swappedText" })

	public void googlePage(String sourceLanguage, String translationLanguage, String sourceText, String translatedText,
			String swapText, String swappedText) throws Exception {
		try {
			// Source Language Selection
			driver.get("https://translate.google.com/");
			System.out.println("The title of the page is " + driver.getTitle());
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//div[contains(@class,'VfPpkd-Bz112c-RLmnJb')])[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[1]")).click();
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[1]"))
					.sendKeys(sourceLanguage);
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[1]")).sendKeys(Keys.ENTER);
			WebElement sourceValue = driver.findElement(By.xpath("//textarea[@aria-label='Source text']"));

			sourceValue.sendKeys(sourceText);

			// Translation Language

			driver.findElement(By.xpath("(//div[@class='VfPpkd-Bz112c-RLmnJb'])[3]")).click();
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[2]")).click();
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[2]"))
					.sendKeys(translationLanguage);
			WebElement translatedValue = driver
					.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[2]"));
			translatedValue.sendKeys(Keys.ENTER);
			Thread.sleep(2000);

			// Verifying the Source Language value with the Translated Value

			WebElement value = driver.findElement(By.xpath("//span[@jsname='W297wb']"));
			String textvalue = value.getText();

			System.out.println("The Translated value is " + textvalue);

			Assert.assertEquals(translatedText, textvalue); // Verified the translated value

			// Swap the language
			driver.findElement(By.xpath("(//div[@class='VfPpkd-Bz112c-RLmnJb'])[2]")).click();

			driver.findElement(By.xpath("(//div[@class='VfPpkd-Bz112c-RLmnJb'])[14]")).click();

			WebElement sourceValue2 = driver.findElement(By.xpath("//textarea[@aria-label='Source text']"));

			sourceValue2.sendKeys(swapText);// Swap the language

			WebElement value2 = driver.findElement(By.xpath("//span[@jsname='W297wb']"));

			String textvalue2 = value2.getText(); // Getting the second text value
			System.out.println("The Translated value 2 is " + textvalue2);

			Assert.assertEquals(swappedText, textvalue2); // Verified the second translated value

		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	}
}
