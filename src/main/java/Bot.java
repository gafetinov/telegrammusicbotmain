import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendMsg(MessageInfo messageInfo)
    {
        try {
            switch (messageInfo.getMessageType()) {
                case "sndmsg": {
                    sendMessage((SendMessage) messageInfo.getSendObj());
                    break;
                }
                case "sndaudio": {
                    sendAudio((SendAudio) messageInfo.getSendObj());
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        PreParser parser = new PreParser();
        RequestInfo requestInfo = parser.parse(message);
        IHandler handler = HandlerManager.getInstance().getHandler(requestInfo);
        MessageInfo messageInfo = handler.handle(requestInfo);
        sendMsg(messageInfo);
    }
    public String getBotUsername() {
        return "SonaMusicBot";
    }

    public String getBotToken() {
        return "779766656:AAFf6t0tm9FhK6KaKfBn1QYR6o5OVJuhblY";
    }
}
