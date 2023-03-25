package net.morher.house.slack.device;

import lombok.RequiredArgsConstructor;
import net.morher.house.api.devicetypes.GeneralDevice;
import net.morher.house.api.entity.Device;
import net.morher.house.api.entity.DeviceId;
import net.morher.house.api.entity.DeviceInfo;
import net.morher.house.api.entity.DeviceManager;
import net.morher.house.slack.bot.MessageLocation;
import net.morher.house.slack.bot.SlackBot;
import net.morher.house.slack.config.SlackConfig;
import net.morher.house.slack.config.SlackConfig.SlackMessageConfig;
import net.morher.house.slack.config.SlackConfig.SwitchDeviceConfig;

@RequiredArgsConstructor
public class SlackController {
  private final DeviceManager deviceManager;
  private final SlackBot bot;

  public void configure(SlackConfig slack) {
    for (SwitchDeviceConfig switchDeviceConfig : slack.getSwitches()) {
      configure(switchDeviceConfig);
    }
  }

  private void configure(SwitchDeviceConfig switchDeviceConfig) {
    SlackMessageConfig locationConfig = switchDeviceConfig.getLocation();
    MessageLocation location =
        bot.location(locationConfig.getChannel(), locationConfig.getThread());

    DeviceId deviceId = switchDeviceConfig.getDevice().toDeviceId();

    Device device = deviceManager.device(deviceId);
    DeviceInfo deviceInfo = new DeviceInfo();
    deviceInfo.setName("Slack device");
    device.setDeviceInfo(deviceInfo);

    new SlackSwitchDevice(location, device.entity(GeneralDevice.ENABLE), switchDeviceConfig);
  }
}
