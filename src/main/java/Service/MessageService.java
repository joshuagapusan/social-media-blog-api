package Service;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        if (message.getMessage_text() != "") {
            if (message.getMessage_text().length() <= 255) {
                if (messageDAO.getRealAccount(message.getPosted_by()) != null) {
                    return messageDAO.insertMessage(message);
                }
            }
        }

        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    public Message deleteMessageByID(int message_id) {
        return messageDAO.deleteMessageByID(message_id);
    }

    public Message patchMessageByID(int message_id, Message message) {
        System.out.println(message_id);
        System.out.println(messageDAO.getMessageByID(message.getMessage_id()));
        if (message.getMessage_text() != "") {
            System.out.println("Not Empty Text");
            if (message.getMessage_text().length() <= 255) {
                System.out.println("Character limit not passed");
                //System.out.println(messageDAO.getMessageByID(message.getMessage_id()));
                if (messageDAO.getMessageByID(message_id) != null) {
                    System.out.println("We passed the checks");
                    return messageDAO.patchMessageByID(message_id, message);
                }
            }
        }

        System.out.println("One of 3 errors");
        return null;
    }

    public List<Message> getAllMessagesByAccount(int account_id) {
        return messageDAO.getAllMessagesByAccount(account_id);
    }
}
