package nl.bos;

import com.eibus.soap.*;

public class CustomTransaction implements ApplicationTransaction {
    //This is invoked by the SOAPTransaction, when the entire SOAPTransaction succeeds and can be committed.
    @Override
    public void commit() {

    }

    //This is invoked by the SOAPTransaction, when one of the ApplicationTransactions has returned false during the process() method.
    @Override
    public void abort() {

    }

    //This method returnsTrue, if the XML mapper can process Web service operations defined of the specified type such as SQL, Java, LDAP and so on.
    @Override
    public boolean canProcess(String s) {
        return false;
    }

    //This is invoked to process the relevant body blocks within this transaction.
    @Override
    public boolean process(BodyBlock bodyBlock, BodyBlock bodyBlock1) {
        return false;
    }
}
