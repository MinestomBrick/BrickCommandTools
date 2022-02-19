# BrickCommandTools

An [Minestom](https://github.com/Minestom/Minestom) library with extra command utilities.


## API

### Maven
```
repositories {
    maven { url "https://repo.jorisg.com/snapshots" }
}

dependencies {
    implementation 'org.minestombrick.commandtools:api:1.0-SNAPSHOT'
}
```

### Usage

Check the [javadocs](https://minestombrick.github.io/BrickCommandTools/)

#### Examples

```java
Command cmd = new Command("foo");

// player execute: /level1 level2 cmd
Command group = CommandGroupBuilder.of("level1").group("level2")
        .withCommand(cmd).build();

MinecraftServer.getCommandManager().register(group);
```