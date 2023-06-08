package nl.bos.auth;

public interface AuthenticationService {
    String getToken();

    String getOTDSTicket();

    String getToken(String otdsTicket);
}
