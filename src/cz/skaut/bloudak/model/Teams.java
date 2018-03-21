package cz.skaut.bloudak.model;

import cz.skaut.bloudak.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Teams {

    public static List<Team> buildEmpty(int size) {
        List<Team> teams = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            teams.add(new Team(String.valueOf(i)));
        }
        return teams;
    }
}
