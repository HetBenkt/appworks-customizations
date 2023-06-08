package nl.bos.message;

public class Monitor extends AbstractMessage {
    private static Monitor instance;

    private Monitor() {
        super();
    }

    public static Monitor getInstance() {
        if (instance == null) {
            instance = new Monitor();
        }
        return instance;
    }

    @Override
    public void send(String message) {
        //TODO this method should be called instead of the way we do now in the validation task!?
    }

    @Override
    public boolean isReady() {
        return instance != null;
    }
}
