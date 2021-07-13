import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private SearchPage searchPage;
    private float[] searchResult;
    private boolean check = true;
    private List<WebElement> elements;
    private WebDriverWait wait;
    @BeforeTest
    void profileSetup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://store.steampowered.com/");
        mainPage = new MainPage(driver);
    }

    @Parameters({"language", "gamename", "quantityChecks"})
    @Test(alwaysRun = true)
    public void testGameSearch(String language, String gamename, Integer quantityChecks) {
//  Выполняем всю последовательность действий по заданию.
        mainPage.setLanguage(language);
        driver.navigate().refresh();
        mainPage.typeSearchInfo(gamename);
        mainPage.clickSearchButton();
        searchPage = new SearchPage(driver);
        searchPage.selectSortByHiestPrice();
        driver.navigate().refresh();
        elements = searchPage.getGetListHighestPrice();
//        получаем из массива вебэлементов цену и переводим ее во флоат
        searchResult = searchPage.getPrices(elements,quantityChecks);
//        проверяем правильность сортировки цен по убывающей
        for (int i = 0; i < searchResult.length-1; i++) {
            if (searchResult[i] < searchResult[i+1]) {
                check = false;
                break;
            };
        }
        Assert.assertTrue(check);
    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}