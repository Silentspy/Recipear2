[![image](resources/recipear.png)](http://www.minecraftforum.net/topic/1621605-)
==========
Recipear's goal is to be able to remove every recipe in the game including from most of the forge mods

##Download & Installation

you can get the latest builds @ http://ci.fragwith.us/job/Recipear2/

put the mod in your mods folder and run the server at least once to get your configs all set up

##Introduction

You can adjust recipe removal in config/Recipear/BannedRecipes.cfg

- `<ID>[:METADATA][:TYPE]` to remove Recipe by id, `<>` is required and `[]` is optional
- `<NAME>[:TYPE]` to remove Recipe by name, `<>` is required and `[]` is optional
- Every recipe ban needs to be seperated with a new line
- Comments can be started with #
- Supported Recipes types is outputted in the log every startup
- If you want to see how it all runs I would recommend turning debug on in the config at least once.
- (2.0.4+) you can remove items by name with a ":" limiter, but you need to escape it with a "\" character, example being `tile.ironchest\:CRYSTAL`

`itemArmorQuantumHelmet` 
Disables the Quantum helmet(IC2) from being crafted

`ic2.itemDustIronSmall:OREWASHING` 
Disables the ore washing recipe(s) related to "Small Pile of Iron Dust"

`58:CRAFTING` 
Disables "Crafting Table" from being crafted

`325` or `item.bucket`
Disables "Bucket" from being crafted

`171:3:CRAFTING` 
Disables "Light Blue Carpet" with 2 in metadata from being Crafted

`tile.cloth.lightBlue:CRAFTING`
Disables "Light Blue Wool" from being Crafted

##Introduction

- /recipear
  - **reload** will reload config and send out update to players
  - **output** outputs recipe list to *Recipear-output.log*

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
