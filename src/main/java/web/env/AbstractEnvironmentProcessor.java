package web.env;

import kotlin.Pair;
import org.apache.maven.artifact.versioning.ComparableVersion;
import web.http.Host;
import web.struct.Destination;
import web.struct.Processor;
import web.struct.assignment.EnvironmentAssigner;

import java.util.*;

public abstract class AbstractEnvironmentProcessor implements Processor<EnvType>, EnvironmentAssigner {

    protected Set<ComparableVersion> versionSet = new HashSet<>();

    protected String protocol;
    protected String server;

    protected Host host;

    protected final static String successMessage = "  * %s tags have been found (%s/%s)";

    @Override
    public void configure(String protocol, String server) {
        Objects.requireNonNull(server, "Empty url value!");

        this.protocol = protocol;
        this.server = server;

        this.host = new Host(protocol, server);
    }

    @Override
    public void process() {}

    @Override
    public Pair<EnvType, Optional<Destination>> transmit() {
        return null;
    }

}
