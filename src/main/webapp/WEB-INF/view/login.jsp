<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path =  request.getContextPath();
	session.setAttribute("appPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<meta name="force-rendering" contect="WebKit">
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<link rel="stylesheet" href="static/login/css/font-awesome.css" />
<link rel="stylesheet" href="static/login/css/ace-fonts.css" />
<link rel="stylesheet" href="static/login/css/bootstrap.css" />
<link rel="stylesheet" href="static/login/css/ace.css" />
<link rel="stylesheet" href="static/login/css/camera.css" />
<style>
	input:-webkit-autofill,textarea:-webkit-autofill,select:-webkit-autofill,input.textinput_click,input:focus{background-color:#f0f0f0 !important;-webkit-box-shadow: 0 0 0px 1000px #f0f0f0 inset !important;}
	.widget-box{background-color:#F7F7F7 !important;box-shadow:0 0 1px 2px #fff !important;}
	.widget-box img{cursor:pointer}
	#signUp .input-group-addon .ace-icon.fa,#findPWD .input-group-addon .ace-icon.fa{min-width:15px}
	html,body{width:100%;overflow:hidden}
	.widget-box{-moz-box-shadow:0 0 10px 2px #597597 !important; -webkit-box-shadow:0 0 10px 2px #597597 !important;box-shadow: 0 0 10px 2px #597597 !important;}
</style>
</head>
<body ontouchstart class="login-layout">
	<div style="width:100%;auto 0;position:absolute;z-index:7">
		<div class="main-container">
			<div class="main-content">
				<div class="row" style="margin:0">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container" style="margin:10% auto">
							<div class="center">
								<h1>
									<span class="blue">景安</span><span class="red">任务</span><span class="blue">管理系统</span>
								</h1>
							</div>
							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login" class="login-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger"><i class="ace-icon fa fa-coffee green"></i>请输入用户名/密码</h4>
											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="account" type="text" value="hanw" class="form-control" placeholder="请输入用户名/手机/邮箱" title="用户名">
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="pwd" type="password" value="111111" class="form-control" placeholder="请输入密码" title="密码">
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>
													<div class="input-group"  style="display:none">
														<input id="loginCode" type="text" placeholder="请输入图片验证码" title="图片验证码" class="form-control input-mask-product">
														<div class="input-group-addon" style="padding:0;background-color:#fff">
															<img style="height:32px;" id="loginCodeImg" alt="点击更换" title="点击更换"  data-input=".visible #loginCode"/>
														</div>
													</div>
													<div class="space"></div>
													<div class="clearfix">
														<button btn id="logins" onclick="javascript:severCheck()" type="button" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i><span class="bigger-110">登录</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->
									</div><!-- /.widget-body -->
								</div><!-- /.login -->
							</div><!-- /.position-relative -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper" style="z-index:6">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="static/login/images/3.jpg"></div>
			<div data-src="static/login/images/3.jpg"></div>
		</div>
	</div>

	<script src="static/login/js/jquery-1.7.2.js"></script>
	<script src="static/login/js/bootstrap.min.js"></script>
	<script src="static/login/js/jquery.easing.1.3.js"></script>
	<script src="static/login/js/camera.min.js"></script>
	<script src="static/login/js/templatemo_script.js"></script>
	<script src="static/login/js/jquery.tips.js"></script>
	<script src="static/login/js/jquery.cookie.js"></script>
	<script type="text/javascript">
		//服务器校验
		function severCheck(){
			if(loginCheck()){
				$("#logins").tip("正在登录 , 请稍后 ...","#68B500");
				var loginname = $("#account").val();
				var password = $("#pwd").val();
				var code = "yank1"+loginname+",llk,"+password+"yank2,llk,"+$("#loginCode").val();
				$.ajax({
					type: "POST",
					url: 'login',
			    	data: {u:loginname,p:password,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							location.replace("my");
						}else if("usererror" == data.result){
							$("#account").tip("用户名或密码有误");
						}else if("codeerror" == data.result){
							$("#loginCode").tip("验证码输入有误");
						}else loginCheck();
						if("success" != data.result){
							$("#account").focus();
						}
					}
				});
			}
		}
		//登录校验
		function loginCheck() {
			var flag=true;
			$("#login input:not(:checkbox)").each(function(){
				if(!$.trim($(this).val())&&$(this).hasClass("code")){
					flag=false;
					$(this).focus().tip(this.title+"不能为空",null,2);
				}
			});
			if(!flag)showfh();
			return flag;
		}
		
	 $(function(){
			$(document).keyup(function(event) {if (event.keyCode == 13)$(".visible [btn]:visible:last").trigger("click");});
			$.fn.tip=function(m,b,s,t){$(this).tips({msg :m,bg : b||"#AE81FF",side:s||1,time : t||1.5});}//提示
			$(".position-relative").delegate("img","click",function(e){$(e.target).changeCode();});
			function toValid(){
				$(".visible [role]").each(function(k,v){DATA[$(v).attr("role")]=$.trim(v.value);});
				if(DATA.view=="send"&&checkImg()&&checkPhone()&&!$(".visible .timmer").length)toPost();
			}
			
			//登录业务
			var account = $.cookie('account');
			var pwd = $.cookie('pwd');
			if (account&&pwd) {
				$("#account").val(account);
				$("#pwd").val(pwd);
			}
			$("#account").focus();
			$(window).on("hashchange",switchBox);
			switchBox();//切换导航
	}); 
		function switchBox(){
			var hash=location.hash.replace(/#/g,"");
			DATA.flag=hash;
// 			var box=["login","signUp","findPWD"];
			var box=["login","findPWD"];
			if($.inArray(hash,box)==-1)location.hash="login";
			else {
				$(".widget-box.visible").removeClass("visible");
				$("#"+hash).addClass("visible");
				if(errTimes<=3&&hash=="login")return;
				$(".visible img").changeCode();
				if(smss&&smss<60)$("[timmer]").timmer(60-smss);//刷新页面后倒计时;
			}
		}
		var DATA={},loading=false,msg="${model.pd.msg}",smss="${model.pd.smss}",errTimes=eval("${model.pd.errTimes}")||0;
		if(/1/.test(msg))alert("此用户在其它终端已经早于您登录,您暂时无法登录");
		if(/2/.test(msg))alert("您被系统管理员强制下线");
		if(top.frames.length)top.location.replace(top.location.href);
	</script>
</body>
</html>