[![image](src/main/resources/recipear.png)](http://www.minecraftforum.net/topic/1621605-)
==========
Recipear's goal is to be able to remove every recipe in the game including from most of the forge mods

##Download & Installation

you can get the latest builds @ http://teamcity.sk89q.com/viewType.html?buildTypeId=recipear2_main&guest=true

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

##Commands

- /recipear (OP only)
  - **reload** will reload config and send out update to players
  - **output** outputs recipe list to *Recipear-output.log*

##Supported Recipe Types
To get a proper list of supported names and itemid's you should use **/recipear output** to get all the recipes logged into a file

- CRAFTING (ITEMID, ITEMID:META, NAME)
- FURNACE (ITEMID, ITEMID:META, NAME)
- INVENTORY (ITEMID, ITEMID:META, NAME)
- CENTRIFUGE (ITEMID, ITEMID:META, NAME)
- COMPRESSOR (ITEMID, ITEMID:META, NAME)
- EXTRACTOR (ITEMID, ITEMID:META, NAME)
- MACERATOR (ITEMID, ITEMID:META, NAME)
- OREWASHING (ITEMID, ITEMID:META, NAME)
- METALFORMER_CUTTING (ITEMID, ITEMID:META, NAME)
- METALFORMER_EXTRUDING (ITEMID, ITEMID:META, NAME)
- METALFORMER_ROLLING (ITEMID, ITEMID:META, NAME)
- SCRAPBOX (ITEMID, ITEMID:META, NAME)
- NEI (ITEMID)

##Compile & Build

Mostly follow this guide here
http://www.minecraftforge.net/forum/index.php?topic=14048.0

but "Program Arguments" for run configuration have to be "--version 1.6.4 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --accessToken FML"
