package com.radientfox.stellarprism.handler;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "stellarprism")
public class CompatibilityHandler {

    // StellarPrism compats
    private static final ResourceLocation CHOSEN_KING =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "chosen_king");
    private static final ResourceLocation PENDRAGON =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "pendragon");
    private static final ResourceLocation FAKER =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "faker");
    // Tensura
    private static final ResourceLocation STARVED =
            ResourceLocation.fromNamespaceAndPath("tensura", "starved");
    private static final ResourceLocation PREDATOR =
            ResourceLocation.fromNamespaceAndPath("tensura", "predator");
    private static final ResourceLocation COMMANDER =
            ResourceLocation.fromNamespaceAndPath("tensura", "commander");
    private static final ResourceLocation SPEARHEAD =
            ResourceLocation.fromNamespaceAndPath("tensura", "spearhead");
    private static final ResourceLocation OBSERVER =
            ResourceLocation.fromNamespaceAndPath("tensura", "observer");
    private static final ResourceLocation SEER =
            ResourceLocation.fromNamespaceAndPath("tensura", "seer");
    private static final ResourceLocation SEEKER =
            ResourceLocation.fromNamespaceAndPath("tensura", "seeker");
    private static final ResourceLocation ANALYST =
            ResourceLocation.fromNamespaceAndPath("tensura", "analyst");
    private static final ResourceLocation CHEF =
            ResourceLocation.fromNamespaceAndPath("tensura", "chef");
    private static final ResourceLocation COOK =
            ResourceLocation.fromNamespaceAndPath("tensura", "cook");
    private static final ResourceLocation FALSIFIER =
            ResourceLocation.fromNamespaceAndPath("tensura", "falsifier");
    private static final ResourceLocation FIGHTER =
            ResourceLocation.fromNamespaceAndPath("tensura", "fighter");
    private static final ResourceLocation MARTIAL_MASTER =
            ResourceLocation.fromNamespaceAndPath("tensura", "martial_master");
    // Nightmare compats
    private static final ResourceLocation IMITATOR =
            ResourceLocation.fromNamespaceAndPath("trnightmare", "imitator");
    private static final EntityType<?> WARDEN = EntityType.WARDEN;
    private static final ResourceLocation SUPERMASSIVE_SLIME =
            ResourceLocation.fromNamespaceAndPath("tensura", "supermassive_slime");
    private static final ResourceLocation SISSIE =
            ResourceLocation.fromNamespaceAndPath("tensura", "sissie");
    private static final ResourceLocation WAR_GNOME =
            ResourceLocation.fromNamespaceAndPath("tensura", "war_gnome");
    private static final ResourceLocation CHARYBDIS =
            ResourceLocation.fromNamespaceAndPath("tensura", "charybdis");
    private static final ResourceLocation WINGED_CAT =
            ResourceLocation.fromNamespaceAndPath("tensura", "winged_cat");
    private static final ResourceLocation METAL_SLIME =
            ResourceLocation.fromNamespaceAndPath("tensura", "metal_slime");
    private static final ResourceLocation ARCH_DAEMON =
            ResourceLocation.fromNamespaceAndPath("tensura", "arch_daemon");
    private static final ResourceLocation SHIN_RYUSEI =
            ResourceLocation.fromNamespaceAndPath("tensura", "shin_ryusei");
    private static final ResourceLocation SHOGO =
            ResourceLocation.fromNamespaceAndPath("tensura", "shogo");

    private static StellarUniqueConfig getConfig() {
        return ConfigRegistry.getConfig(StellarUniqueConfig.class);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {

        if (!getConfig().Compatibility.enabled) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }

        LivingEntity killed = event.getEntity();

        ResourceLocation killedId =
                killed.getType()
                        .builtInRegistryHolder()
                        .key()
                        .location();


        //Pendragon <-> Chosen King

        if (killedId.equals(CHOSEN_KING)
                && hasSkill(player, CHOSEN_KING)
                && roll(getConfig().Compatibility.chosenKingToPendragonChance)) {

            learnSkill(player, PENDRAGON);
            return;
        }

        if (killedId.equals(PENDRAGON)
                && hasSkill(player, PENDRAGON)
                && roll(getConfig().Compatibility.pendragonToChosenKingChance)) {

            learnSkill(player, CHOSEN_KING);
            return;
        }


        //Starved and predator

        if (killedId.equals(SUPERMASSIVE_SLIME)
                && hasSkill(player, STARVED)
                && roll(getConfig().Compatibility.starvedToPredatorChance)) {

            learnSkill(player, PREDATOR);
            return;
        }


        //Commander and spearhead

        if (killed.getType() == WARDEN
                && hasSkill(player, COMMANDER)
                && roll(getConfig().Compatibility.commanderToSpearheadChance)) {

            learnSkill(player, SPEARHEAD);
            return;
        }

        if (killedId.equals(SISSIE)
                && hasSkill(player, SPEARHEAD)
                && roll(getConfig().Compatibility.spearheadToCommanderChance)) {

            learnSkill(player, COMMANDER);
            return;
        }


        //Observer and seer -> Seeker or analyst

        if (killedId.equals(WAR_GNOME)
                && (hasSkill(player, OBSERVER) || hasSkill(player, SEER))
                && roll(getConfig().Compatibility.observerToSeekerAnalystChance)) {

            if (player.getRandom().nextBoolean()) {
                learnSkill(player, SEEKER);
            } else {
                learnSkill(player, ANALYST);
            }

            return;
        }

        //Chef <-> Cook

        if (killedId.equals(CHARYBDIS)
                && hasSkill(player, CHEF)
                && roll(getConfig().Compatibility.chefToCookChance)) {

            learnSkill(player, COOK);
            return;
        }

        if (killedId.equals(WINGED_CAT)
                && hasSkill(player, COOK)
                && roll(getConfig().Compatibility.cookToChefChance)) {

            learnSkill(player, CHEF);
            return;
        }


        //Falsifier and Faker <-> Imitator

        if (killedId.equals(METAL_SLIME)
                && (hasSkill(player, FAKER) || hasSkill(player, FALSIFIER))
                && roll(getConfig().Compatibility.fakerToImitatorChance)) {

            learnSkill(player, IMITATOR);
            return;
        }

        if (killedId.equals(ARCH_DAEMON)
                && hasSkill(player, IMITATOR)
                && roll(getConfig().Compatibility.imitatorToFakerChance)) {

            if (player.getRandom().nextBoolean()) {
                learnSkill(player, FAKER);
            } else {
                learnSkill(player, FALSIFIER);
            }

            return;
        }

        //Fighter <-> Martial Master

        if (killedId.equals(SHIN_RYUSEI)
                && hasSkill(player, FIGHTER)
                && roll(getConfig().Compatibility.fighterToMartialMasterChance)) {

            learnSkill(player, MARTIAL_MASTER);
            return;
        }

        if (killedId.equals(SHOGO)
                && hasSkill(player, MARTIAL_MASTER)
                && roll(getConfig().Compatibility.martialMasterToFighterChance)) {

            learnSkill(player, FIGHTER);
        }

    }

    private static boolean hasSkill(
            ServerPlayer player,
            ResourceLocation skillId) {

        Skills skills = SkillAPI.getSkillsFrom(player);

        return skills.getSkill(skillId).isPresent();
    }

    private static boolean learnSkill(
            ServerPlayer player,
            ResourceLocation skillId) {

        Skills skills = SkillAPI.getSkillsFrom(player);

        if (skills.getSkill(skillId).isPresent()) {
            return false;
        }

        if (!skills.learnSkill(skillId)) {
            return false;
        }

        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                        "The world has granted you a blessing: " + skillId
                ).withStyle(ChatFormatting.LIGHT_PURPLE),
                true
        );

        return true;
    }


    private static boolean roll(double chance) {

        if (chance <= 0.0D) {
            return false;
        }

        if (chance >= 1.0D) {
            return true;
        }

        return Math.random() < chance;
    }
}