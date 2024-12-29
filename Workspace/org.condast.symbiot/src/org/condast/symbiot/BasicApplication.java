package org.condast.symbiot;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;


public class BasicApplication implements ApplicationConfiguration {

    public void configure(Application application) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(WebClient.PAGE_TITLE, "Symbiot Demonstrator 2D");
        application.addEntryPoint("/symbiot", BasicEntryPoint.class, properties);

        properties.put(WebClient.PAGE_TITLE, "Symbiot Demonstrator 1D");
        application.addEntryPoint("/onedim", EntryPoint1D.class, properties);
    }
}
