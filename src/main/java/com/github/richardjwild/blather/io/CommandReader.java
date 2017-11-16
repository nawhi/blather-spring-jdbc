package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.user.User;

public class CommandReader {

    private final Input input;
    private final InputParser inputParser;
    private final CommandFactory commandFactory;

    public CommandReader(Input input, InputParser inputParser, CommandFactory commandFactory) {
        this.input = input;
        this.inputParser = inputParser;
        this.commandFactory = commandFactory;
    }

    public Command readNextCommand() {
        String inputLine = input.readLine();
        switch (inputParser.readVerb(inputLine)) {
            case POST:
                return makePostCommand(inputLine);
            case READ:
                return makeReadCommand(inputLine);
            case FOLLOW:
                return makeFollowCommand(inputLine);
            case WALL:
                return makeWallCommand(inputLine);
            default:
                return commandFactory.makeQuitCommand();
        }
    }

    private Command makePostCommand(String inputLine) {
        User user = inputParser.readUser(inputLine);
        User recipient = inputParser.readPostRecipient(inputLine);
        String message = inputParser.readPostMessage(inputLine);
        return commandFactory.makePostCommand(user, recipient, message);
    }

    private Command makeReadCommand(String inputLine) {
        User user = inputParser.readUser(inputLine);
        User subject = inputParser.readSubject(inputLine);
        return commandFactory.makeReadCommand(user, subject);
    }

    private Command makeFollowCommand(String inputLine) {
        User user = inputParser.readUser(inputLine);
        User subject = inputParser.readFollowSubject(inputLine);
        return commandFactory.makeFollowCommand(user, subject);
    }

    private Command makeWallCommand(String inputLine) {
        User user = inputParser.readUser(inputLine);
        User subject = inputParser.readWallSubject(inputLine);
        return commandFactory.makeWallCommand(user, subject);
    }
}
