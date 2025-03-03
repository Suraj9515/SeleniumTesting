package Javaprogrammes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WhatsAppEmailExtractor {

    public static void main(String[] args) {
        // Path to the ChromeDriver (update as per your system)
        //System.setProperty("webdriver.gecko.driver", "F:\\Selenium Workspaces\\Automation Testing\\WebDriver133\\geckodriver.exe");
    	WebDriverManager.firefoxdriver().setup();
        // Setup Chrome Options
        //ChromeOptions options = new ChromeOptions();
       // options.addArguments("--disable-notifications");

        // Initialize WebDriver
        WebDriver driver = new FirefoxDriver();

        try {
            // Step 1: Open WhatsApp Web (assuming already logged in manually)
            driver.get("https://web.whatsapp.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Step 2: Wait for the page to load fully and for the group to be accessible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[title=\"Premium QA Job's - 8\"]")));

            // Step 3: Navigate to the specific group 'Premium QA Job's - 8'
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[aria-label=\"Search\"]")));
            searchBox.sendKeys("Premium QA Job's - 8");  // Enter group name
            searchBox.sendKeys(Keys.ENTER);  // Press Enter to open the group

            // Step 4: Wait for messages to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._1M9Tg")));

            // Get today's date
            String todayDate = java.time.LocalDate.now().toString();
            System.out.println("Today's date: " + todayDate);

            // List of all messages in the group
            List<WebElement> messages = driver.findElements(By.cssSelector("div._1M9Tg"));
            System.out.println("Found " + messages.size() + " messages.");

            // Pattern to match email addresses
            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

            // Step 5: Extract emails from the messages
            StringBuilder emails = new StringBuilder();
            for (WebElement message : messages) {
                String messageText = message.getText();
                Matcher matcher = emailPattern.matcher(messageText);

                while (matcher.find()) {
                    String email = matcher.group();
                    // If it's a today's post, add email to the string builder
                    if (messageText.contains(todayDate)) {
                        emails.append(email).append("\n");
                        System.out.println("Found email: " + email);
                    }
                }
            }

            // Step 6: Save emails to a text file with today's date
            if (emails.length() > 0) {
                String fileName = "emails_" + todayDate + ".txt";
                File file = new File(fileName);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(emails.toString());
                    System.out.println("Emails saved to " + fileName);
                } catch (IOException e) {
                    System.out.println("Error saving emails to file: " + e.getMessage());
                }
            } else {
                System.out.println("No emails found for today's posts.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            // Step 7: Close the WebDriver
            driver.quit();
        }
    }
}
