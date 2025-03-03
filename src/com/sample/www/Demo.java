package com.sample.www;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Demo {
     @Test
	public void startUp() throws InterruptedException {
	WebDriverManager.firefoxdriver().setup();
	WebDriver driver=new FirefoxDriver();

	driver.get("https://demoqa.com/droppable/");

	Thread.sleep(3000);

	//WebElement ele = driver.findElement(By.className("nDcEnd"));

	//Actions a=new Actions(driver);

	//a.moveToElement(ele).click().perform();

	WebElement dragablle = driver.findElement(By.id("draggable"));

	Thread.sleep(3000);
	WebElement droparea = driver.findElement(By.id("droppable"));

	Actions a=new Actions(driver);
	//a.dragAndDrop(dragablle, droparea).perform();
	a.moveToElement(dragablle).clickAndHold(dragablle).moveToElement(droparea).release(dragablle).perform();

}
}
