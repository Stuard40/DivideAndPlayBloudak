package cz.skaut.bloudak.solvers;

import cz.skaut.bloudak.model.EthnicVector;
import cz.skaut.bloudak.model.Player;
import cz.skaut.bloudak.model.Team;

import java.util.*;
import java.util.stream.Collectors;

public class FriendsBroker {

    private final List<Player> playersPool;

    private final Set<String> origins;
    private final EthnicVector targetVector = new EthnicVector(10);

    public FriendsBroker(List<Player> playersPool, List<Team> teams) {
        this.playersPool = playersPool;
        this.origins = playersPool.stream().map(player -> player.origin).collect(Collectors.toSet());
        for (String origin : origins) {
            targetVector.put(origin, (double) getPlayersCountWihThisOrigin(origin) / (double) teams.size());
        }
    }

    /**
     * Search if in the team exist more then one player
     */
    public void splitFriendsInTeam() {
        Player bestApproachP1;
        Player bestApproachP2 = null;
        do {
            bestApproachP1 = null;
            double bestApproach = 0;
            for (Player p1 : playersPool) {
                for (Player p2 : playersPool) {
                    if (p1 != p2 &&
                            Math.abs(p1.getRating() - p2.getRating()) < 1 &&
                            !p1.origin.equalsIgnoreCase(p2.origin)
                            && p1.getTeam() != p2.getTeam() && Solver.canBeSwitched(p1, p2)) {
                        double d1Before = distance(origins, p1.getTeam().ethnicVector(origins), targetVector);
                        double d2Before = distance(origins, p2.getTeam().ethnicVector(origins), targetVector);
                        double d1After = distance(origins, afterVector(p1.getTeam(), p1, p2), targetVector);
                        double d2After = distance(origins, afterVector(p2.getTeam(), p2, p1), targetVector);
                        if (d1Before + d2Before > d1After + d2After) {
                            double approach = (d1Before + d2Before) - (d1After + d2After);
                            if (approach > bestApproach) {
                                bestApproachP1 = p1;
                                bestApproachP2 = p2;
                                bestApproach = approach;
                            }
                        }

                    }
                }
            }
            if (bestApproachP1 != null) {
                Solver.switchPlayers(bestApproachP1, bestApproachP2);
            }
        } while (bestApproachP1 != null);
    }

    private Integer getPlayersCountWihThisOrigin(String origin) {
        return Math.toIntExact(playersPool.stream().filter(player -> player.origin.equalsIgnoreCase(origin)).count());
    }

    private double distance(Set<String> origins, EthnicVector v1, EthnicVector v2) {
        double sum = 0;
        for (String origin : origins) {
            Double vd1 = v1.get(origin);
            Double vd2 = v2.get(origin);
            sum += Math.pow(vd1 - vd2, 2);
        }
        return Math.sqrt(sum);
    }

    private EthnicVector afterVector(Team team, Player p1, Player p2) {
        EthnicVector vector = team.ethnicVector(origins);
        vector.put(p1.origin, vector.get(p1.origin) - 1);
        vector.put(p2.origin, vector.get(p2.origin) + 1);
        return vector;
    }

}
