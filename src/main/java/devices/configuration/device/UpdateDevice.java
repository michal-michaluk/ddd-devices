package devices.configuration.device;

import lombok.Builder;

import javax.validation.Valid;

@Builder
public record UpdateDevice(
        @Valid Location location,
        @Valid OpeningHours openingHours,
        @Valid Settings settings,
        @Valid Ownership ownership) {

    public static UpdateDevice use(Ownership ownership, Location location) {
        return builder()
                .location(location)
                .ownership(ownership)
                .build();
    }

}
