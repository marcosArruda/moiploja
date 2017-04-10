jQuery(document).ready(function($){
	updateCartItemCount();
});

	function updateCartItemCount()
	{
		$.ajax ({ 
	        url: '/cart/items/count', 
	        type: "GET", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){
	            alert("status: "+ status);
	        	$('#cart-item-count').text('('+responseData.responseJSON.count+')');
	        }
	    });
	}

	function addItemToCart(sku)
	{
		$.ajax ({ 
	        url: '/cart/items', 
	        type: "POST", 
	        dataType: "json",
	        contentType: "application/json",
	        data : '{"sku":"'+ sku +'"}"',
	        complete: function(responseData, status, xhttp){
	            alert("status: "+ status);
	        	updateCartItemCount();
	        }
	    }); 
	}

	function updateCartItemQuantity(sku, quantity)
	{
		$.ajax ({ 
	        url: '/cart/items', 
	        type: "PUT", 
	        dataType: "json",
	        contentType: "application/json",
	        data : '{ "product" :{ "sku":"'+ sku +'"},"quantity":"'+quantity+'"}',
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();        	
	        	location.href = '/cart' 
	        }
	    });
	}

	function removeItemFromCart(sku)
	{
		$.ajax ({ 
	        url: '/cart/items/'+sku, 
	        type: "DELETE", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();
	        	location.href = '/cart' 
	        }
	    });
	}

