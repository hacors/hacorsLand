package skylands.mixin;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skylands.logic.Skylands;
import skylands.util.WorldProtection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = LivingEntity.class.cast(this);

		if(!world.isClient && world.getServer() != null) {
			if(self instanceof PlayerEntity player) {
				if(!WorldProtection.canModify(world, player)) {
					player.sendMessage(Text.of("Skylands > You can't take damage on someone's island"), true);
					if(source.equals(DamageSource.OUT_OF_WORLD)) {
						player.sendMessage(Text.of("Skylands > Teleporting to the Hub!"));
						FabricDimensions.teleport(player, world.getServer().getOverworld(), new TeleportTarget(Skylands.instance.hub.pos, new Vec3d(0, 0, 0), 0, 0));
//						var pos = island.get().spawnPos;
//						player.teleport(pos.getX(), pos.getY(), pos.getZ());
					}
					cir.setReturnValue(false);
				}
			}
			if(source.getAttacker() instanceof PlayerEntity attacker) {
				if(!WorldProtection.canModify(world, attacker)) {
					attacker.sendMessage(Text.of("Skylands > You can't damage entities on someone's island!"), true);
					cir.setReturnValue(false);
				}
			}
		}
	}
}
