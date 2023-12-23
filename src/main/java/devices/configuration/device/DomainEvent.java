package devices.configuration.device;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DomainEvent.OwnershipUpdated.class, name = "OwnershipUpdated_v2"),
        @JsonSubTypes.Type(value = DomainEvent.OpeningHoursUpdated.class, name = "OpeningHoursUpdated_v1"),
        @JsonSubTypes.Type(value = DomainEvent.LocationUpdated.class, name = "LocationUpdated_v1"),
        @JsonSubTypes.Type(value = DomainEvent.SettingsUpdated.class, name = "SettingsUpdated_v1")
})
public interface DomainEvent {
    record LocationUpdated(String deviceId, Location location) implements DomainEvent {
    }

    record OpeningHoursUpdated(String deviceId, OpeningHours openingHours) implements DomainEvent {
    }

    record OwnershipUpdated(String deviceId, Ownership ownership) implements DomainEvent {
    }

    record SettingsUpdated(String deviceId, Settings settings) implements DomainEvent {
    }
}
