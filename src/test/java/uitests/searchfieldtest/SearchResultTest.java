package uitests.searchfieldtest;

import org.course.pageobjects.searchfieldpages.IndexPage;
import org.course.pageobjects.searchfieldpages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchResultTest extends BaseTest {
    private final IndexPage index = new IndexPage(driver);

    @BeforeTest
    public void setup() {
        setUpDriver();
    }

    @AfterTest
    public void tearDown() {
        quit();
    }

    @DataProvider(name = "wrongData")
    public Object[][] wrongData() {
        return new Object[][]{
                {"qwrt4Q&577&&$$$fdsbdrewrewrewrew"},
                {"abcsfghijklmnop"}
        };
    }

    @Test(dataProvider = "wrongData")
    public void verifyNoResultForIncorrectInput(String incorrectProductName) {
        String resultInfo = search(incorrectProductName)
                .getTextForIncorrectProductName();

        Assert.assertEquals(resultInfo, "No results for " + incorrectProductName,
                "Info doesn't contain incorrect input when no results");
    }

    @DataProvider(name = "dataInput")
    public Object[][] dataInput() {
        return new Object[][]{
                {"laptop"},
                {"mouse"}
        };
    }

    @Test(dataProvider = "dataInput")
    public void verifyInputIsPresentInResultInfoBar(String productName) {
        String displayedProductName = search(productName)
                .getProductNameFromResultInfoBar();

        Assert.assertTrue(displayedProductName.contains(productName),
                "Input not present in info bar");
    }

    @Test(dataProvider = "dataInput")
    public void verifyAnyResultTitleContainsInput(String productName) {
        boolean isTitleContainingProductName = search(productName)
                .checkAnyTitleContainsProductName();

        Assert.assertTrue(isTitleContainingProductName,
                "None title contains input");
    }

    private SearchResultPage search(String productName) {
        return index
                .open()
                .searchProduct(productName);
    }
}
