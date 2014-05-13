package net.theepicsnail;

import net.theepicsnail.irc.IRCConnection;
import java.util.Observable;
import java.util.Observer;

public class Main {
  public static void main(String[] args) throws Exception {
    IRCConnection connection = new IRCConnection("localhost", 7777);
    connection.addObserver(new Observer() {
      public void update(Observable connection, Object arg){
        try {
          String message = (String)arg;
          if(message.startsWith("PING"))
            ((IRCConnection)connection).sendMessage(message.replace("PING", "PONG"));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    new Thread(connection).start();
    connection.sendMessage("NICK pybot");
    connection.sendMessage("USER user 0 * :realname");
    Thread.sleep(30*1000);
  }
}
