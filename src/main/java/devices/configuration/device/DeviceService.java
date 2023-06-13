package devices.configuration.device;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final devices.configuration.device.DeviceEventSourcingRepository repository;

    @Transactional
    public DeviceSnapshot createNewDevice(String deviceId, UpdateDevice update) {
        Device device = Device.create(deviceId);
        update.apply(device);
        repository.save(device);
        return device.toSnapshot();
    }

    @Transactional
    public Optional<DeviceSnapshot> update(String deviceId, UpdateDevice update) {
        return repository.findByDeviceId(deviceId)
                .map(device -> {
                    update.apply(device);
                    repository.save(device);
                    return device.toSnapshot();
                });
    }
}