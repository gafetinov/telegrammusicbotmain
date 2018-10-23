import org.telegram.telegrambots.api.methods.send.SendMessage;

public class CommandNotFoundHandler implements IHandler {
    public MessageInfo handle(RequestInfo requestInfo){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Command not found :(");
        sendMessage.setChatId(requestInfo.getMessage().getChatId());
        return new MessageInfo("sndmsg",sendMessage);
    }
}
