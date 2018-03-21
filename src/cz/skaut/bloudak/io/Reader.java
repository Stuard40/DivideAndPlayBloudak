package cz.skaut.bloudak.io;

import cz.skaut.bloudak.io.Configuration;
import cz.skaut.bloudak.model.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static List<Player> readPlayers(String file, Configuration configuration) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            @SuppressWarnings("UnusedAssignment")
            // Skip first line of CSV
            String line = br.readLine();
            List<Player> players = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                players.add(Player.buildFromCSV(line, configuration));
            }
            return players;
        }
    }
}
