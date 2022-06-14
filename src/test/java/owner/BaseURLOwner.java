package owner;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:properties/baseurl.properties"
})
public interface BaseURLOwner extends Config  {
    String baseURL();
    String baseURI();
}
