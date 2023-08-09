package nl.bos.command;

import nl.bos.IMqttService;

/**
 * Example service call from AppWorks:
 * <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
 *   <SOAP:Body>
 *     <subscribe xmlns="cs">
 *       <topic>awp/topic</topic>
 *     </subscribe>
 *   </SOAP:Body>
 * </SOAP:Envelope>
 */
public class CommandSubscribe extends ACommand implements ICommand {

    public CommandSubscribe(final IMqttService service, final String topic) {
        super(service, topic);
    }

    @Override
    public boolean apply() {
        return service.subscribe(topic);
    }
}
