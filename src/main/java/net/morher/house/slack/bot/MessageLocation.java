package net.morher.house.slack.bot;

import java.io.Closeable;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageLocation {
  private final SlackBot bot;
  private final String channel;
  private final String threadTs;

  public void sendMessage(String message) {
    bot.sendMessage(message, channel, threadTs);
  }

  public Closeable addListener(MessageListener listener) {
    return bot.addListener(new FilteredListener(channel, threadTs, listener));
  }

  public Closeable addListener(Consumer<String> listener) {
    return addListener(new ConsumerDelegateListener(listener));
  }
}
