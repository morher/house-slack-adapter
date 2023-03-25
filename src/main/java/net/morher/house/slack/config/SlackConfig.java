package net.morher.house.slack.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import net.morher.house.api.config.DeviceName;

@Data
public class SlackConfig {
  private final SlackTokenConfig tokens = new SlackTokenConfig();
  private final List<SwitchDeviceConfig> switches = new ArrayList<>();

  @Data
  public static class SlackTokenConfig {
    private String app;
    private String bot;
  }

  @Data
  public static class SwitchDeviceConfig {
    private DeviceName device;
    private String icon;
    private final SlackMessageConfig location = new SlackMessageConfig();
    private final SwitchMessageConfig command = new SwitchMessageConfig();
    private final SwitchMessageConfig state = new SwitchMessageConfig();
  }

  @Data
  public static class SwitchMessageConfig {
    private String on;
    private String off;
  }

  @Data
  public static class SlackMessageConfig {
    private String channel;
    private String thread;
  }
}
