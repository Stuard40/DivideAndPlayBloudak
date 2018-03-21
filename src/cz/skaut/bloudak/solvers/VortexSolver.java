package cz.skaut.bloudak.solvers;

import cz.skaut.bloudak.model.Team;
import cz.skaut.bloudak.model.Player;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class VortexSolver extends Solver {

    private final List<Player> playersPool;
    private final List<Team> teams;
    private final int expectedTotalScore;

    public VortexSolver(List<Player> playersPool, List<Team> teams) {
        this.playersPool = playersPool;
        this.teams = teams;
        expectedTotalScore = teams.stream().mapToInt(Team::getTotalScore).sum() / teams.size();
    }

    /* Swap players for closer total score */
    public void vortex(int times) {
        for (int i = 0; i < times; i++) {
            if (i % 10000 == 0) {
                System.out.println("Starting vortex iteration #" + i);
            }
            boolean anyReplaced = false;
            for (Player player : playersPool) {
                anyReplaced = findSwitchPlayers(player);
            }
            if (!anyReplaced) {
                break;
            }
        }
    }

    private boolean findSwitchPlayers(Player p1) {
        int sourceTeamScore = p1.getTeam().getTotalScore();
        double bestApproach = 0;
        Player bestPlayer = null;
        for (Team team : teams) {
            int targetTeamScore = team.getTotalScore();
            if (team != p1.getTeam() && targetTeamScore != expectedTotalScore) {
                for (Player p2 : team.getPlayers()) {
                    if (canBeSwitched(p1, p1)) {
                        double distanceBefore = sqrt(pow(expectedTotalScore - sourceTeamScore, 2)
                                * pow(expectedTotalScore - targetTeamScore, 2));
                        int sourceTeamScoreAfter = sourceTeamScore - p1.getRating() + p2.getRating();
                        int targetTeamScoreAfter = targetTeamScore + p1.getRating() - p2.getRating();
                        double distanceAfter = sqrt(pow(expectedTotalScore - sourceTeamScoreAfter, 2)
                                * pow(expectedTotalScore - targetTeamScoreAfter, 2));
                        if (distanceBefore > distanceAfter) {
                            double approach = distanceBefore - distanceAfter;
                            if (approach > bestApproach) {
                                bestApproach = approach;
                                bestPlayer = p2;
                            }
                        }
                    }
                }
            }
        }
        if (bestPlayer != null) {
            switchPlayers(p1, bestPlayer);
            return true;
        } else {
            return false;
        }
    }
}
