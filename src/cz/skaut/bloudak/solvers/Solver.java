package cz.skaut.bloudak.solvers;

import cz.skaut.bloudak.model.Team;
import cz.skaut.bloudak.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    /* Solve this problem basic */
    public void basicResolve(List<Player> playersPool, List<Team> teams) {
        List<Player> players = new ArrayList<>(playersPool);
        players.sort(Comparator.comparingInt(Player::getRating).reversed());
        // Leaders
        for (Team team : teams) {
            Player leader = players.stream().filter(Player::canBeLeader).findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not enough leaders!"));
            players.remove(leader);
            team.addPlayer(leader);
        }
        // Homemade
        for (Team team : teams) {
            if (!team.hasOtherHomemadeThen(null)) {
                Player homemade = players.stream().filter(Player::isHomemade).findFirst()
                        .orElseThrow(() -> new IllegalStateException("In the input set of players is less players " +
                                "with knowledge of terrain than the number of teams. Every team must have same one " +
                                "who has knowledge of terrain! So you must decide who will be marked as " +
                                "the terrain knowledgeable to solve this problem."));
                players.remove(homemade);
                team.addPlayer(homemade);
            }
        }
        int maxPlayersInTeam = (playersPool.size() / teams.size()) + 1;
        // Other
        for (Player player : players) {
            if (player.getRating() > 0) {
                Team theWeakestTeam = findTeam(teams, false, maxPlayersInTeam);
                theWeakestTeam.addPlayer(player);
            } else {
                Team theStrongestTeam = findTeam(teams, true, maxPlayersInTeam);
                theStrongestTeam.addPlayer(player);
            }
        }
        // Team must have same number of players
        int expectedTotalScore = teams.stream().mapToInt(Team::getTotalScore).sum() / teams.size();
        for(Team team: teams){
            if(team.getPlayers().size() < maxPlayersInTeam - 1) {
                if(expectedTotalScore < team.getTotalScore()) {
                    Team t2 = findTeamThatHas(teams, false, maxPlayersInTeam);
                    Player player = t2.getNobiePlayer();
                    t2.removePlayer(player);
                    team.addPlayer(player);
                } else {
                    Team t2 = findTeamThatHas(teams, true, maxPlayersInTeam);
                    Player player = t2.getStrongPlayer();
                    t2.removePlayer(player);
                    team.addPlayer(player);
                }
            }

        }
    }


    private static Team findTeam(List<Team> teams, boolean strongest, int maxPlayersInTeam) {
        Team bestChoose = null;
        for (Team team : teams) {
            if(team.getPlayers().size() < maxPlayersInTeam) {
                if(bestChoose == null) {
                    bestChoose = team;
                }
                if(strongest) {
                    if(team.getTotalScore() > bestChoose.getTotalScore()){
                        bestChoose = team;
                    }
                } else {
                    if(team.getTotalScore() < bestChoose.getTotalScore()) {
                        bestChoose = team;
                    }
                }
            }
        }
        return bestChoose;
    }

    private static Team findTeamThatHas(List<Team> teams, boolean strongest, int hasPlayers) {
        Team bestChoose = null;
        for (Team team : teams) {
            if(team.getPlayers().size() == hasPlayers) {
                if(bestChoose == null) {
                    bestChoose = team;
                }
                if(strongest) {
                    if(team.getTotalScore() > bestChoose.getTotalScore()){
                        bestChoose = team;
                    }
                } else {
                    if(team.getTotalScore() < bestChoose.getTotalScore()) {
                        bestChoose = team;
                    }
                }
            }
        }
        return bestChoose;
    }

    /**
     * Very stupid method
     * @param p1 - Player from team 1
     * @param p2 - Player from team 2
     * @return if p1 can be switched with p2
     */
    public static boolean canBeSwitched(Player p1, Player p2) {
        if (p1.isHomemade() || p2.isHomemade() || p1.canBeLeader() || p2.canBeLeader()) {
            if (p1.isHomemade() && p1.canBeLeader() && p2.isHomemade() && p2.canBeLeader()) {
                return true;
            } else if (p1.isHomemade() && p1.canBeLeader() && p2.isHomemade() && !p2.canBeLeader()) {
                return p2.getTeam().hasOtherLeaderThen(p2);
            } else if (p1.isHomemade() && p1.canBeLeader() && !p2.isHomemade() && p2.canBeLeader()) {
                return p2.getTeam().hasOtherHomemadeThen(p2);
            } else if (p1.isHomemade() && !p1.canBeLeader() && p2.isHomemade() && p2.canBeLeader()) {
                return p1.getTeam().hasOtherLeaderThen(p1);
            } else if (!p1.isHomemade() && p1.canBeLeader() && p2.isHomemade() && p2.canBeLeader()) {
                return p1.getTeam().hasOtherHomemadeThen(p1);
            } else if (!p1.isHomemade() && p1.canBeLeader() && p2.isHomemade() && !p2.canBeLeader()) {
                return p1.getTeam().hasOtherHomemadeThen(p1) && p2.getTeam().hasOtherLeaderThen(p2);
            } else if (!p1.isHomemade() && p1.canBeLeader() && !p2.isHomemade() && p2.canBeLeader()) {
                return p1.getTeam().hasOtherHomemadeThen(p1) && p2.getTeam().hasOtherHomemadeThen(p2);
            } else if (!p1.isHomemade() && !p1.canBeLeader() && p2.isHomemade() && p2.canBeLeader()) {
                return p1.getTeam().hasOtherHomemadeLeaderThen(p1);
            } else if (!p1.isHomemade() && !p1.canBeLeader() && p2.isHomemade() && !p2.canBeLeader()) {
                return p1.getTeam().hasOtherHomemadeThen(p1);
            } else if (!p1.isHomemade() && !p1.canBeLeader() && !p2.isHomemade() && p2.canBeLeader()) {
                return p1.getTeam().hasOtherLeaderThen(p1);
            } else if (!p1.isHomemade() && !p1.canBeLeader() && !p2.isHomemade() && !p2.canBeLeader()) {
                return true;
            }
        } else {
            return true;
        }
        return true;
    }

    public static void switchPlayers(Player p1, Player p2) {
        System.out.println("Switching " + p1.nick + "@" + p1.getTeam().name +
                " and " + p2.nick + "@" + p2.getTeam().name);
        Team t1 = p1.getTeam();
        Team t2 = p2.getTeam();
        p1.getTeam().removePlayer(p1);
        p2.getTeam().removePlayer(p2);
        t1.addPlayer(p2);
        t2.addPlayer(p1);
    }
}
