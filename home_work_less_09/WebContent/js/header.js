$( document ).ready(function() {
     $('.leftmenutrigger').on('click', function(e) {
     $('.side-nav').toggleClass("open");
     e.preventDefault();
    });
});

$("a.product-logout").click(function(){
	$.get("logout", function(data){
		if(data == "Success"){
			window.location = "index.jsp";
		}
	})
});