package application.Websockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import application.Team.Team;
import application.Team.TeamRepository;
import application.Users.User;
import application.Users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@ServerEndpoint("/announce/{team}/{userName}")
@Component
@Lazy
public class AnnounceServer {
    private static Map < Session, String > sessionUserNameMap = new Hashtable < > ();
    private static Map < String, Session > userNameSessionMap = new Hashtable < > ();
    private static Map < Session, String > sessionTeamMap = new Hashtable< >();
    private final Logger logger = LoggerFactory.getLogger(AnnounceServer.class);

    private final UserRepository userRepository = SpringContext.getBean(UserRepository.class);

    private final TeamRepository teamRepository = SpringContext.getBean(TeamRepository.class);
    /**
     * @param session
     * @param team
     * @param userName
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("team") String team, @PathParam("userName") String userName) throws IOException {
        if (userName == null || team == null) {
//            System.out.println("Username or team is null. Username:"  + userName + ", Team: " + team);
            session.close();
            return;
        }
//        System.out.println(userName + " " + team + " " + session + userNameSessionMap);
//        System.out.println(userNameSessionMap.containsKey(userName));
        sessionTeamMap.put(session, team);
//        logger.info("[onOpen] " + userName);
        if (userNameSessionMap.containsKey(userName)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        } else {
            sessionUserNameMap.put(session, userName);
            userNameSessionMap.put(userName, session);
        }

        String teamName = sessionTeamMap.get(session);
        Team teamA = teamRepository.findByTeamName(teamName);
        List<String> recentAnnouncements = teamA.getAnnouncements();
        System.out.println("Most recent announcements for team " + teamName + ":");
        for (String announcement : recentAnnouncements) {
            System.out.println(announcement);
            broadcast(teamName, announcement);
        }
    }
    /**
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        String userName = sessionUserNameMap.get(session);
        String teamName = sessionTeamMap.get(session);
//        logger.info("[onMessage] " + userName + ": " + message);
//        System.out.println(userName);
//        System.out.println(userRepository.existsByUserName(userName));
        User user = userRepository.findByUserName(userName);

        if ("Coach".equals(user.getAccountType())) {
//            broadcast(teamName, userName + ": " + message);
            Team team = teamRepository.findByTeamName(teamName);
            team.addAnnouncement(userName + ": " + message);
            teamRepository.save(team);
            List<String> recentAnnouncements = team.getAnnouncements();
            System.out.println("Most recent announcements for team " + teamName + ":");
            for (String announcement : recentAnnouncements) {
                System.out.println(announcement);
                broadcast(teamName, announcement);
            }
        }
        else {
//            logger.info("[onMessage] " + userName + ": attempted to post announcement but is not a coach");
            System.out.println("Not a coach bozo");
        }
    }
    /**
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        String userName = sessionUserNameMap.get(session);
//        logger.info("[onClose] " + userName);
        sessionUserNameMap.remove(session);
        userNameSessionMap.remove(userName);
        session.close();
//        broadcast(userName + " disconnected");
        System.out.println(userName + " disconnected");
    }
    /**
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        String userName = sessionUserNameMap.get(session);
        logger.info("[onError]" + userName + ": " + throwable.getMessage());
    }
    /**
     * @param userName The userName of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToParticularUser(String userName, String message) {
        Session session = userNameSessionMap.get(userName);
        if (session == null) {
            logger.error("No session found for username: " + userName);
            return;
        }
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }
    /**
     * @param message The message to be broadcast to all users.
     */
    private void broadcast(String team, String message) {
        sessionTeamMap.forEach((session, teamName) -> {
            if (team.equals(teamName)) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    logger.info("[Broadcast Exception] " + e.getMessage());
                }
            }
        });
    }
}