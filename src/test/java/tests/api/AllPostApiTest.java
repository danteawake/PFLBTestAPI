package tests.api;

import lombok.extern.slf4j.Slf4j;
import models.positive.UserRequest;
import models.positive.UserResponse;
import adapters.UserAdapter;
import org.testng.Assert;
import org.testng.annotations.Test;
@Slf4j
public class AllPostApiTest {

    @Test(
            description = "Проверка добавления денег пользователю",
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

        UserResponse createdUser = UserAdapter.createUser(userRequest); // создаём пользователя

        int userId = createdUser.id;
        log.info("Пользователь создан. ID = {}", userId);
        log.info("Добавляем деньги: {}", addedMoney);
        UserAdapter.addMoney(userId, addedMoney); // добавляем деньги

        UserResponse updatedUser = UserAdapter.getUser(userId);// получаем обновлённого пользователя

        double expectedBalance = initialMoney + addedMoney;// проверка баланса

        log.info("Ожидаемый баланс: {}", expectedBalance);
        log.info("Фактический баланс: {}", updatedUser.money);

        Assert.assertEquals(
                updatedUser.money,
                expectedBalance,"Баланс пользователя рассчитан неверно");
    }
}