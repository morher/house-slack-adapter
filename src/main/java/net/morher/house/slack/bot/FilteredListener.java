package net.morher.house.slack.bot;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilteredListener implements MessageListener {
  private final String channelFilter;
  private final String threadTsFilter;
  private final MessageListener delegate;

  @Override
  public void onMessage(String message, String channel, String thread) {
    if ((this.channelFilter == null || this.channelFilter.equals(channel))
        && (threadTsFilter == null || threadTsFilter.equals(thread))) {
      delegate.onMessage(message, channel, thread);
    }
  }
}
