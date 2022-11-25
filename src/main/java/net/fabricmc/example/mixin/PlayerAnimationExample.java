package net.fabricmc.example.mixin;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.example.IExampleAnimatedPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class PlayerAnimationExample {
    @Inject(method = "use", at = @At("HEAD"))
    private void playAnimation(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        //We must start the animation on client-side
        if (world.isClient) {
            var itemStack = user.getStackInHand(hand);
            //Do some filtering
            if (itemStack.getItem().equals(Items.PAPER)) {
                //If we want to play the animation, get the animation container
                var animationContainer = ((IExampleAnimatedPlayer)user).modid_getModAnimation();

                //Use setAnimation to set the current animation. It will be played automatically.
                KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier("modid", "waving"));

                // Requested API, disable parts of animation.
                // Following code disables the left leg (since API 0.4.0)
                var builder = anim.mutableCopy();
                var part = builder.getPart("leftLeg");
                part.setEnabled(false);

                // done modifying rules
                anim = builder.build();

                animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));

                //Use animationContainer.replaceAnimationWithFade(); to create fading effects instead of sudden changes.
            }
        }

        // For server-side animation playing, implement your own plugin channel or see Emotecraft API
        // https://github.com/KosmX/emotes/tree/dev/emotesAPI/src/main/java/io/github/kosmx/emotes/api/events
    }

}
