package net.starpye.quiz.discordimpl.command;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import net.starpye.quiz.discordimpl.game.GameList;
import net.starpye.quiz.discordimpl.game.LobbyList;

public class CommandContext {

    private final MessageContext messageContext;
    private final GameList gameList;
    private final LobbyList lobbyList;

    public CommandContext(MessageContext messageContext, GameList gameList, LobbyList lobbyList) {
        this.messageContext = messageContext;
        this.gameList = gameList;
        this.lobbyList = lobbyList;
    }

    public TextChannel getChannel() {
        return messageContext.textChannel;
    }

    public Member getAuthor() {
        return messageContext.author;
    }

    public String[] getArgs() {
        return messageContext.args;
    }

    public GameList getGameList() {
        return gameList;
    }

    public LobbyList getLobbyList() {
        return lobbyList;
    }

    public Message getMessage() {
        return messageContext.message;
    }

    public static class MessageContext {

        private final TextChannel textChannel;
        private final Message message;
        private final Member author;
        private final String[] args;

        public MessageContext(TextChannel textChannel, Message message, Member author, String[] args) {
            this.textChannel = textChannel;
            this.message = message;
            this.author = author;
            this.args = args;
        }
    }
}
