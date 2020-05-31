package com.Cardinal.TutorialBot.Util;

import java.io.InputStream;
import java.util.Scanner;

public class InputStreamUtils {

	public static String toString(InputStream stream) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(stream).useDelimiter("\\A");
		return sc.hasNext() ? sc.next() : "";
	}
	
}
