package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import owner.BaseURLOwner;
import owner.DataForWebShop;
import owner.RemoteURLserver;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setUp() {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        //Задаю базовые ссылки для Webshop тестов
        BaseURLOwner linkbaseConfig = ConfigFactory.create(BaseURLOwner.class);
        Configuration.baseUrl = linkbaseConfig.baseURL();
        RestAssured.baseURI = linkbaseConfig.baseURI();

        //Задаю базовые URL удаленного сервера
        RemoteURLserver remoteURL = ConfigFactory.create(RemoteURLserver.class);
        Configuration.remote = "https://" + remoteURL.loginremoteurl() + ":" + remoteURL.passwordremoteurl() + "@" +
                System.getProperty("server_selenoid", "selenoid.autotests.cloud/wd/hub");
        Configuration.browserSize = System.getProperty("browser_size", "1920x1080");

        //Подключение видео для Allure
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    // Что бы после каждого теста собирались артефакты выполнения теста
    @AfterEach
    void afterEach() {
        Attach.screenshotAs("Скриншот выполненного теста");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}
