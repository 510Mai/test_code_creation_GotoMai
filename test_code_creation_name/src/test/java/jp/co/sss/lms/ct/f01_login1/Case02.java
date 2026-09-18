package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() throws Exception {
		//[No.01]ログイン画面にアクセスする
		goTo("http://localhost:8080/lms");
		//1秒待つ
		Thread.sleep(1000);

		//エビデンスを撮る
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case02/No.1.png"));

		System.out.println("No.1: ログイン画面確認とエビデンス保存を完了しました。");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() throws Exception {
		//[No.01]ログイン画面にアクセスする
		goTo("http://localhost:8080/lms");
		//1秒待つ
		Thread.sleep(1000);

		//ログインIDと間違えたパスワードを記憶
		String myId = "StudentAA01";
		String myPw = "StudentAA01_error";

		//[No.02]画面の表示内容を確認する。
		//ログインID、パスワード、ログインボタンが表示されていること
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		loginId.sendKeys(myId);

		final WebElement passWd = webDriver.findElement(By.name("password"));
		passWd.sendKeys(myPw);
		final WebElement loginButton = webDriver.findElement(By.cssSelector(".btn.btn-primary"));

		//3秒待つ
		Thread.sleep(3000);

		//エビデンスを撮る
		File file2 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

		Files.copy(file2.toPath(), Paths.get("./evidence/Case02/No.2.png"));
		System.out.println("テスト1:IDとパスワードの入力を完了し、エビデンスを保存しました。");
	}

	@Test
	@Order(3)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test03() throws Exception {
		//[No.01]ログイン画面にアクセスする
		goTo("http://localhost:8080/lms");
		//1秒待つ
		Thread.sleep(1000);

		//ログインIDと間違えたパスワードを記憶
		String myId = "StudentAA01";
		String myPw = "StudentAA01_error";

		//[No.02]画面の表示内容を確認する。
		//ログインID、パスワード、ログインボタンが表示されていること
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		loginId.sendKeys(myId);

		final WebElement passWd = webDriver.findElement(By.name("password"));
		passWd.sendKeys(myPw);

		//[No.03]エラー画面の表示内容を確認

		final WebElement loginButton = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		loginButton.click();
		//3秒待つ
		Thread.sleep(3000);

		File file3 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

		Files.copy(file3.toPath(), Paths.get("./evidence/Case02/No.3.png"));
		System.out.println("テスト1: エラー画面とエビデンス保存を完了しました。");

	}

}
