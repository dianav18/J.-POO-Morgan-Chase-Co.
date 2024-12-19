package org.poo.handlers;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * The interface Command handler.
 */
public interface CommandHandler {
    /**
     * Execute.
     *
     * @param output the output
     */
    void execute(ArrayNode output);
}
