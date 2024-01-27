import org.junit.jupiter.api.Test;

public class TestSample {

    @Test
    public void test(){
        System.out.println("Hello world!");
        String version = System.getProperty("java.version");
        System.out.println("version = " + version);
    }
}
