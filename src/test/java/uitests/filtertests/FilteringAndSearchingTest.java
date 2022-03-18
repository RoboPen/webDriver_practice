package uitests.filtertests;

import org.course.filterpages.MainPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class FilteringAndSearchingTest extends BaseTest {

    private MainPage mainPage;

    @BeforeTest
    public void setup() {
        setupDriver();
        mainPage = new MainPage(webDriver);
    }

    @AfterTest
    public void cleanup() {
        stop();
    }

    @DataProvider(name = "brands")
    public Object[][] brands() {
        return new Object[][] {
                { "TAKAGI" },
                { "HP" },
                { "Seagate" },
                { "Apple" }
        };
    }

    @Test(dataProvider = "brands")
    public void shouldDisplayProductsOnlyForFilteredBrandWhenFilterApplied(String brand) {
        mainPage.open()
                .computersCategoryClick()
                .chooseFeaturedBrand(brand)
                .getBrandFilteringResults()
                .forEach(result -> Assert.assertTrue(result.contains(brand)));
    }

    @Test
    public void shouldDisplayProductsWithPriceUnder25$() {
        float minPrice = 0;
        float maxPrice = 25;
        mainPage.open()
                .computersCategoryClick()
                .choosePriceRangeUnder25()
                .getPriceFilteringResults()
                .forEach(result -> {
                    float price = Float.parseFloat(result);
                    Assert.assertTrue(price <= maxPrice);
                    Assert.assertTrue(price >= minPrice);
                });
    }

    @Test(dataProvider = "brands")
    public void shouldSortProductsLowestToHighestPriceOrder(String brand) {
        List<Float> prices = mainPage.open()
                .computersCategoryClick()
                .chooseFeaturedBrand(brand)
                .chooseLowestToHighestSorting()
                .getPriceSortingResults();

        assertAscendingPrices(prices);
    }

    private void assertAscendingPrices(List<Float> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            float lowerPrice = prices.get(i);
            float higherPrice = prices.get(i + 1);
            Assert.assertTrue(lowerPrice <= higherPrice, lowerPrice + " should be lower than " + higherPrice);
        }
    }
}
