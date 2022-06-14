package owner;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:properties/remoteurl.properties"})

public interface RemoteURLserver extends Config {
    String loginremoteurl();
    String passwordremoteurl();
}
