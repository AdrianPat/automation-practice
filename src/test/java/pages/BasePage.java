package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static config.WebDriverSingleton.getInstance;
import static utilities.Actions.waitForVisibilityOfElement;

public abstract class BasePage { // abstract blokuje tworzenie obiektów tej klasy

    public BasePage() {
        PageFactory.initElements(getInstance(), this); // this inicjalizuje stronę, która jest aktualnie wykorzystywana
        waitForVisibilityOfElement(pageContent); // musi być widoczna zawartość strony, żeby test mógł działać dalej
    }

    @FindBy(id = "columns")
    private WebElement pageContent;
}
