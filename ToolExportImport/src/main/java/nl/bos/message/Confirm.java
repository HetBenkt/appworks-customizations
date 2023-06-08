package nl.bos.message;

public class Confirm extends AbstractMessage {
    private static Confirm instance;

    private Confirm() {
        super();
    }

    public static Confirm getInstance() {
        if (instance == null) {
            instance = new Confirm();
        }
        return instance;
    }

    @Override
    public void send(String message) {
        Log.getInstance().send("Do a confirmation");
    }

    @Override
    public boolean isReady() {
        return instance != null;
    }
}
