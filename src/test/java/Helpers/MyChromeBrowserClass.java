package Helpers;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.model.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.qameta.allure.Allure.step;

public class MyChromeBrowserClass implements WebDriverProvider {

    @SuppressWarnings("deprecation")
    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        WebDriverManager.chromedriver().setup();

        capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());

        try {
            return new ChromeDriver(capabilities);
        } catch (Exception e) {
            e.printStackTrace();
            step("ChromeDriver error: " + e.getMessage(), Status.FAILED);
            throw e;
        }
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("disable-infobars");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);

        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

}
