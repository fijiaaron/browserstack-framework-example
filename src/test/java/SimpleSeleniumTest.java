import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleSeleniumTest extends SeleniumTestBase
{
    @Tag("selenium")
    @Tag("smoke")
    @Test
    public void openHomePage()
    {
        String url = "https://www.bstackdemo.com/";
        driver.get(url);

        String title = driver.getTitle();
        log.info("got title: " + title);

        assertThat(title).isEqualTo("StackDemo");
    }
}
