package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderNegativeTest {
    private WebDriver driver;

    String nameField = "[data-test-id=name] input";
    String phoneField = "[data-test-id=phone] input";
    String checkbox = "[data-test-id=agreement] span";
    String button = "button";


    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldNotSendFormInvalidNameFieldEnglish() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector(phoneField)).sendKeys("+79998887766");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidNameFieldWithNumbers() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Иванов Иван999");
        driver.findElement(By.cssSelector(phoneField)).sendKeys("+79998887766");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidNameFieldEmpty() {
        driver.findElement(By.cssSelector(phoneField)).sendKeys("+79998887766");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldLetters() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(phoneField)).sendKeys("телефон");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldFewNumbers() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(phoneField)).sendKeys("+7999");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldWithoutPlus() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(phoneField)).sendKeys("79998887766");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldEmpty() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(checkbox)).click();
        driver.findElement(By.cssSelector(button)).click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormUncheckedCheckbox() {
        driver.findElement(By.cssSelector(nameField)).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(phoneField)).sendKeys("+79998887766");
        driver.findElement(By.cssSelector(button)).click();

        String checkboxInvalid = driver.findElement(By.cssSelector("[data-test-id=agreement].checkbox")).getAttribute("className");
        assertTrue(checkboxInvalid.contains("input_invalid"));
    }
}
