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

    public int deleted(int userId) {
        try {
            // создание объекта PreparedStatement для текущего соединения
            // запрос считает количество записей в таблице person, id равен переданному значению
            // используем запрос с "?" защищает от SQL-инъекций
            PreparedStatement preparedStatement = connection.prepareStatement("select COUNT (id)\n" +
                    "from person\n" +
                    "where id=?");
            // подставляем значение houseId на место первого знака вопроса в запросе
            // setInt указывает что значение должно быть целое число
            preparedStatement.setInt(1, userId);
            // выполняем запрос и получаем набор строк из базы - объект ResultSet
            ResultSet rs = preparedStatement.executeQuery();
            // ставим курсор ResultSet на первую строку
            rs.next();
            // получаем значение из первой колонки и сохраняем в переменную count
            int count = rs.getInt(1);
            // возвращаем полученное количество - "0" если дома с таким ID нет и "1" если есть
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int selectCreatedUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select count(id) from person where id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            preparedStatement.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getHouseIdByUserId(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT house_id FROM person WHERE id = ?"
            );
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            int houseId = -1;
            if (rs.next()) {
                houseId = rs.getInt("house_id");
            }
            rs.close();
            preparedStatement.close();
            return houseId;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении house_id пользователя", e);
        }
    }
}