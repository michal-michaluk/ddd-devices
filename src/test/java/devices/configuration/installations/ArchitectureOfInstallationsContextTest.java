package devices.configuration.installations;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import devices.configuration.ArchitectureDescription;
import devices.configuration.device.Location;
import devices.configuration.device.Ownership;
import devices.configuration.protocols.BootNotification;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;

@AnalyzeClasses(
        packages = "devices.configuration",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureOfInstallationsContextTest {
    public static final String PACKAGE = "devices.configuration.installations";

    public static final DescribedPredicate<JavaClass> sharedKernelExposed = belongToAnyOf(
            DomainEvent.DeviceAssigned.class,
            DomainEvent.InstallationCompleted.class
    );
    public static final DescribedPredicate<JavaClass> sharedKernelUsed = belongToAnyOf(
            Ownership.class, Location.class,
            BootNotification.class
    );

    @ArchTest
    public static final ArchRule adaptersDependencies = ArchitectureDescription.adaptersDependencies(PACKAGE, sharedKernelUsed);

    @ArchTest
    public static final ArchRule servicesDependencies = ArchitectureDescription.servicesDependencies(PACKAGE, sharedKernelUsed);

    @ArchTest
    public static final ArchRule modelDependencies = ArchitectureDescription.modelDependencies(PACKAGE, sharedKernelUsed);

    @ArchTest
    public static final ArchRule adaptersIsolation = ArchitectureDescription.adaptersIsolation(PACKAGE);

    @ArchTest
    public static final ArchRule servicesIsolation = ArchitectureDescription.servicesIsolation(PACKAGE, ArchitectureDescription.mediators);

    @ArchTest
    public static final ArchRule modelIsolation = ArchitectureDescription.modelIsolation(PACKAGE, sharedKernelExposed);
}
