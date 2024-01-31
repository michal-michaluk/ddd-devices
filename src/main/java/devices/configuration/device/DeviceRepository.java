package devices.configuration.device;

import org.springframework.data.jpa.repository.JpaRepository;

interface DeviceRepository extends JpaRepository<DeviceEntity, String> {

}
