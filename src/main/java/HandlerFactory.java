import org.aopalliance.reflect.Class;

public class HandlerFactory {

  public IHandler createHandler(String type) {
    switch (type) {
      case ("YouTubeAudioLoadHandler"):
        return new YouTubeAudioLoadHandler();
    }
    return new SimpleTextHandler("Command not found :(");
  }
}
