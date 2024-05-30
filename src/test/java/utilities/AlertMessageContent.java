package utilities;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AlertMessageContent {

    public static List<String> getAlertContent(List<WebElement> alertContent) {
        List<String> alertMessages = new ArrayList<>();
        for (WebElement message : alertContent) {
            alertMessages.add(message.getText());
        }
        return alertMessages;
    }
}
