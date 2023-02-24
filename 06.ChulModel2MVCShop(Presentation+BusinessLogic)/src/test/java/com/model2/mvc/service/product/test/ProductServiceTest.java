package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/context*.xml" })
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
//		product.setProdNo(9999);
		product.setProdName("testProdName");
		product.setPrice(9999);
		product.setManuDate("23-02-15");
		product.setProdDetail("testProdDetail");
		product.setFileName("a.jpg");
//		product.setEmail("");
		
		productService.addProduct(product);
		
		product = productService.getProduct(10018);
		System.out.println("ProductServiceTest.java      testAddProduct "+product);
		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
//		Assert.assertEquals("testUserId", product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals(9999, product.getPrice());
		Assert.assertEquals("23-02-15", product.getManuDate());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("a.jpg", product.getFileName());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> 필요하다면...
//		user.setUserId("testUserId");
//		user.setUserName("testUserName");
//		user.setPassword("testPasswd");
//		user.setSsn("1111112222222");
//		user.setPhone("111-2222-3333");
//		user.setAddr("경기도");
//		user.setEmail("test@test.com");
		
		product = productService.getProduct(10018);

		//==> console 확인
		System.out.println("ProductServiceTest.java         testGetProduct "+product);
		
		//==> API 확인
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals(9999, product.getPrice());
		Assert.assertEquals("23-02-15", product.getManuDate());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("a.jpg", product.getFileName());

//		Assert.assertNotNull(productService.getProduct(10013));
//		Assert.assertNotNull(productService.getProduct(10013));
	}
	
	@Test
	 public void testUpdateProduct() throws Exception{
		 
		 Product product = productService.getProduct(10007);
//		Assert.assertNotNull(product);
//		
//		Assert.assertEquals("testProdName", product.getProdName());
//		Assert.assertEquals(9999, product.getPrice());
//		Assert.assertEquals("23-02-15", product.getManuDate());
//		Assert.assertEquals("testProdDetail", product.getProdDetail());
//		Assert.assertEquals("a.jpg", product.getFileName());


		product.setProdName("삼송센스");
		product.setPrice(8888);
		product.setManuDate("C-02-15");
		product.setProdDetail("change");
		product.setFileName("change.jpg");
		productService.updateProduct(product);
		
		product = productService.getProduct(10007);
		System.out.println("UserServiceTest.java testUpdateUser "+product);
		Assert.assertNotNull(product);
		
		//==> console 확인
		//System.out.println(user);
			
		//==> API 확인
		Assert.assertEquals("삼송센스", product.getProdName());
		Assert.assertEquals(8888, product.getPrice());
		Assert.assertEquals("C-02-15", product.getManuDate());
		Assert.assertEquals("change", product.getProdDetail());
		Assert.assertEquals("change.jpg", product.getFileName());
	 }
	
	 //==>  주석을 풀고 실행하면....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("================testGetProductListAll=======================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println("UserServiceTest.java testGetProductListAll "+totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10014");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	System.out.println("im upper list"+list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("==================testGetProductListByProdNo=====================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println("im below list"+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println("ProductServiceTest.java   totalCount= "+totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("보르도");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	for(int i=0; i<list.size(); i++) {
			System.out.println("testGetProductListByProdName list111=         "+list.get(i));
		}
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	System.out.println("ProductServiceTest.java list "+list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println("ProductServiceTest.java list "+totalCount);
	 	
	 	System.out.println("=================testGetProductListByProdName======================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	System.out.println("testGetProductListByProdName "+map);
	 	
	 	list = (List<Object>)map.get("list");
	 	for(Object obj : list){      //(변수 선언 : 배열또는 배열을 리턴하는 함수)
			System.out.println( (String)obj);
	 	}

//
//	 	for(int i=0; i<list.size(); i++) {
//			System.out.println("testGetProductListByProdName list2222=              "+list.get(i));
//		}
//	 	System.out.println("ProductServiceTest.java list= "+list);
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println("ProductServiceTest.java totalCount= "+totalCount);
	 }	 
}