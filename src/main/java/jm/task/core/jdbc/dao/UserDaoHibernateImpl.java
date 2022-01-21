package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private String SQL;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        SQL = "create table IF NOT EXISTS users(\n" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "name varchar(30),\n" +
                "lastName varchar(30),\n" +
                "age INT(2) unsigned\n" +
                ");";

        session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        SQL = "DROP TABLE IF EXISTS users;";

        session.createSQLQuery(SQL).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        SQL = "INSERT INTO users (name, lastName, age) VALUES(\"" + name + "\", \"" + lastName + "\", \"" + age + "\");";

        session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        SQL = "DELETE FROM users WHERE id=" + id + ";";

        session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> users;
        Query query = session.createSQLQuery("SELECT * From users").addEntity(User.class);
        users = query.list();
        session.close();
        return users;
    }



    @Override
    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
