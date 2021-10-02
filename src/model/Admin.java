package model;

import dao.AdminDao;

import java.io.Serializable;
import java.sql.SQLException;

public class Admin  implements Serializable {
    private int id;
    private String username;
    private String name;
    private AdminDao dao(){
        return new AdminDao();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String password;
    private int roles;

    public Admin() {
    }

    public Admin(String username, String password, int roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }
    public void insert() throws SQLException {
        dao().insert(this);

    }
}
