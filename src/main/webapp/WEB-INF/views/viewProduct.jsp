<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container-wrapper">
	<div class="container">
		<h2>Product Detail</h2>
		<p>Here is the detail information of the product!</p>
		
		<div class="row">
			<div class="col-md-6">
				<c:set var="imageFilename" value="/resources/images/${product.name}.jpg"/>
				<img src="<c:url value="${imageFilename}" />" alt="image"
					style="width: 80%" />
			</div>

			<div class="col-md-6">
				<h3>${product.name}</h3>
				<p>${product.description}</p>
				<p><span style="font-weight: bold">Manufacturer</span> : ${product.manufacturer}</p>
				<p><span style="font-weight: bold">Category</span> : ${product.category}</p>
				<h3>${product.price}원</h3>
			</div>
		</div>
	</div>
</div>