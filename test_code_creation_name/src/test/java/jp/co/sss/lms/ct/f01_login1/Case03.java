package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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
		// 画面上にログインID入力欄が存在するかチェック
		final WebElement loginId = webDriver.findElement(By.name("loginId"));

		final WebElement passWd = webDriver.findElement(By.name("password"));

		final WebElement loginButton = webDriver.findElement(By.cssSelector(".btn.btn-primary"));

		//ログイン画面が表示されていることを「assertTrue」で検証
		assertTrue(loginId.isDisplayed());

		assertTrue(passWd.isDisplayed());

		assertTrue(loginButton.isDisplayed());

		//エビデンスを撮る
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case03/No.1.png"));

		System.out.println("No.1: ログイン画面確認とエビデンス保存を完了しました。");

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {

		//[No.01]ログイン画面にアクセスする
		goTo("http://localhost:8080/lms");

		//ログインIDとパスワードを記憶
		String myId = "StudentAA01";
		String myPw = "studentAA01";

		//[No.02]画面の表示内容を確認する。
		//ログインID、パスワード、ログインボタンが表示されていること
		final WebElement checkLoginId = webDriver.findElement(By.name("loginId"));
		checkLoginId.sendKeys(myId);

		final WebElement checkPass = webDriver.findElement(By.name("password"));
		checkPass.sendKeys(myPw);
		final WebElement checkloginButton = webDriver.findElement(By.cssSelector(".btn.btn-primary"));

		// 入力された文字列が正しく反映されているかを検証（仕様書の期待値より）
		assertEquals(myId, checkLoginId.getAttribute("value"), "ログインIDが正しく入力されていません。");
		assertEquals(myPw, checkPass.getAttribute("value"), "パスワードが正しく入力されていません。");

		//3秒待つ
		Thread.sleep(3000);

		//エビデンスを撮る
		File file2 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

		Files.copy(file2.toPath(), Paths.get("./evidence/Case03/No.2.png"));
		System.out.println("No.2:IDとパスワードの入力を完了し、エビデンスを保存しました。");
		//  ログインボタンをクリック
		checkloginButton.click();

		// 画面が切り替わるのを少し待つ
		Thread.sleep(2000);

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 コース詳細画面の表示内容確認")
	void test03() throws Exception {

		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(currentUrl.contains("lms"), "コース詳細画面へ遷移していません。現在のURL: " + currentUrl);

		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("受講生"), "ログインした受講生の専用情報が表示されていません。");
		// エビデンスを撮る（最終確認画面）
		File file3 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file3.toPath(), Paths.get("./evidence/Case03/No.3.png"));

		System.out.println("No.3:コース詳細画面の表示内容確認とエビデンス保存を完了しました。");
	}

}
