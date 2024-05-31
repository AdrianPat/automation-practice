package utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.IOException;

import static config.WebDriverSingleton.getInstance;

public abstract class Screenshots {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] captureFullPageScreenshot() {
        byte[] screenshot = {};
        try {
            screenshot = Shutterbug.shootPage(getInstance(), Capture.FULL, true).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshot;
    }

    /* @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] captureScreenshot() {
        return ((TakesScreenshot) getInstance()).getScreenshotAs(OutputType.BYTES);
    } */
}
