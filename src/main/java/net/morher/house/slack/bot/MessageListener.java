package net.morher.house.slack.bot;

public interface MessageListener {

  void onMessage(String message, String channel, String thread);
}
