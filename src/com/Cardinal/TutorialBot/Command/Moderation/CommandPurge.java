package com.Cardinal.TutorialBot.Command.Moderation;

import java.util.EnumSet;
import java.util.List;

import com.Cardinal.CommandPackage.Command.ArgumentTypes;
import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Command.Category.DefaultCategories;
import com.Cardinal.CommandPackage.Command.Category.ICategory;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.TutorialBot.Emotes;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPurge implements ICommand {

	@Override
	public String getName() {
		return "purge";
	}

	@Override
	public ICategory getCategory() {
		return DefaultCategories.MODERATION;
	}

	@Override
	public EnumSet<Permission> getPermissions() {
		return EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.MESSAGE_MANAGE);
	}

	@Override
	public ArgumentTypes[] getArgumentTypes() {
		return new ArgumentTypes[] { ArgumentTypes.INTEGER };
	}

	@Override
	public String[] getArgumentNames() {
		return new String[] { "Amount" };
	}

	@Override
	public String getDescription() {
		return "Deletes the given amount of message from the current channel.";
	}

	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		int amount = (int) arguments[0];
		MessageHistory history = event.getChannel().getHistoryFromBeginning(amount).complete();
		List<Message> messages = history.getRetrievedHistory();
		event.getChannel().purgeMessages(messages);
		event.getMessage().addReaction(Emotes.HEAVY_CHECK.toUnicode()).queue();
	}

}
