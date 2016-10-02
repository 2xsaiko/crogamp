# crogamp
Cross-platform game modding platform

This is a work-in-progress mod manager for various games (intended for Skyrim, but should also be usable 
for most games that don't require decompressing the game files to change stuff). It has been tested with
only Skyrim at this point.

You should only use this on game files that won't be changed during gameplay (Skyrim: use the Skyrim/data/
directory), since it can mess up stuff :)

Current features:
 - Mod management (enabling/disabling, setting priorities, etc)
 - Importing mods from zip

TODO:
 - GUI
 - Scripting language support (NMM, others, maybe even a custom one?)
 - Pull files directly from the Internet (with scripts)
 - Other compression formats (tar[.gz|.xz|.bz2], .rar, .7z)