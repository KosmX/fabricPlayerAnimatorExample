# PlayerAnimator Fabric Example

Fabric example mod, modified to demonstrate how to use [PlayerAnimator](https://github.com/KosmX/minecraftPlayerAnimator) library on Fabric.

This example uses mixins, to see a mixinless setup (not recommended) see [forge example](https://github.com/KosmX/forgePlayerAnimatorExample/blob/main/src/main/java/com/example/examplemod/PlayerAnimatorExample.java#L38).

## Setup

For setup instructions please see the [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup) that relates to the IDE that you are using.

### AnimationResourceLoading

This will load animations form resource packs, including the mod pack.  
```java
//Use this to get a loaded KeyframeAnimation
AnimationResourceLoading.animations.get(animationID)
```

### PlayerAnimationExample

An example animation event, what will make the player `wave` when using a paper item.  
Of course, you'll create your own events with mixins or packets.

## License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
