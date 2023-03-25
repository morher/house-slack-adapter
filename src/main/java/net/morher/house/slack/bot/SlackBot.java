package net.morher.house.slack.bot;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.MessageChangedEvent;
import com.slack.api.model.event.MessageEvent;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlackBot {
  private final List<MessageListener> listeners = new ArrayList<>();
  private final App app;

  public SlackBot(String appToken, String botToken) {
    AppConfig config = new AppConfig();
    config.setSingleTeamBotToken(botToken);

    app = new App(config);
    app.event(MessageEvent.class, this::onMessage);
    app.event(MessageChangedEvent.class, this::onMessageChanged);
    try {
      SocketModeApp s = new SocketModeApp(appToken, app);
      s.startAsync();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Response onMessage(EventsApiPayload<MessageEvent> payload, EventContext context) {
    MessageEvent event = payload.getEvent();
    onMessage(event.getTs(), event.getText(), event.getChannel(), event.getThreadTs());
    return Response.ok();
  }

  private Response onMessageChanged(
      EventsApiPayload<MessageChangedEvent> payload, EventContext context) {
    MessageChangedEvent event = payload.getEvent();
    onMessage(
        event.getTs(),
        event.getMessage().getText(),
        event.getChannel(),
        event.getMessage().getThreadTs());
    return Response.ok();
  }

  private void onMessage(String ts, String text, String channel, String threadTs) {
    for (MessageListener listener : listeners) {
      listener.onMessage(text, channel, threadTs);
    }
  }

  public Closeable addListener(MessageListener listener) {
    listeners.add(listener);
    return () -> listeners.remove(listener);
  }

  public MessageLocation location(String channel, String threadTs) {
    return new MessageLocation(this, channel, threadTs);
  }

  public void sendMessage(String message, String channel, String thread) {
    try {
      app.client().chatPostMessage(r -> r.channel(channel).threadTs(thread).text(message));

    } catch (IOException | SlackApiException e) {
      e.printStackTrace();
    }
  }
}
