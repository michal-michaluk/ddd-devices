package devices.configuration.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static devices.configuration.device.DeviceConfigurationAssert.assertThat;
import static devices.configuration.device.DeviceFixture.*;
import static devices.configuration.device.Violations.builder;

class DeviceTest {

    @Test
    void newDeviceHasSettingsSetToDefaults() {
        DeviceEntity device = DeviceEntity.newDevice(DeviceFixture.randomId());

        assertThat(device)
                .hasSettings(Settings.defaultSettings())
                .hasViolationsLikeNotConfiguredDevice();
    }

    @Test
    void overrideSettings() {
        DeviceEntity device = givenDevice();

        Settings settings = settingsForPublicDevice();
        Objects.requireNonNull(settings);
        boolean changed = false;
        if (settings.autoStart() != null && !Objects.equals(device.autoStart, settings.autoStart())) {
            device.autoStart = settings.autoStart();
            changed = true;
        }
        if (settings.remoteControl() != null && !Objects.equals(device.remoteControl, settings.remoteControl())) {
            device.remoteControl = settings.remoteControl();
            changed = true;
        }
        if (settings.billing() != null && !Objects.equals(device.billing, settings.billing())) {
            device.billing = settings.billing();
            changed = true;
        }
        if (settings.reimbursement() != null && !Objects.equals(device.reimbursement, settings.reimbursement())) {
            device.reimbursement = settings.reimbursement();
            changed = true;
        }
        if (settings.showOnMap() != null && !Objects.equals(device.showOnMap, settings.showOnMap())) {
            device.showOnMap = settings.showOnMap();
            changed = true;
        }
        if (settings.publicAccess() != null && !Objects.equals(device.publicAccess, settings.publicAccess())) {
            device.publicAccess = settings.publicAccess();
            changed = true;
        }

        if (changed) {
            device.registerEvent(new DomainEvent.SettingsUpdated(device.deviceId, device.getSettings()));
        }

        assertThat(device)
                .hasSettings(settingsForPublicDevice())
                .hasNoViolations();
    }

    @Test
    void updateSingleValueInSettings() throws JsonProcessingException {
        DeviceEntity device = givenDevice();

        Settings settings = settingsWithAutoStartOnly();
        Objects.requireNonNull(settings);
        boolean changed = false;
        if (settings.autoStart() != null && !Objects.equals(device.autoStart, settings.autoStart())) {
            device.autoStart = settings.autoStart();
            changed = true;
        }
        if (settings.remoteControl() != null && !Objects.equals(device.remoteControl, settings.remoteControl())) {
            device.remoteControl = settings.remoteControl();
            changed = true;
        }
        if (settings.billing() != null && !Objects.equals(device.billing, settings.billing())) {
            device.billing = settings.billing();
            changed = true;
        }
        if (settings.reimbursement() != null && !Objects.equals(device.reimbursement, settings.reimbursement())) {
            device.reimbursement = settings.reimbursement();
            changed = true;
        }
        if (settings.showOnMap() != null && !Objects.equals(device.showOnMap, settings.showOnMap())) {
            device.showOnMap = settings.showOnMap();
            changed = true;
        }
        if (settings.publicAccess() != null && !Objects.equals(device.publicAccess, settings.publicAccess())) {
            device.publicAccess = settings.publicAccess();
            changed = true;
        }

        if (changed) {
            device.registerEvent(new DomainEvent.SettingsUpdated(device.deviceId, device.getSettings()));
        }

        assertThat(device)
                .hasSettings(Settings.defaultSettings().toBuilder()
                        .autoStart(true))
                .hasNoViolations();
    }

    @Test
    void mergeSettings() throws JsonProcessingException {
        DeviceEntity device = givenDevice();

        Settings settings1 = settingsForPublicDevice();
        Objects.requireNonNull(settings1);
        boolean changed1 = false;
        if (settings1.autoStart() != null && !Objects.equals(device.autoStart, settings1.autoStart())) {
            device.autoStart = settings1.autoStart();
            changed1 = true;
        }
        if (settings1.remoteControl() != null && !Objects.equals(device.remoteControl, settings1.remoteControl())) {
            device.remoteControl = settings1.remoteControl();
            changed1 = true;
        }
        if (settings1.billing() != null && !Objects.equals(device.billing, settings1.billing())) {
            device.billing = settings1.billing();
            changed1 = true;
        }
        if (settings1.reimbursement() != null && !Objects.equals(device.reimbursement, settings1.reimbursement())) {
            device.reimbursement = settings1.reimbursement();
            changed1 = true;
        }
        if (settings1.showOnMap() != null && !Objects.equals(device.showOnMap, settings1.showOnMap())) {
            device.showOnMap = settings1.showOnMap();
            changed1 = true;
        }
        if (settings1.publicAccess() != null && !Objects.equals(device.publicAccess, settings1.publicAccess())) {
            device.publicAccess = settings1.publicAccess();
            changed1 = true;
        }

        if (changed1) {
            device.registerEvent(new DomainEvent.SettingsUpdated(device.deviceId, device.getSettings()));
        }
        Settings settings = settingsWithAutoStartOnly();
        Objects.requireNonNull(settings);
        boolean changed = false;
        if (settings.autoStart() != null && !Objects.equals(device.autoStart, settings.autoStart())) {
            device.autoStart = settings.autoStart();
            changed = true;
        }
        if (settings.remoteControl() != null && !Objects.equals(device.remoteControl, settings.remoteControl())) {
            device.remoteControl = settings.remoteControl();
            changed = true;
        }
        if (settings.billing() != null && !Objects.equals(device.billing, settings.billing())) {
            device.billing = settings.billing();
            changed = true;
        }
        if (settings.reimbursement() != null && !Objects.equals(device.reimbursement, settings.reimbursement())) {
            device.reimbursement = settings.reimbursement();
            changed = true;
        }
        if (settings.showOnMap() != null && !Objects.equals(device.showOnMap, settings.showOnMap())) {
            device.showOnMap = settings.showOnMap();
            changed = true;
        }
        if (settings.publicAccess() != null && !Objects.equals(device.publicAccess, settings.publicAccess())) {
            device.publicAccess = settings.publicAccess();
            changed = true;
        }

        if (changed) {
            device.registerEvent(new DomainEvent.SettingsUpdated(device.deviceId, device.getSettings()));
        }

        assertThat(device)
                .hasSettings(Settings.builder()
                        .publicAccess(true)
                        .showOnMap(true)
                        .autoStart(true)
                        .remoteControl(false)
                        .billing(false)
                        .reimbursement(false)
                )
                .hasNoViolations();
    }

    @Test
    void newDeviceHasOwnershipSetToUnowned() {
        DeviceEntity device = DeviceEntity.newDevice(DeviceFixture.randomId());

        assertThat(device)
                .hasOwnership(Ownership.unowned())
                .hasViolationsLikeNotConfiguredDevice();
    }

    @Test
    void assignStationToOwner() {
        DeviceEntity device = givenDevice();
        Ownership ownership = someOtherOwnership();
        Objects.requireNonNull(ownership);
        if (!Objects.equals(device.getOwnership(), ownership)) {
            device.operator = ownership.operator();
            device.provider = ownership.provider();
            device.registerEvent(new DomainEvent.OwnershipUpdated(device.deviceId, device.getOwnership()));

            if (ownership.isUnowned()) {
                device.resetToDefaults();
            }
        }

        assertThat(device)
                .hasOwnership(someOtherOwnership())
                .hasNoViolations();
    }

    @Test
    void resetOwnership() {
        DeviceEntity device = givenDevice();

        Ownership ownership = Ownership.unowned();
        Objects.requireNonNull(ownership);
        if (!Objects.equals(device.getOwnership(), ownership)) {
            device.operator = ownership.operator();
            device.provider = ownership.provider();
            device.registerEvent(new DomainEvent.OwnershipUpdated(device.deviceId, device.getOwnership()));

            if (ownership.isUnowned()) {
                device.resetToDefaults();
            }
        }

        assertThat(device)
                .hasOwnership(Ownership.unowned())
                .hasSettings(Settings.defaultSettings())
                .hasLocation(null)
                .hasOpeningHours(OpeningHours.alwaysOpened())
                .hasViolationsLikeNotConfiguredDevice();
    }

    @Test
    void resetToDefaults() {
        DeviceEntity device = givenDevice();
        device.resetToDefaults();

        assertThat(device)
                .hasOwnership(DeviceFixture.ownership())
                .hasSettings(Settings.defaultSettings())
                .hasLocation(null)
                .hasOpeningHours(OpeningHours.alwaysOpened())
                .hasViolations(Violations.builder()
                        .locationMissing(true));
    }

    @Test
    void newDeviceHasNoLocation() {
        DeviceEntity device = DeviceEntity.newDevice(DeviceFixture.randomId());

        assertThat(device)
                .hasLocation(null)
                .hasViolationsLikeNotConfiguredDevice();
    }

    @Test
    void updateLocation() {
        DeviceEntity device = DeviceEntity.newDevice(DeviceFixture.randomId());
        Location location = location();
        if (!Objects.equals(device.getLocation(), location)) {
            if (location != null) {
                device.street = location.street();
                device.houseNumber = location.houseNumber();
                device.city = location.city();
                device.postalCode = location.postalCode();
                device.state = location.state();
                device.country = location.country();
                device.longitude = location.coordinates().longitude();
                device.latitude = location.coordinates().latitude();

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

        assertThat(device)
                .hasLocation(location())
                .hasViolations(builder()
                        .providerNotAssigned(true)
                        .operatorNotAssigned(true)
                );
    }

    @Test
    void overrideLocation() {
        DeviceEntity device = givenDevice();
        Location location = someOtherLocation();
        if (!Objects.equals(device.getLocation(), location)) {
            if (location != null) {
                device.street = location.street();
                device.houseNumber = location.houseNumber();
                device.city = location.city();
                device.postalCode = location.postalCode();
                device.state = location.state();
                device.country = location.country();
                device.longitude = location.coordinates().longitude();
                device.latitude = location.coordinates().latitude();

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

        assertThat(device)
                .hasLocation(someOtherLocation())
                .hasNoViolations();
    }

    @Test
    void resetLocation() {
        DeviceEntity device = givenDevice();
        if (!Objects.equals(device.getLocation(), null)) {
            if (null != null) {
                device.street = ((Location) null).street();
                device.houseNumber = ((Location) null).houseNumber();
                device.city = ((Location) null).city();
                device.postalCode = ((Location) null).postalCode();
                device.state = ((Location) null).state();
                device.country = ((Location) null).country();
                device.longitude = ((Location) null).coordinates().longitude();
                device.latitude = ((Location) null).coordinates().latitude();

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

        assertThat(device)
                .hasLocation(null)
                .hasViolations(builder()
                        .locationMissing(true));
    }
}
