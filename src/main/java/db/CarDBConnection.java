package db;

import java.sql.*;

public class CarDBConnection extends BaseDBConnection {

    public int select(int carId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select id from car where id = ?");
            preparedStatement.setInt(1, carId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            preparedStatement.close();
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int selectCreatedCar(int carId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(id)\n" +
                    "from car c \n" +
                    "where id=?");
            preparedStatement.setInt(1, carId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String selectUpdated(int carIdUpdated) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_name\n" +
                    "FROM car c\n" +
                    "JOIN engine_type et ON c.engine_type_id = et.id\n" +
                    "where c.id =?;\n");
            preparedStatement.setInt(1, carIdUpdated);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("type_name");
            }
            preparedStatement.close();
            return "По данному id {carIdUpdated} ничего не найдено";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleted(int carId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select COUNT (id)\n" +
                    "from car\n" +
                    "where id=?");
            preparedStatement.setInt(1, carId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
