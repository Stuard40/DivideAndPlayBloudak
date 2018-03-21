package cz.skaut.bloudak.model;

import cz.skaut.bloudak.model.Player;

import java.util.*;

public class Team {

    // ID of the team
    @SuppressWarnings("WeakerAccess")
    public final String name;

    private final List<Player> teamMates = new ArrayList<>();

    private int totalScore = 0;

    @SuppressWarnings("WeakerAccess")
    public Team(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        teamMates.add(player);
        totalScore += player.getRating();
        player.setTeam(this);
    }

    public void removePlayer(Player player) throws IllegalStateException {
        if (teamMates.contains(player)) {
            teamMates.remove(player);
            totalScore -= player.getRating();
        } else {
            throw new IllegalStateException("Team not contain this player");
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(teamMates);
    }

    public boolean hasOtherHomemadeThen(Player player) {
        return teamMates.stream().filter(Player::isHomemade).anyMatch(player1 -> !player1.equals(player));
    }

    public boolean hasOtherLeaderThen(Player player) {
        return teamMates.stream().filter(Player::canBeLeader).anyMatch(player1 -> !player1.equals(player));
    }

    public boolean hasOtherHomemadeLeaderThen(Player player) {
        return teamMates.stream().filter(Player::canBeLeader).filter(Player::isHomemade)
                .anyMatch(player1 -> !player1.equals(player));
    }

    public Player getNobiePlayer() {
        return teamMates.stream().filter(p -> !p.canBeLeader()).filter(p -> !p.isHomemade())
                .min(Comparator.comparingInt(Player::getRating))
                .orElseThrow(() -> new IllegalStateException("Cannot get newbie player"));
    }

    public Player getStrongPlayer() {
        return teamMates.stream().filter(p -> !p.canBeLeader()).filter(p -> !p.isHomemade())
                .max(Comparator.comparingInt(Player::getRating))
                .orElseThrow(() -> new IllegalStateException("Cannot get skilled player"));
    }

    public EthnicVector ethnicVector(Set<String> origins) {
        EthnicVector originMap = new EthnicVector(origins.size() * 2);
        for (String origin : origins) {
            originMap.put(origin, 0.0);
            for (Player player : getPlayers()) {
                if (player.origin.equalsIgnoreCase(origin)) {
                    originMap.put(origin, originMap.get(origin) + 1);
                }
            }
        }
        return originMap;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("Team{" +
                "name='" + name + '\'' +
                ", totalScore=" + totalScore +
                "}\n");
        for (Player player : teamMates) {
            print.append(player.toString()).append("\n");
        }
        print.append("\n");
        return print.toString();
    }

    public String toStringTeamOnly() {
        return "Team{" +
                "name='" + name + '\'' +
                ", totalScore=" + totalScore +
                ", players=" + teamMates.size() +
                "}";
    }
}
