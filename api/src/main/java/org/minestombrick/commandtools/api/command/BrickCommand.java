package org.minestombrick.commandtools.api.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.minestombrick.commandtools.api.CommandTools;
import org.minestombrick.commandtools.api.condition.CommandConditionBuilder;

import java.util.function.Consumer;

public class BrickCommand extends Command {

    public BrickCommand(@NotNull String name, @Nullable String... aliases) {
        super(name, aliases);
    }

    public BrickCommand(@NotNull String name) {
        super(name);
    }

    public void setupDefaultCondition() {
        CommandTools.setupDefaultCondition(this);
    }

    public void setupCommandGroupDefaults() {
        CommandTools.setupCommandGroupDefaults(this);
    }

    // tools

    public void setCondition(@NotNull Consumer<CommandConditionBuilder> consumer) {
        CommandTools.setCondition(this, consumer);
    }

    public void setConditionsAny(@NotNull Consumer<CommandConditionBuilder> consumer) {
        CommandTools.setConditionsAny(this, consumer);
    }

    public void addConditionalSyntax(@NotNull Consumer<CommandConditionBuilder> consumer,
                                     @NotNull CommandExecutor executor, @NotNull Argument<?>... args) {
        CommandTools.addConditionalSyntax(this, consumer, executor, args);
    }

    // INVALID MESSAGES

    public void setInvalidUsageMessage(@NotNull String key) {
        CommandTools.setInvalidUsageMessage(this, key);
    }

    public void setInvalidArgumentMessage(@NotNull Argument<?> argument) {
        CommandTools.setInvalidArgumentMessage(this, argument);
    }

    public void setInvalidArgumentMessage(@NotNull Argument<?> argument, @NotNull String key) {
        CommandTools.setInvalidArgumentMessage(this, argument, key);
    }
}
