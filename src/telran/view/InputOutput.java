package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public interface InputOutput {
	String readString(String prompt);

	void writeObject(Object obj);
	default void close() {}

	default void writeLine(Object obj) {
		String str = obj + "\n";
		writeObject(str);
	}

	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		R result = null;
		while (true) {
			String str = readString(prompt);
			try {
				result = mapper.apply(str);
				break;
			} catch (Exception e) {
				if (e.getMessage() !=null) {
					writeLine(errorPrompt + e.getMessage());
				} else {
					writeLine(errorPrompt);
				}				
			}
		}
		return result;

	}
	default Integer readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}
	default Integer readInt(String prompt, String errorPrompt, int min, int max) {
		return readObject(prompt, errorPrompt, s -> {
			int num = Integer.parseInt(s);
			if (num < min) {
				throw new RuntimeException("less than " + min);
			}
			if (num > max) {
				throw new RuntimeException("greater than " + max);
			}
			return num;
			
		});
	}
	default Long readLong(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Long::parseLong);
	}
	default String readOption(String prompt, String errorPrompt, List<String> options) {
		return readObject(String.format("%s %s", prompt, options),
						errorPrompt, s -> {
							if (options.contains(s)) {
								return s;
							}
							throw new RuntimeException();
						}) ;
	}
	default LocalDate readDate(String prompt, String errorPrompt) {
		return readDate(prompt, errorPrompt, "yyyy-MM-dd");
	}
	
	default LocalDate readDate(String prompt, String errorPrompt, String format) {
		return readObject(String.format("%s in format %s",prompt,format), errorPrompt, s -> {
					DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
					return LocalDate.parse(s, dateFormat);
				});
		
	}
	default String readPredicate(String prompt, String errorPrompt, Predicate<String> predicate) {
		return  readObject(String.format("%s in format",prompt), errorPrompt, s -> {
			return predicate.test(s)? s:errorPrompt;
			
		});
	}

}
