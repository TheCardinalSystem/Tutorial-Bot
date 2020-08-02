package com.Cardinal.TutorialBot.GUI.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.Cardinal.CommandPackage.Impl.CommandClient;
import com.Cardinal.TutorialBot.GUI.Panels.ConsolePanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8003603899456689951L;

	private ConsolePanel consolePanel;

	public MainFrame(String title, CommandClient client) {
		super(title);

		try {
			String avatarURL = client.getJDA().getSelfUser().getAvatarUrl();
			if (avatarURL != null) {
				URL url = new URL(avatarURL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.addRequestProperty("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, lile Gecko) Chrome/84.0.4147.105 Safari/537.36");
				connection.connect();
				InputStream in = connection.getInputStream();
				BufferedImage image = ImageIO.read(in);
				connection.disconnect();
				setIconImage(image);
			}
		} catch (IOException e) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(os);
			e.printStackTrace(ps);
			String stacktrace = os.toString();
			CommandClient.LOGGER.warning(stacktrace);
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setSize((int) (width / 1.9), (int) (height / 2));
		this.setLocation((int) width / 4, (int) height / 4);

		consolePanel = new ConsolePanel(client);

		this.setLayout(new BorderLayout());
		this.add(consolePanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> client.getJDA().shutdownNow()));
	}
}
