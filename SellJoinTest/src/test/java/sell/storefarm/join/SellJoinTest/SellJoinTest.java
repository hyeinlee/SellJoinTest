package sell.storefarm.join.SellJoinTest;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Screenshots;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.fail;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


//@SuppressWarnings("deprecation")

public class SellJoinTest {

	private static final String WebDriverAction = null;
	protected WebDriver driver;

	@Before
	public void setUp() {
		//firefox
		driver = new FirefoxDriver();
		
		//chrome
		//System.setProperty("webdriver.chrome.driver",
		//		"C:\\Users\\NAVER\\Desktop\\Cucumber 스터디\\chromedriver_win32\\chromedriver.exe");
		//driver = new ChromeDriver();
		
		//ie
		//System.setProperty("webdriver.ie.driver","C:\\Users\\NAVER\\Desktop\\Cucumber 스터디\\IEDriverServer_x64_2.46.0\\IEDriverServer.exe");
        //driver = new InternetExplorerDriver();

		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// Serenity.takeScreenshot();
	}
	
	// ---------------------------------------------------------------------------시나리오 1

	@Given("^판매자센터 개인판매자 유형 가입페이지에 접속한다\\.$")
	@Screenshots(forEachAction=true)
	public void 판매자센터_개인판매자_유형_가입페이지에_접속한다() {

		driver.get("http://alpha.sell.storefarm.naver.com/s/");
		driver.findElement(By.xpath("//*[@id='login_footer']/p/a")).click();
		driver.findElement(By.xpath("//*[@id='content']/div[2]/a")).click();
		Serenity.takeScreenshot();
	}

	@When("^약관동의 (\\d+)가지에 모두 체크하고 \\[동의\\]버튼을 클릭한다\\.$")
	@Screenshots(forEachAction=true)
	public void 약관동의_가지에_모두_체크하고_동의_버튼을_클릭한다(int arg1) {
		driver.findElement(By.xpath("//*[@id='chk_choice']")).click();
		driver.findElement(By.xpath("//*[@id='chk_choice2']")).click();
		driver.findElement(By.xpath("//*[@id='chk_choice3']")).click();
		driver.findElement(By.xpath("//*[@id='content']/div[8]/a[1]")).click();
	}

	@When("^휴대폰 실명인증 우회를 On으로 체크한다$")
	public void 휴대폰_실명인증_우회를_On으로_체크한다() {
		driver.findElement(
				By.xpath("//*[@id='darkMobileRealNameCheckAuthRadioOn']"))
				.click();
	}

	@When("^휴대폰 실명인증 우회: 이름, SSN을 입력한다\\.$")
	public void 휴대폰_실명인증_우회_이름_SSN을_입력한다() {
		driver.findElement(
				By.xpath("//*[@id='darkMobileRealNameCheckAuthName']"))
				.sendKeys("테스트");
		driver.findElement(
				By.xpath("//*[@id='darkMobileRealNameCheckAuthSsn']"))
				.sendKeys("811111-1111137");

	}

	@When("^\\[휴대폰 본인인증\\] 버튼을 클릭한다\\.$")
	public void 휴대폰_본인인증_버튼을_클릭한다() {

		driver.findElement(By.xpath("//*[@id='content']/div[1]/a")).click();
	}

	@Then("^필수입력정보 페이지 접속을 확인한다\\.$")
	public void 필수입력정보_페이지_접속을_확인한다() {

		WebElement check = driver.findElement(By.name("seller.loginId"));

		if (check.isDisplayed())
			check.click();
		else
			fail("fail");

		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					"C:\\Users\\NAVER\\workspace\\SellJoinTest\\test1.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ----------------------------------------------------------------------------
	// 시나리오 2
	@When("^아이디 입력 후 중복확인 팝업에서 \\[사용하기\\] 버튼을 클릭한다\\.$")
	public void 아이디_입력_후_중복확인_팝업에서_사용하기_버튼을_클릭한다() throws Exception{
				
		// 아이디 중복확인
		driver.findElement(By.name("seller.loginId")).sendKeys("sw1tcc034");

		WebElement checkId = driver
				.findElement(By
						.xpath("//*[@id='content']/form/div[1]/div/table/tbody/tr[1]/td/a/span"));
		checkId.click();
		
		String currentWindowid = driver.getWindowHandle();
		Set<String> allwindowsid = driver.getWindowHandles();

		if (!allwindowsid.isEmpty()) {
			for (String windowId : allwindowsid) {
				driver.switchTo().window(windowId);
				
				Thread.sleep(2000);
				
				if (driver.getPageSource().contains("아이디입니다.")) {
					try {
						WebElement useId = driver.findElement(By.linkText("사용하기"));
						useId.click();
						break;
					} catch (NoSuchWindowException e) {
						e.printStackTrace();
					}
				}
			}
		}
		driver.switchTo().window(currentWindowid);
	}

	@When("^스토어팜 이름 입력 후 중복확인 팝업에서 \\[사용하기\\] 버튼을 클릭한다\\.$")
	public void 스토어팜_이름_입력_후_중복확인_팝업에서_사용하기_버튼을_클릭한다() throws Exception {

		// 스토어팜 이름 중복확인
		driver.findElement(By.name("seller.shop.name")).sendKeys("sw1tcc034");

		WebElement storefarmName = driver
				.findElement(By
						.xpath("//*[@id='content']/form/div[1]/div/table/tbody/tr[9]/td/a"));
		storefarmName.click();

		String parentWindowName = driver.getWindowHandle();
		Set<String> allwindowsName = driver.getWindowHandles();

		if (!allwindowsName.isEmpty()) {
			for (String windowId : allwindowsName) {
				driver.switchTo().window(windowId);
					
				Thread.sleep(2000);
				
				if (driver.getPageSource().contains("이름입니다.")) {
					try {
						WebElement useId = driver.findElement(By.linkText("사용하기"));
						useId.click();
						break;
					} catch (NoSuchWindowException e) {
						e.printStackTrace();
					}
				}
			}
		}
		driver.switchTo().window(parentWindowName);
	}

	@When("^스토어팜 주소 입력 후 중복확인 팝업에서 \\[사용하기\\] 버튼을 클릭한다\\.$")
	public void 스토어팜_주소_입력_후_중복확인_팝업에서_사용하기_버튼을_클릭한다() throws Exception {

		// 스토어팜 주소 중복확인
		driver.findElement(By.name("seller.shop.url")).sendKeys("sw1tcc034");

		WebElement storefarmAdd = driver
				.findElement(By
						.xpath("//*[@id='content']/form/div[1]/div/table/tbody/tr[10]/td/a"));
		storefarmAdd.click();

		String parentWindowId11 = driver.getWindowHandle();
		Set<String> allwindows11 = driver.getWindowHandles();

		if (!allwindows11.isEmpty()) {
			for (String windowId : allwindows11) {
				driver.switchTo().window(windowId);
				
				Thread.sleep(2000);
				
				if (driver.getPageSource().contains("주소를")) {
					try {
						WebElement useId = driver.findElement(By.linkText("사용하기"));
						driver.manage().timeouts()
								.implicitlyWait(5, TimeUnit.SECONDS);
						useId.click();
						break;
					} catch (NoSuchWindowException e) {
						e.printStackTrace();
					}
				}
			}
		}		
		driver.switchTo().window(parentWindowId11);
	}

	@When("^휴대폰 인증 우회를 On으로 체크한다\\.$")
	public void 휴대폰_인증_우회를_On으로_체크한다() throws Exception {

		Select drop = new Select(driver.findElement(By
				.name("seller.cellPhoneNumber.head")));
		drop.selectByVisibleText("010");
		driver.findElement(By.name("seller.cellPhoneNumber.tail")).sendKeys(
				"1111");
		driver.findElement(By.name("seller.cellPhoneNumber.body")).sendKeys(
				"2222");

		driver.findElement(By.xpath("//*[@id='darkMobileNumberAuthRadioOn']"))
				.click();

		driver.findElement(
				By.xpath("//*[@id='content']/form/div[1]/div/table/tbody/tr[6]/td/a"))
				.click();

		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.accept();
	}

	@When("^그 외 입력란 입력 후 \\[다음\\] 버튼 클릭\\.$")
	public void 그_외_입력란_입력_후_다음_버튼_클릭() {

		// 비밀번호, 비밀번호 확인
		driver.findElement(By.name("seller.password")).sendKeys("qatest");
		driver.findElement(By.name("passwordConfirm")).sendKeys("qatest");

		// 전화번호 입력
		Select drop = new Select(driver.findElement(By
				.name("seller.representativeDomesticTelephoneNumber.head")));
		drop.selectByVisibleText("010");
		driver.findElement(
				By.name("seller.representativeDomesticTelephoneNumber.body"))
				.sendKeys("1111");
		driver.findElement(
				By.name("seller.representativeDomesticTelephoneNumber.tail"))
				.sendKeys("2222");

		// 메인/메일수신여부
		driver.findElement(By.name("seller.emailAddress.username")).sendKeys(
				"nv95m_25");
		Select mail = new Select(driver.findElement(By
				.name("seller.emailAddress.domainNameList")));
		mail.selectByVisibleText("naver.com");

		// 소개글 입력
		driver.findElement(By.name("seller.shop.description")).sendKeys(
				"sw1tcc034");

		// 카테고리 선택
		Select cate = new Select(driver.findElement(By
				.name("seller.shop.categoryId")));
		cate.selectByVisibleText("패션의류");

		// 주소 입력
		WebElement address = driver
				.findElement(By
						.xpath("//*[@id='content']/form/div[1]/div/table/tbody/tr[8]/td/a"));
		address.click();

		String parentWindowIdAdd = driver.getWindowHandle();
		Set<String> allwindowsAdd = driver.getWindowHandles();

		if (!allwindowsAdd.isEmpty()) {
			for (String windowId : allwindowsAdd) {
				driver.switchTo().window(windowId);
				if (driver.getPageSource().contains("도로명주소")) {
					try {
						WebElement add = driver.findElement(By
								.xpath("//*[@id='ipt_lb']"));
						add.sendKeys("가산");
						driver.findElement(
								By.xpath("//*[@id='pop_content']/form/div[1]/a"))
								.click();
						// WebDriverWait wait = new WebDriverWait(driver, 5);
						driver.findElement(
								By.xpath("//*[@id='pop_content']/form/div[2]/ul/li[1]/a/span[1]"))
								.click();
						break;
					} catch (NoSuchWindowException e) {
						e.printStackTrace();
					}
				}
			}
		}
		driver.switchTo().window(parentWindowIdAdd);

		// [다음]버튼 클릭
		driver.findElement(By.xpath("//*[@id='content']/form/div[4]/a"))
				.click();
	}

	@Then("^배송/정산정보 입력 단계로 이동하는지 확인\\.$")
	public void 배송_정산정보_입력_단계로_이동하는지_확인() {
		// 페이지 이동 확인.
		driver.findElement(By.xpath("//*[@id='content']/form/div[2]/h3/span"))
				.click();

		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					"C:\\Users\\NAVER\\workspace\\SellJoinTest\\test2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------------
	// 시나리오 3
	@When("^출고지, 반품지 관리이름을 입력한다\\.$")
	public void 출고지_반품지_관리이름을_입력한다() {
		driver.findElement(By.name("releaseAddressBook.name")).click();
		driver.findElement(By.name("releaseAddressBook.name"))
				.sendKeys("출고지주소");

		driver.findElement(By.name("refundOrExchangeAddressBook.name")).click();
		driver.findElement(By.name("refundOrExchangeAddressBook.name"))
				.sendKeys("반품교환지주소");
	}

	@When("^출고지, 반품지 연락처 (\\d+),(\\d+)를 각각 입력한다\\.$")
	public void 출고지_반품지_연락처_를_각각_입력한다(int arg1, int arg2) {

		// 출고지 연락처
		Select phone1 = new Select(driver.findElement(By
				.name("releaseAddressBook.phoneNumber1.head")));
		phone1.selectByVisibleText("010");
		driver.findElement(By.name("releaseAddressBook.phoneNumber1.body"))
				.sendKeys("3333");
		driver.findElement(By.name("releaseAddressBook.phoneNumber1.tail"))
				.sendKeys("3333");

		Select phone2 = new Select(driver.findElement(By
				.name("releaseAddressBook.phoneNumber2.head")));
		phone2.selectByVisibleText("010");
		driver.findElement(By.name("releaseAddressBook.phoneNumber2.body"))
				.sendKeys("4444");
		driver.findElement(By.name("releaseAddressBook.phoneNumber2.tail"))
				.sendKeys("4444");

		// 반품/교환지 연락처
		Select phone3 = new Select(driver.findElement(By
				.name("refundOrExchangeAddressBook.phoneNumber1.head")));
		phone3.selectByVisibleText("010");
		driver.findElement(
				By.name("refundOrExchangeAddressBook.phoneNumber1.body"))
				.sendKeys("3333");
		driver.findElement(
				By.name("refundOrExchangeAddressBook.phoneNumber1.tail"))
				.sendKeys("3333");

		Select phone4 = new Select(driver.findElement(By
				.name("refundOrExchangeAddressBook.phoneNumber2.head")));
		phone4.selectByVisibleText("010");
		driver.findElement(
				By.name("refundOrExchangeAddressBook.phoneNumber2.body"))
				.sendKeys("4444");
		driver.findElement(
				By.name("refundOrExchangeAddressBook.phoneNumber2.tail"))
				.sendKeys("4444");
	}

	@When("^출고지 주소를 입력한다\\.$")
	public void 출고지_주소를_입력한다() {
		WebElement address = driver
				.findElement(By
						.xpath("//*[@id='content']/form/div[2]/div/table/tbody/tr[1]/td/ul/li[4]/a"));
		address.click();

		String parentWindowId = driver.getWindowHandle();
		Set<String> allwindows = driver.getWindowHandles();

		if (!allwindows.isEmpty()) {
			for (String windowId : allwindows) {
				driver.switchTo().window(windowId);
				if (driver.getPageSource().contains("도로명주소")) {
					try {
						WebElement add = driver.findElement(By
								.xpath("//*[@id='ipt_lb']"));
						add.sendKeys("가산");
						driver.findElement(
								By.xpath("//*[@id='pop_content']/form/div[1]/a"))
								.click();
						// WebDriverWait wait = new WebDriverWait(driver, 5);
						driver.findElement(
								By.xpath("//*[@id='pop_content']/form/div[2]/ul/li[1]/a/span[1]"))
								.click();
						break;
					} catch (NoSuchWindowException e) {
						e.printStackTrace();
					}
				}
			}
		}
		driver.switchTo().window(parentWindowId);
	}

	@When("^반품 주소를 출고지주소와 동일 체크한다\\.$")
	public void 반품_주소를_출고지주소와_동일_체크한다() {
		WebElement checkadd = driver.findElement(By.id("samewithupperaddress"));
		if (!checkadd.isSelected())
			checkadd.click();
	}

	@When("^정산대금 은행을 선택하고 계좌번호를 입력한다\\.$")
	public void 정산대금_은행을_선택하고_계좌번호를_입력한다() {
		Select bank = new Select(driver.findElement(By
				.name("domesticSettlementAccount.bankCode")));
		bank.selectByVisibleText("신한은행");
		driver.findElement(By.name("domesticSettlementAccount.accountNumber"))
				.sendKeys("111111111111");
	}

	@When("^계좌번호 인증 버튼을 클릭한다\\.$")
	public void 계좌번호_인증_버튼을_클릭한다() {
		// 인증우회
		driver.findElement(
				By.xpath("//*[@id='darkAccountOwnerNameAuthRadioOn']")).click();

		driver.findElement(
				By.xpath("//*[@id='content']/form/div[2]/div/table/tbody/tr[4]/td/div/a"))
				.click();
	}

	@Then("^인증완료 얼럿을 확인한다\\.$")
	public void 인증완료_얼럿을_확인한다() throws Exception {
		Thread.sleep(1000);
		
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	@Then("^그_외_입력란_입력_후_다음_버튼_클릭(\\d+)$")
	public void 그_외_입력란_입력_후_다음_버튼_클릭(int arg1) {
		driver.findElement(By.xpath("//*[@id='content']/form/div[4]/a"))
				.click();
	}

	@Then("^해외상품 판매 동의 단계로 이동하는지 확인\\.$")
	public void 해외상품_판매_동의_단계로_이동하는지_확인() {
		driver.findElement(By.xpath("//*[@id='content']/form/div[3]/div[1]/h4"))
				.click();

		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					"C:\\Users\\NAVER\\workspace\\SellJoinTest\\test3.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------------
	// 시나리오 4

	@When("^해외상품 판매 동의 체크한다\\.$")
	public void 해외상품_판매_동의_체크한다() {
		WebElement checkagree = driver.findElement(By.id("chk_choice"));
		if (!checkagree.isSelected())
			checkagree.click();
	}

	@When("^상품배송 유형, 수입형태 select box 선택 한다\\.$")
	public void 상품배송_유형_수입형태_select_box_선택_한다() {

		Select ship = new Select(driver.findElement(By
				.name("seller.productDeliveryType")));
		ship.selectByVisibleText("택배");

		Select import1 = new Select(driver.findElement(By
				.name("seller.importType")));
		import1.selectByVisibleText("정식");
	}

	@When("^\\[다음\\]버튼 클릭한다\\.$")
	public void 다음_버튼_클릭한다() {
		driver.findElement(By.xpath("//*[@id='content']/form/div[4]/a"))
				.click();
	}

	@Then("^가입완료 페이지 확인한다\\.$")
	public void 가입완료_페이지_확인한다() {
		
		WebDriverWait wait = new WebDriverWait(driver, 5);

		driver.findElement(By.xpath("//*[@id='content']/p[1]/span")).click();

		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					"C:\\Users\\NAVER\\workspace\\SellJoinTest\\test4.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
