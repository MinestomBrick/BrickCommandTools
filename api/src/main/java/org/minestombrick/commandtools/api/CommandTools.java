package org.minestombrick.commandtools.api;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.CommandSyntax;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.condition.CommandCondition;
import net.minestom.server.utils.callback.CommandCallback;
import org.jetbrains.annotations.NotNull;
import org.minestombrick.commandtools.api.condition.CommandConditionBuilder;
import org.minestombrick.i18n.api.I18nAPI;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public class CommandTools {

    // initial setups

    public static void setupDefaultCondition(Command cmd) {
        cmd.setCondition(combineSyntaxConditions(cmd));
    }

    public static void setupCommandGroupDefaults(@NotNull Command cmd) {
        // default condition check
        cmd.setCondition((sender, commandString) -> {
            boolean result = cmd.getSubcommands().stream()
                    .map(Command::getCondition)
                    .anyMatch(cc -> cc == null || cc.canUse(sender, null));
            if ( commandString == null ) {
                return result;
            }

            if ( !result ) {
                CommandCallback cb = MinecraftServer.getCommandManager().getUnknownCommandCallback();
                if (cb != null) cb.apply(sender, commandString);
            }

            return result;
        });

        // sub command not found
        cmd.setDefaultExecutor((sender, context) -> {
            CommandCallback cb = MinecraftServer.getCommandManager().getUnknownCommandCallback();
            if (cb != null) cb.apply(sender, context.getInput());
        });
    }

    // tools

    public static void setCondition(@NotNull Command cmd, @NotNull Consumer<CommandConditionBuilder> consumer) {
        CommandConditionBuilder builder = new CommandConditionBuilder();
        consumer.accept(builder);
        cmd.setCondition(builder.build());
    }

    public static void setConditionsAny(@NotNull Command cmd, @NotNull Consumer<CommandConditionBuilder> consumer) {
        CommandConditionBuilder builder = new CommandConditionBuilder().or();
        consumer.accept(builder);
        cmd.setCondition(builder.build());
    }

    public static void addConditionalSyntax(@NotNull Command cmd, @NotNull Consumer<CommandConditionBuilder> consumer,
                                            @NotNull CommandExecutor executor, @NotNull Argument<?>... args) {
        CommandConditionBuilder builder = new CommandConditionBuilder();
        consumer.accept(builder);
        cmd.addConditionalSyntax(builder.build(), executor, args);
    }

    // INVALID MESSAGES

    public static void setInvalidUsageMessage(@NotNull Command cmd, @NotNull String key) {
        cmd.setDefaultExecutor((sender, context) ->
                I18nAPI.get(cmd).send(sender, key));
    }

    public static void setInvalidArgumentMessage(@NotNull Command cmd, @NotNull Argument<?> argument) {
        cmd.setArgumentCallback((sender, exception) ->
                I18nAPI.get(cmd).send(sender, "cmd.error.args", argument.getId(), exception.getInput()), argument);
    }

    public static void setInvalidArgumentMessage(@NotNull Command cmd, @NotNull Argument<?> argument, @NotNull String key) {
        cmd.setArgumentCallback((sender, exception) ->
                I18nAPI.get(cmd).send(sender, key, exception.getInput()), argument);
    }

    // internal

    private static CommandCondition combineSyntaxConditions(@NotNull Command cmd) {
        return (sender, commandString) -> cmd.getSyntaxes().stream()
                .map(CommandSyntax::getCommandCondition)
                .anyMatch(cc -> cc == null || cc.canUse(sender, null));
    }

}
