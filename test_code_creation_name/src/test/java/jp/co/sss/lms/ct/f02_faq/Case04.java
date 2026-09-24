package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		Files.copy(file.toPath(), Paths.get("./evidence/Case04/No.1.png"));

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

		Files.copy(file2.toPath(), Paths.get("./evidence/Case04/No.2.png"));

		System.out.println("No.2: ログイン成功とコース詳細画面のエビデンス保存を完了しました。");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() throws Exception {
		// 1. 上部メニューの「機能」メニューをクリック
		final WebElement functionMenu = webDriver.findElement(By.linkText("機能"));
		functionMenu.click();
		// プルダウンメニューが開くのを少し待つ
		Thread.sleep(1000);

		// 2. 開いたプルダウンメニューの中から「ヘルプ」をクリック
		final WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();
		// ヘルプ画面への遷移を待つ
		Thread.sleep(2000);

		// 3. ヘルプ画面の文言とリンクの確認
		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("ヘルプ"), "「ヘルプ」のタイトルが表示されていません。");
		assertTrue(pageText.contains("マニュアルはこちらからダウンロード"), "マニュアルダウンロードエリアが表示されていません。");
		assertTrue(pageText.contains("よくあるご質問はこちら"), "よくあるご質問エリアが表示されていません。");

		final WebElement manualLink = webDriver.findElement(By.linkText("受講生LMS利用マニュアル"));
		final WebElement faqLink = webDriver.findElement(By.linkText("よくある質問"));
		assertTrue(manualLink.isDisplayed(), "「受講生LMS利用マニュアル」のリンクが表示されていません。");
		assertTrue(faqLink.isDisplayed(), "「よくある質問」のリンクが表示されていません。");

		// エビデンス保存（No.3: ヘルプ画面）
		File file3 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file3.toPath(), Paths.get("./evidence/Case04/No.3.png"));
		System.out.println("No.3: 機能メニューからヘルプ画面への遷移確認とエビデンス保存を完了しました。");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws Exception {
		// ヘルプ画面内にある「よくある質問」リンクをクリック
		final WebElement faqLink = webDriver.findElement(By.linkText("よくある質問"));
		faqLink.click();
		Thread.sleep(3000); // 別タブが開くのを少し待つ

		List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
		if (tabs.size() > 1) {
			webDriver.switchTo().window(tabs.get(1));
		}

		// 新しく開いた別タブ（よくある質問画面）の表示内容確認
		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("よくある質問"), "「よくある質問」のタイトルが表示されていません。");
		assertTrue(pageText.contains("キーワード検索"), "「キーワード検索」エリアが表示されていません。");
		assertTrue(pageText.contains("カテゴリ検索"), "「カテゴリ検索」エリアが表示されていません。");
		assertTrue(pageText.contains("検索結果"), "「検索結果」エリアが表示されていません。");

		// エビデンス保存（No.4: よくある質問画面）
		File file4 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		Files.copy(file4.toPath(), Paths.get("./evidence/Case04/No.4.png"));
		System.out.println("No.4: よくある質問画面（別タブ）の確認とエビデンス保存を完了しました。");

	}

}
