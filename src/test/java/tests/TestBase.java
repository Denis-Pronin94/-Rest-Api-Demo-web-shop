package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase{
    static CredentialConfig config = ConfigFactory.create(CredentialConfig.class);
    static String login = config.login(),
            password = config.password(),
            authCookieName = config.authCookieName();

    @BeforeAll
    static void setUp() throws Exception {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        CredentialConfig config = ConfigFactory.create(CredentialConfig.class);
        String selenoidLogin = config.selenoidLogin();
        String selenoidPassword = config.selenoidPassword();
        String selenoidServer = System.getProperty("selenoid_server", "selenoid.autotests.cloud/wd/hub");
        String login = config.login();
        String password = config.password();
        String authCookieName = config.authCookieName();
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
        Configuration.browserSize = "1920x1080";
        Configuration.remote = "https://" + selenoidLogin + ":" + selenoidPassword + "@" +
                selenoidServer;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

    }
    @BeforeEach
    void openBaseUrl() {
        open("");
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }


    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}
