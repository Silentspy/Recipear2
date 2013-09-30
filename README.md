[Recipear](http://www.minecraftforum.net/topic/1621605-)
==========

##Description
Recipear's goal is to be able to remove every recipe in the game including from most of the forge mods

##Download & Installation

you can get the latest builds @ http://ci.fragwith.us/

put the mod in your mods folder and run the client/server at least once to get your configs all set up

##Introduction

You can adjust the recipe removal in config/Recipear/BannedRecipes.cfg

- `<ID>[:METADATA][:TYPE]` to remove item by id, `<>` is required and `[]` is optional
- `<NAME>[:TYPE]` to remove item by name, `<>` is required and `[]` is optional
- Every recipe ban needs to be seperated with a new line,
- Comments can be started with #
- Supported Recipes types is outputted in the log every startup
- If you want to see how it all runs I would recommend turning debug on in the config at least once.

`ic2.core.item.armor.ItemArmorQuantumSuit` 
Disables the entirety of quantum suit from being crafted

`ic2.itemDustIronSmall:OREWASHING` 
Disables the ore washing recipe(s) related to that item

`58:CRAFTING` 
Disables "Crafting Table" from being crafted

`325` 
Disables "Bucket" from being crafted

`171:3:CRAFTING` 
Disables "Light Blue Carpet" with 2 in metadata from being Crafted

`Light Blue Wool:CRAFTING`
Disables "Light Blue Wool" From being Crafted

##Compile & Build

if you like to do this by hand you can seek out these options

Option 1 (Eclipse with ant, Assuming you have Forge workspace)
- Create a new Project in forge workspace called "Recipear"
- Add Required Project "Minecraft" in Project Properties
- Drag & drop build.xml into "Recipear"
- Drag & drop everything from src into "Recipear"
- Add Referenced library for Google Guava (Minecraft/jar/libraries/com/google/guava/guava/14.0/guava-14.0.jar)
- Add Referenced Library for deobfuscated(deobf) IndustrialCraft2 which should be downloaded manually at http://ic2api.player.to:8080/job/IC2_experimental/
- Adjust Run properties to include Recipear in it's classpath
- Enjoy!

Option 2 (Manual)
- Run [Apache Ant](http://ant.apache.org/bindownload.cgi) in the repository root: `ant package`
