	//物品需要设置为绝对定位absolute
	function change(x,y){//改变物品位置方法
		var app = document.getElementById("app");
		app.style.left=(x)+"px";
		app.style.top=(y)+"px";
	};
	function bug_move(){//bug移动
		var bug = document.getElementById("bug");
		var app = document.getElementById("app");
		var app_x=app.style.left.replace("px","")*1;
		var app_y=app.style.top.replace("px","")*1;
		var bug_x=bug.style.left.replace("px","")*1;
		var bug_y=bug.style.top.replace("px","")*1;
		console.log("app位置"+app_x+"   "+app_y+"		");
		console.log("bug位置"+bug_x+"   "+bug_y);
		console.log("============");
		bug.style.left=(app_x+15)+"px";
		bug.style.top=(app_y-5)+"px";
	};
	function bug_trun(){//bug转身
		var bug = document.getElementById("bug");
		var app = document.getElementById("app");
		var app_x=app.style.left.replace("px","")*1;
		var app_y=app.style.top.replace("px","")*1;
		var bug_x=bug.style.left.replace("px","")*1;
		var bug_y=bug.style.top.replace("px","")*1;
		if(app_x>bug_x){
		bug.classList.add("right");
			console.log("转身");
		}else if(app_x<bug_x){
			bug.classList.remove("right");
		}
	}
	function app_say(){//app喊话
		var app_text = document.getElementById("app_text");
		if(app_text.textContent==""){
		app_text.innerHTML="Help!";
		setInterval(function(){
			app_text.classList.toggle("flash");
		},1000);
	};};
	function bug_kill(){
		var bug_img = document.getElementById("bug_img");
		bug_img.parentNode.removeChild(bug_img);
	}
	//网页加载完毕后进行脚本
	$(document).ready(function(){//调用流程
		$("#bug_img").click(function(){
			$(this).remove();
			$("#app_text").text("Thanks!");
			});
		$("body").mousedown(function(e) {  
     var positionX=e.pageX-$(this).offset().left; //获取当前鼠标相对img的X坐标  
     var positionY=e.pageY-$(this).offset().top; //获取当前鼠标相对img的Y坐标 
	change(positionX,positionY);
	bug_trun();
	bug_move();
	app_say();
 });
//document
});