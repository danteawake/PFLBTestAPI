package db;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class BaseDBConnection {
    private final String URL = "jdbc:postgresql://82.142.167.37:4832/pflb_trainingcenter";
    private final String USER = "pflb-at-read";
    private final String PASSWORD = "PflbQaTraining2354";

    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Соединение не выполнено", e);
        }
        log.info("Подключение к БД успешно выполнено");
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения,e");
        }
        log.info("Подключение к БД успешно закрыто");
    }
}
