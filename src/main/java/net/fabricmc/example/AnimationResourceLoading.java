package net.fabricmc.example;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class AnimationResourceLoading implements ClientModInitializer {
    public static Map<Identifier, KeyframeAnimation> animations = new HashMap<>();

    @Override
    public void onInitializeClient() {

        /*
         * Custom resource loading is documented on Fabric wiki: https://fabricmc.net/wiki/tutorial:custom_resources
         *
         * You don't have to create a minecraft-like resource loading, you can load files from folders or implement custom animation modifiers in Java
         */
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("modid", "animation");
            }

            @Override
            public void reload(ResourceManager manager) {
                var map = new HashMap<Identifier, KeyframeAnimation>();

                //Find animations in the given directory
                for (var resource : manager.findResources("animation", path -> path.getPath().endsWith(".json")).entrySet()) {
                    //Try to open each
                    try (var input = resource.getValue().getInputStream()) {

                        //Deserialize the animation json. GeckoLib animation json can contain multiple animations.
                        for (var animation : AnimationSerializing.deserializeAnimation(input)) {

                            //Save the animation for later use.
                            map.put(new Identifier(resource.getKey().getNamespace(), serializeTextToString((String) animation.extraData.get("name"))), animation);
                        }
                    } catch(IOException e) {
                        throw new RuntimeException(e);//Somehow handle invalid animations
                    }
                }
                AnimationResourceLoading.animations = map;
            }
        });
    }

    /**
     * Emotecraft emotes has a text as their name.
     * This is just a helper stuff
     * @param arg Text as json
     * @return  The String
     */
    private static String serializeTextToString(String arg) {
        var component = Text.Serializer.fromJson(arg);
        if (component != null) {
            return component.getString();
        } else {
            return arg.replace("\"", "");
        }
    }
}
