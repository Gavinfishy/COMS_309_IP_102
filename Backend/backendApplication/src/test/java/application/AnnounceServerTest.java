package application;

import application.Team.TeamRepository;
import application.Users.User;
import application.Users.UserRepository;
import application.Websockets.AnnounceServer;
import application.Websockets.SpringContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//@WebMvcTest(AnnounceServer.class)
//@Import(AnnounceServerTest.TestConfig.class)
@SpringBootTest
public class AnnounceServerTest {


//    @TestConfiguration
//    public static class TestConfig {
//
//        @Bean
//        public ServerEndpointExporter serverEndpointExporter() {
//            return mock(ServerEndpointExporter.class);
//        }
//    }
//    @Mock
//    private Session session;
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private SpringContext springContext;
//    @Mock
//    private TeamRepository teamRepository;
//    @InjectMocks
//    private AnnounceServer announceServer;
//    @Before
//    public void setup() {
//        when(SpringContext.getBean(UserRepository.class)).thenReturn(userRepository);
//        when(SpringContext.getBean(TeamRepository.class)).thenReturn(teamRepository);
//    }
//
//    @Test
//    public void testOnOpen() throws IOException {
//        String team = "team1";
//        String userName = "user1";
//        Session session = mock(Session.class);
//        when(session.getBasicRemote()).thenReturn(mock(RemoteEndpoint.Basic.class));
//        doNothing().when(session).close();
//        announceServer.onOpen(session, team, userName);
//        verify(session, times(1)).getBasicRemote();
//    }
//
//    @Test
//    public void testOnMessage() throws IOException {
//        String message = "Hello World";
//        String userName = "user1";
//        User user = new User();
//        user.setAccountType("Coach");
//        when(userRepository.findByUserName(userName)).thenReturn(user);
//        announceServer.onMessage(session, message);
//        verify(userRepository, times(1)).findByUserName(userName);
//    }
}
