package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;

public class SearchTest extends TestConfig {

    @Test // TODO: add DataProvider
    public void successfulSearchingProducts() {
        new Home()
                .submitSearch("dress")
                .userShouldSeeProductsGrid();
    }

    @Test // TODO: add DataProvider
    public void noResultsOfSearchingProducts() {
        new Home()
                .submitSearch("dresss")
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
