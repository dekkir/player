package dek21.tests;

import dek21.utils.ReadProperties;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.SneakyThrows;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

import static dek21.utils.Helper.replaceSlash;

public class CommonTest {

    private PrintStream log;

    private File fileLog;

    protected static ReadProperties config;

    @BeforeClass
    @SneakyThrows
    public void prepareLogFile() {
        fileLog = new File(
                replaceSlash(config.getValue("LOG_PATH"))
                        + this.getClass().getSimpleName()
                        + "_log.txt"
        );
        fileLog.getParentFile().mkdirs();
        fileLog.createNewFile();
        log = new PrintStream(fileLog);
    }

    @AfterClass(description = "Add logs to Allure Report")
    @SneakyThrows
    public void addLogToAllure() {
        log.close();

        Allure.addAttachment("Logs", new FileInputStream(fileLog));
    }

    @BeforeClass
    public void setRestAssured() {
        RestAssured.filters(
                RequestLoggingFilter.logRequestTo(log),
                ResponseLoggingFilter.logResponseTo(log),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @BeforeClass
    public static void configureEndPoints() {
        config = ReadProperties.getInstance();
        RestAssured.baseURI = config.getValue("BASE_URL");
    }
}
