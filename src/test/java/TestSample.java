import org.junit.jupiter.api.Test;

public class TestSample {

    @Test
    public void test(){
        String version = System.getProperty("java.version");
        System.out.println("Java version = " + version);
    }
}
