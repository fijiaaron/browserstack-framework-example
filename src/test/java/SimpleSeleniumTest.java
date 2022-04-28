import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SimpleSeleniumTest extends BaseTest
{
    @Tag("selenium")
    @Tag("smoke")
    @Test
    public void openHomePage()
    {
        String url = "https://www.bstackdemo.com/";
        driver.get(url);

        System.out.println("got title: " + driver.getTitle());
    }
}
