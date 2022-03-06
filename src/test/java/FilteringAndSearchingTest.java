import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class FilteringAndSearchingTest {

    public WebDriver driver;

    @BeforeTest
    public void driverSetupPageOpening() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://www.amazon.com/b?node=16225007011&pf_rd_r=SM5D2S60J1PS1V880E7X&pf_rd_p=e5b0c85f-569c-4c90-a58f-0c0a2" +
                "60e45a0&pd_rd_r=34a43d50-1a1b-42dc-a688-e6ed63e03ca3&pd_rd_w=NoO9r&pd_rd_wg=zILV6&ref_=pd_gw_unk");
    }

    @AfterTest
    public void driverClose() {
        driver.close();
        driver.quit();
    }

    @Test
    public void searchingFeaturedBrand() {

        WebElement searchButton = driver.findElement(By.xpath("//span[text() = \"TAKAGI\"]"));
        searchButton.click();


        driver.findElements(By.xpath("//span[@data-component-type=\"s-search-results\"]"))
                .forEach(webElement -> Assert.assertTrue(webElement.getText().contains("TAKAGI")));

    }

    @Test
    public void filteringPrice() {

        WebElement searchButton = driver.findElement(By.xpath("//span[text() = \"Under $25\"]"));
        searchButton.click();

        float minPrice = 0;
        float maxPrice = 25;

        driver
                .findElements(By.xpath("//span[@class=\"a-price\"]/span[@class=\"a-offscreen\"]"))
                .forEach(webElement -> {
                    float price = Float.parseFloat(webElement.getAttribute("textContent").replace("$", ""));
                    Assert.assertTrue(price <= maxPrice);
                    Assert.assertTrue(price >= minPrice);

                });
    }

    @Test
    public void sortingByPrice() {

        WebElement searchButton = driver.findElement(By.xpath("//span[text() = \"TAKAGI\"]"));
        searchButton.click();

        WebElement searchDropdownList = driver.findElement(By.id("a-autoid-0-announce"));
        searchDropdownList.click();

        WebElement selectDropdownList = driver.findElement(By.id("s-result-sort-select_1"));
        selectDropdownList.click();


        List<Float> pricesLowestToHighest = new ArrayList<>();
        driver.findElements(By.className("a-price-whole"))
                .forEach(webElement -> {
                    float price = Float.parseFloat(webElement.getText());
                    pricesLowestToHighest.add(price);
                });

        assertAscendingPrices(pricesLowestToHighest);

    }

    private void assertAscendingPrices(List<Float> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1));
        }
    }
}
