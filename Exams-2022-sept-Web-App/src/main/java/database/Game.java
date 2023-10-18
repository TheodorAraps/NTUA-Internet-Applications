package database;

import java.util.Date;

public class Game {

	final Date gameDate;
	final String name1;
	final Integer team1sc;
	final String name2;
	final Integer team2sc;
	
	public Game(Date gameDate, String name1, Integer team1sc, String name2, Integer team2sc) {
		super();
		this.gameDate = gameDate;
		this.name1 = name1;
		this.team1sc = team1sc;
		this.name2 = name2;
		this.team2sc = team2sc;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public String getName1() {
		return name1;
	}

	public Integer getTeam1SC() {
		return team1sc;
	}

	public String getName2() {
		return name2;
	}

	public Integer getTeam2SC() {
		return team2sc;
	}
	

	@Override
	public String toString() {
		return "Game [Game Date=" + gameDate + ", Team 1=" + name1 + ", Team 1 Score=" + team1sc + ", Team 2="
				+ name2 + ", Team 2 Score=" + team2sc + "]";
	}
	

}

