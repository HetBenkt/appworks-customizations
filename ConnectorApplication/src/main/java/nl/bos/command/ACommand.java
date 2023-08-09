package nl.bos.command;

import nl.bos.IMqttService;

public abstract class ACommand {

    protected final IMqttService service;
    protected final String topic;

    protected ACommand(final IMqttService service, final String topic) {
        this.service = service;
        this.topic = topic;
    }
}
