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

package com.mohistmc;

import com.mohistmc.action.v_1_19.v_1_19;
import com.mohistmc.config.MohistConfigUtil;
import com.mohistmc.libraries.CustomLibraries;
import com.mohistmc.libraries.DefaultLibraries;
import com.mohistmc.util.DataParser;
import com.mohistmc.util.MohistModuleManager;
import com.mohistmc.util.i18n.i18n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static com.mohistmc.util.EulaUtil.hasAcceptedEULA;
import static com.mohistmc.util.EulaUtil.writeInfos;

public class MohistMCStart {

    public static List<String> mainArgs = new ArrayList<>();
    public static float javaVersion = Float.parseFloat(System.getProperty("java.class.version"));

    public static String getVersion() {
        return (MohistMCStart.class.getPackage().getImplementationVersion() != null) ? MohistMCStart.class.getPackage().getImplementationVersion() : "unknown";
    }

    public static void main(String[] args) throws Exception {
        mainArgs.addAll(List.of(args));
        DataParser.parseVersions();
        DataParser.parseLaunchArgs();
        DataParser.parseLibrariesClassPath();

        MohistConfigUtil.copyMohistConfig();

        if (MohistConfigUtil.bMohist("show_logo", "true"))
            System.out.println("\n" + "\n" +
                    " __    __   ______   __  __   __   ______   ______  \n" +
                    "/\\ \"-./  \\ /\\  __ \\ /\\ \\_\\ \\ /\\ \\ /\\  ___\\ /\\__  _\\ \n" +
                    "\\ \\ \\-./\\ \\\\ \\ \\/\\ \\\\ \\  __ \\\\ \\ \\\\ \\___  \\\\/_/\\ \\/ \n" +
                    " \\ \\_\\ \\ \\_\\\\ \\_____\\\\ \\_\\ \\_\\\\ \\_\\\\/\\_____\\  \\ \\_\\ \n" +
                    "  \\/_/  \\/_/ \\/_____/ \\/_/\\/_/ \\/_/ \\/_____/   \\/_/ \n" +
                    "                                                    \n" + "\n" +
                    "                                      " + i18n.get("mohist.launch.welcomemessage") + " - " + getVersion() + ", Java " + javaVersion);

        if (MohistConfigUtil.bMohist("check_libraries", "true")) {
            DefaultLibraries.run();
            new v_1_19().run();
        }
        CustomLibraries.loadCustomLibs();

        //The server can be run with Java 16+
        if (Float.parseFloat(System.getProperty("java.class.version")) >= 60.0) {
            System.out.println("Setting launch args");
            new MohistModuleManager(DataParser.launchArgs);
            System.out.println("Done");
        }

        // make sure gson use this EnumTypeAdapter
        Class.forName("com.google.gson.internal.bind.TypeAdapters$EnumTypeAdapter").getClassLoader();

        if (mainArgs.contains("-noserver"))
            System.exit(0); //-noserver -> Do not run the Minecraft server, only let the installation running.

        if (!hasAcceptedEULA()) {
            System.out.println(i18n.get("eula"));
            while (!"true".equals(new Scanner(System.in).next())) ;
            writeInfos();
        }

        List<String> forgeArgs = new ArrayList<>();
        forgeArgs.add("--add-modules ALL-MODULE-PATH");
        for (String arg : DataParser.launchArgs.stream()
                .filter(s -> s.startsWith("--launchTarget") || s.startsWith("--fml.forgeVersion") || s.startsWith("--fml.mcVersion") || s.startsWith("--fml.forgeGroup") || s.startsWith("--fml.mcpVersion")).collect(Collectors.toList())) {
            forgeArgs.add(arg.split(" ")[0]);
            forgeArgs.add(arg.split(" ")[1]);
        }


        String[] args_ = Stream.concat(forgeArgs.stream(), Arrays.stream(args)).toArray(String[]::new);
        System.out.println(args_);
        var cl = Class.forName("cpw.mods.bootstraplauncher.BootstrapLauncher");
        var method = cl.getMethod("main", String[].class);
        method.invoke(null, (Object) args_);
    }
}
