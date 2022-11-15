package devices.configuration.protocols.iot16;

import devices.configuration.intervals.DeviceInfo;
import devices.configuration.intervals.Protocols;

record BootNotificationRequest(
        String chargePointVendor,
        String chargePointModel,
        String chargePointSerialNumber,
        String chargeBoxSerialNumber,
        String firmwareVersion,
        String iccid,
        String imsi,
        String meterType,
        String meterSerialNumber) {

    DeviceInfo toDevice(String deviceId) {
        return new DeviceInfo(
                deviceId,
                chargePointVendor,
                chargePointModel,
                Protocols.IoT16
        );
    }
}