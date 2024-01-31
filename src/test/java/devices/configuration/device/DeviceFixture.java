package devices.configuration.device;

import devices.configuration.tools.JsonConfiguration;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class DeviceFixture {

    public static String randomId() {
        return UUID.randomUUID().toString();
    }

    @NotNull
    public static DeviceEntity givenDevice() {
        DeviceEntity device = DeviceEntity.newDevice(randomId());
        Ownership ownership = ownership();
        Objects.requireNonNull(ownership);
        if (!Objects.equals(device.getOwnership(), ownership)) {
            device.operator = ownership.operator();
            device.provider = ownership.provider();
            device.registerEvent(new DomainEvent.OwnershipUpdated(device.deviceId, device.getOwnership()));

            if (ownership.isUnowned()) {
                device.resetToDefaults();
            }
        }
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

        OpeningHours openingHours = OpeningHours.alwaysOpened();
        Objects.requireNonNull(openingHours);
        if (!Objects.equals(device.getOpeningHours(), openingHours)) {
            device.openingHours.clear();
            device.openingHours.addAll(DeviceEntity.OpeningHoursEntity.of(device.deviceId, openingHours));
            device.registerEvent(new DomainEvent.OpeningHoursUpdated(device.deviceId, device.getOpeningHours()));
        }
        Settings settings = Settings.defaultSettings();
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
        device.clearEvents();
        return device;
    }

    @NotNull
    public static DeviceConfiguration givenDeviceConfiguration() {
        return givenDeviceConfiguration(randomId());
    }

    @NotNull
    public static DeviceConfiguration givenDeviceConfiguration(String deviceId) {
        return new DeviceConfiguration(
                deviceId,
                ownership(),
                location(),
                OpeningHours.alwaysOpened(),
                Settings.defaultSettings(),
                Violations.builder().build(),
                new Visibility(true, Visibility.ForCustomer.calculateForCustomer(true, false))
        );
    }

    @NotNull
    public static Location location() {
        return new Location(
                "Rakietowa",
                "1A",
                "Wrocław",
                "54-621",
                null,
                "POL",
                new Location.Coordinates(new BigDecimal("51.09836221719513"), new BigDecimal("16.931752852309156")));
    }

    @NotNull
    public static Location someOtherLocation() {
        return new Location(
                "Żwirki i Wigury",
                "1H",
                "Warszawa",
                "54-202",
                null,
                "PL",
                new Location.Coordinates(
                        new BigDecimal("51.11745363251369"),
                        new BigDecimal("16.997318413019084"))
        );
    }

    @NotNull
    public static Ownership ownership() {
        return new Ownership("Devicex.nl", "public-devices");
    }

    @NotNull
    public static Ownership someOtherOwnership() {
        return new Ownership("Devicex.pl", "public-devices");
    }

    @SneakyThrows
    public static Settings settingsWithAutoStartOnly() {
        @Language("JSON") var json = """
                {
                    "autoStart": true
                }
                """;
        return JsonConfiguration.OBJECT_MAPPER.readValue(json, Settings.class);
    }

    @SneakyThrows
    public static Settings settingsWithPublicAccessAndShowOnMapOnly() {
        return Settings.builder()
                .showOnMap(true)
                .publicAccess(true)
                .build();
    }

    public static Settings settingsForPublicDevice() {
        return Settings.defaultSettings().toBuilder()
                .showOnMap(true)
                .publicAccess(true)
                .build();
    }

    public static DeviceEntity givenStepByStepConfiguredDevice() {
        DeviceEntity device = DeviceEntity.newDevice(randomId());
        Ownership ownership = ownership();
        Objects.requireNonNull(ownership);
        if (!Objects.equals(device.getOwnership(), ownership)) {
            device.operator = ownership.operator();
            device.provider = ownership.provider();
            device.registerEvent(new DomainEvent.OwnershipUpdated(device.deviceId, device.getOwnership()));

            if (ownership.isUnowned()) {
                device.resetToDefaults();
            }
        }
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

        OpeningHours openingHours = OpeningHours.alwaysOpened();
        Objects.requireNonNull(openingHours);
        if (!Objects.equals(device.getOpeningHours(), openingHours)) {
            device.openingHours.clear();
            device.openingHours.addAll(DeviceEntity.OpeningHoursEntity.of(device.deviceId, openingHours));
            device.registerEvent(new DomainEvent.OpeningHoursUpdated(device.deviceId, device.getOpeningHours()));
        }
        Settings settings = Settings.defaultSettings();
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
        return device;
    }

    @NotNull
    public static OpeningHours closedAtWeekend() {
        return OpeningHours.openAt(
                OpeningHours.OpeningTime.opened24h(),
                OpeningHours.OpeningTime.opened24h(),
                OpeningHours.OpeningTime.opened24h(),
                OpeningHours.OpeningTime.opened24h(),
                OpeningHours.OpeningTime.opened24h(),
                OpeningHours.OpeningTime.closed24h(),
                OpeningHours.OpeningTime.closed24h()
        );
    }
}
