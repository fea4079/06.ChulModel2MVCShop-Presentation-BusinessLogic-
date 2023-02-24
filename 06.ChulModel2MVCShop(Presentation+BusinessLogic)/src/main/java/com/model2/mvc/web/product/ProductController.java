package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;




//==> 상품관리 Controller
@Controller("productController")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println("ProductController.java.ProductController() "+this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	//@Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize']}")
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception {

		System.out.println("ProductController.java /ProductView.do");
		
		return "forward:/product/addProductView.jsp";
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("ProductController.java /addProduct.do");
		//Business Logic
		productService.addProduct(product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct( @RequestParam("prodNo") int prodNo, 
								@RequestParam("menu") String menu,
								Model model ) throws Exception {
		
		System.out.println("ProductController.java /getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
//		String menu = request.getParameter("menu");
		// Model 과 View 연결
		model.addAttribute("product", product);
		System.out.println("ProductController.java /getProduct.do  product "+product);
		//System.out.println("ProductController.java /getProduct.do  menu "+menu);
//		model.addAttribute("menu", menu);
		
//		if(menu.equals("manage")) {
//			return "forward:/getProduct.do?prodNo="+product.getProdNo()+"&menu="+menu;	
//		}else {
//			return "forward:/product/getProduct.jsp";
//		}
		
		if(menu.equals("manage")) {
			return "forward:/updateProductView.do";	
		}else {
			return "forward:/product/getProduct.jsp";
//			return "forward:/getProduct.do?prodNo="+product.getProdNo();
		}
//		return "forward:/product/getProduct.jsp";
//		
//		String viewName = menu.equals("manage")?"forward:/product/updateProductView.jsp":"forward:/product/getProduct.jsp";
//		return viewName;
//		return "forward:/product/getProduct.jsp";
//		return "forward:/getProduct.do?prodNo="+product.getProdNo();
//		return "forward:/getProduct.do?prodNo="+product.getProdNo()+"&menu="+menu;
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , 
									Model model ) throws Exception{

		System.out.println("ProductController.java /updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("product") Product product ,
								 @RequestParam("menu") String menu,
									Model model , HttpSession session) throws Exception{

		System.out.println("ProductController.java /updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		System.out.println("ProductController.java /updateProduct.do 실행됨실행됨 제발 실행됨");
		System.out.println("menu "+menu);
		
//		model.addAttribute("menu", menu);
		
//		String sessionId=((Product)session.getAttribute("product")).getProdNo();
//		if(sessionId.equals(product.getProdNo())){
//			session.setAttribute("product", product);
//		}
//		return "forward:/getProduct.do";
		return "forward:/product/getProduct.jsp";
//		return "forward:/getProduct.do?menu="+menu;
	}
	
		
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search ,
								Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("ProductController.java에 /listProduct.do실행됨");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, search.getPageSize());
//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
//		model.addAttribute("product",product);
//		model.addAttribute("menu", menu);
//		if(menu.equals("manage")) {
//			return "forward:/product/updateProductView.do";	
//		}else {
//			return "forward:/product/getProduct.jsp";
//		}
		return "forward:/product/listProduct.jsp";
	}
}