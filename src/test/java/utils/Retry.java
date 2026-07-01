package utils;

import lombok.extern.log4j.Log4j2;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Log4j2
public class Retry implements IRetryAnalyzer {

    private static final int MAX_RETRY = 3;
    private int attempt = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {//проверяем, если тест не пройден успешно
            if (attempt < MAX_RETRY) {//проверяем, достигнуто ли максимальное количество попыток
                attempt++;// увеличиваем maxTry count на 1
                iTestResult.setStatus(ITestResult.FAILURE); //отметить тест как неудавшийся
                log.warn("Повторение еще раз");
                return true;//сообщает TestNG о необходимости повторного запуска теста
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);//если maxCount достигнут, тест помечается как проваленный
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);//если тест пройден, TestNG отмечает его как пройденный
        }
        return false;
    }
}
