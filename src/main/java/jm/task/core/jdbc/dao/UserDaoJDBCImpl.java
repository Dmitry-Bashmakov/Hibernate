package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection bdConnection;
    private String SQL;
    private Statement getStatement;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        SQL = "create table IF NOT EXISTS user(\n" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name varchar(30),\n" +
                "    lastName varchar(30),\n" +
                "    age INT(2) unsigned\n" +
                ");";
        try {
            bdConnection = Util.getConnection();
            getStatement = bdConnection.createStatement();
            getStatement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        SQL = "DROP TABLE IF EXISTS user;";

        try {
            bdConnection = Util.getConnection();
            getStatement = bdConnection.createStatement();
            getStatement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            bdConnection = Util.getConnection();
            PreparedStatement preparedStatement =
                    bdConnection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES(?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            /*getStatement = bdConnection.createStatement();
            getStatement.execute(SQL);*/
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void removeUserById(long id) {
        try {
            bdConnection = Util.getConnection();
            PreparedStatement preparedStatement =
                    bdConnection.prepareStatement("DELETE FROM user WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQL = "SELECT * FROM user";
        ResultSet resultSet;
        try {
            bdConnection = Util.getConnection();
            getStatement = bdConnection.createStatement();
            resultSet = getStatement.executeQuery(SQL);
            while (true) {
                User user = new User();
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    users.add(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
