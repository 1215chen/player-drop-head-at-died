package com.playerdrophead;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;

public class PlayerDropHeadMod implements ModInitializer {

	@Override
	public void onInitialize() {
		// 当玩家死亡时，在死亡位置掉落该玩家自己的头颅
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity instanceof ServerPlayer player) {
				ItemStack head = new ItemStack(Items.PLAYER_HEAD);
				head.set(DataComponents.PROFILE, new ResolvableProfile(player.getGameProfile()));
				player.spawnAtLocation(head);
			}
		});
	}
}
