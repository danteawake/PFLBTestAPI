package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseDBConnection extends BaseDBConnection {

    public int deleted(int houseId) {
        try {
            // создание объекта PreparedStatement для текущего соединения
            // запрос считает количество записей в таблице house, id равен переданному значению
            // используем запрос с "?" защищает от SQL-инъекций
            PreparedStatement preparedStatement = connection.prepareStatement("select COUNT (id)\n" +
                    "from house\n" +
                    "where id=?");
            // подставляем значение houseId на место первого знака вопроса в запросе
            // setInt указывает что значение должно быть целое число
            preparedStatement.setInt(1, houseId);
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

}
