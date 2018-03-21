package cz.skaut.bloudak.io;

import cz.skaut.bloudak.model.Player;
import cz.skaut.bloudak.model.Team;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Writer {

    private static String headerPlayer = "jméno,přezdívka,oddíl,věk,úroveň,znalí ústí,šance";
    private static String headerTeam = "jméno,počet hraču,šance celkem";


    public void writeTeams(List<Team> teams, String file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (Team team : teams) {
                bw.write(headerTeam);
                bw.newLine();
                bw.write(team.name + "," + team.getPlayers().size() + "," + team.getTotalScore());
                bw.newLine();
                bw.write(headerPlayer);
                bw.newLine();
                for (Player player : team.getPlayers()) {
                    bw.write(player.toCSV());
                    bw.newLine();
                }
            }
        }
    }

}
