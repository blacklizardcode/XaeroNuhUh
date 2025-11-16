# XaeroNuhUh

XaeroNuhUh is a simple server-side utility mod designed to **disable Xaero’s Minimap and Xaero’s World Map** for all players, regardless of their client settings.  
Perfect for servers that want fair exploration, no free world info, and a more immersive gameplay experience.

## ✨ Features
- Fully **server-enforced** disabling of:
    - **Xaero’s Minimap**
    - **Xaero’s World Map**
- Works automatically when players join.
- No client installation required.
- Lightweight, plug-and-play.

## 🛠️ Added Gamerules
XaeroNuhUh introduces two gamerules you can configure:

| Gamerule | Type | Description |
|----------|-------|-------------|
| `DisableMiniMap` | boolean | When `true`, disables Xaero’s Minimap for all players. |
| `EnableFairMode` | boolean | When `true`, enforces fair-mode restrictions for both Xaero’s Minimap and World Map. |

### Example Commands
```mcfunction
/gamerule DisableMiniMap true
/gamerule EnableFairMode true
