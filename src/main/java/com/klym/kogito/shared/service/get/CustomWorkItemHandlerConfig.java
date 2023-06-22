package com.klym.kogito.shared.service.get;

import javax.enterprise.context.ApplicationScoped;
import org.kie.kogito.process.impl.DefaultWorkItemHandlerConfig;

@ApplicationScoped
public class CustomWorkItemHandlerConfig extends DefaultWorkItemHandlerConfig {
    {
            register("GetWS", new CustomGetWSTaskWorkItemHandler());
    }
}
