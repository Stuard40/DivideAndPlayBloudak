package cz.skaut.bloudak.model;

import cz.skaut.bloudak.io.BooleanParser;
import cz.skaut.bloudak.io.Configuration;

@SuppressWarnings("WeakerAccess")
public class Player {

    private final Configuration configuration;

    public final String name;
    public final String origin;
    public final String nick;
    public final int age;
    public final int level;
    public final boolean homemade;

    private int rating;
    private Team team;

    public Player(Configuration configuration, String name, String origin,
                  String nick, int age, int level, boolean homemade) {
        this.name = name;
        this.nick = nick;
        this.age = age;
        this.level = level;
        this.homemade = homemade;
        this.origin = origin;
        this.rating = solveRating(this, configuration);
        this.configuration = configuration;
    }

    private static int solveRating(Player player, Configuration configuration) {
        return ((Math.min(player.age, configuration.getMaxPlayerAge()) + player.level) / 2) - 8;
    }

    public int getRating() {
        return rating;
    }

    public boolean isHomemade() {
        return homemade && age >= configuration.getMinHomemadeAge();
    }

    public boolean canBeLeader() {
        return age >= configuration.getMinLeaderAge();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", age=" + age +
                ", level=" + level +
                ", homemade=" + homemade +
                ", rating=" + rating +
                '}';
    }

    public String toCSV() {
        return name + "," + nick + "," + origin + "," + age + "," + level + "," + homemade + "," + rating;
    }

    public static Player buildFromCSV(String line, Configuration configuration) {
        String[] fields = line.split(",", -1);
        if (fields.length != 6) {
            throw new IllegalStateException("Bad format. '" + line + '"');
        } else {
            String name = fields[0];
            String nick = fields[1];
            String origin = fields[2];
            int age = Integer.parseInt(fields[3]);
            int level = Integer.parseInt(fields[4]);
            boolean homemade = BooleanParser.parse(fields[5]);
            return new Player(configuration, name, origin, nick, age, level, homemade);
        }
    }
}
