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
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
		Files.copy(file.toPath(), Paths.get("./evidence/Case08/No.1.png"));

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

		Files.copy(file2.toPath(), Paths.get("./evidence/Case08/No.2.png"));

		System.out.println("No.2: ログイン成功とコース詳細画面のエビデンス保存を完了しました。");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws Exception {
		//提出済みの行にある（二個目の）詳細ボタンを取得する
		final WebElement detailButton = webDriver
				.findElement(By.cssSelector("input[value='2'] + input"));
		// 詳細ボタンをクリックする
		detailButton.click();
		// 画面が切り替わるまで2秒待つ
		Thread.sleep(2000);

		// 画面全体のテキストを取得する
		String pageText = webDriver.findElement(By.tagName("body")).getText();
		// 画面内に「提出済み週報を確認する」ボタンの文言があることを検証する（画面遷移の確認）
		assertTrue(pageText.contains("本日のレポート"), "セクション詳細画面に遷移していません。");

		// 画面のスクリーンショットを撮影する
		File file3 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		// 撮影したスクリーンショットをエビデンス（No.3）として保存する
		Files.copy(file3.toPath(), Paths.get("./evidence/Case08/No.3.png"));
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws Exception {

		final WebElement reportButton = webDriver
				.findElement(By.cssSelector("input[value='提出済み週報【デモ】を確認する']"));
		// 「提出済み週報を確認する」ボタンをクリックする
		reportButton.click();

		Thread.sleep(3000);
		// 画面内にあるレポート入力欄（textarea）を取得する
		final WebElement textArea = webDriver.findElement(By.tagName("textarea"));
		// 入力欄が画面上に表示され、レポート登録画面に遷移できていることを検証
		assertTrue(textArea.isDisplayed(), "レポート登録画面（修正画面）に遷移していません。");

		File file4 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file4.toPath(), Paths.get("./evidence/Case08/No.4.png"));

		System.out.println("No.4: レポート登録画面への遷移とエビデンス保存を完了しました。");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() throws Exception {
		// By.tagName でレポートの入力欄（textarea）を取得する
		final WebElement textArea = webDriver.findElement(By.tagName("textarea"));
		// 消去する
		textArea.clear();
		// 修正用の新しい報告内容のテキストを打つ
		textArea.sendKeys("週報の修正テスト入力を完了しました。");
		// 文字の入力反映をしっかり1秒待つ
		Thread.sleep(1000);

		// ケース07で送信タイプ（type='submit'）の提出ボタンをCSSセレクターで取得する
		final WebElement submitButton = webDriver.findElement(By.cssSelector(".btn-primary"));
		// 「提出する」ボタンをクリックして修正内容を送信する
		submitButton.sendKeys(Keys.ENTER);
		// 画面が切り替わって元の『セクション詳細画面』に戻るまで待つ
		Thread.sleep(2000);

		// 現在の遷移後のURLを取得する
		String currentUrl = webDriver.getCurrentUrl();
		// 提出後に無事に元のセクション詳細画面に戻ってきていることを検証する
		assertTrue(currentUrl.contains("lms"), "提出後に詳細画面に戻っていません。");

		// 提出完了後に戻った『セクション詳細画面』のスクリーンショットを撮影・保存する
		File file5 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file5.toPath(), Paths.get("./evidence/Case08/No.5.png"));

		System.out.println("No.5: レポート提出完了とエビデンス保存を完了しました。");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
	}

}
