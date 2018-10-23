import java.util.Dictionary;
import java.util.Hashtable;

public class HandlerManager {

    private static HandlerManager ourInstance = new HandlerManager();
    private Dictionary<String, IHandler> stringIHandlerDictionary;
    private HandlerManager() {
        stringIHandlerDictionary = new Hashtable<>();
    }

    public static HandlerManager getInstance() {
        return ourInstance;
    }

    public void addNewHandler(String command, IHandler handler) {
        stringIHandlerDictionary.put(command,handler);
    }
    public IHandler getHandler(RequestInfo requestInfo){
        IHandler handler = this.stringIHandlerDictionary.get(requestInfo.getCommand());
        if (handler == null)
            return new CommandNotFoundHandler();
        return handler;
    }
}
