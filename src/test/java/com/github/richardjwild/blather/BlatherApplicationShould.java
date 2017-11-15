package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.io.CommandReader;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.io.InputParser;
import com.github.richardjwild.blather.io.Output;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlatherApplicationShould {

    @Mock
    private Input input;

    @Mock
    private Output output;

    private InputParser inputParser = new InputParser();
    private CommandFactory commandFactory = new CommandFactory();
    private CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
    private AppController appController = new AppController();

    @Test
    public void display_a_users_posted_messages() {
        when(input.readLine())
                .thenReturn("Alice -> My first message")
                .thenReturn("Alice")
                .thenReturn("Quit");

        Blather blather = new Blather(appController, commandReader);
        blather.eventLoop();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("My first message");
        inOrder.verify(output).writeLine("Bye!");
    }
}