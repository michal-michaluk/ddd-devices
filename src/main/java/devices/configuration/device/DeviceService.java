package devices.configuration.device;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository repository;

    @Transactional(readOnly = true)
    public Optional<DeviceConfiguration> get(String deviceId) {
        return repository.findById(deviceId)
                .map(device -> {
                    Violations violations = device.checkViolations();
                    boolean usable = violations.isValid() && device.publicAccess;
                    Visibility visibility = new Visibility(usable, Visibility.ForCustomer.calculateForCustomer(usable, device.showOnMap));
                    return new DeviceConfiguration(
                            device.deviceId,
                            device.getOwnership(),
                            device.getLocation(),
                            device.getOpeningHours(),
                            device.getSettings(),
                            violations,
                            visibility
                    );
                });
    }

    public DeviceConfiguration createNewDevice(String deviceId, UpdateDevice update) {
        DeviceEntity device = DeviceEntity.newDevice(deviceId);
        if (update.location != null) {
            if (!Objects.equals(device.getLocation(), update.location)) {
                if (update.location != null) {
                    device.street = update.location.street();
                    device.houseNumber = update.location.houseNumber();
                    device.city = update.location.city();
                    device.postalCode = update.location.postalCode();
                    device.state = update.location.state();
                    device.country = update.location.country();
                    device.longitude = update.location.coordinates().longitude();
                    device.latitude = update.location.coordinates().latitude();

                    device.registerEvent(new DomainEvent.LocationUpdated(device.deviceId, device.getLocation()));
                } else {
                    device.street = null;
                    device.houseNumber = null;
                    device.city = null;
                    device.postalCode = null;
                    device.state = null;
                    device.country = null;
                    device.longitude = null;
                    device.latitude = null;

                    device.registerEvent(new DomainEvent.LocationUpdated(device.deviceId, device.getLocation()));
                }
            }

        }
        if (update.openingHours != null) {
            Objects.requireNonNull(update.openingHours);
            if (!Objects.equals(device.getOpeningHours(), update.openingHours)) {
                device.openingHours.clear();
                device.openingHours.addAll(DeviceEntity.OpeningHoursEntity.of(device.deviceId, update.openingHours));
                device.registerEvent(new DomainEvent.OpeningHoursUpdated(device.deviceId, device.getOpeningHours()));
            }
        }
        if (update.settings != null) {
            Objects.requireNonNull(update.settings);
            boolean changed = false;
            if (update.settings.autoStart() != null && !Objects.equals(device.autoStart, update.settings.autoStart())) {
                device.autoStart = update.settings.autoStart();
                changed = true;
            }
            if (update.settings.remoteControl() != null && !Objects.equals(device.remoteControl, update.settings.remoteControl())) {
                device.remoteControl = update.settings.remoteControl();
                changed = true;
            }
            if (update.settings.billing() != null && !Objects.equals(device.billing, update.settings.billing())) {
                device.billing = update.settings.billing();
                changed = true;
            }
            if (update.settings.reimbursement() != null && !Objects.equals(device.reimbursement, update.settings.reimbursement())) {
                device.reimbursement = update.settings.reimbursement();
                changed = true;
            }
            if (update.settings.showOnMap() != null && !Objects.equals(device.showOnMap, update.settings.showOnMap())) {
                device.showOnMap = update.settings.showOnMap();
                changed = true;
            }
            if (update.settings.publicAccess() != null && !Objects.equals(device.publicAccess, update.settings.publicAccess())) {
                device.publicAccess = update.settings.publicAccess();
                changed = true;
            }

            if (changed) {
                device.registerEvent(new DomainEvent.SettingsUpdated(device.deviceId, device.getSettings()));
            }
        }
        if (update.ownership != null) {
            Objects.requireNonNull(update.ownership);
            if (!Objects.equals(device.getOwnership(), update.ownership)) {
                device.operator = update.ownership.operator();
                device.provider = update.ownership.provider();
                device.registerEvent(new DomainEvent.OwnershipUpdated(device.deviceId, device.getOwnership()));

                if (update.ownership.isUnowned()) {
                    device.resetToDefaults();
                }
            }
        }
        repository.save(device);
        Violations violations = device.checkViolations();
        boolean usable = violations.isValid() && device.publicAccess;
        Visibility visibility = new Visibility(usable, Visibility.ForCustomer.calculateForCustomer(usable, device.showOnMap));
        return new DeviceConfiguration(
                device.deviceId,
                device.getOwnership(),
                device.getLocation(),
                device.getOpeningHours(),
                device.getSettings(),
                violations,
                visibility
        );
    }

    public Optional<DeviceConfiguration> update(String deviceId, UpdateDevice update) {
        return repository.findById(deviceId)
                .map(device -> {
                    if (update.location != null) {
                        if (!Objects.equals(device.getLocation(), update.location)) {
                            if (update.location != null) {
                                device.street = update.location.street();
                                device.houseNumber = update.location.houseNumber();
                                device.city = update.location.city();
                                device.postalCode = update.location.postalCode();
                                device.state = update.location.state();
                                device.country = update.location.country();
                                device.longitude = update.location.coordinates().longitude();
                                device.latitude = update.location.coordinates().latitude();

                                device.registerEvent(new DomainEvent.LocationUpdated(device.deviceId, device.getLocation()));
                            } else {
                                device.street = null;
                                device.houseNumber = null;
                                device.city = null;
                                device.postalCode = null;
                                device.state = null;
                                device.country = null;
                                device.longitude = null;
                                device.latitude = null;

                                device.registerEvent(new DomainEvent.LocationUpdated(device.deviceId, device.getLocation()));
                            }
                        }

                    }
                    if (update.openingHours != null) {
                        Objects.requireNonNull(update.openingHours);
                        if (!Objects.equals(device.getOpeningHours(), update.openingHours)) {
                            device.openingHours.clear();
                            device.openingHours.addAll(DeviceEntity.OpeningHoursEntity.of(device.deviceId, update.openingHours));
                            device.registerEvent(new DomainEvent.OpeningHoursUpdated(device.deviceId, device.getOpeningHours()));
                        }
                    }
                    if (update.settings != null) {
                        Objects.requireNonNull(update.settings);
                        boolean changed = false;
                        if (update.settings.autoStart() != null && !Objects.equals(device.autoStart, update.settings.autoStart())) {
                            device.autoStart = update.settings.autoStart();
                            changed = true;
                        }
                        if (update.settings.remoteControl() != null && !Objects.equals(device.remoteControl, update.settings.remoteControl())) {
                            device.remoteControl = update.settings.remoteControl();
                            changed = true;
                        }
                        if (update.settings.billing() != null && !Objects.equals(device.billing, update.settings.billing())) {
                            device.billing = update.settings.billing();
                            changed = true;
                        }
                        if (update.settings.reimbursement() != null && !Objects.equals(device.reimbursement, update.settings.reimbursement())) {
                            device.reimbursement = update.settings.reimbursement();
                            changed = true;
                        }
                        if (update.settings.showOnMap() != null && !Objects.equals(device.showOnMap, update.settings.showOnMap())) {
                            device.showOnMap = update.settings.showOnMap();
                            changed = true;
                        }
                        if (update.settings.publicAccess() != null && !Objects.equals(device.publicAccess, update.settings.publicAccess())) {
                            device.publicAccess = update.settings.publicAccess();
                            changed = true;
                        }

                        if (changed) {
                            device.registerEvent(new DomainEvent.SettingsUpdated(device.deviceId, device.getSettings()));
                        }
                    }
                    if (update.ownership != null) {
                        Objects.requireNonNull(update.ownership);
                        if (!Objects.equals(device.getOwnership(), update.ownership)) {
                            device.operator = update.ownership.operator();
                            device.provider = update.ownership.provider();
                            device.registerEvent(new DomainEvent.OwnershipUpdated(device.deviceId, device.getOwnership()));

                            if (update.ownership.isUnowned()) {
                                device.resetToDefaults();
                            }
                        }
                    }
                    repository.save(device);
                    Violations violations = new Violations(
                            device.operator == null,
                            device.provider == null,
                            device.getLocation() == null,
                            device.showOnMap && device.getLocation() == null,
                            device.showOnMap && !device.publicAccess
                    );
                    boolean usable = violations.isValid() && device.publicAccess;
                    Visibility visibility = new Visibility(usable, Visibility.ForCustomer.calculateForCustomer(usable, device.showOnMap));
                    return new DeviceConfiguration(
                            device.deviceId,
                            device.getOwnership(),
                            device.getLocation(),
                            device.getOpeningHours(),
                            device.getSettings(),
                            violations,
                            visibility
                    );
                });
    }
}
