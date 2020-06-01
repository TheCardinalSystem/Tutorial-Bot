package com.Cardinal.TutorialBot;

import java.net.MalformedURLException;
import java.util.Arrays;

import javax.security.auth.login.LoginException;

import com.Cardinal.CommandPackage.Command.DefaultHelpCommand;
import com.Cardinal.CommandPackage.Command.DefaultPrefixCommand;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegisterException;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.CommandPackage.Impl.CommandClient;
import com.Cardinal.CommandPackage.Impl.CommandClient.CommandClientBuilder;
import com.Cardinal.TutorialBot.Command.Developer.CommandShutdown;
import com.Cardinal.TutorialBot.Command.Fun.CommandMath;
import com.Cardinal.TutorialBot.Command.Fun.CommandRandom;
import com.Cardinal.TutorialBot.Command.Fun.CommandRoll;
import com.Cardinal.TutorialBot.Command.Moderation.CommandBan;
import com.Cardinal.TutorialBot.Command.Moderation.CommandKick;
import com.Cardinal.TutorialBot.Command.Moderation.CommandPurge;
import com.Cardinal.TutorialBot.Handle.GsonManager;

public class TutorialBot {

	public static void main(String[] args) throws CommandRegisterException, IllegalStateException, LoginException,
			InterruptedException, MalformedURLException {
		CommandClientBuilder builder = new CommandClientBuilder();
		builder.withToken(GsonManager.getConstant("Auth.DISCORD.TOKEN", String.class));
		Arrays.stream((String[]) GsonManager.getConstant("DEVELOPERS", String[].class)).forEach(builder::withDeveloper);

		CommandRegistry registry = new CommandRegistry();
		registry.registerCommand(new DefaultHelpCommand());
		registry.registerCommand(new DefaultPrefixCommand());
		registry.registerCommand(new CommandKick());
		registry.registerCommand(new CommandBan());
		registry.registerCommand(new CommandPurge());
		registry.registerCommand(new CommandRoll());
		registry.registerCommand(new CommandRandom());
		registry.registerCommand(new CommandMath());
		registry.registerCommand(new CommandShutdown());

		builder.withRegistry(registry);

		@SuppressWarnings("unused")
		CommandClient client = builder.build();
	}

}
