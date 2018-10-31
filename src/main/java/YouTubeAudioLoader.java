import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class YouTubeAudioLoader implements IAudioLoader {

    public YouTubeAudioLoader(String directory){
        directoryToLoad = new File(directory);
        directoryPath = directory;
        directoryToLoad.mkdir();
    }
    public File download(String link) {
        String downloadPage = "https://tomp3.pro/" + link.replaceAll("(:/+|\\.|/|\\?|=)","-");
        WebDriver driver = browserSetup();
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

        return this.getLastModifiedFile();
    }

    private File directoryToLoad;
    private String directoryPath;

    private WebDriver browserSetup() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/TheDAX/Desktop/TelegramBot/MusicBot/src/main/resources/drivers/chromedriver.exe");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download" ,false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("download.default_directory",  directoryPath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        return new ChromeDriver(options);
    }

    private File getLastModifiedFile()
    {
        File[] audiofiles = directoryToLoad.listFiles();
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

}