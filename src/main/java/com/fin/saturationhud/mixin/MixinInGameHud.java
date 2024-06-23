package com.fin.saturationhud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
	@Final
	@Shadow
	private MinecraftClient client;
	@Shadow private int scaledWidth;

	@Shadow private int scaledHeight;

	@Inject(
			method = "renderHotbar",
			at = @At(value = "TAIL")
	)
	private void renderSaturationBar(float tickDelta, DrawContext context, CallbackInfo ci) {
		if (client.player == null) {return;}

		int satX = scaledWidth / 2 + 94;
		int satY = scaledHeight - 1;

		float sat = client.player.getHungerManager().getSaturationLevel();
		if (sat == 0) {return;}

		context.fill(satX - 1, satY + 1, satX + 2, satY - 21, 0x80000000);
		context.fill(satX, satY, satX + 1, satY - (int) sat, 0xFFFFAA00);
	}
}