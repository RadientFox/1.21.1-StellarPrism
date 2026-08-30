package com.radientfox.stellarprism.races.Fox.Elemental;

import com.mojang.serialization.Codec;
import com.radientfox.stellarprism.storages.KitsuneElementStorage;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.radientfox.stellarprism.storages.KitsuneElementStorage.getKey;

public enum KitsuneElement implements StringRepresentable {
    FLAME(0, "flame", 7605294, TensuraDamageTypes.FIRE_ELEMENTAL),
    EARTH(1, "earth", 15247701, TensuraDamageTypes.EARTH_ELEMENTAL),
    SPACE(2, "space", 16267520, TensuraDamageTypes.SPACE_ELEMENTAL),
    WIND(3, "wind", 7456228, TensuraDamageTypes.WIND_ELEMENTAL),
    TIME(4, "time", 7332069, TensuraDamageTypes.ENERGY_DRAIN),
    SPACETIME(4, "spacetime", 7332069, TensuraDamageTypes.MIND_CRUSH),
    WATER(5, "water", 15712004, TensuraDamageTypes.WATER_ELEMENTAL),
    GRAVITY(5, "gravity", 15712004, TensuraDamageTypes.GRAVITY_ELEMENTAL),
/*
    LIGHT(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),
    DARK(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),
    SOUND(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),
    ICE(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),
    ELECTRIC(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),
    BLOOD(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),
    SOUL(5, "star", 15712004, (RegistrySupplier)null, TensuraDamageTypes.HOLY_DAMAGE, DragonElement.AwakeningType.TRUE_HERO),



 */



    UNIDENTIFIED(6, "unidentified", 16777215, null);

    public static final Codec<KitsuneElement> CODEC = StringRepresentable.fromEnum(KitsuneElement::values);
    private static final KitsuneElement[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(KitsuneElement::getId)).toArray((x$0) -> {
        return new KitsuneElement[x$0];
    });
    private final int id;
    private final String namespace;
    private final int color;
    private final @Nullable ResourceKey<DamageType> defaultDamage;


    KitsuneElement(int id, String namespace, int color, ResourceKey defaultDamage/*, DeferredHolder strengthenItem*/) {
        this.id = id;
        this.namespace = namespace;
        this.color = color;
        this.defaultDamage = defaultDamage;

    }

    public static KitsuneElement byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static List<KitsuneElement> getCommandSuggestColor() {
        return List.of(FLAME, EARTH, SPACE, WATER, WIND, TIME, SPACETIME,GRAVITY);
    }

    public static boolean byName(String name) {
        for (KitsuneElement element : values()) {
            if (element.namespace.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static KitsuneElement getElement(LivingEntity player) {
        if (player instanceof StorageHolder holder) {
            StorageKey<KitsuneElementStorage> currentKey = getKey();

            KitsuneElementStorage storage = holder.manasCore$getStorage(currentKey);

            if (storage != null && storage.getElement() != null) {
                return storage.getElement();
            }
        }

        return KitsuneElement.UNIDENTIFIED;
    }

    public static KitsuneElement SetElement(LivingEntity player, KitsuneElement element) {
        if (player instanceof StorageHolder holder) {

            StorageKey<KitsuneElementStorage> currentKey = getKey();

            KitsuneElementStorage storage = holder.manasCore$getStorage(currentKey);

            if (storage != null) {
                storage.setElement(element);
                return element;
            }
        }

        return KitsuneElement.UNIDENTIFIED;
    }

    public @NotNull String getSerializedName() {
        return this.namespace;
    }

    public MutableComponent getName() {
        return Component.translatable("stellarprism.fox_element.element." + this.namespace);
    }

    public MutableComponent getColoredName() {
        return Component.translatable("stellarprism.fox_element.element." + this.namespace).withStyle((style) -> {
            return style.withColor(this.color);
        });
    }

    public int getId() {
        return this.id;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int getColor() {
        return this.color;
    }

    public @Nullable ResourceKey<DamageType> getDefaultDamage() {
        return this.defaultDamage;
    }

}
