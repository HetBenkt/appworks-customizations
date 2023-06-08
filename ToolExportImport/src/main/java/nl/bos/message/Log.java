package nl.bos.message;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log extends AbstractMessage {
    private static Log instance;

    private Log() {
        super();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    @Override
    public void send(String message) {
        send(message, Level.INFO);
    }

    public void send(String message, Level level) {
        Logger.getLogger(Log.class.getName()).log(level, message);
    }

    @Override
    public boolean isReady() {
        if (instance == null) {
            return false;
        }
        Log.getInstance().send("Logging is ready.");
        return true;
    }
}
