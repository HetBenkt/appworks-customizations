package nl.bos.message;

import nl.bos.validation.IValidator;

import javax.mail.MessagingException;

abstract class AbstractMessage implements IValidator {

    abstract void send(String message) throws MessagingException, NoSuchFieldException;

}
