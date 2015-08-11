<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
	<title>我要爆料</title>
	<link rel="stylesheet" type="text/css" href="CSS/main.css" />
	<script type="text/javascript" src="JS/zepto.min.js"></script>
	<script type="text/javascript" src="JS/config.js"></script>
	<script type="text/javascript" src="JS/common.js"></script>
	<script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";</script>
</head>
<body>
	<article id="page">
		<div  class="textarea">
			<h3>爆料</h3>
			<textarea placeholder="说说这里有什么好玩的亲子活动吧...">
			</textarea>
		</div>
		
		<div class="form bot baoliao">
			<div class="fitem_input">
				<span class="fh">活动时间</span>
				<span class="fd">
					<input	type="text" id="realname" class="realname" value="" style="color: #333;" placeholder="请输入时间">
					</input>
				</span>
			</div>

			<div class="fitem_input">
				<span class="fh">活动地点</span>
				<span class="fd">
					<input	type="text" id="realname" class="realname" value="" style="color: #333;" placeholder="请输入地点">
					</input>
				</span>
			</div>

			<div class="fitem_input">
				<span class="fh">联系方式</span>
				<span class="fd">
					<input	type="tel" id="realname" class="realname" value="" style="color: #333;" placeholder="请输入联系方式">
					</input>
				</span>
			</div>
		</div>

		<div class="login">
				<button id="btn_submit" class="tapable">提交</button>
			</div>
	</article>
</body>
</html>