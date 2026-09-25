package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		Files.copy(file.toPath(), Paths.get("./evidence/Case07/No.1.png"));

		System.out.println("No.1: ログイン画面確認とエビデンス保存を完了しました。");

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {
		//ログイン画面が開いている前提で入力を行う
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

		//  ログインボタンをクリック
		checkloginButton.click();

		// 画面が切り替わるのを少し待つ
		Thread.sleep(2000);

		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(currentUrl.contains("lms"), "コース詳細画面へ遷移していません。現在のURL: " + currentUrl);

		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("受講生"), "ログインした受講生の専用情報が表示されていません。");
		//エビデンスを撮る
		File file2 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

		Files.copy(file2.toPath(), Paths.get("./evidence/Case07/No.2.png"));

		System.out.println("No.2: ログイン成功とコース詳細画面のエビデンス保存を完了しました。");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws Exception {

		final WebElement detailButton = webDriver
				.findElement(By.cssSelector("form[action*='/lms/section/detail'] .btn.btn-default"));
		// 詳細ボタンをクリックする
		detailButton.click();
		// 画面が切り替わるまで待つ
		Thread.sleep(2000);
		// 画面全体の目に見えるテキストを取得します
		String pageText = webDriver.findElement(By.tagName("body")).getText();

		//「本日のレポート」の文字があることを検証
		assertTrue(pageText.contains("本日のレポート"), "「本日のレポート」が表示されていません。");

		// 「セクション詳細画面」のスクリーンショットを撮影・保存
		File file3 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file3.toPath(), Paths.get("./evidence/Case07/No.3.png"));

		System.out.println("No.3: セクション詳細画面への遷移とエビデンス保存を完了しました。");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws Exception {
		final WebElement reportButton = webDriver.findElement(By.cssSelector(
				"form[action*='report'] input[type='submit'], form[action*='report'] button, input[value*='提出する']"));
		reportButton.click();

		// 画面が完全に『レポート登録画面』に切り替わるまで待つ
		Thread.sleep(3000);

		// 画面内に入力欄（textarea）が存在するかを検証
		final WebElement textArea = webDriver.findElement(By.tagName("textarea"));
		assertTrue(textArea.isDisplayed(), "レポート入力欄（textarea）が表示されていません。");

		File file4 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file4.toPath(), Paths.get("./evidence/Case07/No.4.png"));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() throws Exception {
		// レポートの入力欄（textarea）を取得する
		final WebElement textArea = webDriver.findElement(By.tagName("textarea"));
		// 入力欄に報告内容のテキストを打つ
		textArea.sendKeys("Case07");
		// 文字の入力反映を待つ
		Thread.sleep(1000);

		// 「提出する」ボタンを取得する

		final WebElement submitButton = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		// 「提出する」ボタンをクリックして送信する
		submitButton.click();
		// 画面が切り替わるまで待つ
		Thread.sleep(2000);

		String pageText = webDriver.findElement(By.tagName("body")).getText();

		// 画面内に、画像に映っている「本日のレポート」の文字があることを検証
		assertTrue(pageText.contains("本日のレポート"), "「本日のレポート」が表示されていません。");

		// 画面のスクリーンショットを撮影する
		File file5 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		// 撮影したスクリーンショットをエビデンス（No.5）として保存する
		Files.copy(file5.toPath(), Paths.get("./evidence/Case07/No.5.png"));
	}

}
