package com.radientfox.stellarprism.item;

import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import net.minecraft.world.item.Item;

import javax.annotation.processing.Generated;

public class ExtraCoreItem extends Item {
    private final KitsuneElement element;

    public ExtraCoreItem() {
        this(KitsuneElement.UNIDENTIFIED);
    }

    public ExtraCoreItem(KitsuneElement element) {
        super((new Item.Properties()).fireResistant().durability(500));
        this.element = element;
    }

    public KitsuneElement getElement() {
        return this.element;
    }
}
