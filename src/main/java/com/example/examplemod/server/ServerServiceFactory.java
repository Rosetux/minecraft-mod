package com.example.examplemod.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerServiceFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public IServerService createServerService() {
        LOGGER.info("Creating server service");
        return new ServerService();
    }
}
