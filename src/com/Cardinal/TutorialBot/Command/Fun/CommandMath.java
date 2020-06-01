package com.Cardinal.TutorialBot.Command.Fun;

import java.util.EnumSet;

import com.Cardinal.CommandPackage.Command.ArgumentTypes;
import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Command.Category.DefaultCategories;
import com.Cardinal.CommandPackage.Command.Category.ICategory;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.CommandPackage.Util.MarkdownUtils;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandMath implements ICommand {

	@Override
	public String getName() {
		return "math";
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
		return new ArgumentTypes[] { ArgumentTypes.STRING_ARRAY };
	}

	@Override
	public String[] getArgumentNames() {
		return new String[] { "Expression" };
	}

	@Override
	public String getDescription() {
		return "Evaluates the given mathmatical expression.";
	}

	@Override
	public void execute(MessageReceivedEvent event, CommandRegistry registry, String prefix, Object... arguments)
			throws Exception {
		String[] array = (String[]) arguments[0];
		String expression = String.join("", array);
		DoubleEvaluator evaluator = new DoubleEvaluator();
		double result = evaluator.evaluate(expression);
		event.getChannel().sendMessage(MarkdownUtils.codeBox(expression + " = " + result)).queue();
	}

}
