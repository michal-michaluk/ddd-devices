package devices.configuration.device;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static devices.configuration.device.DeviceConfigurationAssert.assertThat;
import static devices.configuration.device.DeviceFixture.*;
import static devices.configuration.device.UpdateDevice.builder;

class DeviceServiceTest {

    final Map<String, Device> devices = new HashMap<>();
    final DeviceService service = new DeviceService(new FakeRepo());

    @Test
    void getDeviceConfiguration() {
        String deviceId = givenDevice();
        Optional<DeviceConfiguration> configuration = service.get(deviceId);

        assertThat(configuration)
                .hasOwnership(ownership())
                .hasLocation(location())
                .hasOpeningHours(OpeningHours.alwaysOpened())
                .hasSettings(Settings.defaultSettings())
                .hasNoViolations()
                .isNotVisible();
    }

    @Test
    void getUnknownDeviceConfiguration() {
        Optional<DeviceConfiguration> configuration = service.get("fake-device-id");

        Assertions.assertThat(configuration).isEmpty();
    }

    @Test
    void createNewDevice() {
        DeviceConfiguration configuration = service.createNewDevice(
                "new-device-id",
                UpdateDevice.use(ownership(), location())
        );

        assertThat(configuration)
                .hasOwnership(ownership())
                .hasLocation(location())
                .hasOpeningHours(OpeningHours.alwaysOpened())
                .hasSettings(Settings.defaultSettings())
                .hasNoViolations()
                .isNotVisible()
        ;

    }

    @Test
    void recreateDeviceWithExistingId() {
        String existingDeviceId = givenDevice();

        DeviceConfiguration configuration = service.createNewDevice(
                existingDeviceId,
                UpdateDevice.use(someOtherOwnership(), someOtherLocation())
        );

        assertThat(configuration)
                .hasOwnership(someOtherOwnership())
                .hasLocation(someOtherLocation())
                .hasOpeningHours(OpeningHours.alwaysOpened())
                .hasSettings(Settings.defaultSettings())
                .hasNoViolations();
    }

    @Test
    void update() {
        String existingDeviceId = givenDevice();

        Optional<DeviceConfiguration> configuration = service.update(
                existingDeviceId,
                builder()
                        .openingHours(closedAtWeekend())
                        .settings(settingsWithPublicAccessAndShowOnMapOnly())
                        .build()
        );

        assertThat(configuration)
                .hasOwnership(ownership())
                .hasLocation(location())
                .hasOpeningHours(closedAtWeekend())
                .hasSettings(settingsForPublicDevice())
                .hasNoViolations();
    }


    private String givenDevice() {
        Device device = DeviceFixture.givenDevice();
        devices.put(device.getDeviceId(), device);
        return device.getDeviceId();
    }

    class FakeRepo implements DeviceRepository {
        @Override
        public Optional<Device> findById(String deviceId) {
            return Optional.ofNullable(devices.get(deviceId));
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public Device save(Device device) {
            devices.put(device.getDeviceId(), device);
            return device;
        }

        @Override
        public List<Device> findAll() {
            return null;
        }

        @Override
        public List<Device> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Device> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public List<Device> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String s) {

        }

        @Override
        public void delete(Device entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends Device> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public <S extends Device> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Device> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Device> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<Device> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<String> strings) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Device getOne(String s) {
            return null;
        }

        @Override
        public Device getById(String s) {
            return null;
        }

        @Override
        public Device getReferenceById(String s) {
            return null;
        }

        @Override
        public <S extends Device> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Device> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Device> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends Device> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Device> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Device> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Device, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
    }
}
