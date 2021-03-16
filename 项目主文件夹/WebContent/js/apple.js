function create_apple(x,y,i){
		var apple = document.getElementById(i);
		var capple = apple.cloneNode(true);
		$(capple).css("left",(x-10)+"px");
		$(capple).css("top",(y-30)+"px");
		$(capple).css("display","block");
		$("body").append(capple);
		  $(capple).animate({top:"-=50px",opacity:"0"},1);
		setTimeout(function(){
			$(capple).remove();
		},1500);
		
		
	}
	//网页加载完毕后进行脚本
	$(document).ready(function(){
		var i =0;
		$("body").mousedown(function(e) {  
     var clickX=e.pageX-$(this).offset().left; //获取当前鼠标相对img的X坐标  
     var clickY=e.pageY-$(this).offset().top; //获取当前鼠标相对img的Y坐标 
			if(i<9){//随机重置
			i++;
		}else{
			i=1;
		}
	create_apple(clickX,clickY,i);
 });
//document
});