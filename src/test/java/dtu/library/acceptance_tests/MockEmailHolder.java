package dtu.library.acceptance_tests;

import dtu.library.app.servers.EmailServer;
import dtu.library.app.LibraryApp;

import static org.mockito.Mockito.mock;

public class MockEmailHolder {

    private EmailServer mock_email_server = mock(EmailServer.class);

    public MockEmailHolder(LibraryApp library_app){
        library_app.setEmailServer(mock_email_server);
    }

    public EmailServer getMockEmailServer(){
        return this.mock_email_server;
    }

    public void setMockEmailServer(EmailServer e){
        this.mock_email_server = e;
    }

}
