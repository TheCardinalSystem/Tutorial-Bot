package com.Cardinal.TutorialBot.Command.Fun;

import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.Cardinal.CommandPackage.Command.ArgumentTypes;
import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Command.Category.DefaultCategories;
import com.Cardinal.CommandPackage.Command.Category.ICategory;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.CommandPackage.Util.MarkdownUtils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandRandom implements ICommand {

	@Override
	public String getName() {
		return "random";
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
		return new ArgumentTypes[] { ArgumentTypes.STRING.optional() };
	}

	@Override
	public String[] getArgumentNames() {
		return new String[] { "Number Range" };
	}

	@Override
	public String getDescription() {
		return "Get's a random number in the given range, if specified.";
	}

	//1-5
	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		@SuppressWarnings("unchecked")
		Optional<String> optional = (Optional<String>) arguments[0];
		String[] range = optional.isPresent() ? optional.get().split("\\-")
				: new String[] { "0", String.valueOf(Integer.MAX_VALUE) };
		ThreadLocalRandom random = ThreadLocalRandom.current();
		event.getChannel()
				.sendMessage("Your random number is " + MarkdownUtils
						.code(String.valueOf(random.nextInt(Integer.parseInt(range[0]), Integer.parseInt(range[1])))))
				.queue();
	}

}
