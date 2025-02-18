package id.ac.ui.cs.advpro.eshop.functional;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception{
        driver.get(baseUrl+"/product/create");
        String pageTitle = driver.getTitle();

        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createProductButton_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");

        WebElement createButton = driver.findElement(By.xpath("//a[text()='Create Product']"));
        createButton.click();

        String pageHeader  = driver.findElement(By.tagName("h3")).getText();
        assertEquals(baseUrl + "/product/create", driver.getCurrentUrl());
        assertEquals("Create New Product", pageHeader);
    }

    @Test
    void createProductPage_sendForm_isCorrect(ChromeDriver driver) throws  Exception {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Susu Cap Bambang");
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("100");

        // Send form
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        submitButton.click();

        WebElement productNameElement = driver.findElement(By.xpath("//td[text()='Susu Cap Bambang']"));
        WebElement productQuantityElement = driver.findElement(By.xpath("//td[text()='100']"));
        assertEquals(baseUrl + "/product/list", driver.getCurrentUrl());
        assertTrue(productNameElement.isDisplayed());
        assertTrue(productQuantityElement.isDisplayed());
    }
}