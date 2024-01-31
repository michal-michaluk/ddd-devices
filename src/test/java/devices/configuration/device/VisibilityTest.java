package devices.configuration.device;

import org.junit.jupiter.api.Test;

import static devices.configuration.device.Visibility.ForCustomer.*;
import static org.assertj.core.api.Assertions.assertThat;

class VisibilityTest {

    @Test
    void usableAndVisibleOnMap() {
        assertThat(new Visibility(true, calculateForCustomer(true, true)))
                .isEqualTo(new Visibility(true, USABLE_AND_VISIBLE_ON_MAP));
    }

    @Test
    void usableButHiddenOnMap() {
        assertThat(new Visibility(true, calculateForCustomer(true, false)))
                .isEqualTo(new Visibility(true, USABLE_BUT_HIDDEN_ON_MAP));
    }

    @Test
    void inaccessibleAndHiddenOnMap() {
        assertThat(new Visibility(false, calculateForCustomer(false, false)))
                .isEqualTo(new Visibility(false, INACCESSIBLE_AND_HIDDEN_ON_MAP));
    }

    @Test
    void inaccessibleAndHiddenOnMapEvenWhenIntendedToShow() {
        assertThat(new Visibility(false, calculateForCustomer(false, true)))
                .isEqualTo(new Visibility(false, INACCESSIBLE_AND_HIDDEN_ON_MAP));
    }
}
