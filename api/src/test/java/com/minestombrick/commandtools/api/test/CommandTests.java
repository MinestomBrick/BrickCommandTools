package com.minestombrick.commandtools.api.test;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.block.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.minestombrick.commandtools.api.command.CommandGroupBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTests {

    @BeforeEach
    public void init() {
        MinecraftServer.init();
    }

    @Test
    public void groupBuilderTest() {
        CommandManager cm = MinecraftServer.getCommandManager();

        Command cmd = new Command("fizz");
        cmd.setCondition((sender, commandString) -> true);

        AtomicBoolean called = new AtomicBoolean(false);
        cmd.setDefaultExecutor((sender, context) -> called.set(true));

        Command group = CommandGroupBuilder.of("foo").group("bar")
                .withCommand(cmd).build();

        cm.register(group);

        cm.execute(cm.getConsoleSender(), "foo bar fizz");
        assertTrue(called.get());
    }

}
