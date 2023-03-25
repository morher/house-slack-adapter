package net.morher.house.slack.config;

import lombok.Data;

@Data
public class SlackAdapterConfig {
  private final SlackConfig slack = new SlackConfig();
}
