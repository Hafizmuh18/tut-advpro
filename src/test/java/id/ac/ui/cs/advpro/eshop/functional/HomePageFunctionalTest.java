package id.ac.ui.cs.advpro.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class HomePageFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception{
        driver.get(baseUrl+"/product/list");
        String pageTitle = driver.getTitle();

        // Verify
        assertEquals("Product List", pageTitle);
    }

    @Test
    void welcomeMessage_homePage_isCorrect(ChromeDriver driver) throws Exception{
        // Excercise
        driver.get(baseUrl+"/product/list");
        String welcomeMessage = driver.findElement(By.tagName("h2")).getText();

        // Verify
        assertEquals("Product List", welcomeMessage);
    }
}