package org.poo.handlers;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface CommandHandler {
    void execute(ArrayNode output);
}
