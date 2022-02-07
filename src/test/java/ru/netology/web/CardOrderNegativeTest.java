package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderNegativeTest {
    private WebDriver driver;

    private WebElement nameField;
    private WebElement phoneField;
    private WebElement checkbox;
    private WebElement button;

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
        nameField = driver.findElement(By.cssSelector("[data-test-id=name] input"));
        phoneField = driver.findElement(By.cssSelector("[data-test-id=phone] input"));
        checkbox = driver.findElement(By.cssSelector("[data-test-id=agreement] span"));
        button = driver.findElement(By.cssSelector("button"));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldNotSendFormInvalidNameFieldEnglish() {
        nameField.sendKeys("Ivanov Ivan");
        phoneField.sendKeys("+79998887766");
        checkbox.click();
        button.click();

        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidNameFieldWithNumbers() {
        nameField.sendKeys("Иванов Иван999");
        phoneField.sendKeys("+79998887766");
        checkbox.click();
        button.click();

        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidNameFieldEmpty() {
        phoneField.sendKeys("+79998887766");
        checkbox.click();
        button.click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldLetters() {
        nameField.sendKeys("Иванов Иван");
        phoneField.sendKeys("телефон");
        checkbox.click();
        button.click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldFewNumbers() {
        nameField.sendKeys("Иванов Иван");
        phoneField.sendKeys("+7999");
        checkbox.click();
        button.click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldWithoutPlus() {
        nameField.sendKeys("Иванов Иван");
        phoneField.sendKeys("79998887766");
        checkbox.click();
        button.click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormInvalidPhoneFieldEmpty() {
        nameField.sendKeys("Иванов Иван");
        checkbox.click();
        button.click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();

        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldNotSendFormUncheckedCheckbox() {
        nameField.sendKeys("Иванов Иван");
        phoneField.sendKeys("+79998887766");
        button.click();

        String checkboxInvalid = driver.findElement(By.cssSelector("[data-test-id=agreement].checkbox")).getAttribute("className");
        assertTrue(checkboxInvalid.contains("input_invalid"));
    }
}
