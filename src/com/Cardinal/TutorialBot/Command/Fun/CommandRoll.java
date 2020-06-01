package com.Cardinal.TutorialBot.Command.Fun;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import com.Cardinal.CommandPackage.Command.ArgumentTypes;
import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Command.Category.DefaultCategories;
import com.Cardinal.CommandPackage.Command.Category.ICategory;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.CommandPackage.Util.MarkdownUtils;
import com.Cardinal.TutorialBot.Emotes;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandRoll implements ICommand {

	@Override
	public String getName() {
		return "roll";
	}

	@Override
	public ICategory getCategory() {
		return DefaultCategories.FUN;
	}

	@Override
	public EnumSet<Permission> getPermissions() {
		return EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);
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
		return "Rolls a dice.";
	}

	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int numberRolled = random.nextInt(1, 7);
		event.getChannel().sendMessage(
				"You rolled " + MarkdownUtils.code(String.valueOf(numberRolled)) + " " + Emotes.DICE.toUnicode())
				.queue();
	}

}
