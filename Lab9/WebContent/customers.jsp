<%@page import="com.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customers Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/customers.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Customers Service Management</h1>
				<form id="formCustomer" name="formCustomer">
					Customer Name: <input id="Name" name="Name" type="text"
						class="form-control form-control-sm"> <br> Customer Address:
					<input id="Address" name="Address" type="text"
						class="form-control form-control-sm"> <br> Credit Card
					ID: <input id="CreditCardID" name="CreditCardID" type="text"
						class="form-control form-control-sm"> <br> Country:
					 <input id="Country" name="Country" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidCIDSave" name="hidCIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divCustomersGrid">
					<%
						Customer customerObj = new Customer();
						out.print(customerObj.readCustomers());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>