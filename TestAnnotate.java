package smoke;

import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAnnotate {

	@BeforeClass
	public void setup() {
		System.out.println("Once Before the @Test execution");
		//Environment Setup
		
	}
	
	@BeforeMethod
	public void BeforeEveryTest() {
		System.out.println("Before Every Test");
	}
	
	
	@Test(priority=1)
	public void FirstTest() {
		System.out.println("Always the first test case");
	}
	
	@Test(priority=2)
	public void SecondTest() {
		System.out.println("Test Case 2");
	}
	
	@Test(groups="grp1",priority=3)
	public void SecondTest3() {
		System.out.println("Test Case 3");
	}
	
	@Test(groups="grp1",priority=4)
	public void SecondTest4() {
		System.out.println("Test Case 4");
	}
	
	@Test(groups="grp1",priority=5)
	public void SecondTest5() {
		System.out.println("Test Case 5");
		Assert.assertTrue(false);
	}
	
	@Test(dependsOnGroups="grp1",priority=6)
	public void SecondTest6() {
		System.out.println("Test Case 6");
	}
	
	@Test(priority=7)
	public void SecondTest7() {
		System.out.println("Test Case 7");
	}
	
	
	@Test(dependsOnMethods = { "SecondTest6","SecondTest7" },priority=8)
	public void SecondTest8() {
		System.out.println("Test Case 8");
	}
	
	@Test(dependsOnMethods = { "SecondTest7" },priority=9)
	public void SecondTest9() {
		System.out.println("Test Case 9");
	}
	
	@Test(priority=10)
	public void SecondTest10() {
		System.out.println("Test Case 10");
	}
	
	
	@Test(priority=11)
	public void SecondTest11() {
		System.out.println("Test Case 11");
	}
	
	@DataProvider(name="dp1")
	public Object[][] passData() {
		Object[][] a={{3,18},{5,30},{95,110}};
		
		return a;
	}
	
	@Test(priority=12,dataProvider="dp1")
	public void SecondTest12(int input,int expectedoutput) {
		System.out.println("Test Case 12 - start");
		Assert.assertTrue(input+15==expectedoutput);
		
		System.out.println("Test Case 12 - end");
	}
	
	
	@Test(priority=13)
	public void SecondTest13() {
		System.out.println("Test Case 13");
	}
	
	@Test(priority=14)
	public void SecondTest14() {
		System.out.println("Test Case 14");
	}
	
	@Test(priority=15)
	public void SecondTest15() {
		System.out.println("Test Case 15");
	}
	
	@AfterClass
	public void teardown() {
		System.out.println("Once After Test execution");
	}
	
	@AfterMethod
	public void AfterEveryTest() {
		System.out.println("After Every Test");
	}
}
