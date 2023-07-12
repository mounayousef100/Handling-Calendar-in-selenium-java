package demo.dempProject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CalendarTest {
	private static final Map<String, Integer> monthNumber = new HashMap<>();

	static {
		monthNumber.put("January", 1);
		monthNumber.put("February", 2);
		monthNumber.put("March", 3);
		monthNumber.put("April", 4);
		monthNumber.put("May", 5);
		monthNumber.put("June", 6);
		monthNumber.put("July", 7);
		monthNumber.put("August", 8);
		monthNumber.put("September", 9);
		monthNumber.put("October", 10);
		monthNumber.put("November", 11);
		monthNumber.put("December", 12);
	}

	@Test
	public void Calendar() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		new WebDriverWait(driver, 15);

		driver.get("http://seleniumpractise.blogspot.com/2016/08/how-to-handle-calendar-in-selenium.html");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String expectedDate = "2029-09-27";
		selectDate(driver, expectedDate);

		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.quit();
	}

	public static void selectDate(WebDriver driver, String expectedDate) {
		driver.findElement(By.id("datepicker")).click();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-datepicker-div")));

		LocalDate expectedLocalDate = LocalDate.parse(expectedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		int expectedDay = expectedLocalDate.getDayOfMonth();
		int expectedMonth = expectedLocalDate.getMonthValue();
		int expectedYear = expectedLocalDate.getYear();

		while (true) {
			String currentMonthText = driver.findElement(By.className("ui-datepicker-month")).getText();
			int currentMonthNumber = monthNumber.get(currentMonthText);
			int currentYearNumber = Integer.parseInt(driver.findElement(By.className("ui-datepicker-year")).getText());

			if (currentYearNumber == expectedYear && currentMonthNumber == expectedMonth) {
				break;
			} else if (currentYearNumber < expectedYear
					|| (currentYearNumber == expectedYear && currentMonthNumber < expectedMonth)) {
				driver.findElement(By.xpath("//span[text()='Next']")).click();
			} else {
				driver.findElement(By.xpath("//span[text()='Prev']")).click();
			}
		}

		java.util.List<WebElement> days = driver.findElements(By.xpath("//a[@class='ui-state-default']"));
		for (WebElement day : days) {
			if (day.getText().equals(String.valueOf(expectedDay))) {
				day.click();
				break;
			}
		}
	}
}