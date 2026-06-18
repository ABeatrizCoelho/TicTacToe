package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class BestPlayersModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public static record BestPlayer(
			String name,
			int wonTimes,
			int lostTimes,
			int drawTimes) implements Serializable {
	}

	private final List<BestPlayer> bestPlayers;
	private final int MAX;

	public BestPlayersModel(final int MAX) {
		this.bestPlayers = new ArrayList<>(MAX);
		this.MAX = MAX;
	}
	
	public void writeToFile(String fileName) throws Exception {
		try (var oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(this);
		}
	}
	
	static public BestPlayersModel readFromFile(String fileName) throws Exception {
		try (var ois = new ObjectInputStream(new FileInputStream(fileName))) {
			return (BestPlayersModel) ois.readObject();
		}
	}

	public void addBestPlayer(String name, int wonTimes, int lostTimes, int drawTimes) {
		this.addBestPlayer(new BestPlayer(name, wonTimes, lostTimes, drawTimes));
	}

	public void addBestPlayer(BestPlayer bestPlayer) {
		bestPlayers.add(bestPlayer);
		Collections.sort(bestPlayers, (a, b) -> (b.wonTimes - a.wonTimes));
		if (bestPlayers.size() > MAX) {
			bestPlayers.remove(MAX);
		}
	}

	public Stream<BestPlayer> asStream() {
		return bestPlayers.stream();
	}
	
}