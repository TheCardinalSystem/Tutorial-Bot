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

public class CommandBan implements ICommand {

	@Override
	public String getName() {
		return "ban";
	}

	@Override
	public ICategory getCategory() {
		return DefaultCategories.MODERATION;
	}

	@Override
	public EnumSet<Permission> getPermissions() {
		return EnumSet.of(Permission.MESSAGE_READ, Permission.BAN_MEMBERS);
	}

	@Override
	public ArgumentTypes[] getArgumentTypes() {
		return new ArgumentTypes[] { ArgumentTypes.USER_MENTION, ArgumentTypes.INTEGER };
	}

	@Override
	public String[] getArgumentNames() {
		return new String[] { "User", "Delete Days" };
	}

	@Override
	public String getDescription() {
		return "Bans the given user and deletes all his messages from the specified number of days (past).";
	}

	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		User user = (User) arguments[0];
		int deleteDays = (int) arguments[1];
		
		event.getGuild().ban(event.getGuild().getMember(user), deleteDays).queue(v -> {
			event.getChannel().sendMessage("Banned " + user.getAsMention()).queue();
		});
	}

}
