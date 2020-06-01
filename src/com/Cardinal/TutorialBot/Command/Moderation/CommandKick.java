package com.Cardinal.TutorialBot.Command.Moderation;

import java.util.EnumSet;

import com.Cardinal.CommandPackage.Command.ArgumentTypes;
import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Command.Category.DefaultCategories;
import com.Cardinal.CommandPackage.Command.Category.ICategory;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandKick implements ICommand {

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public ICategory getCategory() {
		return DefaultCategories.MODERATION;
	}

	@Override
	public EnumSet<Permission> getPermissions() {
		return EnumSet.of(Permission.MESSAGE_READ, Permission.KICK_MEMBERS);
	}

	@Override
	public ArgumentTypes[] getArgumentTypes() {
		return new ArgumentTypes[] { ArgumentTypes.USER_MENTION };
	}

	@Override
	public String[] getArgumentNames() {
		return new String[] { "User" };
	}

	@Override
	public String getDescription() {
		return "Kicks the given user.";
	}

	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		User user = (User) arguments[0];
		event.getGuild().kick(event.getGuild().getMember(user)).queue(v -> {
			event.getChannel().sendMessage("Kicked " + user.getAsMention()).queue();
		});
	}

}
