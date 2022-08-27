package skylands.mixin.world.protection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skylands.util.Texts;
import skylands.util.WorldProtection;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelMixin {

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		if(!world.isClient && player != null) {
			if(!WorldProtection.canModify(world, player)) {
				player.sendMessage(Texts.prefixed("message.skylands.world_protection.flint_and_steel_use"), true);
				cir.setReturnValue(ActionResult.FAIL);
			}
		}
	}
}
