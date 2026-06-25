package db;

import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UserDBConnection extends BaseDBConnection {

    public double getUserBalanceFromDB(int userId) {
        try {
            String query = "SELECT money FROM person WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("money");
                log.info("Баланс пользователя {} в БД: {}", userId, balance);
                return balance;
            }
            log.warn("Пользователь с ID {} не найден в БД", userId);
            return -1;
        } catch (SQLException e) {
            log.error("Ошибка при получении баланса пользователя", e);
            throw new RuntimeException("Ошибка при получении баланса пользователя", e);
        }
    }
}