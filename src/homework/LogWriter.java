package homework;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriter {
    private static LogWriter ourInstance;
    public static final Logger appLogger = Logger.getLogger("src");

    private LogWriter() {}

    public static LogWriter getInstance() {
        if(ourInstance == null) {
            createLogger();
            ourInstance = new LogWriter();
        }
        return ourInstance;
    }

    private static void createLogger() {
        FileHandler handler = null;
        try {
            handler = new FileHandler("log.xml");
            appLogger.addHandler(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
