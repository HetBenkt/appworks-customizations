package nl.bos.command;

import nl.bos.IMqttService;

public abstract class ACommand {

    protected final IMqttService service;
    protected String topic;

    protected ACommand(IMqttService service, String topic) {
        this.service = service;
        this.topic = topic;
    }
}
