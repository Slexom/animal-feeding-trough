package slexom.animal_feeding_trough.platform.common.block;

import net.minecraft.util.StringIdentifiable;

public enum FeedingTroughContent implements StringIdentifiable {
	WHEAT("wheat"),
	CARROT("carrot"),
	POTATO("potato"),
	MEAT("meat");

	private final String name;

	private FeedingTroughContent(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name;
	}

	public String asString() {
		return this.name;
	}
}