package utils;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.info("======================================== STARTING TEST {} ========================================",
                result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("======================================== FINISHED TEST {} Duration: {}s ========================================",
                result.getName(), getExecutionTime(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("======================================== FAILED TEST {} Duration: {}s ========================================",
                result.getName(), getExecutionTime(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("======================================== SKIPPING TEST {} ========================================",
                result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Не используется
    }

    @Override
    public void onStart(ITestContext context) {
        // Не используется
    }

    @Override
    public void onFinish(ITestContext context) {
        // Не используется
    }

    private long getExecutionTime(ITestResult result) {
        return TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
    }

    @Attachment(value = "Скриншот при падении", type = "image/png")
    public byte[] takeScreenshot() {
        return Selenide.screenshot(OutputType.BYTES);
    }
}