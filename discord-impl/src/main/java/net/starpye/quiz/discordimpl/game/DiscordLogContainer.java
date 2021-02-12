package net.starpye.quiz.discordimpl.game;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DiscordLogContainer implements LogContainer {

    private final Set<Snowflake> logs;
    private final TextChannel channel;

    public DiscordLogContainer(TextChannel channel) {
        this.channel = channel;
        this.logs = new HashSet<>();
    }

    @Override
    public void trackMessage(Snowflake id) {
        logs.add(id);
    }

    @Override
    public void deleteMessages() {
        synchronized (this) {
            logs.forEach(this::deleteLog);
            logs.clear();
        }
    }

    private void deleteLog(Snowflake id) {
        channel.getMessageById(id).subscribe(Message::delete);
    }
}
