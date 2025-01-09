package Controller;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // Start of my written and edited code
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postVerifyHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIDHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    // Start of my written and editted code
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(om.writeValueAsString(addedAccount));
        }
    }

    private void postVerifyHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);

        if (verifiedAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(om.writeValueAsString(verifiedAccount));
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(om.writeValueAsString(addedMessage));
        }
    }

    public void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageByID = messageService.getMessageByID(message_id);

        if (messageByID == null) {
            ctx.status(200);
        } else {
            ctx.json(om.writeValueAsString(messageByID));
        }
    }

    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageByID = messageService.deleteMessageByID(message_id);

        if (messageByID == null) {
            ctx.status(200);
        } else {
            ctx.json(om.writeValueAsString(messageByID));
        }
    }

    private void patchMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageByID = messageService.patchMessageByID(message_id, message);

        if (messageByID == null) {
            ctx.status(400);
        } else {
            ctx.json(om.writeValueAsString(messageByID));
        }
    }

    public void getAllMessagesByAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccount(account_id);
        ctx.json(om.writeValueAsString(messages));
    }
}