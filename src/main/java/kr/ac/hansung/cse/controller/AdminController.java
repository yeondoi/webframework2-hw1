package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ProductService productService;
	
	//메소드 레벨의 request mapping. 인자를 적지 않았기 때문에 /admin으로 오는 요청 수행
	@RequestMapping
	public String adminPage() {
		return "admin";
	}
	
	@RequestMapping("/productInventory") // /admin/productInventory
	public String getProducts(Model model) { //controller -> model -> view
		
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);
		
		return "productInventory";	
	}
	
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.GET)
	public String addProduct(Model model) {
		
		Product product = new Product(); //객체 내용을 입력받기 위해 객체 생성하여 model에 저장
		product.setCategory("컴퓨터"); //기본 라디오버튼 선택
		
		model.addAttribute("product", product);
		
		return "addProduct";
	}
	
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.POST)
	public String addProductPost(@Valid Product product, BindingResult result) { //form에서 입력한 내용의 객체가 자동으로 담김. data binding
		
		if(result.hasErrors()) {
			System.out.println("Form data has some errors");
			List<ObjectError> errors=result.getAllErrors();
			
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
			}
			
			return "addProduct"; //에러에 있을 때 다시 입력하는 페이지로 이동
		}
		
		if( !productService.addProduct(product) ) //db에 저장
			System.out.println("Adding product cannot be done");
		
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/productInventory/deleteProduct/{id}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable int id) { //url의 id 값이 인자로
		
		if( !productService.deleteProduct(id) )
			System.out.println("Deleting product cannot be done");
		
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/productInventory/updateProduct/{id}", method=RequestMethod.GET)
	public String updateProduct(@PathVariable int id, Model model) {
		
		Product product = productService.getProductById(id);
		
		model.addAttribute("product", product); //<sf:..> 태그를 사용하여 model로 보낸 객체 정보와 path=”name”같은 필드 정보가 저절로 매핑된다 -> 기존 제품의 정보가 폼에 입력된 채로 확인 가능
		
		return "updateProduct";
	}
	
	@RequestMapping(value="/productInventory/updateProduct", method=RequestMethod.POST)
	public String updateProductPost(@Valid Product product, BindingResult result) { //form에서 입력한 내용의 객체가 자동으로 담김. data binding
		
		//System.out.println(product);
		
		if(result.hasErrors()) {
			System.out.println("Form data has some errors");
			List<ObjectError> errors=result.getAllErrors();
			
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
			}
			
			return "updateProduct"; //에러에 있을 때 다시 입력하는 페이지로 이동
		}
		
		if( !productService.updateProduct(product) )
			System.out.println("Updating product cannot be done");
		
		return "redirect:/admin/productInventory";
	}

}
