package cz.skaut.bloudak.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("FieldCanBeLocal")
public class Configuration {

    private final String MAX_PLAYER_AGE = "player.maxAge";
    private final String MIN_LEADER_AGE = "player.leader.minAge";
    private final String MIN_HOMEMADE_AGE = "player.homeKnowledge.minAge";

    private final String TEAM_COUNT = "teams.count";

    private final Integer MAX_PLAYER_AGE_DEFAULT = 18;
    private final Integer MIN_LEADER_AGE_DEFAULT = 15;
    private final Integer MIN_HOMEMADE_AGE_DEFAULT = 12;


    private Properties properties = new Properties();

    public Configuration(String confFile) throws IOException {
        if (confFile == null) {
            throw new IllegalStateException("No configuration specified.");
        } else {
            try (InputStream is = new FileInputStream(confFile)){
                properties.load(is);
            }
        }
    }

    public int getMaxPlayerAge(){
        if(properties.containsKey(MAX_PLAYER_AGE)) {
            return Integer.parseInt((String) properties.get(MAX_PLAYER_AGE));
        } else {
            return MAX_PLAYER_AGE_DEFAULT;
        }
    }

    public int getMinLeaderAge(){
        if(properties.containsKey(MIN_LEADER_AGE)) {
            return Integer.parseInt((String) properties.get(MIN_LEADER_AGE));
        } else {
            return MIN_LEADER_AGE_DEFAULT;
        }
    }

    public int getMinHomemadeAge(){
        if(properties.containsKey(MIN_HOMEMADE_AGE)) {
            return Integer.parseInt((String) properties.get(MIN_HOMEMADE_AGE));
        } else {
            return MIN_HOMEMADE_AGE_DEFAULT;
        }
    }

    public int getTeamCount(){
        if(properties.containsKey(TEAM_COUNT)) {
            return Integer.parseInt((String) properties.get(TEAM_COUNT));
        } else {
            throw new IllegalStateException("You must specified at least number of teams!");
        }
    }

}
