# JobStats - A Fabric tool stat tracker based on https://modrinth.com/plugin/toolstats

This was primarily "vibe coded" with Gemini over a couple days as a replacement for toolstats since there isn't anything exactly like it for Fabric server.

## Color config

### The mod will spin off a color configuration file under config/JobStats/ToolTipColors.yaml with the following options (color options are listed in the .yaml):
- use_one_color
  - Boolean to limit all tracked stats to one color
- universal_color
  - The color that "use_one_color" would use

#### Tracked stat color defaults:
- BLOCKS_MINED: GRAY
- CROPS_HARVESTED: GREEN
- MOB_KILLS: DARK_RED
- DAMAGE_DEALT: RED
- ARMOR_DAMAGE: BLUE
- FISH_CAUGHT: AQUA
- SHEEP_SHEARED: WHITE
- ARROWS_SHOT: YELLOW
- ELYTRA_FLIGHT: LIGHT_PURPLE
  - Time in flight
- CRITICAL_STRIKES: GOLD
- TRIDENTS_THROWN: DARK_AQUA
- LOGS_STRIPPED: GOLD