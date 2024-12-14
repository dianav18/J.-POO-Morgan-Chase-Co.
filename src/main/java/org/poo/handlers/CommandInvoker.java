package org.poo.handlers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.handlers.CommandHandler;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
    private final List<CommandHandler> commands = new ArrayList<>();

    public void addCommand(final CommandHandler command) {
        commands.add(command);
    }

    public void executeCommands(final ArrayNode output) {
        for (final CommandHandler command : commands) {
            command.execute(output);
        }
        commands.clear();
    }
}
