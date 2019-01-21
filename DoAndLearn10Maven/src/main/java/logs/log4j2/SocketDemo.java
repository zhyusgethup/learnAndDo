package logs.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketDemo {
    static Logger logger = (Logger) LogManager.getLogger ("apiall");
    public static void main(String[] args) {
        logger.error("this is log4j2 remote test error");
        logger.debug("this is log4j2 remote test debug");
        logger.info("this is log4j2 remote test info");
        logger.trace("this is log4j2 remote test trace");
    }

}
