package org.poo.handlers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Command invoker.
 */
public class CommandInvoker {
    private final List<CommandHandler> commands = new ArrayList<>();

    /**
     * Add command.
     *
     * @param command the command
     */
    public void addCommand(final CommandHandler command) {
        commands.add(command);
    }

    /**
     * Execute commands.
     *
     * @param output the output
     */
    public void executeCommands(final ArrayNode output) {
        for (final CommandHandler command : commands) {
            command.execute(output);
        }
        commands.clear();
    }
}
