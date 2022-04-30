import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest extends SeleniumTest
{
    @Tag("passing")
    @Tag("smoke")
    @Test
    public void testSanity()
    {
        assertThat(true).isEqualTo(true);
    }

    @Tag("failing")
    @Tag("smoke")
    @Test
    public void testInsanity()
    {
        assertThat(false).isEqualTo(true);
    }

    @Tag("slow")@Tag("smoke")
    @Test
    public void testSlowly() throws InterruptedException
    {
        int count = 0;
        while(count < 5 * 60) // run for 5 minutes
        {
            Thread.sleep(1000);
            count++;
        }

        assertThat(count).isEqualTo(300);
    }
}
