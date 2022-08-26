package net.fabricmc.example;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;

public interface IExampleAnimatedPlayer {
    /**
     * Use your mod ID in the method name to avoid collisions with other mods
     * @return Mod animation container
     */
    ModifierLayer<IAnimation> modid_getModAnimation();
}
