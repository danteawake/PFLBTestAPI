package tests.api;

import adapters.UserAdapter;
import lombok.extern.slf4j.Slf4j;
import models.positive.UserRequest;
import models.positive.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class AllPostApiTest extends BaseAPITest {

    @Test(  description = "Проверка добавления денег пользователю через API",
            testName = "Добавление денег пользователю"
    )
    public void checkAddMoneyToUser() {
        double initialMoney = 500.0;
        double addedMoney = 500.0;
        log.info("Создаём пользователя с балансом: {}", initialMoney);

        UserRequest userRequest = UserRequest.builder()
                .firstName("Max")
                .secondName("Ivanov")
                .age(20)
                .sex("MALE")
                .money(initialMoney)
                .build();
        // создаём пользователя
        UserResponse createdUser = UserAdapter.createUser(userRequest, token);
        int userId = createdUser.id;
        log.info("Пользователь создан. ID = {}", userId);
        // добавляем деньги
        log.info("Добавляем деньги: {}", addedMoney);
        UserResponse updatedUser = UserAdapter.addMoney(userId, addedMoney, token);
        // Проверяем баланс
        double expectedBalance = initialMoney + addedMoney;

        log.info("Ожидаемый баланс: {}", expectedBalance);
        log.info("Фактический баланс: {}", updatedUser.money);

        Assert.assertEquals(
                updatedUser.money,
                expectedBalance, "Баланс пользователя рассчитан неверно");
    }
}