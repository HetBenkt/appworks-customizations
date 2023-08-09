package nl.bos.command;

import nl.bos.IMqttService;

/**
 * Example service call from AppWorks:
 * <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
 *   <SOAP:Body>
 *     <publish xmlns="cs">
 *       <topic>awp/topic</topic>
 *       <payload>Hello world!</payload>
 *       <retain>false</retain>
 *     </publish>
 *   </SOAP:Body>
 * </SOAP:Envelope>
 */
public class CommandPublish extends ACommand implements ICommand {

    private final String payload;
    private final boolean retain;

    public CommandPublish(final IMqttService service, final String topic, final String payload, final boolean retain) {
        super(service, topic);
        this.payload = payload;
        this.retain = retain;
    }

    @Override
    public boolean apply() {
        return service.publish(topic, payload, retain);
    }
}
