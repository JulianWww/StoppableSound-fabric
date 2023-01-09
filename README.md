# Fabric Example Mod

[![Build](https://github.com/JulianWww/StoppableSound-fabric/actions/workflows/build.yml/badge.svg)](https://github.com/JulianWww/StoppableSound-fabric/actions/workflows/build.yml)
[![GitHub downloads](https://img.shields.io/github/downloads/JulianWww/StoppableSound-fabric/total?label=Github%20downloads&logo=github)](https://github.com/JulianWww/StoppableSound-fabric/releases)
[![GitHub contributors](https://img.shields.io/github/contributors/JulianWww/StoppableSound-fabric?label=Contributors&logo=github)](https://github.com/JulianWww/StoppableSound-fabric/graphs/contributors)

## Setup

```gradle
repositories {
    maven {
        name = 'Denanu Mods'
        url = 'https://wandhoven.ddns.net/maven/'
    }
}

dependencies {
    modImplementation "net.denanu.StoppableSound:StoppableSound-<Minecraft_Version>:<StoppableSound_version>"
}
```

# Usage
Play at a fixed location:
```Java
var stoppable = StoppableSound.of(world, pos, sound, category, volume, pitch).play();
```

Play at an entity:
```Java
var stoppable = StoppableSound.of(entity,sound, category, volume, pitch).play()
```

Stop a playing sound
```Java
stoppable.stop()
```