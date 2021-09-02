import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Demo {
	
	public static void main(String[]args) {
		
		System.setProperty("webdriver.chrome.driver","drivers//Chromedriver.exe");
		
		WebDriver driver= new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("http://www.amazon.com/");
		
		 List <WebElement> links=driver.findElements(By.tagName("a"));
		
		System.out.println("No of links are " +links.size());
		List<String> urlList =new ArrayList<String>();
		
		for(WebElement e : links) {
			String url=e.getAttribute("href");
			urlList.add(url);
			//checkBrokenLink(url);
		}
		long stTime=System.currentTimeMillis();
		urlList.parallelStream().forEach(e -> checkBrokenLink(e));
		driver.quit();
		long endTime=System.currentTimeMillis();
		System.out.println("total time taken: "+(endTime-stTime));
	}
	
	public static void checkBrokenLink (String linkUrl) {
		
		try {
			URL url=new URL(linkUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(5000);
			httpUrlConnection.connect();
			
			if(httpUrlConnection.getResponseCode()>=400) {
				System.out.println(linkUrl +"---> "+httpUrlConnection.getResponseMessage() +" is a broken link");
				}
			else {
				System.out.println(linkUrl + "--->"+httpUrlConnection.getResponseMessage());
			}
		}
		catch (Exception e) {
	}
	}
}
	
	
	
		
	
