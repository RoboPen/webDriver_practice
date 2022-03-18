package uitests.addremovetests;

import org.course.pageobjects.cartpages.CartPage;
import org.course.pageobjects.cartpages.MainPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddRemoveTest extends BaseTest {

    public MainPage mainPage;
    public CartPage cart;


    @BeforeTest
    public void testSetup() {
        setUpDriver();
        initObjects();
        mainPage.open().searchCategory().drawAnItem().addToTheCart();
    }

    @AfterTest
    public void driverClose() {
        quit();
    }

    @Test
    public void verifyItemWasSuccessfullyAddedToCart() {
        Assert.assertEquals(cart.getNumberOfItemsInTheCart(), 1);
        Assert.assertEquals(cart.getAddedToCartConfirmation(), "Added to Cart");
    }

    @Test
    public void verifyItemWasSuccessfullyRemovedFromCart() {
        cart.deleteItemFromTheCart();
        Assert.assertEquals(cart.getCartIsEmptyConfirmation(), "Your Amazon Cart is empty.");
        Assert.assertEquals(cart.getPriceOfItemInTheCart(), "$0.00");
    }

    private void initObjects() {
        mainPage = new MainPage(driver);
        cart = new CartPage(driver);
    }
}
