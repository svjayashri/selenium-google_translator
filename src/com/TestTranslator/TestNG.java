package com.TestTranslator;

import java.time.Duration;
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
		driver.manage().deleteAllCookies();
	}

	@Test
	@Parameters({ "sourceLanguage", "translationLanguage", "sourceText", "translatedText", "swapText", "swappedText" })

	public void googlePage(String sourceLanguage, String translationLanguage, String sourceText, String translatedText,
			String swapText, String swappedText) throws Exception {
		
		try {
			// Source Language Selection
			
			driver.get("https://translate.google.com/");
			System.out.println("The title of the page is " + driver.getTitle());

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

			driver.findElement(By.xpath("(//div[contains(@class,'VfPpkd-Bz112c-RLmnJb')])[1]")).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[1]")).click();
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[1]"))
					.sendKeys(sourceLanguage);

			System.out.println("The Source Language is " + sourceLanguage);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[1]")).sendKeys(Keys.ENTER);

			// Translation Language
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//div[@class='VfPpkd-Bz112c-RLmnJb'])[3]")).click();
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[2]")).click();
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[2]"))
					.sendKeys(translationLanguage);
			System.out.println("The Translation Language is " + translationLanguage);
			driver.findElement(By.xpath("(//input[contains(@aria-label,'Search languages')])[2]")).sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

			// Verifying the Source Language value with the Translated Value
			WebElement sourceValue = driver.findElement(By.xpath("//textarea[@aria-label='Source text']"));

			sourceValue.sendKeys(sourceText);
			System.out.println("The Source Text is " + sourceText);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement value = driver.findElement(By.xpath("//span[@jsname='W297wb']"));
			String textvalue = value.getText();

			Assert.assertEquals(translatedText, textvalue); // Verified the translated value
			System.out.println("The Translated value is " + textvalue);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			// Swap the language
			
			driver.findElement(By.xpath("(//div[@class='VfPpkd-Bz112c-RLmnJb'])[2]")).click();

			driver.findElement(By.xpath("(//div[@class='VfPpkd-Bz112c-RLmnJb'])[14]")).click();

			WebElement sourceValue2 = driver.findElement(By.xpath("//textarea[@aria-label='Source text']"));

			sourceValue2.sendKeys(swapText);
			
			System.out.println("The Swapped Text is " + swapText);

			WebElement value2 = driver.findElement(By.xpath("//span[@jsname='W297wb']"));

			String textvalue2 = value2.getText(); // Getting the second text values
			System.out.println("The Translated value 2 is " + textvalue2);

			Assert.assertEquals(swappedText, textvalue2); // Verified the second translated value

		} catch (Exception e) {
			throw (e);
		}
	}

	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	}
}
