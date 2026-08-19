package com.radientfox.stellarprism.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class BubbleWand extends Item {

    public BubbleWand(
            Tier tier,
            int attackDamage,
            float attackSpeed,
            Item.Properties properties
    ) {
        super(properties);
    }
}