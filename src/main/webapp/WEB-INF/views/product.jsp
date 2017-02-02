<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Products</title>
</head>
<body>
	<section>
		<div class="pull-right" style="padding-right: 50px">
			<a href="?language=en&id=${product.productId}">English</a> | <a
				href="?language=nl&id=${product.productId}">Dutch</a> | <a
				href="<c:url value="/logout" />">Logout</a>
		</div>
	</section>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Products</h1>
			</div>
		</div>
	</section>
	<section class="container">
		<div class="row">
			<div class="col-md-5">
				<img src="<c:url value="/img/${product.productId}.png"></c:url>"
					alt="${product.productId}.png" style="width: 100%" />
			</div>
			<div class="col-md-5">
				<h3>${product.name}</h3>
				<p>${product.description}</p>
				<p>
					<strong><spring:message
							code="product.form.productItemCode.label" />: </strong><span
						class="label label warning">${product.productId} </span>
				</p>
				<p>
					<strong><spring:message
							code="product.form.productManufacturer.label" />: </strong> :
					${product.manufacturer}
				</p>
				<p>
					<strong><spring:message
							code="product.form.productCategory.label" />: </strong> :
					${product.category}
				</p>
				<p>
					<strong><spring:message
							code="product.form.productAvailableUnitsInStock.label" />: </strong> :
					${product.unitsInStock}
				</p>
				<h4>${product.unitPrice}USD</h4>
				<p>
					<a href="<spring:url value="/market/products" />"
						class="btn btn-default"> <span
						class="glyphicon-hand-left glyphicon"></span> <spring:message
							code="product.form.backButton.label" />
					</a> <a href="#" class="btn btn-warning btn-large"> <span
						class="glyphicon-shopping-cart glyphicon"> </span> <spring:message
							code="product.form.orderNowButton.label" />
					</a>
				</p>
			</div>
		</div>
	</section>
</body>
</html>