package dev.latvian.kubejs.mekanism.custom.module;

import dev.latvian.kubejs.mekanism.MekanismKubeJSPlugin;
import dev.latvian.kubejs.mekanism.custom.enums.UnitItemSlots;
import dev.latvian.kubejs.mekanism.custom.item.KubeJSUnitItemBuilder;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import mekanism.api.functions.TriConsumer;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.ModuleData;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.providers.IItemProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class KubeJSModuleDataBuilder extends AKubeJSModuleDataBuilder<KubeJSModuleData, ModuleData.ModuleDataBuilder<KubeJSModuleData>, KubeJSModuleDataBuilder>{

    public BiFunction<IModule<KubeJSModuleData>, ModuleConfigItemCreator, Void> initCallBack;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<IHUDElement>> addHUDElementsCallBack;
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickServerCallBack;
    public BiFunction<IModule<KubeJSModuleData>, Player, Void> tickClientCallBack;
    public TriConsumer<IModule<KubeJSModuleData>, Player, Consumer<Component>> addHUDStringsCallBack;

    public int maxStackSize = 1;
    public Rarity rarity = Rarity.COMMON;
    public int exclusive;
    public boolean handlesModeChange = false;
    public boolean modeChangeDisabledByDefault = false;
    public boolean rendersHUD = false;
    public boolean noDisable = false;
    public boolean disabledByDefault = false;

    public UnitItemSlots.Slots slot;

    private static final Set<KubeJSModuleDataBuilder> allBuilder = new HashSet<>();

    public static Set<KubeJSModuleDataBuilder> getAllBuilder() {
        return allBuilder;
    }

    public KubeJSModuleDataBuilder maxStackSize(int i) {
        this.maxStackSize = i;
        return this;
    }

    public KubeJSModuleDataBuilder setExclusiveByFlag(ModuleData.ExclusiveFlag... flags) {
        this.exclusive = flags.length == 0 ? ModuleData.ExclusiveFlag.ANY : ModuleData.ExclusiveFlag.getCompoundMask(flags);
        return this;
    }

    public KubeJSModuleDataBuilder rarity(Rarity r) {
        this.rarity = r;
        return this;
    }

    public KubeJSModuleDataBuilder setSlot(UnitItemSlots.Slots slot) {
        this.slot = slot;
        return this;
    }

    public KubeJSModuleDataBuilder exclusive(int i) {
        this.exclusive = i;
        return this;
    }

    public KubeJSModuleDataBuilder handlesModeChange(boolean b) {
        this.handlesModeChange = b;
        return this;
    }

    public KubeJSModuleDataBuilder modeChangeDisabledByDefault(boolean b) {
        this.modeChangeDisabledByDefault = b;
        return this;
    }

    public KubeJSModuleDataBuilder rendersHUD(boolean b) {
        this.rendersHUD = b;
        return this;
    }

    public KubeJSModuleDataBuilder noDisable(boolean b) {
        this.noDisable = b;
        return this;
    }

    public KubeJSModuleDataBuilder disabledByDefault(boolean b) {
        this.disabledByDefault = b;
        return this;
    }


    public KubeJSModuleDataBuilder(ResourceLocation i) {
        super(i);
        allBuilder.add(this);
    }

    public KubeJSModuleDataBuilder(KubeJSUnitItemBuilder b) {
        super(b.id);
        this.initCallBack                = b.initCallBack;
        this.addHUDElementsCallBack      = b.addHUDElementsCallBack;
        this.tickServerCallBack          = b.tickServerCallBack;
        this.tickClientCallBack          = b.tickClientCallBack;
        this.addHUDStringsCallBack       = b.addHUDStringsCallBack;
        this.maxStackSize                = b.maxModuleSize;
        this.rarity                      = b.rarity;
        this.exclusive                   = b.exclusive;
        this.handlesModeChange           = b.handlesModeChange;
        this.modeChangeDisabledByDefault = b.modeChangeDisabledByDefault;
        this.rendersHUD                  = b.rendersHUD;
        this.slot                        = b.slot;
        allBuilder.add(this);
    }

    public static KubeJSModuleDataBuilder create(ResourceLocation id) {
        return new KubeJSModuleDataBuilder(id);
    }

    public static KubeJSModuleDataBuilder create(KubeJSUnitItemBuilder builder) {
        return new KubeJSModuleDataBuilder(builder);
    }

    @Override
    protected Supplier bindBuilder() {
        UnaryOperator<ModuleData.ModuleDataBuilder<KubeJSModuleData>> a = builder -> {
            var bb = builder.rarity(rarity)
                    .maxStackSize(maxStackSize);
            if (exclusive != 0) {
                bb = bb.exclusive(exclusive);
            }
            if (handlesModeChange) {
                bb = bb.handlesModeChange();
            }
            if (modeChangeDisabledByDefault) {
                bb = bb.modeChangeDisabledByDefault();
            }
            if (rendersHUD) {
                bb = bb.rendersHUD();
            }
            if (noDisable) {
                bb = bb.noDisable();
            }
            if (disabledByDefault) {
                bb = bb.disabledByDefault();
            }
            return bb;
        };
        NonNullSupplier<KubeJSModuleData> s = () -> new KubeJSModuleData(builder());
        IItemProvider i = () -> Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(id)).asItem();
        var e = ModuleData.ModuleDataBuilder.custom(s, i);
        return () -> a.apply(e);
    }

    @Override
    public RegistryInfo getRegistryType() {
        return MekanismKubeJSPlugin.MODULE_DATA;
    }



    @Override
    public KubeJSModuleData createObject() {
        return new KubeJSModuleData(builder()) {
            @Override
            public void init(@NotNull IModule<KubeJSModuleData> module, @NotNull ModuleConfigItemCreator configItemCreator) {
                if (initCallBack != null) {
                    initCallBack.apply(module, configItemCreator);
                } else {
                    super.init(module, configItemCreator);
                }
            }

            @Override
            public void tickClient(@NotNull IModule<KubeJSModuleData> module, @NotNull Player player) {
                if (tickClientCallBack!= null) {
                    tickClientCallBack.apply(module, player);
                } else {
                    super.tickClient(module, player);
                }
            }

            @Override
            public void tickServer(@NotNull IModule<KubeJSModuleData> module, @NotNull Player player) {
                if (tickServerCallBack!= null) {
                    tickServerCallBack.apply(module, player);
                } else {
                    super.tickServer(module, player);
                }
            }

            @Override
            public void addHUDElements(@NotNull IModule<KubeJSModuleData> module, @NotNull Player player, @NotNull Consumer<IHUDElement> hudElementAdder) {
                if (addHUDElementsCallBack!= null) {
                    addHUDElementsCallBack.accept(module, player, hudElementAdder);
                } else {
                    super.addHUDElements(module, player, hudElementAdder);
                }
            }

            @Override
            public void addHUDStrings(@NotNull IModule<KubeJSModuleData> module, @NotNull Player player, @NotNull Consumer<Component> hudStringAdder) {
                if (addHUDStringsCallBack!= null) {
                    addHUDStringsCallBack.accept(module, player, hudStringAdder);
                } else {
                    super.addHUDStrings(module, player, hudStringAdder);
                }
            }
        };
    }
}
