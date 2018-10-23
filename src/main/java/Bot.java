import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


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
                case "sndmsg":
                    sendMessage((SendMessage) messageInfo.getSendObj());
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Parser parser = new Parser();
        RequestInfo requestInfo = parser.parse(message);
        IHandler handler = HandlerManager.getInstance().getHandler(requestInfo);
        MessageInfo messageInfo = handler.handle(requestInfo);
        sendMsg(messageInfo);
    }
    public String getBotUsername() {
        return "MyF4kingMusicBot";
    }

    public String getBotToken() {
        return "659916332:AAFgUX8BzG47rS1ECuixC985gN-RqqvdoJU";
    }
}
