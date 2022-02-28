package org.minestombrick.commandtools.api.command;

import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.NotNull;
import org.minestombrick.commandtools.api.CommandTools;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CommandGroupBuilder {

    private final Command command;
    private final Set<Command> commands = new HashSet<>();

    CommandGroupBuilder(Command command) {
        this.command = command;
    }

    public static CommandGroupBuilder of(@NotNull String name) {
        return new CommandGroupBuilder(new Command(name));
    }

    public static CommandGroupBuilder of(@NotNull Command cmd) {
        return new CommandGroupBuilder(cmd);
    }

    //

    public CommandGroupBuilder withCommand(@NotNull Command command) {
        commands.add(command);
        return this;
    }

    public CommandGroupBuilder group(@NotNull String name, Consumer<CommandGroupBuilder> consumer) {
        return group(new Command(name), consumer);
    }

    public CommandGroupBuilder group(@NotNull Command cmd, Consumer<CommandGroupBuilder> consumer) {
        CommandGroupBuilder builder = new CommandGroupBuilder(cmd);
        consumer.accept(builder);
        commands.add(builder.build());
        return this;
    }

    public Command build() {
        commands.forEach(command::addSubcommand);
        CommandTools.setupCommandGroupDefaults(command);
        return command;
    }
}
