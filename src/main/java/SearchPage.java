import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.NumberFormat;
import java.util.List;


public class SearchPage {

    private WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    private float[] prices;
    private int schetchikPustyhStrok = 0;
    private int size;
    private List<WebElement> listResultSearch;
    private By sortTrigger = By.xpath("//*[@id='sort_by_trigger']");
    private By highestPrice = By.xpath("//*[@id='Price_DESC']");
    private By listSearchResult = By.xpath("//*[@id='search_resultsRows']/a/div[2]/div[4]/div[2]");

    public SearchPage selectSortByHiestPrice() {
        driver.findElement(sortTrigger).click();
        driver.findElement(highestPrice).click();
        return this;
    }

    public List<WebElement> getGetListHighestPrice() {
        listResultSearch = driver.findElements(listSearchResult);
        return listResultSearch;
    }
//  переводим цены товаров в списке вебэлементов в флоат. к ценам обращаемся через аттрибут innerText. т.к. там бывает несколько цен то отсекаем сначала последний .руб
//  потом по индексу оставшегося .руб, если он есть отсекаем сначала.пришлось ввести счетчик пустых строк, т.к. в результатах поиска появились в середине списка пустые
//  цены. я предположил, что такая ситуация должна давать положительную проверку, и при заполнении массива флоат, просто пропускал эти позиции
    public float[] getPrices(List<WebElement> elements, int quantityChecks) {
        if (elements.size() < quantityChecks) {
            size = getGetListHighestPrice().size();
        } else size = quantityChecks;
        float[] prices = new float[quantityChecks];
        for (int i = 0; i < size; i++) {
            if (elements.get(i).getAttribute("innerText") != "") {
                String text = elements.get(i).getAttribute("innerText").substring(0, elements.get(i).getAttribute("innerText").length() - 5);
                text = text.replaceAll(",", ".");
                if (text.indexOf('б') != -1) {
                    text = text.substring(text.indexOf('б') + 2);
                }
                prices[i-schetchikPustyhStrok] = Float.parseFloat(text);
            }
            else schetchikPustyhStrok++;
        }
        for (float price: prices) System.out.println(price);
        return prices;
    }
}

