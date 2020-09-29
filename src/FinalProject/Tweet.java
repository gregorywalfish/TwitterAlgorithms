package FinalProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tweet implements Comparable<Tweet> {
	private String author;
	private String dateAndTime; // format of the string "yyyy-MM-dd HH:mm:ss"
	private String message;

	public Tweet(String a, String dAndT, String m) {
		this.author = a;
		this.dateAndTime = dAndT;
		this.message = m;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getDateAndTime() {
		return this.dateAndTime;
	}

	public String getMessage() {
		return this.message;
	}

	public String toString() {
		String[] info = { this.author, this.dateAndTime, this.message };
		return String.join("\t", info);
	}

	public int compareTo(Tweet t) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime1 = LocalDateTime.parse(this.dateAndTime, format);
		LocalDateTime dateTime2 = LocalDateTime.parse(t.dateAndTime, format);

		int c = dateTime1.compareTo(dateTime2);
		if (c == 0) {
			return this.author.compareTo(t.author);
		}

		return c;
	}

}
