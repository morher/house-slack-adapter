package net.morher.house.slack;

import net.morher.house.api.context.HouseAdapter;
import net.morher.house.api.context.HouseMqttContext;
import net.morher.house.slack.bot.SlackBot;
import net.morher.house.slack.config.SlackAdapterConfig;
import net.morher.house.slack.config.SlackConfig;
import net.morher.house.slack.device.SlackController;

public class SlackAdapter implements HouseAdapter {

  public static void main(String[] args) throws Exception {
    new SlackAdapter().run(new HouseMqttContext("slack-adapter"));
  }

  @Override
  public void run(HouseMqttContext ctx) {
    SlackConfig config = ctx.loadAdapterConfig(SlackAdapterConfig.class).getSlack();

    SlackBot bot = new SlackBot(config.getTokens().getApp(), config.getTokens().getBot());

    new SlackController(ctx.deviceManager(), bot).configure(config);
  }
}
