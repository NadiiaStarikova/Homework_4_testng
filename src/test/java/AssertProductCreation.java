import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssertProductCreation {

    @Test
    public void validateNewProductCreation() {
        StringBuilder productName  = ProductCreation.createdProductName;
        String expectedName = productName.toString();
        String expectedQuantity = ProductCreation.createdProductQuantity;
        String expectedPrice = ProductCreation.createdProductPrice;

        WebDriver driver = new ChromeDriver();
        //Переход на главную страницу магазина -> Все товары
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text()='Все товары']/..")));
        WebElement allGoodsButton = driver.findElement(By.xpath(".//*[text()='Все товары']/.."));
        allGoodsButton.click();

        //Проверка наличия созданного продукта на странице магазина
        assertTrue("No new product on the page.", driver.findElements(By.linkText(expectedName)).size() > 0);
        System.out.println("New product " + expectedName + " is present on the page");
        //Переход на страницу продукта
        WebElement newGood = driver.findElement(By.linkText(expectedName));
        newGood.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("h1")));

        //Проверки соответствия названия , количества и цены тем значениям, которые вводились при создании продукта
        WebElement actualName = driver.findElement(By.className("h1"));
        assertEquals("Product name is incorrect", expectedName, actualName);
        System.out.println("Expected product name: " + expectedName + ". Actual product name: "  + actualName + ".");
        WebElement productQuantity = driver.findElement(By.className("product-quantities"));
        String actualQuantity = productQuantity.getText().substring(11, 13);
        assertEquals("Quantity of product is incorrect", expectedQuantity, actualQuantity);
        System.out.println("Expected product quantity: " + expectedQuantity + ". Actual product quantity: "  + actualQuantity + ".");
        WebElement productPrice = driver.findElement(By.className("current-price"));
        String actualPrice = productPrice.getText().substring(0, 5);
        assertEquals("Price of product is incorrect", expectedPrice, actualPrice);
        System.out.println("Expected product price: " + expectedPrice + ". Actual product price: "  + actualPrice + ".");
    }
}