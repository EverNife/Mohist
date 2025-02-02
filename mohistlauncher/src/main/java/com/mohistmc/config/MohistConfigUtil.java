/*
 * Mohist - MohistMC
 * Copyright (C) 2018-2022.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.mohistmc.config;

import com.mohistmc.MohistMCStart;
import com.mohistmc.yaml.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class MohistConfigUtil {

    public static File mohistyml = new File("mohist-config/mohist.yml");
    public static YamlConfiguration yml = YamlConfiguration.loadConfiguration(mohistyml);

    public static void copyMohistConfig() {
        try {
            if (!mohistyml.exists()) {
                mohistyml.mkdirs();
                Files.copy(MohistMCStart.class.getClassLoader().getResourceAsStream("configs/mohist.yml"), mohistyml.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("File copy exception!");
        }
    }

    public static boolean INSTALLATIONFINISHED() {
        return yml.getBoolean("mohist.installation-finished", false);
    }

    public static boolean CHECK_LIBRARIES() {
        if (yml.get("mohist.check_libraries") == null) {
            yml.set("mohist.check_libraries", true);
            save();
        }
        return yml.getBoolean("mohist.check_libraries", true);
    }

    public static boolean aBoolean(String key, boolean defaultReturn) {
        return yml.getBoolean(key, defaultReturn);
    }

    public static void save() {
        try {
            yml.save(mohistyml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
