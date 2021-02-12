package net.starpye.quiz.discordimpl.input;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import net.starpye.quiz.discordimpl.command.*;
import net.starpye.quiz.discordimpl.command.CommandContext.MessageContext;
import net.starpye.quiz.discordimpl.game.GameList;
import net.starpye.quiz.discordimpl.game.LobbyList;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MessageInputListener implements Consumer<MessageCreateEvent> {

    public static final String PREFIX = "?";

    private final Collection<? extends QuizCommand> commands;
    private final LobbyList lobbyList;
    private final GameList gameList;

    public MessageInputListener(LobbyList lobbyList, GameList gameList) {
        this.lobbyList = lobbyList;
        this.gameList = gameList;
        this.commands = initCommands();
    }

    @Override
    public void accept(MessageCreateEvent event) {

        Message message = event.getMessage();
        String content = message.getContent();
        if(!content.startsWith(PREFIX)) {
            return;
        }

        String[] args = content.split(" ");

        Optional<? extends QuizCommand> optCommand = findByName(args[0].replace(PREFIX, ""));
        message.getChannel()
                .cast(TextChannel.class)
                .map(channel -> Tuples.of(channel, optCommand))
                .filter(tuple -> tuple.getT2().isPresent())
                .map(tuple -> Tuples.of(tuple.getT1(), tuple.getT2().get()))
                .subscribe(tuple -> processCommand(
                        tuple.getT2(),
                        tuple.getT1(),
                        message,
                        event.getMember().get(), // Optional guaranteed to be present because of the filter
                        args)
                );
    }

    private void processCommand(QuizCommand command, TextChannel channel, Message message, Member member, String... args) {
        MessageContext messageContext = new MessageContext(channel, message, member, args);
        CommandContext context = new CommandContext(messageContext, gameList, lobbyList);
        command.execute(context);
    }

    private Optional<? extends QuizCommand> findByName(String name) {
        return commands
                .stream()
                .filter(command -> command.getName().equals(name))
                .findAny();
    }

    private Collection<? extends QuizCommand> initCommands() {
        return Arrays.asList(
                new CreateLobbyCommand(),
                new StartGameCommand(),
                new SubmitCommand(),
                new LeaveCommand(),
                new NextRoundCommand(),
                new ForceNextRoundCommand(),
                new RulesDisplayCommand(),
                new GenerateDatabaseCommand(),
                new CompiledQuestionSetCommand(),
                new ZipQuestionSetCommand(),
                new QueryAddCommand(),
                new ClearQueryCommand()
        );
    }

    public static Predicate<MessageCreateEvent> createFilter() {
        return (event) -> event.getMember().map(member -> !member.isBot()).orElse(false);
    }
}
