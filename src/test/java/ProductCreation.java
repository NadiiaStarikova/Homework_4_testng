import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.testng.annotations.DataProvider;

public class ProductCreation extends Annotations {

    public static final StringBuilder createdProductName = productNameGenerator();
    public static final String createdProductQuantity = productQuantityGenerator();
    public static final String createdProductPrice = productPriceGenerator();

    @Test//(dataProvider = "user1")
    public void productCreation() {
        //Вход в Админ Панель
        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        driver.findElement(By.id("email")).sendKeys("webinar.test@gmail.com");
        driver.findElement(By.id("passwd")).sendKeys("Xcg7299bnSmMuRLp9ITw");
        driver.findElement(By.className("ladda-label")).click();

        //Явное ожидание входа в Админ Панель
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("subtab-AdminCatalog")));
        //Выбор пункта меню Каталог -> товары
        WebElement adminCatalog = driver.findElement(By.id("subtab-AdminCatalog"));
        Actions actions = new Actions(driver);
        actions.moveToElement(adminCatalog).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("subtab-AdminCatalog")));
        adminCatalog.findElements(By.cssSelector("li")).get(0).click();
        //Явное ожидание загрузки страницы управления категориями и появления кнопки «Новый товар»
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("page-header-desc-configuration-add")));
        //Нажатие кнопки «Новый товар» для перехода к созданию нового товара
        WebElement newProduct = driver.findElement(By.id("page-header-desc-configuration-add"));
        newProduct.click();
        //Явное ожидание загрузки страницы
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form_step1_name_1")));
        //Ввод названия, количества и цены создаваемого продукта
        WebElement productName = driver.findElement(By.id("form_step1_name_1"));
        productName.sendKeys(createdProductName);
        WebElement productQuantity = driver.findElement(By.id("form_step1_qty_0_shortcut"));
        productQuantity.clear();
        productQuantity.sendKeys(createdProductQuantity);
        WebElement productPrice = driver.findElement(By.name("form[step1][price_shortcut]"));
        productPrice.clear();
        productPrice.sendKeys(createdProductPrice);
        //Активация продукта
        WebElement activeButton = driver.findElement(By.className("switch-input"));
        activeButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("growl-message")));
        WebElement growlClose = driver.findElement(By.className("growl-close"));
        growlClose.click();
        //Сохранение продукта
        WebElement saveButton = driver.findElement(By.id("submit"));
        saveButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("growl-message")));
        growlClose.click();
    }
    private static StringBuilder productNameGenerator() {
        String symbols = "abcdefqwerty";
        StringBuilder prodName = new StringBuilder();
        int count = (int)(Math.random()*15);
        for(int i=0;i<count;i++)
            prodName.append(symbols.charAt((int)(Math.random()*symbols.length())));
        return prodName;
    }
    private static String productQuantityGenerator() {
        Random randQuantity = new Random();
        int productQuantity = randQuantity.nextInt(100);
        return Integer.toString(productQuantity);
    }
    private static String productPriceGenerator() {
        float minY = 0.1f;
        float maxY = 100.0f;
        Random randPrice = new Random();
        float productPrice = randPrice.nextFloat() * (maxY - minY) + minY;
        return String.format("%.2f", productPrice);
    }
}