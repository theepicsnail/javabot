package net.theepicsnail.irc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.net.Socket;
import java.nio.charset.Charset;

public class IRCConnection extends Observable implements Runnable {

  private Socket sock;
  private BufferedReader reader;

  public IRCConnection(String server, int port) throws UnknownHostException, IOException {
    sock = new Socket(server, port);
    reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
  }

  @Override
  public void run() {
    String line;
    try {
      while (true) {
        line = reader.readLine();
        System.out.println(">> " + line);
        setChanged();
        notifyObservers(line);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(String message) throws IOException {
    sock.getOutputStream().write(message.getBytes(Charset.forName("UTF-8")));
    sock.getOutputStream().write(13);
    sock.getOutputStream().write(10);
    System.out.println("<< " +message);
  }
}
