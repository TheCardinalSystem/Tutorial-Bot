package com.Cardinal.TutorialBot.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.Cardinal.CommandPackage.Command.ICommand;
import com.Cardinal.CommandPackage.Handle.Command.CommandRegisterListener;
import com.Cardinal.CommandPackage.Impl.CommandClient;
import com.Cardinal.CommandPackage.Util.StringUtils;
import com.Cardinal.TutorialBot.Emotes;
import com.Cardinal.TutorialBot.IO.MultiOutputStream;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class ConsolePanel extends JPanel {

	private static final long serialVersionUID = 2473646228426865709L;
	private static final Pattern ID_PATTERN = Pattern.compile("\\d{18}");

	private static JTextArea console;

	private JScrollPane scrollPane;
	private JComboBox<String> channels, action;
	private JTextField command;
	private JButton execute;
	private JPanel dropDownPanel, commandPanel;
	private Set<String> commands;

	public ConsolePanel(CommandClient client) {
		List<String> messageChannels = new ArrayList<String>();

		for (Guild guild : client.getJDA().getGuilds()) {
			for (TextChannel channel : guild.getChannels().stream().filter(c -> c.getType().equals(ChannelType.TEXT))
					.toArray(TextChannel[]::new)) {
				messageChannels.add(channel.getName() + " | " + channel.getId() + " | " + channel.getGuild().getName());
				messageChannels.add(channel.getId() + " | " + channel.getName() + " | " + channel.getGuild().getName());
			}
		}

		channels = new JComboBox<String>(messageChannels.stream().sorted(StringUtils::compare).toArray(String[]::new));
		AutoCompleteDecorator.decorate(channels);

		action = new JComboBox<String>(new String[] { "Execute", "Say" });

		commands = new HashSet<String>();
		commands.addAll(client.getRegistry().getCommandNames());
		commands.addAll(client.getRegistry().getAliasesMapping().keySet());

		command = new JTextField(20);
		AutoCompleteDecorator.decorate(command, new ArrayList<String>(commands), false);

		command.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					execute.doClick();
				}
			}
		});
		
		client.getRegistry().addListener(new CommandRegisterListener() {

			@Override
			public void commandUnregistered(ICommand arg0) {
				commands.remove(arg0.getName());
				AutoCompleteDecorator.decorate(command, new ArrayList<String>(commands), false);
			}

			@Override
			public void commandRegistered(ICommand arg0) {
				commands.add(arg0.getName());
				AutoCompleteDecorator.decorate(command, new ArrayList<String>(commands), false);
			}

			@Override
			public void aliasUnregistered(String arg0, ICommand arg1) {
				commands.remove(arg0);
				AutoCompleteDecorator.decorate(command, new ArrayList<String>(commands), false);
			}

			@Override
			public void aliasRegistered(String arg0, ICommand arg1) {
				commands.add(arg0);
				AutoCompleteDecorator.decorate(command, new ArrayList<String>(commands), false);
			}
		});

		execute = new JButton(Emotes.ARROW_RIGHT.toUnicode());
		execute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = command.getText();
				if(text == null || text.isEmpty()) {
					return;
				}
				command.setText("");
				Matcher m = ID_PATTERN.matcher((String) channels.getSelectedItem());
				m.find();
				TextChannel channel = client.getJDA().getTextChannelById(m.group());
				String actionSelected = (String) action.getSelectedItem();

				if (actionSelected.equalsIgnoreCase("say")) {
					channel.sendMessage(text).queue();
				} else {
					client.dispatchCommand(text, channel);
				}
			}
		});

		scrollPane = new JScrollPane(console);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		dropDownPanel = new JPanel(new BorderLayout());
		dropDownPanel.add(channels, BorderLayout.WEST);
		dropDownPanel.add(action, BorderLayout.EAST);

		commandPanel = new JPanel(new BorderLayout());
		commandPanel.add(dropDownPanel, BorderLayout.WEST);
		commandPanel.add(command, BorderLayout.CENTER);
		commandPanel.add(execute, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(commandPanel, BorderLayout.SOUTH);
	}

	public static void init() {
		console = new JTextArea();
		console.setEditable(false);

		OutputStream textAreaOutputStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				console.append(String.valueOf((char) b));
			}
		};

		MultiOutputStream stream = new MultiOutputStream(System.out, textAreaOutputStream);
		MultiOutputStream errStream = new MultiOutputStream(System.err, textAreaOutputStream);

		PrintStream out = new PrintStream(stream);
		System.setOut(out);
		PrintStream err = new PrintStream(errStream);
		System.setErr(err);
	}
}
