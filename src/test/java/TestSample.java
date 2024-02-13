import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSample {

    @Test
    public void test(){
        String version = System.getProperty("java.version");
        assertThat(version).isEqualTo("21.0.1");
    }
}
