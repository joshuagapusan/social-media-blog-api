package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"), rs.getString("password"));
                return account;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generated_account_id = rs.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account verifyAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int generated_account_id = rs.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
