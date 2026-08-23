package com.radientfox.stellarprism.storages;

import com.radientfox.stellarprism.races.Fox.Elemental.IKitsuenElement;
import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.manascore.storage.api.StorageKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class KitsuneElementStorage extends Storage implements IKitsuenElement {
    private static final Logger log = LogManager.getLogger(KitsuneElementStorage.class);
    private static StorageKey<KitsuneElementStorage> key = null;
    private KitsuneElement element = null;

    protected KitsuneElementStorage(LivingEntity holder) {
        super((StorageHolder) holder);
    }

    public static void init() {
        StorageEvents.REGISTER_ENTITY_STORAGE.register((StorageEvents.RegisterStorage)(registry) -> {
            ResourceLocation var10001 = ResourceLocation.fromNamespaceAndPath("stellarprism", "kitsune_element_storage");
            Objects.requireNonNull(LivingEntity.class);
            Objects.requireNonNull(LivingEntity.class);
            key = registry.register(var10001, KitsuneElementStorage.class, LivingEntity.class::isInstance, (target) -> {
                return new KitsuneElementStorage((LivingEntity)target);
            });
        });

    }

    public static StorageKey<KitsuneElementStorage> getKey() {
        return key;
    }

    public KitsuneElement getElement() {
        return this.element;
    }

    public void setElement(KitsuneElement element) {
        this.element = element;
    }

    public void save(CompoundTag data) {
        if (this.element != null) {
            data.putInt("element", this.element.getId());
        } else {
            data.putInt("element", -1);
        }

    }

    public void load(CompoundTag data) {
        if (data.contains("element")) {
            int id = data.getInt("element");
            if (id >= 0 && id <= 8) {
                try {
                    this.element = KitsuneElement.byId(id);
                } catch (Exception var4) {
                    Exception e = var4;
                    log.warn("Invalid element id {} in NBT, clearing element.", id, e);
                }
            }
        }

    }



}