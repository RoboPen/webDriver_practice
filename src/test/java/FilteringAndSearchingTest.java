import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class FilteringAndSearchingTest {
    @Test
    public void SearchingFeaturedBrand() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.amazon.com/b?node=16225007011&pf_rd_r=SM5D2S60J1PS1V880E7X&pf_rd_p=e5b0c85f-569c-4c90-a58f-0c0a2" +
                "60e45a0&pd_rd_r=34a43d50-1a1b-42dc-a688-e6ed63e03ca3&pd_rd_w=NoO9r&pd_rd_wg=zILV6&ref_=pd_gw_unk");

        WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/div/div[5]/ul/li[1]/span/a/span"));
        searchButton.click();

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/span[3]"))
                .findElements(By.className("a-size-base-plus"))
                .forEach(webElement -> Assert.assertTrue(webElement.getText().contains("TAKAGI")));

        driver.close();
        driver.quit();
    }

    @Test
    public void FilteringPrice() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.amazon.com/b?node=16225007011&pf_rd_r=SM5D2S60J1PS1V880E7X&pf_rd_p=e5b0c85f-569c-4c90-a58f-0c0a2" +
                "60e45a0&pd_rd_r=34a43d50-1a1b-42dc-a688-e6ed63e03ca3&pd_rd_w=NoO9r&pd_rd_wg=zILV6&ref_=pd_gw_unk");

        WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/div/div[12]/ul/li[1]/span"));
        searchButton.click();

        float minPrice = 0;
        float maxPrice = 25;

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/span[3]/div[2]"))
                .findElements(By.className("a-price-whole"))
                .forEach(webElement -> {
                    float price = Float.parseFloat(webElement.getText());
                    Assert.assertTrue(price <= maxPrice);
                    Assert.assertTrue(price >= minPrice);

                });


        driver.close();
        driver.quit();
    }

    @Test
    public void SortingByPrice() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.amazon.com/b?node=16225007011&pf_rd_r=SM5D2S60J1PS1V880E7X&pf_rd_p=e5b0c85f-569c-4c90-a58f-0c0a2" +
                "60e45a0&pd_rd_r=34a43d50-1a1b-42dc-a688-e6ed63e03ca3&pd_rd_w=NoO9r&pd_rd_wg=zILV6&ref_=pd_gw_unk");

        WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/div/div[5]/ul/li[1]/span/a/span"));
        searchButton.click();

        WebElement searchDropdownList = driver.findElement(By.id("a-autoid-0-announce"));
        searchDropdownList.click();

        WebElement selectDropdownList = driver.findElement(By.id("s-result-sort-select_1"));
        selectDropdownList.click();


        List<Float> pricesLowestToHighest = new ArrayList<>();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/span[3]/div[2]"))
                .findElements(By.className("a-price-whole"))
                .forEach(webElement -> {
                    float price = Float.parseFloat(webElement.getText());
                    pricesLowestToHighest.add(price);
                });

        assertAscendingPrices(pricesLowestToHighest);

        driver.close();
        driver.quit();
    }

    private void assertAscendingPrices(List<Float> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) < prices.get(i + 1));
        }
    }
}
