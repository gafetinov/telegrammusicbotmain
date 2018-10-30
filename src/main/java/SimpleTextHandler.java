import org.telegram.telegrambots.api.methods.send.SendMessage;

public class SimpleTextHandler implements IHandler {
    private String answer;

    public SimpleTextHandler(String answer) {
        this.answer = answer;
    }

    public MessageInfo handle(RequestInfo requestInfo){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(answer);
        sendMessage.setChatId(requestInfo.getMessage().getChatId());
        return new MessageInfo("sndmsg",sendMessage);
    }

}
