package owner;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:properties/datawebshop.properties"
})
public interface DataWebShop extends Config  {
    String login();
    String password();
    String authCookieName();
}