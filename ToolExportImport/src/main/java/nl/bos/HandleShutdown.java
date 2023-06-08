package nl.bos;

import nl.bos.message.Log;

class HandleShutdown extends Thread {

    @Override
    public void run() {
        Log.getInstance().send("Shutdown hook!");
    }
}
