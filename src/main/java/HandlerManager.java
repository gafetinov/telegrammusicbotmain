import java.util.Dictionary;
import java.util.Hashtable;

public class HandlerManager {

    private static HandlerManager ourInstance = new HandlerManager();
    private Dictionary<String, String> stringIHandlerDictionary;
    private HandlerFactory handlerFactory;
    private HandlerManager() {
        handlerFactory = new HandlerFactory();
        stringIHandlerDictionary = new Hashtable<>();
        addNewHandler("/play", YouTubeAudioLoadHandler.class.getName());
    }

    public static HandlerManager getInstance() {
        return ourInstance;
    }

    public void addNewHandler(String command,String handlerType) {
        stringIHandlerDictionary.put(command,handlerType);
    }
    public IHandler getHandler(RequestInfo requestInfo){
        return handlerFactory.createHandler(this.stringIHandlerDictionary.get(requestInfo.getCommand()));
    }
}
