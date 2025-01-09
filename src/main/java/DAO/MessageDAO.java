package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getRealAccount(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, posted_by);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){ // was while
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generated_message_id = rs.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),
                           message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;

        try {
            String sqlSelect = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement psSelect = connection.prepareStatement(sqlSelect);
            psSelect.setInt(1, message_id);
            ResultSet rs = psSelect.executeQuery();

            if(rs.next()){
                message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
            }

            String sqlDelete = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement psDelete = connection.prepareStatement(sqlDelete);
            psDelete.setInt(1, message_id);
            psDelete.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message patchMessageByID(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message_id);

            ps.executeUpdate();
            /*ResultSet rs = ps.executeQuery();
            if(rs.next()){
                message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return message;
            }*/

            String sqlSelect = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement psSelect = connection.prepareStatement(sqlSelect);
            psSelect.setInt(1, message_id);
            ResultSet rs = psSelect.executeQuery();

            if(rs.next()){
                message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesByAccount(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, account_id); // posted_by references account_id in the SQL tables

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
