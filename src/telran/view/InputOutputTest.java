package telran.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InputOutputTest {
	InputOutput io = new ConsoleInputOutput();

	@Test
	@Disabled
	void readObjectTest() {
		Integer[] array = io.readObject("Enter numbers separated by space", "no number ", s -> {

			String strNumbers[] = s.split(" ");
			return Arrays.stream(strNumbers).map(str -> Integer.parseInt(str)).toArray(Integer[]::new);

		});
		io.writeLine(Arrays.stream(array).collect(Collectors.summarizingInt(x -> x)));
	}

	@Test
	@Disabled
	void readIntMinMax() {
		Integer res = io.readInt("Enter any number in range [1, 40]", "no number ", 1, 40);
		io.writeLine(res);
	}

	@Test
	@Disabled
	void readLong() {
		Long res = io.readLong("Enter any long number ", "no long number \n");
		io.writeLine(res);
	}

	@Test
	@Disabled
	void readOptions() {
		List<String> options = new ArrayList<String>();
		options.add("Toyota");
		options.add("Mazda");
		options.add("KIA");
		String option = io.readOption("Enter one of options ", "Entered string is not in options \n", options);
		io.writeLine(option);
	}

	@Test
	@Disabled
	void readDate() {
		LocalDate date = io.readDate("Enter date", "Wrong date or format");
		io.writeLine(date);
	}

	@Test
	@Disabled
	void readDateFormat() {
		String pattern = "dd-MM-yyyy";
		LocalDate date = io.readDate("Enter date ", "Wrong date or format ", pattern);
		io.writeLine(date);
	}

	@Test

	void readPredicate() {
		Predicate<String> predicate = Pattern.compile("^(.+)@company.com$").asPredicate();
		String email = io.readPredicate("Enter emaile name@company.com ", "Wrong email or format ", predicate);
		io.writeLine(email);
	}

}
