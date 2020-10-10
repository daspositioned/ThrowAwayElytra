# ThrowAwayElytra

A simple spigot plugin that gives Elytras to players in certain WorldGuard regions and removes them after a successful landing or leaving the area.

<iframe width="1905" height="686" src="https://www.youtube.com/embed/5hgM1ogVGUA" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

## Dependencies

As of now, this plugin only has one dependency:

- WorldGuard

## Configuration

```yaml
spawn-worldguard-region: spawn					# The name of the WorldGuard region
elytra-check-interval-in-ticks: 20				# Interval between location checks
item:
  name: '&6Throw-Away Elytra'					# The name of the Elytra item
  lore:											# The lore of the Elytra item
  - '&aElytra which will &cdisappear &aafter'
  - '&ayou &cland &aoutside of spawn'
```

## Download

The plugin can be downloaded from the [releases page](https://github.com/Seliba/ThrowAwayElytra/releases).

Alternatively it is also possible to build it from source:

## Building from source

#### Windows

```powershell
$ gradlew.bat shadowJar
```

#### Linux / OS X

```shell
$ ./gradlew shadowJar
```

## Suggestions & Problems

Please open an issue [here](https://github.com/Seliba/ThrowAwayElytra/issues) if you have a problem or suggestion for improvement.

## Contributing

All contributions are welcome, feel free to open a [pull request](https://github.com/Seliba/ThrowAwayElytra/pulls) at any time.