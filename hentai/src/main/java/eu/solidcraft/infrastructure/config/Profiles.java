package eu.solidcraft.infrastructure.config;


/**
 * This is a set of typical profiles used in andamio.
 * Default profile is set to local.
 *
 * This is a class, not an enum, because in different technologies (Groovy for example) enums get tricky to use
 * in annotations over classes in different source roots (test directory).
 */
public class Profiles {
    /**
     * Profile used for any environment to run microservice in production mode.
     */
    public static final String PROD = "prod";

    /**
     * Profile used for development servers.
     */
    public static final String DEV = "dev";

    /**
     * Profile used for test servers, i.e.: manual testing on test servers.
     * This is NOT for integration tests
     */
    public static final String TEST = "test";

    /**
     * Profile used for integration tests
     */
    public static final String INTEGRATION = "integration";

    /**
     * Profile used for running on local machine. No service registry.
     */
    public static final String LOCAL = "local";

}
