package net.morher.house.slack.device;

import net.morher.house.api.entity.switches.SwitchEntity;
import net.morher.house.api.entity.switches.SwitchOptions;
import net.morher.house.slack.bot.MessageLocation;
import net.morher.house.slack.config.SlackConfig.SwitchDeviceConfig;

public class SlackSwitchDevice {
  private final MessageLocation messageLocation;
  private final SwitchEntity entity;
  private final String commandOnMessage;
  private final String commandOffMessage;
  private final String stateOnMessage;
  private final String stateOffMessage;

  public SlackSwitchDevice(
      MessageLocation messageLocation, SwitchEntity entity, SwitchDeviceConfig config) {
    this.messageLocation = messageLocation;
    this.entity = entity;

    this.commandOnMessage = config.getCommand().getOn();
    this.commandOffMessage = config.getCommand().getOff();
    this.stateOnMessage = config.getState().getOn();
    this.stateOffMessage = config.getState().getOff();

    SwitchOptions options = new SwitchOptions();
    options.setIcon(config.getIcon());
    entity.setOptions(options);

    entity.command().subscribe(this::onCommand);
    messageLocation.addListener(this::onMessage);
  }

  private void onCommand(boolean command) {
    if (command) {
      messageLocation.sendMessage(commandOnMessage);
    } else {
      messageLocation.sendMessage(commandOffMessage);
    }
  }

  private void onMessage(String message) {
    if (stateOnMessage != null && stateOnMessage.equals(message)) {
      entity.state().publish(true);
    } else if (stateOffMessage != null && stateOffMessage.equals(message)) {
      entity.state().publish(false);
    }
  }
}
