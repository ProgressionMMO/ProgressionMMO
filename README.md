# ProgressionMMO
ProgressionMMO is a plugin for spigot that aims to provide more mmo-like content to minecraft.  
The skills are mostly inspired by RuneScape, with some changes and additions to fit minecraft.


## Features

### Skills
| Skill Name     | Description                                                      |
|----------------|------------------------------------------------------------------|
| Agility        | Reduces jump damage, decreases sprint food-cost.                 |
| Archery        | Increases arrow damage                                           |
| Armorsmithing  | Reduces repair cost, allows smithing of special items            |
| Attack         | Increased crit chance                                            |
| Defense        | Resistance to damage                                             |
| Farming        | Higher yield, rare drop chance                                   |
| Fishing        | Higher chance for rare items and events                          |
| Health         | Increased HP and healing from all sources                        |
| Herblore       | Allows creation of new potions, increases their duration         |
| Mining         | Higher yield and rare drop chance                                |
| Slayer         | Slayer kill-task with reward system for spells, items            |
| Sorcery        | Adds magic spells                                                |
| Strength       | Increases melee base damage                                      |
| Summoning      | Allows creation of pouches to summon temporary familiars         |
| Thieving       | Steal items for rare loot, increased chance for success and loot |
| Weaponsmithing | Reduces repair cost, allows smithing of special items            |
| _more soon?_   |                                                                  |

### Skill XP Table

![](https://i.imgur.com/WN6QQpW.png "XP Curve Graph")

| XP  | Level      | Cumulative   |
|-----|------------|--------------|
| 1   | 0          | 0            |
| 2   | 83         | 83           |
| 3   | 92         | 175          |
| 4   | 102        | 277          |
| 5   | 112        | 389          |
| 6   | 124        | 513          |
| 7   | 137        | 651          |
| 8   | 152        | 803          |
| 9   | 168        | 970          |
| 10  | 185        | 1.155        |
| 11  | 204        | 1.360        |
| 12  | 226        | 1.585        |
| 13  | 249        | 1.834        |
| 14  | 275        | 2.109        |
| 15  | 304        | 2.413        |
| 16  | 335        | 2.748        |
| 17  | 370        | 3.118        |
| 18  | 408        | 3.526        |
| 19  | 450        | 3.976        |
| 20  | 497        | 4.473        |
| 21  | 548        | 5.021        |
| 22  | 605        | 5.626        |
| 23  | 668        | 6.294        |
| 24  | 737        | 7.032        |
| 25  | 814        | 7.845        |
| 26  | 898        | 8.743        |
| 27  | 991        | 9.734        |
| 28  | 1.094      | 10.828       |
| 29  | 1.207      | 12.035       |
| 30  | 1.332      | 13.367       |
| 31  | 1.470      | 14.837       |
| 32  | 1.623      | 16.460       |
| 33  | 1.791      | 18.251       |
| 34  | 1.977      | 20.228       |
| 35  | 2.182      | 22.410       |
| 36  | 2.409      | 24.819       |
| 37  | 2.659      | 27.478       |
| 38  | 2.935      | 30.413       |
| 39  | 3.240      | 33.652       |
| 40  | 3.576      | 37.229       |
| 41  | 3.948      | 41.176       |
| 42  | 4.358      | 45.534       |
| 43  | 4.811      | 50.344       |
| 44  | 5.310      | 55.655       |
| 45  | 5.862      | 61.517       |
| 46  | 6.472      | 67.989       |
| 47  | 7.144      | 75.133       |
| 48  | 7.887      | 83.020       |
| 49  | 8.707      | 91.727       |
| 50  | 9.612      | 101.339      |
| 51  | 10.612     | 111.951      |
| 52  | 11.715     | 123.666      |
| 53  | 12.934     | 136.600      |
| 54  | 14.279     | 150.879      |
| 55  | 15.764     | 166.642      |
| 56  | 17.404     | 184.046      |
| 57  | 19.214     | 203.260      |
| 58  | 21.213     | 224.473      |
| 59  | 23.420     | 247.892      |
| 60  | 25.856     | 273.748      |
| 61  | 28.546     | 302.295      |
| 62  | 31.516     | 333.811      |
| 63  | 34.795     | 368.606      |
| 64  | 38.416     | 407.022      |
| 65  | 42.413     | 449.435      |
| 66  | 46.826     | 496.261      |
| 67  | 51.699     | 547.960      |
| 68  | 57.079     | 605.039      |
| 69  | 63.019     | 668.058      |
| 70  | 69.577     | 737.635      |
| 71  | 76.818     | 814.452      |
| 72  | 84.812     | 899.264      |
| 73  | 93.638     | 992.902      |
| 74  | 103.383    | 1.096.286    |
| 75  | 114.143    | 1.210.429    |
| 76  | 126.022    | 1.336.451    |
| 77  | 139.138    | 1.475.589    |
| 78  | 153.619    | 1.629.208    |
| 79  | 169.608    | 1.798.816    |
| 80  | 187.260    | 1.986.076    |
| 81  | 206.750    | 2.192.826    |
| 82  | 228.269    | 2.421.095    |
| 83  | 252.028    | 2.673.123    |
| 84  | 278.259    | 2.951.382    |
| 85  | 307.221    | 3.258.603    |
| 86  | 339.198    | 3.597.800    |
| 87  | 374.502    | 3.972.303    |
| 88  | 413.482    | 4.385.785    |
| 89  | 456.519    | 4.842.305    |
| 90  | 504.036    | 5.346.341    |
| 91  | 556.499    | 5.902.840    |
| 92  | 614.423    | 6.517.263    |
| 93  | 678.376    | 7.195.638    |
| 94  | 748.985    | 7.944.624    |
| 95  | 826.945    | 8.771.568    |
| 96  | 913.019    | 9.684.587    |
| 97  | 1.008.052  | 10.692.639   |
| 98  | 1.112.977  | 11.805.616   |
| 99  | 1.228.825  | 13.034.441   |

## Items

Items come with a few default properties:  

| Property         | Description                                                         |
|------------------|---------------------------------------------------------------------|
| `rarity`         | `common`, `uncommon`, `rare`, `epic`, `legendary`                   |
| `itemClass`      | `player`, `guild`, `neutral`                                        |
| `soulbound`      | Player item binding to: on pickup, on equip or unbound              |
| `itemLevel`      | The item's level (`1-100`), defines the damage and effect strength  |
| `activeEffects`  | Active effects that can be triggered by the player (have cooldowns) |
| `passiveEffects` | Effects that automatically trigger, can have cooldowns              |
| `dropSource`     | Where the item was obtained                                         |
| `owner`          | Either a player or a guild                                          |
| `title`          | A unique title for the item                                         |
| `lore`           | A lore on what the item is or where it was obtained                 |



## Mobs

## Guilds

Guilds can be created by every player, and allow grouping. Groups provide following features:
* Prevent team-damage
* Allow obtaining & use of guild items
  * Can only be obtained while in a guild, if a guild is abandoned, the items will be destroyed
* Team-chat channel
* Mark a base and allow a teleport to the base
* Allows the summoning of guild-bosses
  * Bosses to kill and obtain rewards
* Allows the summoning of guild-familiars
  * Temporary Mobs to help the guild (combat, farming, etc.)

## Commands

### `/pmlevels`
Shows an overview of the current Levels:  
![](https://i.imgur.com/NYseiSY.png)  
Players can click on a row to automatically run `/pmskill` of the skill clicked.

### `/pmskill <skillname>`
Shows detailed info about a skill:  
![](https://i.imgur.com/kHmchTE.png)  

## Spells & Abilities

### Agility Abilities

### Archery Abilities
#### Windquiver
* **Spell Name:** `Windquiver`
* **Spell Type:** `Passive`
* **Description:** `Every arrow has x% chance to be returned to your inventory`
* **Unlocked at Level:** `10`
* **Upgrades at Levels:** `20/30/40/50/60/70/80/90/99`
* **Chance at Levels:** `5/6/7/8/10/15/20/25/30/35/100`
* _Refund chance is reduced by 75% when shooting a tipped arrow_


### Armorsmithing Abilities

### Attack Abilities

### Defense Abilities
#### Counterwind
* **Spell Name:** `Counterwind`  
* **Spell Type:** `Passive`  
* **Description:** `Reflect any projectile back to it's origin`  
* **Unlocked at Level:** `40`  
* **Upgrades at Levels:** `50/60/70/80/90/99`  
* **Cooldown:** `80s/60s/40s/30s/25s/20s/15s`  

### Farming Abilities

### Fishing Abilities

### Health Abilities
#### Vampirism
* **Spell Name:** `Vampirism`
* **Spell Type:** `Passive`
* **Description:** `Heal of x% of your damage dealt.`
* **Unlocked at Level:** `40`
* **Upgrades at Levels:** `50/60/70/80/90/99`
* **Cooldown:** `30/27/24/20/18/15/12`
* **Heal Percentage at Levels:** `5/8/12/16/20/22/25`

### Herblore Abilities

### Mining Abilities

### Slayer Abilities

### Sorcery Abilities

### Strength Abilities

### Summoning Abilities

### Thieving Abilities

### Weaponsmithing Abilities