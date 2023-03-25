package net.morher.house.slack.bot;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsumerDelegateListener implements MessageListener {
  private final Consumer<String> delegate;

  @Override
  public void onMessage(String message, String channel, String thread) {
    delegate.accept(message);
  }
}
