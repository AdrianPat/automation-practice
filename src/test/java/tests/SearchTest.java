package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;
import utilities.DataProviders;

public class SearchTest extends TestConfig {

    @Test(dataProvider = "dataSetWithProductsToSearch", dataProviderClass = DataProviders.class)
    public void successfulSearchingProducts(String product) {
        new Home()
                .submitSearch(product)
                .userShouldSeeProductsGrid();
    }

    @Test(dataProvider = "dataSetForInvalidSearch", dataProviderClass = DataProviders.class)
    public void noResultsOfSearchingProducts(String product) {
        new Home()
                .submitSearch(product)
                .userShouldSeeNoResultsAlert();
    }

    @Test
    public void noResultsForSearchingEmptyPhrase() {
        new Home()
                .submitSearch("")
                .userShouldSeePleaseEnterASearchKeywordAlert();
    }

    @Test
    public void changeViewFromGridToListShouldWork() {
        new Home()
                .submitSearch("dress")
                .changeViewFromGridToList()
                .userShouldSeeProductsList();
    }

    @Test
    public void changeViewFromListToGridShouldWork() throws InterruptedException {
        new Home()
                .submitSearch("dress")
                .changeViewFromGridToList()
                .changeViewFromListToGrid()
                .userShouldSeeProductsGrid();
    }

    @Test
    public void successfulNextSearchingOfProducts() {
        new Home()
                .submitSearch("dress")
                .nextSearchingOfProducts("blouse")
                .userShouldSeeProductsGrid();
    }
}
