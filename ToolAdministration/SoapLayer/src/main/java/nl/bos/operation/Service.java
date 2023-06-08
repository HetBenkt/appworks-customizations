package nl.bos.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.bos.models.SoapEnvelope;

public interface Service {
    SoapEnvelope call(String body, Class<? extends SoapEnvelope> soapEnvelope) throws JsonProcessingException;
}
