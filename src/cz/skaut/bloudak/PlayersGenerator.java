package cz.skaut.bloudak;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlayersGenerator {

    private static final int maxOld = 24;
    private static final int minOld = 6;

    private static final int maxDifLevelFromAge = 2;
    private static final int maxLevel = 18;

    private static final int homeMadePercent = 30;

    /*
    public List<Player> generate(int times) {
        List<Player> players = new ArrayList<>();
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < times; i++) {
            int age = random.nextInt(maxOld - minOld) + minOld;
            int level = age - maxDifLevelFromAge + random.nextInt(maxDifLevelFromAge * 2);
            boolean homeMade = random.nextInt(100) < homeMadePercent;
            Player player = new Player("Jmeno[" + i + "] Přijmení[" + i + "]", "Původ[" + i + "]",
                    "Přezdívka[" + i + "]", age, level, homeMade);
            players.add(player);
        }
        return players;
    }*/

    /*
    public List<Player> generate(Map<Integer, Integer> countByAge) {
        List<Player> players = new ArrayList<>();
        Random random = new Random(System.nanoTime());
        for (Map.Entry<Integer, Integer> entry : countByAge.entrySet()) {
            int age = entry.getKey();
            for (int i = 0; i < entry.getValue(); i++) {
                int level = age - maxDifLevelFromAge + random.nextInt(maxDifLevelFromAge * 2);
                boolean homeMade = random.nextInt(100) < homeMadePercent;
                int playerID = random.nextInt(899999) + 100000;
                Player player = new Player("Jmeno[" + playerID + "]", "Původ[" + playerID + "]",
                        "Přezdívka[" + playerID + "]", age, level, homeMade);
                players.add(player);

            }
        }
        return players;
    }*/
}
