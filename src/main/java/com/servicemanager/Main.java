/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.servicemanager;

import static io.helidon.config.ConfigSources.classpath;
import static io.helidon.config.ConfigSources.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.logging.LogManager;

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.microprofile.server.Server;

/**
 * The application main class.
 */
public final class Main {
    static final String logPath = "-DlogPath";
    static final String configPath = "-DconfigPath";

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     * 
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {

        System.setProperty("log-path", "./applogs");
        String configFolderPath = "./config";
        System.out.print("Application started with following parameters:");
        for (String str : args) {
            String[] keyValuePair = str.split("=");
            System.out.print(str);
            if (keyValuePair[0].equalsIgnoreCase(logPath)) {
                System.setProperty("log-path", keyValuePair[1]);
            }
            if (keyValuePair[0].equalsIgnoreCase(configPath)) {
                configFolderPath = keyValuePair[1];
            }
        }

        System.out.println();

        // load logging configuration
        setupLogging();

        // start the server
        startServer(configFolderPath);

    }

    /**
     * Start the server.
     * 
     * @return the created {@link Server} instance
     */
    static Server startServer(String configFolderPath) {

        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        return Server.builder().config(buildConfig(configFolderPath)).build().start();
    }

    private static Config buildConfig(String configFolderPath) {

        String microprofileConfigPath = Paths.get(configFolderPath, "microprofile-config.properties").toString();
        System.out.println("Calculated config path is:" + microprofileConfigPath);
        return Config.builder().sources(ConfigSources.create(file(microprofileConfigPath),
                classpath("META-INF/microprofile-config.properties"))).build();

    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }
}
