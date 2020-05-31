package com.Cardinal.TutorialBot;

import java.net.MalformedURLException;

import javax.security.auth.login.LoginException;

import com.Cardinal.CommandPackage.Command.DefaultHelpCommand;
import com.Cardinal.CommandPackage.Command.DefaultPrefixCommand;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegisterException;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegistry;
import com.Cardinal.CommandPackage.Impl.CommandClient;
import com.Cardinal.CommandPackage.Impl.CommandClient.CommandClientBuilder;
import com.Cardinal.TutorialBot.Handle.GsonManager;

public class TutorialBot {

	public static void main(String[] args) throws CommandRegisterException, IllegalStateException, LoginException, InterruptedException, MalformedURLException {
		CommandClientBuilder builder = new CommandClientBuilder();
		builder.withToken(GsonManager.getConstant("Auth.DISCORD.TOKEN", String.class));
		
		CommandRegistry registry = new CommandRegistry();
		registry.registerCommand(new DefaultHelpCommand());
		registry.registerCommand(new DefaultPrefixCommand());
		
		builder.withRegistry(registry);
		
		CommandClient client = builder.build();
		System.out.println(client.generateInviteLink());
	}

}
