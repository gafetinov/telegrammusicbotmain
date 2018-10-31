import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkTests {

    private Link link;
    @Before
    public void SetUp(){
        link = new Link();
    }
    @Test
    public void LinkGoesWithEanglishRequest() {
        Assert.assertEquals("https://www.youtube.com/watch?v=Cwkej79U3ek",link.searchLink("a thousand miles"));
    }
    @Test
    public void LinkGoesWithRussianRequest() {
        Assert.assertEquals("https://www.youtube.com/watch?v=QiFBgtgUtfw", link.searchLink("три полоски"));
    }
    @Test
    public void LinkGoesWithWierdChinesSymbolsWTF(){
        Assert.assertEquals("https://www.youtube.com/watch?v=_ytjjhOYgOU", link.searchLink("Mermaid girl-秋葉工房 MIX"));
    }
}
