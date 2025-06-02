# PlayerEX alcohol

## What has been done?

1. Reduced critical damage attribute strength
   - Modified ranged critical damage calculation formula
      - Original formula: `damage * (1.0 + (10.0 * v))`, where `v` is the current ranged critical damage value, which was multiplied by 10x in calculation, causing damage to double for every 10% increase in ranged critical damage
      - Modified formula: `damage * (1.0 + v)`, to prevent damage scaling too quickly in modded or modpack environments
   - Modified melee critical damage calculation formula
      - Original formula: `damage * (1.5 + v)`, where `v` is the current melee critical damage value
      - Modified to: `damage * (1.0 + v)`, using the same calculation method as the ranged critical damage mentioned above
2. Improved localization
   - Completed missing UI localization
      - Specific text locations shown in the image below
      - ![Preview](https://s2.loli.net/2025/06/02/ajRNJfgmtz2sdnZ.png)
      - [View en_us](src/main/resources/assets/playerex/lang/en_us.json#L259)
      - [View zh_cn](src/main/resources/assets/playerex/lang/zh_cn.json#L83)
   - Added Chinese localization
      - But the translation is not yet complete...
      - [View localization file](src/main/resources/assets/playerex/lang/zh_cn.json)
3. The above content is translated from AI
