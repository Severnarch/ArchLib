package io.github.severnarch.archlib.exceptions.command;

public class ArgumentNotFoundException extends Throwable {
    public ArgumentNotFoundException(String argumentName, String commandName) {
        super("Argument of name '"+argumentName+"' could not be found in command '"+commandName+"'.");
    }
}
