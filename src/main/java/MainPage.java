import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By siteButton = By.xpath("//*[@id='logo_holder']/a/img");
    private By StoreButton = By.xpath("//*[@id='global_header']//a[@data-tooltip-content='.submenu_store']");
    //всплывающее меню russian english spanish
    private By LanguageListbox = By.xpath("//*[@id='language_pulldown']");
    private By setLanguageSpanish = By.xpath("//a[contains(@class,'popup_menu') and contains(@href,'?l=spanish')]");
    private By setLanguageEnglish = By.xpath("//a[contains(@class,'popup_menu') and contains(@href,'?l=english')]");
    private By setLanguageRussian = By.xpath("//a[contains(@class,'popup_menu') and contains(@href,'?l=russian')]");
    private By searchField = By.xpath("//*[@id='store_nav_search_term']");
    private By searchButton = By.xpath("//*[@id='store_search_link']/img") ;

    public String getNameStoreButton() {

        WebElement element = driver.findElement(StoreButton);
        String elementName = element.getAttribute("innerText");
        return elementName;

    }
    public void pushSiteButton() {
        driver.findElement(siteButton).click();
    }
//  не стал делать дополнительную проверку на язык страницы с которым она открывается изначально, т.к. если она открывается на нужном нам языке, то этого элемента нет
//  в списке. и тогда по прошествии неявного времени тест просто идет дальше. то есть понятно что страница уже на нужном языке.
    public MainPage setLanguage(String language) {
        driver.findElement(LanguageListbox).click();
        switch (language) {
            case "english":
                try {
                    driver.findElement(setLanguageEnglish).click();
                } finally {
                    break;
                }
            case "spanish":
                try {
                    driver.findElement(setLanguageSpanish).click();
                } finally {
                    break;
                }

            case "russian":
                try {
                    driver.findElement(setLanguageRussian).click();
                } finally {
                    break;
                }
            default:
                System.out.println("Для указанного языка тест не предусмотрен!");
                break;
        }
        return this;
    }
    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public void typeSearchInfo(String gamename) {
        driver.findElement(searchField).sendKeys(gamename);
    }
}
