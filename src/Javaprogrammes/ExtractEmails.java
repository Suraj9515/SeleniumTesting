package Javaprogrammes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtractEmails {
    public static void main(String[] args) throws InterruptedException {
        // Set the path to the ChromeDriver executable
       // System.setProperty("webdriver.gecko.driver", "F:\\Selenium Workspaces\\Automation Testing\\WebDriver133\\geckodriver.exe");
    	WebDriverManager.chromedriver().setup();
        // Initialize the WebDriver (Chrome in this case)
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the webpage
            driver.get("https://web.whatsapp.com/");

            // Wait for the page to load completely using Explicit Wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            // Example: Wait until a specific element is visible that indicates the page is loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Chats']")));  // Replace with an actual element

            // Alternatively, wait for the page to be completely loaded
           //wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));


         // Group names that you want to click
            String[] groupNames = {"Premium QA Job's - 8", "QA IDEA 77"};

         //identify the group search box element
            WebElement groupsearchbox = driver.findElement(By.xpath("//div[@aria-label=\"Search\"]"));

          //Click On the Required Group that provided
            for (String groupName : groupNames) {
                // Generate the XPath based on the group name

               groupsearchbox.click();
               groupsearchbox.clear();
               Thread.sleep(3000);
               groupsearchbox.sendKeys(groupName);
               Thread.sleep(3000);
               groupsearchbox.sendKeys(Keys.ENTER);
               Thread.sleep(10000);

             //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
            // Get the active DOM (HTML) of the page
            String pageSource = driver.getPageSource();

            // Extract emails from the DOM
            String[] emails = extractEmails(pageSource);

            // Print the extracted emails
            for (String email : emails) {
                System.out.println(email);
            }

            // Save the emails to a text file
            saveEmailsToFile(emails, "Postemailids.txt");

        }
        }
        finally {
            // Close the driver
            driver.quit();
        }
    }

    // Function to extract emails using regex
    public static String[] extractEmails(String pageSource) {
        // Regular expression to match email addresses
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(pageSource);

        // Collect matched emails
        StringBuilder emails = new StringBuilder();
        while (matcher.find()) {
            emails.append(matcher.group()).append("\n");
        }

        return emails.toString().split("\n");
    }

    // Function to save emails to a text file
    public static void saveEmailsToFile(String[] emails, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename), true))) {
            // Write each email to the file
            for (String email : emails) {
                writer.write(email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
