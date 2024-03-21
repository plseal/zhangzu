package com.plseal.zhangzu;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class BaseSeleniumTests {

    protected WebDriver driver;

    @Before
    public void setUp() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:/Github/zhangzu/src/test/resources/chromedriver.exe"))
                .build();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        // options.addArguments("--headless");
        // options.setExperimentalOption("useAutomationExtension", false);
        // options.addArguments("start-maximized"); // open Browser in maximized mode
        // options.addArguments("disable-infobars"); // disabling infobars
        // options.addArguments("--disable-extensions"); // disabling extensions
        // options.addArguments("--disable-gpu"); // applicable to windows os only
        // options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.merge(capabilities);
        //chrome.exeバージョン: 96.0.4664.110（Official Build） （64 ビット）
        options.setBinary("C:/Program Files/Google/Chrome/Application/chrome.exe");
        this.driver = new ChromeDriver(service, options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}