package application.Websockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/team/chat/{teamName}/{userName}")
@Component
public class TeamChatServer {
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();
    private static Map<String, String> usernameTeamNameMap = new Hashtable<>();
    
    private final Logger logger = LoggerFactory.getLogger(TeamChatServer.class);

    /**
     * @param session  represents the WebSocket session for the connected user.
     * @param userName userName specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("teamName") String teamName, @PathParam("userName") String userName) throws IOException {
        logger.info("[onOpen] " + userName + " " + teamName);
        if (usernameSessionMap.containsKey(userName)) {
            session.getBasicRemote().sendText("userName already exists");
            session.close();
        } else {
            sessionUsernameMap.put(session, userName);
            usernameSessionMap.put(userName, session);
            usernameTeamNameMap.put(userName, teamName);
            sendMessageToPArticularUser(userName, "Welcome to the " + teamName + " team chat, " + userName);
            teamBroadcast("User: " + userName + " has Joined the Websockets", teamName);
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        String userName = sessionUsernameMap.get(session);
        String teamName = usernameTeamNameMap.get(userName);

        logger.info("[onMessage] " + userName + ": " + message);

        if (message.startsWith("@")) {

            // split by space
            String[] split_msg =  message.split("\\s+");

            // Combine the rest of message
            StringBuilder actualMessageBuilder = new StringBuilder();
            for (int i = 1; i < split_msg.length; i++) {
                actualMessageBuilder.append(split_msg[i]).append(" ");
            }
            String destUserName = split_msg[0].substring(1);    //@userName and get rid of @
            String actualMessage = actualMessageBuilder.toString();
            sendMessageToPArticularUser(destUserName, "[DM from " + userName + "]: " + actualMessage);
            sendMessageToPArticularUser(userName, "[DM from " + userName + "]: " + actualMessage);
        }
        else { // Message to whole chat
            teamBroadcast(userName + ": " + message, teamName);
        }
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        String userName = sessionUsernameMap.get(session);
        String teamName = usernameTeamNameMap.get(userName);

        logger.info("[onClose] " + userName);

        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(userName);
        usernameTeamNameMap.remove(userName);

        teamBroadcast(userName + " disconnected", teamName);
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        String userName = sessionUsernameMap.get(session);

        logger.info("[onError]" + userName + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param userName The userName of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToPArticularUser(String userName, String message) {
        try {
            usernameSessionMap.get(userName).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all users on the team of the sender in the chat.
     *
     * @param message The message to be broadcasted to all users online and on team of sender.
     */
    private void teamBroadcast(String message, String teamName) {

        sessionUsernameMap.forEach((session, userName) -> {
            if (usernameTeamNameMap.get(userName).equals(teamName)) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    logger.info("[Broadcast Exception] " + e.getMessage());
                }
            }
        });
    }

}