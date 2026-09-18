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
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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
		Files.copy(file.toPath(), Paths.get("./evidence/Case01/No.1.png"));

		System.out.println("No.1: ログイン画面確認とエビデンス保存を完了しました。");

	}

}
