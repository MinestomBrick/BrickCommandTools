package org.minestombrick.commandtools.api.command;

import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.NotNull;

public class ChildCommandGroupBuilder extends CommandGroupBuilder {

    private final CommandGroupBuilder parent;

    ChildCommandGroupBuilder(@NotNull Command command, @NotNull CommandGroupBuilder parent) {
        super(command);
        this.parent = parent;
    }

    @Override
    public ChildCommandGroupBuilder withCommand(@NotNull Command command) {
        super.withCommand(command);
        return this;
    }

    public CommandGroupBuilder close() {
        parent.withCommand(super.build());
        return parent;
    }

    public Command build() {
        return close().build();
    }
}