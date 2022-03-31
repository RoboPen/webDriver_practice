package uitestes.delivertotest;

import org.course.pageobjects.delivertopages.MainPage;
import org.testng.Assert;
import org.testng.annotations.*;


public class DeliverToTest extends BaseTest {
    private MainPage mainPage;

    @BeforeMethod
    public void setUp() {
        setUpDriver();
        mainPage = new MainPage(driver);
    }

    @DataProvider(name = "countries")
    public Object[][] countries() {
        return new Object[][]{
                {"Austria"},
                {"Germany"},
                {"India"}
        };
    }

    @Test(dataProvider = "countries")
    public void verifyShippingCountry(String country) {
        String countryName = mainPage.open()
                .changeCountry(country)
                .headsetsLinkClick()
                .clickFirstResult()
                .getCountryName();

        Assert.assertEquals(countryName, country);
    }

    @DataProvider(name = "zipCodes")
    public Object[][] zipCodes() {
        return new Object[][]{
                {"Montgomery", "36104"},
                {"Phoenix", "85001"},
                {"Santa Fe", "87501"}
        };
    }

    @Test(dataProvider = "zipCodes")
    public void deliveryZipCodeTest(String cityName, String zipCode) {
        mainPage.open()
                .changeZipCode(zipCode);

        Assert.assertTrue(mainPage.getDestinationName().contains(cityName));
    }

    @Test
    public void polandIsPresentOnList() {
        Assert.assertTrue(mainPage.open()
                .getAvailableCountires()
                .contains("Poland"));
    }

    @AfterMethod
    public void tearDown() {
        quit();
    }
}

