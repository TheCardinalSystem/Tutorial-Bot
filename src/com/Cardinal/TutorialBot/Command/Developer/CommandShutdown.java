package com.Cardinal.TutorialBot.Command.Developer;

import java.util.EnumSet;

import com.Cardinal.CommandPackage.Command.ArgumentTypes;
import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Command.Category.DefaultCategories;
import com.Cardinal.CommandPackage.Command.Category.ICategory;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.TutorialBot.Emotes;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandShutdown implements ICommand {

	@Override
	public String getName() {
		return "shutdown";
	}

	@Override
	public ICategory getCategory() {
		return DefaultCategories.DEVELOPER;
	}

	@Override
	public EnumSet<Permission> getPermissions() {
		return EnumSet.of(Permission.MESSAGE_READ);
	}

	@Override
	public ArgumentTypes[] getArgumentTypes() {
		return null;
	}

	@Override
	public String[] getArgumentNames() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Shuts down the bot.";
	}

	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		event.getMessage().addReaction(Emotes.HEAVY_CHECK.toUnicode()).complete();
		event.getJDA().shutdown();
		System.exit(0);
	}

}
