import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import java.util.Comparator;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class YoutubeVideoLoadHandler implements IHandler {
    private String path;
    private String name;

    public YoutubeVideoLoadHandler()
    {
        path = "C:/Users/TheDAX/Desktop/TelegramBot/MusicBot/src/main/resources/audioFiles";
        File dir = new File(path);
        dir.mkdir();
    }

    private WebDriver browserSetup(Long chatID) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/TheDAX/Desktop/TelegramBot/MusicBot/src/main/resources/drivers/chromedriver.exe");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download" ,false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("download.default_directory", path + "/" + chatID);
        File dir = new File(path + "/"+ chatID);
        dir.mkdir();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        System.out.println("RUN_DRIVER:");
        return new ChromeDriver(options);
    }

    private File download(String link, Long chatID) {
        String downloadPage = "https://tomp3.pro/" + link.replaceAll("(:/+|\\.|/|\\?|=)","-");
        WebDriver driver = browserSetup(chatID);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(downloadPage);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        WebElement searchbutton  = driver.findElement((By.cssSelector("span.fa.fa-search")));
        searchbutton.click();
        WebElement button = driver.findElement(By.cssSelector("li.v-dl-mp3"));
        button.click();
        WebElement dynamicElement = (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class$='ready']")));
        WebElement downloadButton = driver.findElement(By.cssSelector("li.media-parent"));
        downloadButton.click();
        try { // FIX FIX FIX
            TimeUnit.SECONDS.sleep(30);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        driver.quit();


        File folder = new File(path + "/"  + chatID);
        File[] audiofiles = folder.listFiles();
        Arrays.sort( audiofiles, new Comparator()
        {
            public int compare(Object o1, Object o2) {

                if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                    return -1;
                } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }

        });
        return audiofiles[0];
    }


    public MessageInfo handle(RequestInfo requestInfo) {
        String searchStrLink =  new Link().searchLink(requestInfo.getMessage().getText().split("\"")[1]);
        SendAudio sendAudio = new SendAudio();
        sendAudio.setNewAudio(download(searchStrLink,requestInfo.getMessage().getChatId()));
        sendAudio.setChatId(requestInfo.getMessage().getChatId());
        MessageInfo messageInfo = new MessageInfo("sndaudio", sendAudio);
        return messageInfo;
    }

}
