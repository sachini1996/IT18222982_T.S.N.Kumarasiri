$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateCustomerForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidCIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "CustomersAPI",
		type : type,
		data : $("#formCustomer").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onCustomerSaveComplete(response.responseText, status);
		}
	});
});

function onCustomerSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divCustomersGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidCIDSave").val("");
	$("#formCustomer")[0].reset();
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidCIDSave").val($(this).closest("tr").find('#hidCIDUpdate').val());
	$("#Name").val($(this).closest("tr").find('td:eq(0)').text());
	$("#Address").val($(this).closest("tr").find('td:eq(1)').text());
	$("#CreditCardID").val($(this).closest("tr").find('td:eq(2)').text());
	$("#Country").val($(this).closest("tr").find('td:eq(3)').text());
});
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "CustomersAPI",
		type : "DELETE",
		data : "CID=" + $(this).data("cid"),
		dataType : "text",
		complete : function(response, status) {
			onCustomerDeleteComplete(response.responseText, status);
		}
	});
});

function onCustomerDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divCustomersGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
// CLIENT-MODEL================================================================
function validateCustomerForm() {
	// Name
	if ($("#Name").val().trim() == "") {
		return "Insert Name.";
	}
	// Address
	if ($("#Address").val().trim() == "") {
		return "Insert Address.";
	}
	// CreditCardID-------------------------------
	if ($("#CreditCardID").val().trim() == "") {
		return "Insert CreditCardID.";
	}
	// is numerical value
	// var tmpPrice = $("#itemPrice").val().trim();
	// if (!$.isNumeric(tmpPrice)) {
	// return "Insert a numerical value for Item Price.";
	// }
	// convert to decimal price
	// $("#itemPrice").val(parseFloat(tmpPrice).toFixed(2));
	// DESCRIPTION------------------------
	if ($("#Country").val().trim() == "") {
		return "Insert Country.";
	}
	return true;
}
