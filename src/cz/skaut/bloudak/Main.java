package cz.skaut.bloudak;

import cz.skaut.bloudak.io.Configuration;
import cz.skaut.bloudak.io.Reader;
import cz.skaut.bloudak.io.Writer;
import cz.skaut.bloudak.model.Player;
import cz.skaut.bloudak.model.Team;
import cz.skaut.bloudak.model.Teams;
import cz.skaut.bloudak.solvers.FriendsBroker;
import cz.skaut.bloudak.solvers.Solver;
import cz.skaut.bloudak.solvers.VortexSolver;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 1 && args[0].equalsIgnoreCase("-h")){
            // HELP
        } else {
            String configurationPath = args[0];
            Configuration configuration = new Configuration(configurationPath);
            List<Team> teams = Teams.buildEmpty(configuration.getTeamCount());
            String sourcePath = args[1];
            List<Player> players = Reader.readPlayers(sourcePath, configuration);
            // Solving here ...
            new Solver().basicResolve(players, teams);
            new VortexSolver(players, teams).vortex(100000);
            new FriendsBroker(players, teams).splitFriendsInTeam();
            // Write to file
            String targetPath = args[2];
            new Writer().writeTeams(teams, targetPath);
        }


        /*


        Map<Integer, Integer> conf = new HashMap<>();
        conf.put(6,5);
        conf.put(7,10);
        conf.put(8,15);
        conf.put(9,20);
        conf.put(10,20);
        conf.put(11,15);
        conf.put(12,15);
        conf.put(13,15);
        conf.put(14,15);
        conf.put(15,7);
        conf.put(16,6);
        conf.put(17,5);
        conf.put(18,2);
        PlayersGenerator playersGenerator = new PlayersGenerator();
        List<Player> players = playersGenerator.generate(conf);
        for(Player player: players) {
            System.out.println(player.toCSV());
        }
        System.out.println("\n\n\n\n");
        System.out.println("After Basic Resolve");
        System.out.println("\n\n");
        List<Team> teams = Teams.buildEmpty(20);
        new Solver().basicResolve(players, teams);
        for(Team team: teams) {
            System.out.println(team.toStringTeamOnly());
        }
        VortexSolver vortexSolver = new VortexSolver(players, teams);
        vortexSolver.vortex(100000);
        System.out.println("\n\n\n\n");
        System.out.println("After Vortex");
        System.out.println("\n\n");
        for(Team team: teams) {
            System.out.println(team.toStringTeamOnly());
        }*/
    }
}
