import java.util.logging.*;
import java.util.logging.Logger;

public class LoggerTest {


    public static void main(String[] args) {
        Logger log = Logger.getLogger("com");
        log.setLevel(Level.WARNING);
        Logger log2 = Logger.getLogger("com.xiya");
        log2.info("111");
        log2.severe("222");
        log2.warning("333");
    }

}
