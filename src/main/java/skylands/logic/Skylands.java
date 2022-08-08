package skylands.logic;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import skylands.util.NbtMigrator;
import xyz.nucleoid.fantasy.Fantasy;

public class Skylands {
	public int format = 0;
	public static Skylands instance;
	public MinecraftServer server;
	public Fantasy fantasy;
	public IslandStuck islandStuck;
	public Hub hub;

	public Skylands(MinecraftServer server) {
		this.server = server;
		this.fantasy = Fantasy.get(server);
		this.islandStuck = new IslandStuck();
		this.hub = new Hub();
	}

	public void readFromNbt(NbtCompound nbt) {
		NbtCompound skylandsNbt = nbt.getCompound("skylands");
		NbtMigrator.update(skylandsNbt);

		this.format = skylandsNbt.getInt("format");
		this.islandStuck.readFromNbt(skylandsNbt);
		this.hub.readFromNbt(skylandsNbt);
	}

	public void writeToNbt(NbtCompound nbt) {
		NbtCompound skylandsNbt = new NbtCompound();

		skylandsNbt.putInt("format", this.format);
		this.islandStuck.writeToNbt(skylandsNbt);
		this.hub.writeToNbt(skylandsNbt);

		nbt.put("skylands", skylandsNbt);
	}

	public static Skylands getInstance() {
		return Skylands.instance;
	}

	public void onTick(MinecraftServer server) {

	}

}
