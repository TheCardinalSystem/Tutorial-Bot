package com.Cardinal.TutorialBot.IO;

import java.io.IOException;
import java.io.OutputStream;

public class MultiOutputStream extends OutputStream {

	private OutputStream[] streams;

	public MultiOutputStream(OutputStream... outputStreams) {
		this.streams = outputStreams;
	}

	@Override
	public void write(int b) throws IOException {
		for (OutputStream stream : streams) {
			stream.write(b);
		}
	}

}
