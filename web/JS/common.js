//通用工具
tq.t = {
  down: {
      ios: 'https://itunes.apple.com/cn/app/pocket/id309601447?mt=8'
  }
  ,  
  getRad: function(d) {
    return d * Math.PI / 180.0;
  }

  //弹框(比如说用户输入错误的提示框，css样式未写)
  ,
  alert: function(str) {
    var s = '<div class="shide">' + '<div class="alert"><img class="cancel" src="image/cancel2.png">' + str + '</div></div>';
    $(document.body).append(s);
  },
  delshide: function() {
    $('.shide').addClass('none');
    setTimeout(function() {
      $('.shide').remove();
    }, 1000);
  },
  cancel: function() {
      $(".cancel").on("click", function() {
        tq.t.delshide();
      })
  }
  //后退
  ,
  back: function() {
    $('.back').on("click", function() {
      history.back(-1);
    });
  }

  ,delCookie: function(){
    tq.t.cookie.del("fee_arr"); //成人儿童一组
    tq.t.cookie.del("participant_idArr"); //出行人id组合
    tq.t.cookie.del("order_adult_num"); //成人总数
    tq.t.cookie.del("order_child_num"); //儿童总数
    tq.t.cookie.del("mobile"); //出行人电话
    tq.t.cookie.del("contacts"); //出行人姓名
  }

  ,encodeUTF8: function(str){
    var temp = "",rs = "";
    for( var i=0 , len = str.length; i < len; i++ ){
      temp = str.charCodeAt(i).toString(16);
      rs  += "\\u"+ new Array(5-temp.length).join("0") + temp;
    }
    return rs;
  }

  ,decodeUTF8: function(str){
      return str.replace(/(\\u)(\w{4}|\w{2})/gi, function($0,$1,$2){
      return String.fromCharCode(parseInt($2,16));
    }); 
  }

  //加载
  ,
  wait: function(msg) {
    var s = '<div class="loading">';
    s += '<div class="ld">';
    s += '<img src="image/loading.gif" />';
    s += '<p>' + (msg || '加载中...') + '</p>';
    s += '</div></div>';
    $(document.body).append(s);
    tq.doing = true;
  }

  //加载ok
  ,
  waitok: function() {
    $('.loading').remove();
    tq.doing = false;
  },
  loading: function(done) {
      if (!done) $('.body').append('<p id="p_loading" class="center"><br /><br />加载中...</p>');
      else $('#p_loading').remove();
  }
  //url取参
  ,
  getQueryString: function(name) {
      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
      var r = window.location.search.substr(1).match(reg);
      if (r != null) return unescape(r[2]);
      return null;
  }
  //post请求入口
  ,
  post: function(url, para, callback) {
    // url = tq.url + url;
    $.post(url, para, function(data) {
      if (data.errno == 0 && !data.status) {
        callback();
      } else {
        alert("erro");
      }
    });
  }

  //get请求入口
  ,
  get: function(url, para, callback) {
    $.get(url, para, function(data) {
      if (data.errno == 0 && !data.status) {
        callback();
      } else {
        alert("erro");
      }
    });
  }

  //加载外部js(方便微信分享，支付等js的引入)
  ,
  loadJs: function(src) {
    var st = document.createElement("script");
    st.src = src;
    document.body.appendChild(st);
  }

  //设置，获取，删除cookie
  ,
  cookie: {
    get: function(key) {
      var arr = new RegExp('\w?' + key + '=(.*?)(;|$)', 'i').exec(document.cookie);
      return arr ? decodeURIComponent(arr[1]) : '';
    },
    set: function(key, val, days) {
      var reg = key + '=' + encodeURIComponent(val);
      if (days) {
        var exp = new Date();
        exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
        reg += "; expires=" + exp.toGMTString();
      }
      reg += '; path=/';
      document.cookie = reg;
    },
    del: function(key) {
      tq.t.cookie.set(key, '', -10);
    }
  }

  //获取位置信息
  ,
  getLocation: function(lat2, lng2) {

      navigator.geolocation.getCurrentPosition(function(res) { //获取地理位置成功
        lng1 = res.coords.longitude;
        lat1 = res.coords.latitude;
        loc = (lat1 + ',' + lng1);

        s = tq.t.getDistance(lat1, lng1, lat2, lng2);
        $(".distance").html(s + "km");

      }, function(res) {
        $(".distance").css("display", "none");
      }, {
        enableHighAcuracy: false,
        timeout: 5000,
        maximumAge: 30000
      });
  }
  //获得时间戳
  ,
  getTimeStamp: function() {
    var timestamp = new Date().getTime();
    var timestampstring = timestamp.toString(); //一定要转换字符串
    oldTimeStamp = timestampstring;
    return timestampstring.substring(0, 10);
  },
  getDistance: function(lat1, lng1, lat2, lng2) {

    var radLat1 = tq.t.getRad(lat1);
    var radLat2 = tq.t.getRad(lat2);

    var a = radLat1 - radLat2;
    var b = tq.t.getRad(lng1) - tq.t.getRad(lng2);
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * tq.R;
    s = Math.round(s * 10000) / 10000.0;
    s = parseInt(s);
    return s;
  }
  ,
  getScrollImg: function() {
      var slider = Swipe(document.getElementById('scroll_img'), {
        auto: 3000,
        continuous: true,
        callback: function(pos) {
          var i = bullets.length;
          while (i--) {
            bullets[i].className = ' ';
          }
          bullets[pos].className = 'on';
        }
      });
      var bullets = document.getElementById('scroll_position').getElementsByTagName('li');
  }
  ,
  confirm: function (msg, func_cancel, func_ok) {
      var s = "<div class='shide'>";
      s += "<div class='alert'>";
      s += "<img class='cancel' src='image/cancel2.png'>"+msg+"" ; 
      s += "<span class= 'confirm'>"
      s += "<i class='green cancel2'>取消</i>";
      s += "<i class='green del2'>确定</i>";
      s += "</spn></div></div>";
      $(document.body).append(s);
      $(".cancel2").on("click", function(){
        tq.t.delshide();
        func_cancel();
      })
      $(".del2").on("click", function(){
        tq.t.delshide();
        func_ok();
      })
  }
  //验证身份证
  ,
  valiID: function(number) {
      return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(number);
  }
  //验证电话号码
  ,valPho: function (s) {
      return /^1\d{10}$/.test(s);
  }
  //验证护照
  ,
  valiPass: function(number) {
    return /(P\d{7})|G\d{8}/.test(number);
  }
  //删除sessionStorage
  ,delSession: function(){
    sessionStorage.removeItem("mobile_mdy");
    sessionStorage.removeItem("contacts_mdy");
    sessionStorage.removeItem("mobile");
    sessionStorage.removeItem("contacts");
    sessionStorage.removeItem("participant_arr");
    sessionStorage.removeItem("fee_arr");
    sessionStorage.removeItem("click_index");
    sessionStorage.removeItem("adultSum");
    sessionStorage.removeItem("childSum");
    sessionStorage.removeItem("participant_idArr");
  }

  // 判断是否在可视区域
  ,
  isView: function(obj){
    var a = $(obj).offsetTop;
    console.log(a);
    if (a >= $(window).scrollTop() && a < ($(window).scrollTop()+$(window).height())) {
        // alert("div在可视范围");
        // console.log("在可视区域")
        return true;
    }else{
      return false;
    }
  }
  // 判断设备
  , 
  isandroid: function () {
      return navigator.userAgent.toLowerCase().indexOf('android') != -1;

  }
  , isios: function () {
      var ua = navigator.userAgent.toLowerCase();
      return ua.indexOf('iphone') != -1 || ua.indexOf('ipad') != -1;
  }
  , isweixin: function () {
      return navigator.userAgent.toLowerCase().indexOf('micromessenger') != -1;
  }
}

//各个页面的js

tq.home = {
  //通过短信验证码登录
  login: function(odiv, phonetext, codetext, submit) {
      var InterValObj; //timer变量，控制时间
      var count = 60; //间隔函数，1秒执行
      var curCount; //当前剩余秒数
      var param = window.location.search;
      var url = param.substring(1, param.length);

      //获取验证码
      odiv.on('click', function() {
        sendMessage();
      });

      //点击使用密码登陆
      $(".loginWay").on("click", function(){
        location.href = "loginpsw.html?"+url+"";
      })

      //点击注册
      $(".add").on("click",function(){
        event.preventDefault();
        location.href = "registerpsw.html?"+url+"";
      })
      //登录
      submit.on("click", function() {
        var api = tq.url + "auth/login/code";
        var phone = phonetext.val(); //手机号码
        var code = codetext.val();
        if(!phone || phone == ""){
          tq.t.alert("请输入手机号");
          tq.t.cancel();
        }else if(!tq.t.valPho(phone)){
          tq.t.alert("手机号输入不正确");
          tq.t.cancel();
        }else if(!code || code == ""){
          tq.t.alert("请输入验证码");
          tq.t.cancel();
        }else{
          $.post(api, {
            mobile: phone,
            code: code
          }, function(res) {
            if (res.errno == 0) {
              tq.t.cookie.set("utoken", res.data.token, 20);
              location.href = url;
            } else {
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }

          }) // post_end
        } //else_end
        
      }) //submit_end

      function sendMessage() {
        var api = tq.url + "auth/send";
        curCount = count;
        var phone = phonetext.val(); //手机号码

        $.post(api, {
          mobile: phone,
          type: "login"
        }, function(res) {
          if (res.errno == 0) {
            suceess();
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        });
      }

      function SetRemainTime() {
        if (curCount == 1) {
          window.clearInterval(InterValObj); //停止计时器
          odiv.removeAttr("disabled"); //启用按钮
          odiv.removeClass("colddown");
          odiv.html("重新发送");
        } else {
          odiv.html(curCount + "秒后重试");
          tq.t.waitok();
          curCount--;
        }
      }

      function suceess() {
        odiv.attr("disabled", "true");
        tq.t.wait();
        odiv.val(curCount + "秒后重试");
        odiv.addClass("colddown");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
      }
  }
  
  //通过密码登录
  ,loginPsw: function(phonetext, pswtext, submit){
      var param = window.location.search;
      var url = param.substring(1, param.length);

      //点击注册
      $(".add").on("click",function(){
        event.preventDefault();
        location.href = "registerpsw.html?"+url+"";
      })

      //忘记密码
      $(".forgetpsw").on("click",function(){
        location.href = "resetpsw.html?"+url+"";
      })

      $(".loginmes").on("click", function(){
        location.href = "login.html?"+url+"";
      })
      //登录
      submit.on("click", function() {
        var api = tq.url + "auth/login";
        var phone = phonetext.val(); //手机号码
        var password = pswtext.val(); //密码
        if(!phone || phone == ""){
          tq.t.alert("请输入手机号");
          tq.t.cancel();
        }else if(!tq.t.valPho(phone)){
          tq.t.alert("手机号输入不正确");
          tq.t.cancel();
        }else if(!password || password == ""){
          tq.t.alert("请输入密码");
          tq.t.cancel();
        }else if(!tq.t.valPho(phone)){
          tq.t.alert("手机号输入不正确");
          tq.t.cancel();
        }else{
          $.post(api, {
            mobile: phone,
            password: password
          }, function(res) {
            if (res.errno == 0) {
              tq.t.cookie.set("utoken", res.data.token, 20);
              location.href = url;
            } else {
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }

          }) // post_end
        } //else_end
        
      }) //submit_end
  }
  //设置密码的注册
  ,registerPsw: function(odiv, nicktext, phonetext, codetext, pswtex, submit){
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount; //当前剩余秒数
    var param = window.location.search;
    var url = param.substring(1, param.length);

    //获取验证码
    odiv.on('click', function() {
      sendMessage();
    });

    //点击登录
    $(".add .add").on("click",function(){
      event.preventDefault();
      location.href = "loginpsw.html?"+url+"";
    })

    //注册
    submit.on("click", function() {
      var api = tq.url + "auth/register";
      var nickName = nicktext.val(); //昵称
      var phone = phonetext.val(); //手机号码
      var code = codetext.val(); //验证码
      var password = pswtex.val(); //密码

      if(!phone || phone == ""){
        tq.t.alert("请输入手机号");
        tq.t.cancel();
      }else if(!tq.t.valPho(phone)){
        tq.t.alert("手机号输入不正确");
        tq.t.cancel();
      }else if(!code || code == ""){
        tq.t.alert("请输入验证码");
        tq.t.cancel();
      }else if(!nickName || nickName == ""){
        tq.t.alert("请输入昵称");
        tq.t.cancel();
      }else if(!password || password == ""){
        tq.t.alert("请设置密码");
        tq.t.cancel();
      }else{
        $.post(api, {
          nickname: nickName,
          mobile: phone,
          code: code,
          password: password
        }, function(res) {
          if (res.errno == 0) {
            tq.t.cookie.set("utoken", res.data.token);
            location.href = "profileInfo.html";
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        }); //post_end;
      } //else_end;      
    }) //submit_end

    function sendMessage() {
      var api = tq.url + "auth/send";
      curCount = count;
      var phone = phonetext.val(); //手机号码

      $.post(api, {
        mobile: phone,
        type: "register"
      }, function(res) {
        if (res.errno == 0) {
          suceess();
        } else {
          tq.t.alert(res.errmsg);
          tq.t.cancel();
        }
      });
    }

    function SetRemainTime() {
      if (curCount == 1) {
        window.clearInterval(InterValObj); //停止计时器
        odiv.removeAttr("disabled"); //启用按钮
        odiv.removeClass("colddown");
        odiv.html("重新发送");
      } else {
        odiv.html(curCount + "秒后重试");
        tq.t.waitok();
        curCount--;
      }
    }

    function suceess() {
      odiv.attr("disabled", "true");
      tq.t.wait();
      odiv.val(curCount + "秒后重试");
      odiv.addClass("colddown");
      InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    }
  }
  //重设置密码
  ,resetPsw: function(odiv, phonetext, codetext, pswtex, submit){

    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount; //当前剩余秒数
    var param = window.location.search;
    var url = param.substring(1, param.length);

    //获取验证码
    odiv.on('click', function() {
      sendMessage();
    });

    //注册
    submit.on("click", function() {
      var api = tq.url + "auth/password";
      var phone = phonetext.val(); //手机号码
      var code = codetext.val(); //验证码
      var password = pswtex.val(); //密码

      if(!phone || phone == ""){
        tq.t.alert("请输入手机号");
        tq.t.cancel();
      }else if(!tq.t.valPho(phone)){
        tq.t.alert("手机号输入不正确");
        tq.t.cancel();
      }else if(!code || code == ""){
        tq.t.alert("请输入验证码");
        tq.t.cancel();
      }else if(!password || password == ""){
        tq.t.alert("请设置密码");
        tq.t.cancel();
      }else{
        $.post(api, {
          mobile: phone,
          code: code,
          password: password
        }, function(res) {
          if (res.errno == 0) {
            tq.t.cookie.set("utoken", res.data.token);
            location.href = "aloginpsw.html?"+url+"";
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        }); //post_end;
      } //else_end;      
    }) //submit_end

    function sendMessage() {
      var api = tq.url + "auth/send";
      curCount = count;
      var phone = phonetext.val(); //手机号码

      $.post(api, {
        mobile: phone,
        type: "register"
      }, function(res) {
        if (res.errno == 0) {
          suceess();
        } else {
          tq.t.alert(res.errmsg);
        }
      });
    }

    function SetRemainTime() {
      if (curCount == 1) {
        window.clearInterval(InterValObj); //停止计时器
        odiv.removeAttr("disabled"); //启用按钮
        odiv.removeClass("colddown");
        odiv.html("重新发送");
      } else {
        odiv.html(curCount + "秒后重试");
        tq.t.waitok();
        curCount--;
      }
    }

    function suceess() {
      odiv.attr("disabled", "true");
      tq.t.wait();
      odiv.val(curCount + "秒后重试");
      odiv.addClass("colddown");
      InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    }
  }
  //获取活动列表数据
  ,
  getActsList: function(wrap,pageindex) {
    api = tq.url + "home";
    $.get(api, {pageindex:pageindex,city:1}, function(data) {
      if(data.errno == 0){
        var data = data.data.products;
        for (var i = 0; i < data.length; i++) {
          var id = data[i].id;
          if(i == data.length-1){
            var s = '<div class="act_list last_act">';
          }else{
            var s = '<div class="act_list">';
          }
          s += "<a href=actsDetail.html?id=" + id + ">";
          s += '<img src=' + data[i].cover + ' />'; //获取图片
          s += '<div class="act_detail">';
          s += '<h3>' + data[i].title + '</h3>'; //获取title
          s += '<p class="address">' + data[i].scheduler + ''; //获取时间
          s += '<div class="act_attend">';
          s += '<span class="act_time">' + data[i].address + '</span>'; //获取地址
          s += '<span class="act_price"><i class="rmb">￥</i><i>' + data[i].price + '</i><i class="rmb">起</i></span>'; //获取报名费用
          s += "</div></div></a></div>";
          $(wrap).append(s);
        }
      }else{
        tq.t.alert(data.errmsg);
        tq.t.cancel();
      }
    });
  }

  //获取首页轮播图片数据
  ,
  getIndexScrollImg: function(wrap) {
    api = tq.url + "home?pageindex=0&city=1";
    $.ajax({
      type: "get",
      url: api,
      async: false,
      success: function(data) {
        if(data.errno == 0){
          var data = data.data.banners;
          if (data.length == 0) {
            $(wrap).css("display", "none");
          } else if (data.length == 1) {
            for (var i = 0; i < data.length; i++) {
              var s = "<a href='" + data[i].action + "' style='display:block'><img src='" + data[i].cover + "' width='100%' /></a>";
            }
            $(wrap).append(s);
          } else {
            var ul1 = "<ul class = 'scroll_wrap'>";
            for (var i = 0; i < data.length; i++) {
              ul1 += "<li><a href='" + data[i].action + "'><img src='" + data[i].cover + "' width='100%' /></a></li>";
            }
            ul1 += "</ul>";

            var ul2 = "<ul class='scroll_position' id='scroll_position'>";
            for (var j = 0; j < data.length; j++) {
              if (j == 0) {
                ul2 += "<li class='on'><a href='javascript:void(0);'</a></li>";
              } else {
                ul2 += "<li><a href='javascript:void(0);'</a></li>";
              }
            }
            ul2 += "</ul>";
            $(wrap).append(ul1).append(ul2);
            wrap_li = $(".scroll_position");
            tq.t.getScrollImg();
          }
        }else{
          tq.t.alert(data.errmsg);
          tq.t.cancel();
        }
      }
    });
  }

  // 收藏活动
  ,
  collect: function(){
    var id = tq.t.getQueryString("id");
    var utoken = tq.t.cookie.get("utoken");
    if (!utoken || utoken == "" || utoken == null) {
      location.href = "registerpsw.html?actsDetail.html?id=" + id + "";
    } else {
      var api = tq.url + "product/favor";
      $.post(api, {utoken:utoken,id:id}, function(res){
        if(res.errno == 0){
          $(".collect img").attr("src", "image/collect_check2x.png");
        }else{
          tq.t.alert(res.errmsg);
          tq.t.cancel();
        }
      });
    }
  }

  //uncollect
  ,
  uncollect: function(){
    var id = tq.t.getQueryString("id");
    var utoken = tq.t.cookie.get("utoken");
    if (!utoken || utoken == "" || utoken == null) {
      location.href = "registerpsw.html?actsDetail.html?id=" + id + "";
    } else {
      var api = tq.url + "product/unfavor";
      $.post(api, {utoken:utoken,id:id}, function(res){
        if(res.errno == 0){
          $(".collect img").attr("src", "image/collect2.png");
        }else{
          tq.t.alert(res.errmsg);
          tq.t.cancel();
        }
      });
    }
  }

  // 获取个人收藏
  ,
  getCollect: function(){
    var utoken = tq.t.cookie.get("utoken");
    var api = tq.url + "user/favorite?utoken="+utoken+"&start=0";
    $.get(api,{},function(res){
      if(res.errno == 0){
        var pro_id = [];
        // console.log(res);
        var data = res.data.list;
        for(var i=0; i<data.length; i++){
          pro_id.push(data[i].id);
          var s = '<div class="collect_pad">';
          s += '<img src="'+data[i].cover+'" alt="">';
          s += '<div class="collect_main">';
          s += '<div class="collect_info">';
          s += '<p class="title">'+data[i].title+'</p>';
          s += '<p class="schedule">'+data[i].scheduler+'</p>';
          s += '<p class="ad_pr" style="margin-bottom:0">';
          s += '<span class="address">'+data[i].address+'</span>';
          s += '<span class="price">'+data[i].price+'<i>起</i></span>';
          s += '</p>';
          s += '</div></div></div>';
          $("section").append(s);
        }
      }else{
        tq.t.alert(res.errmsg);
        tq.t.cancel();
      }

      //查看当前活动详情
      $(".collect_pad").on("click", function() {
        var index = $(".collect_pad").index(this);
        var api = tq.url + "product";
        var id = pro_id[index];
        $.get(api, {
          id: id
        }, function(res) {
          if (res.errno == 0) {
            location.href = "actsDetail.html?id=" + id + "";
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        })
      });
    });
  }
  //获取sku信息
  ,
  getSKU: function() {
    var utoken = tq.t.cookie.get("utoken");
    var id = tq.t.getQueryString("id");
    var mobile_mdy = sessionStorage.getItem("mobile_mdy");
    var contacts_mdy = sessionStorage.getItem("contacts_mdy");
    var prices = sessionStorage.getItem("fee_arr");
    var click_index = sessionStorage.getItem("click_index");

    api = tq.url + "product/order?id=" + id + "&utoken=" + utoken + "";
    $.get(api, {}, function(res) {
      if (res.errno == 0) {
        var data = res.data;
        var arr_stock = [];
        var mobile = data.contacts.mobile;
        var contacts = data.contacts.name;

        sessionStorage.setItem("mobile",mobile);
        sessionStorage.setItem("contacts",contacts);
        for (var i = 0; i < data.skus.length; i++) {
          arr_stock.push(data.skus[i].stock);
          if(data.skus.length == 1){
            var s = "<div class='form01 flag' id='last'>";
          }
          else{
            if (i == 0) {
              var s = "<div class='form01 flag'>";
            } 
            else if (i == data.skus.length - 1) {
              var s = "<div class='form01' id='last'>";
            }
            else {
              var s = "<div class='form01'>";
            }
          }
          s += "<div class='pad_bot left'>";
          if(data.skus[i].limit <= 0){
            s += "<p class='time'>" + data.skus[i].time + "</p>";
          }else{
            s += "<p class='time'>" + data.skus[i].time + "(每人限购"+data.skus[i].limit+"件)</p>";
          }
          s += "<span class='price'><i class='orange'>￥" + data.skus[i].minPrice + "</i>起</span>";
          if(data.skus[i].stock == 0){
            s += "<span class='num'>名额已满</span>";
          }else if(data.skus[i].type == 1){
            s += "<span class='num'></span>";
          }
          else{
            s += "<span class='num'>仅剩" + data.skus[i].stock + "个名额</span>";
          }
          s += "</div>";
          if (i == 0) {
            s += "<div class='chk right'></div>";
          } else {
            s += "<div class='chk right none'></div>";
          }
          s += "<div style='clear:both'></div>";
          s += "</div>";
          $("#chk_time").append(s);

          var data1 = data.skus[i].prices;
          if (i == 0) {
            var m = "<div class='order_detail fee flag1' id='chk_fee'>";
          } else {
            m = "<div class='order_detail fee none' id='chk_fee'>";
          }
          for (var j = 0; j < data1.length; j++) {
            if (j == data1.length - 1) {
              m += "<div class='form01 fee' id='last'>";
            } else {
              m += "<div class='form01 fee'>";
            }
            m += "<div class='pad_bot left'>";
            if (data1[j].adult != 0 && data1[j].child != 0) {
              if(data1[j].desc){
                m += "<p class='time child_adlut' id="+data1[j].adult+"_"+data1[j].child+">" + data1[j].adult + "成人" + data1[j].child + "儿童("+data1[j].desc+")：￥<i class='orange fee1'>" + data1[j].price + "</i>/" + data1[j].unit + "</p>";
                m += "</div>";
                m += "<div class='chk_num right ac'>";
              }else{
                m += "<p class='time child_adlut' id="+data1[j].adult+"_"+data1[j].child+">" + data1[j].adult + "成人" + data1[j].child + "儿童：￥<i class='orange fee1'>" + data1[j].price + "</i>/" + data1[j].unit + "</p>";
                m += "</div>";
                m += "<div class='chk_num right ac'>";
              }
              
            } else if (data1[j].adult == 0) {
              if(data1[j].desc){
                m += "<p class='time child' id="+data1[j].child+"c>" + data1[j].child + "儿童("+data1[j].desc+")：￥<i class='orange fee1'>" + data1[j].price + "</i>/" + data1[j].unit + "</p>";
                m += "</div>";
                m += "<div class='chk_num right c'>";
              }else{
                m += "<p class='time child' id="+data1[j].child+"c>" + data1[j].child + "儿童：￥<i class='orange fee1'>" + data1[j].price + "</i>/" + data1[j].unit + "</p>";
                m += "</div>";
                m += "<div class='chk_num right c'>";
              }
              
            } else if (data1[j].child == 0) {
              if(data1[j].desc){
                m += "<p class='time adult' id="+data1[j].adult+"a>" + data1[j].adult + "成人("+data1[j].desc+")：￥<i class='orange fee1'>" + data1[j].price + "</i>/" + data1[j].unit + "</p>";
                m += "</div>";
                m += "<div class='chk_num right a'>";
              }else{
                m += "<p class='time adult' id="+data1[j].adult+"a>" + data1[j].adult + "成人：￥<i class='orange fee1'>" + data1[j].price + "</i>/" + data1[j].unit + "</p>";
                m += "</div>";
                m += "<div class='chk_num right a'>";
              }
            }
            m += "<span class='minus gray' style='font-size:0.15rem'>-</span>";
            m += "<span class='num01'>0</span>";
            m += "<span class='plus green' style='font-size:0.15rem'>+</span>";
            m += "</div>";
            m += "<div style='clear:both'></div>";
            m += "</div>";
          }
          m += "</div>";
          if(data.skus[0].needRealName == false){
            $("#chk_name").addClass("none");
          }
          $("section .chk_fee").append(m);
        }
        if (data.skus.length > 3) {
          var k = "<div class='form02 last'>";
          k += "<p class='chk_time r'>选择其他场次</p>";
          k += "</div>";
          $("#chk_time").append(k);
        }

        var form01 = $(".time .form01");
        var fee = $(".chk_fee .order_detail");

        //判断当前是否有click_index,若有，则把form01[index]置为勾选
        if(click_index != null){
          $(".form02").addClass("none");
          $(form01[i]).removeClass("none");
          $(form01).removeClass("flag");
          $(form01[click_index]).addClass("flag");
          $(fee).addClass("none").removeClass("flag1");  //将所有价格面板不显示
          $(fee[click_index]).removeClass("none").addClass("flag1"); //显示当前场次的面板
          $(".form01 .chk").addClass("none"); // 将所有场次绿色勾隐藏
          $(form01[click_index]).find(".chk").removeClass("none"); //显示当前场次的绿色勾
          var num_arr = $(".flag1 .fee").find(".num01");
          var fee1_arr = $(".flag1 .fee").find(".time");
          var minus_arr = $(".flag1 .fee").find(".minus");
          var single_price = $(fee[click_index]).find(".fee1");

          if(data.skus[click_index].needRealName == false){
            $("#chk_name").addClass("none");
          }

          if(prices != null){
            prices = JSON.parse(prices);
            for(var i=0 ; i<prices.length; i++){
              for(var j=0; j<num_arr.length; j++){
                if($(fee1_arr[j]).attr("id") == prices[i].prices_id && ($(single_price[j]).html() == prices[i].price)){
                  $(num_arr[j]).html(prices[i].count);
                  $(minus_arr[i]).removeClass("gray").addClass("green");
                }
              }
            }
          }

          $(".chk_sub .total_price i").html(sessionStorage.getItem("total_price"));
        }
        else{
          //只选择三个场次
          for (var i = 3; i < form01.length; i++) {
            $(form01[i]).addClass("none");
          }
        }

        //打开选择场次按钮
        $(".form02 .chk_time").on("click", function() {
          $(form01).removeClass("none");
          $(".form02 .chk_time").css("display", "none");
        });

        //点击场次信息更换价格面板
        $(form01).on("click", function() {
          $(form01).removeClass("flag");
          $(this).addClass("flag");
          $(".num01").html("0");
          $(".minus").removeClass("green").addClass("gray");
          var index = $(form01).index(this);
          if(data.skus[index].needRealName == false){
            $("#chk_name").addClass("none");
          }
          if(data.skus[index].needRealName == true){
            $("#chk_name").removeClass("none");
          }
          $(fee).addClass("none").removeClass("flag1");
          $(".form01 .chk").addClass("none");
          $(fee[index]).removeClass("none").addClass("flag1");
          $(this).find(".chk").removeClass("none");
          tq.t.delSession();
          $(".chk_sub .total_price i").html("0");
          $(".show_detail .chk_info .chk_name i").html("请选择出行人");
        });
        
        //选择出行人
        $(".chk_name").on("click", function(){
          event.preventDefault();
          tq.home.saveCurrent();
          var adultSum = sessionStorage.getItem("adultSum");
          var childSum = sessionStorage.getItem("childSum");

          //若成人和儿童的数量均为0，提示选择出行人数
          if (adultSum == 0 && childSum == 0) {
            tq.t.alert("报名人数不能为0");
            tq.t.cancel();
          } else {
            location.href = "choose_Outer.html?id="+id+"";
          }

        });

        //计算总价
        var total_price = 0;
        function totalMoney() {
          total_price = 0;
          var num_arr = $(".flag1 .fee").find(".num01");
          var fee1_arr = $(".flag1 .fee").find(".fee1");
          for(var i=0; i<num_arr.length; i++){
            var price = parseFloat($(fee1_arr[i]).html());
            var num = parseInt($(num_arr[i]).html());
            total_price += price * num;
          }
          $(".chk_sub .total_price i").html(total_price.toFixed(2));
        }
          
        //加号
        var num = 0;
        $(".chk_num .plus").on("click", function() {
          var input = $(this).siblings(".num01");
          var index = $(".time .form01").index($(".flag"));
          var num_arr = $(".flag1 .fee").find(".num01");
          num = 0;
          for(var i=0; i<num_arr.length; i++){
            num += parseInt($(num_arr[i]).html());
              if(num >= arr_stock[index]){ //这是大于库存的时候
                tq.t.alert("名额已满");
                tq.t.cancel();
                return;
            }
          }
          input.html(parseInt(input.html()) + 1);
          //当前数目大于0时候减号可以点击
          if (parseInt(input.html()) > 0) {
            $(this).siblings(".minus").removeClass("gray").addClass("green");
          }
          totalMoney();
        });

        //减号
        $(".chk_num .minus").on("click", function() {
          var index = $(".time .form01").index($(".flag"));
          var input = $(this).siblings(".num01");
          input.html(parseInt(input.html()) - 1);
          //小于0时不能再减
          if (parseInt(input.html()) <= 0) {
            $(this).removeClass("green").addClass("gray");
            $(this).siblings(".num01").html("0");
          } else {
            $(this).removeClass("gray").addClass("green");
          }

          //当前数目大于0时候减号可以点击
          if (parseInt(input.html()) < arr_stock[index]) {
            $(this).siblings(".plus").removeClass("gray").addClass("green");

          }
          totalMoney();
        });

        //提交订单
        $("#btn_submit").on("click", function() {
          event.preventDefault();
          tq.home.saveCurrent();
          var adultSum = parseInt(sessionStorage.getItem("adultSum"));
          var childSum = parseInt(sessionStorage.getItem("childSum"));
          var total_price = $(".chk_sub .total_price i").html();
          var api = tq.urls + "order";
          var utoken = tq.t.cookie.get("utoken");
          var index = $(".time .form01").index($(".flag"));
          var click_index = sessionStorage.getItem("click_index");
          if(click_index != null){
            var sku_id = data.skus[click_index].skuId; //sku_id
            var pro_id = data.skus[click_index].productId; //product_id;
            var time = data.skus[click_index].time;
            var limit = data.skus[click_index].limit;
            var needRealName = data.skus[click_index].needRealName;
          }
          var p_arr = [];
          if(sessionStorage.getItem("participant_idArr") != null){
            var participant_arr = sessionStorage.getItem("participant_idArr").split(",");
            for (var i = 0; i < participant_arr.length; i++) {
              p_arr.push(parseInt(participant_arr[i]));
            }
          }
          
          if(mobile_mdy != null ){
            mobile = mobile_mdy;
          }
          if(contacts_mdy != null){
            contacts = contacts_mdy;
          }   

          if(sessionStorage.getItem("fee_arr") != null){
            var count_sum = 0;
            var prices = JSON.parse(sessionStorage.getItem("fee_arr"));
            for(var i=0; i<prices.length; i++){
              count_sum += prices[i].count;
            }
          }

          if(total_price != null && total_price != "" && prices != null && prices.length != 0){
            //如果不限购
            if(limit <=0 ){
               //如果没有出行人
              if(needRealName == false){
                post_order();
              }
              //如果有出行人
              if(needRealName == true){
                if(p_arr && p_arr.length != 0 && p_arr != "" && p_arr != null){
                  if((childSum + adultSum) != p_arr.length){
                    tq.t.alert("出行人不匹配，重新选择出行人");
                    tq.t.cancel();
                  }else{
                    post_order();
                  }
                }else{
                  tq.t.alert("请选择出行人");
                  tq.t.cancel();
                }
              }
              //如果限购
            }else{
              if(needRealName == false){
                if(count_sum == limit){
                  post_order();
                }else{
                  tq.t.alert("每人限购"+limit+"件");
                  tq.t.cancel();
                }
              }
              if(needRealName == true){
                if(p_arr && p_arr.length != 0 && p_arr != "" && p_arr != null){
                  if((childSum + adultSum) != p_arr.length){
                    tq.t.alert("出行人不匹配，重新选择");
                    tq.t.cancel();
                  }else{
                    if(count_sum == limit){
                      post_order();
                    }else{
                      tq.t.alert("每人限购"+limit+"件");
                      tq.t.cancel();
                    }
                  }
                }else{
                  tq.t.alert("请选择出行人");
                  tq.t.cancel();
                }
              }
            }
          } else{
            tq.t.alert("报名人数不能为空");
            tq.t.cancel();
          }

          function post_order(){
            var data_json = {
              "productId": pro_id,
              "skuId": sku_id,
              "contacts": contacts,
              "mobile": mobile,
              "participants": p_arr,
              "prices": prices
            }
            var data_order = JSON.stringify(data_json);
            $.post(api, {
              utoken: utoken,
              order: data_order
            }, function(res) {
              if (res.errno == 0) {
                var data = res.data;
                var order_id = data.id; //订单id;
                var pro_id = data.productId; //活动id;
                var sku_id = data.skuId; //sku_id;
                location.href = "orderPay.html?order_id=" + order_id + "&pro_id=" + pro_id + "&sku_id=" + sku_id + "&participants="+tq.t.encodeUTF8(res.data.participants)+"&totalFee="+res.data.totalFee+"&time="+tq.t.encodeUTF8(time)+""; 
                tq.t.delSession();
              } else {
                tq.t.alert(res.errmsg);
                tq.t.cancel();
              }
            });
          }
            
        });

        if((sessionStorage.getItem("adultSum") == 0 && sessionStorage.getItem("childSum") == 0) || (sessionStorage.getItem("adultSum") == null && sessionStorage.getItem("childSum") == null) || (sessionStorage.getItem("adultSum") == "" && sessionStorage.getItem("childSum") == "")){
          $(".show_detail .chk_info .chk_name i").html("请选择出行人");
        }else{
          $(".show_detail .chk_info .chk_name i").html(sessionStorage.getItem("adultSum") + "成人" + sessionStorage.getItem("childSum") + "儿童");
        }
        //显示联系人信息
        if(mobile_mdy != null && mobile_mdy.length != 0 && mobile_mdy != ""){
          $(".show_detail .chk_info .chk_phone i").html(mobile_mdy.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
        }else{
          $(".show_detail .chk_info .chk_phone i").html(sessionStorage.getItem("mobile").replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
        }
        //编辑联系人
        $(".chk_phone").on("click", function() {
          event.preventDefault();
          tq.home.saveCurrent();
          location.href = "outer_info.html?id=" + id + "";
        });
      }else{
        tq.t.alert(res.errmsg);
        tq.t.cancel();
      }
    });
  }
  ,
  saveCurrent: function(){
    var form01 = $(".time .form01");
    var fee = $(".chk_fee .order_detail");
    var index = $(".time .form01").index($(".flag"));
    sessionStorage.setItem("click_index", index); //存当前选中的场次的索引
    sessionStorage.setItem("total_price",$(".chk_sub .total_price i").html()); //存入当前的总价
    var num_arr = $(fee[index]).find(".num01");
    var time_arr = $(fee[index]).find(".time");
    var single_price = $(fee[index]).find(".fee1");
    var adult = 0;
    var child = 0;
    var order_child_adult = 0;
    var order_child_single = 0;
    var order_adult_single = 0;
    var order_child_price = 0;
    var order_adult_price = 0;
    var order_child_adult_price = 0;
    var fee_arr = [];

    //计算儿童和成人的数量
    for (var i = 0; i < num_arr.length; i++) {
      if ($(time_arr[i]).hasClass("child_adlut")) {

        order_child_adult = parseInt($(num_arr[i]).html());
        order_child_adult_price = parseFloat($(single_price[i]).html());
        var idA = $(time_arr[i]).attr("id").split("_")[0];
        var idC = $(time_arr[i]).attr("id").split("_")[1];
      
        if (order_child_adult > 0) {
          var fee_json1 = {
            "child": parseInt(idC),
            "price": order_child_adult_price,
            "count": order_child_adult,
            "adult": parseInt(idA),
            "prices_id": $(time_arr[i]).attr("id")
          }
          child += parseInt(idC)*order_child_adult;
          adult += parseInt(idA)*order_child_adult;

          fee_arr.push(fee_json1);
        }
      } else if ($(time_arr[i]).hasClass("child")) {

        order_child_single = parseInt($(num_arr[i]).html());
        order_child_price = parseFloat($(single_price[i]).html());

        if (order_child_single > 0) {
          var fee_json2 = {
            "child": parseInt(idC),
            "price": order_child_price,
            "count": order_child_single,
            "prices_id": $(time_arr[i]).attr("id")
          }
          child += parseInt(idC)*order_child_single;
          fee_arr.push(fee_json2);
        }
      } else if ($(time_arr[i]).hasClass("adult")) {

        order_adult_single = parseInt($(num_arr[i]).html());
        order_adult_price = parseFloat($(single_price[i]).html());

        if (order_adult_single > 0) {
          var fee_json3 = {
            "adult": parseInt(idA),
            "price": order_adult_price,
            "count": order_adult_single,
            "prices_id": $(time_arr[i]).attr("id")
          }

          adult += parseInt(idA)*order_adult_single;
          fee_arr.push(fee_json3);
        }
      }
      sessionStorage.setItem("fee_arr", JSON.stringify(fee_arr));
      sessionStorage.setItem("adultSum", adult);
      sessionStorage.setItem("childSum", child);
    }
  }
  //获取(编辑)联系人
  ,
  getContacts: function() {
    var id = tq.t.getQueryString("id");
    var utoken = tq.t.cookie.get("utoken");
    api = tq.url + "product/order?id=" + id + "&utoken=" + utoken + "";
    if(sessionStorage.getItem("contacts_mdy") != null ){
     $("#realname").val(sessionStorage.getItem("contacts_mdy"));
    }
    if(sessionStorage.getItem("mobile_mdy") != null){
      $("#realphone").val(sessionStorage.getItem("mobile_mdy"));
    }
    if(sessionStorage.getItem("contacts_mdy") == null ){
     $("#realname").val(sessionStorage.getItem("contacts"));
    }
    if(sessionStorage.getItem("mobile_mdy") == null){
      $("#realphone").val(sessionStorage.getItem("mobile"));
    }

    $(".add").on("click", function() {
      var contacts = $("#realname").val();
      var mobile = $("#realphone").val();

      if(mobile == ""){
        tq.t.alert("手机号不能为空");
        tq.t.cancel();
      }else{
        if(!tq.t.valPho(mobile)){
          tq.t.alert("手机号输入不正确");
          tq.t.cancel();
        }else{
          sessionStorage.setItem("mobile_mdy",mobile);
          sessionStorage.setItem("contacts_mdy",contacts);
          location.href = "orderDetail.html?id=" + id + "";
        }
      }
    });
  }
  //获得所有(选择)出行人
  ,
  getOuter: function() {
      var utoken = tq.t.cookie.get("utoken");
      var id = tq.t.getQueryString("id"); //此id为product_id;
      var adult = sessionStorage.getItem("adultSum");
      var child = sessionStorage.getItem("childSum");
      var url = location.href;

      $(".adult").html(adult);
      $(".child").html(child);

      $(".form01").on("click", function() {
        var outer_id = $(this).attr("id");
        location.href = "edit_Outer.html?id=" + outer_id + "&url="+url+"";
      });

      $(".form01 .pay-chk").on("click", function() {
        event.stopPropagation(); //阻止继承父元素的事件
        var index = $(".form01 .pay-chk").index(this);
        $(this).toggleClass("pay_checked");
        $($(".form01")[index]).toggleClass("choose_chk");
      });

      $(".add").on("click", function() { // 传入product_id
        location.href = "addOuter.html?url="+url+"";
      });
    //选择
    $(".add_submit").on("click", function() {
      if (!$(".pay-chk").hasClass("pay_checked")) {
        tq.t.alert("请选择出行人");
        tq.t.cancel();
      } else {
        var participant_idArr = [];
        var childSum = 0;
        var adultSum = 0;

        for (var i = 0; i < $(".form01.choose_chk").length; i++) {
          var index = $(".form01").index($(".form01.choose_chk")[i]);
          if (data[index].type == "儿童") {
            childSum++;
          } else {
            adultSum++;
          }
          participant_idArr.push(data[index].id);

        } //for_end
        if (childSum == child && adultSum == adult) {
          location.href = "orderDetail.html?id=" + id + "";
        } else {
          tq.t.alert("所选出行人数目不匹配");
          tq.t.cancel();
        }
        participant_idArr = participant_idArr.join(",");
        sessionStorage.setItem("participant_idArr", participant_idArr);
      } //else_end

    }); //选择完成end

  } //getOuter_end

  //编辑出行人
  ,
  get_edit_Outer: function() {
    var outer_id = tq.t.getQueryString("id");
    var utoken = tq.t.cookie.get("utoken");
    var url = tq.t.getQueryString("url");
    //将修改后的提交
    $(".edit_submit").on("click", function() {
      var api = tq.url + "participant/update";
      if ($("#realname").val() == null || $("#birth").val() == null || $("#gender").val() == null || $("#card_num").val() == null || $("#certificate").val() == null) {
        tq.t.alert("信息不完整");
        tq.t.cancel();
      } else if (!tq.t.valiID($("#card_num").val()) && !tq.t.valiPass($("#card_num").val())) {
        tq.t.alert("证件号码不正确");
        tq.t.cancel();
      } else {
        var arr = {
          "id": outer_id,
          "name": $("#realname").val(),
          "birthday": $("#birth").val(),
          "sex": $("#gender").val(),
          "idType": $("#certificate").val(),
          "idNo": $("#card_num").val()
        }
        var data = JSON.stringify(arr);
        $.post(api, {
          participant: data,utoken:utoken
        }, function(res) {
          if (res.errno == 0) {
            location.href = url;
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        });
      }
    });

    $(".back").on("click",function(){
      location.href = url;
    })
    // 删除出行人
    $(".delete").on("click",function(){
      var api = tq.url + "participant/delete";
      $.post(api,{utoken:utoken,id:outer_id},function(res){
        if(res.errno == 0){
          location.href = url;
        }else{
          tq.t.alert(res.errmsg);
          tq.t.cancel();
        }
      })
    })
  }

  //添加出行人
  ,
  add_Outer: function() {
    $(".add").on("click", function() {
      var id = tq.t.getQueryString("id");
      var utoken = tq.t.cookie.get("utoken");
      var api = tq.url + "participant?utoken=" + utoken + "";
      var url = tq.t.getQueryString("url");
      if ($("#realname").val() == "" || $("#birth").val() == "" || $("#gender").val() == "" || $("#card_num").val() == "" || $("#certificate").val() == "") {
        tq.t.alert("信息不完整");
        tq.t.cancel();
      } else {
        if (!tq.t.valiID($("#card_num").val()) && !tq.t.valiPass($("#card_num").val())) {
          tq.t.alert("证件号码不正确");
          tq.t.cancel();
        } else {
          var arr = {
            "name": $("#realname").val(),
            "sex": $("#gender").val(),
            "birthday": $("#birth").val(),
            "idType": $("#certificate").val(),
            "idNo": $("#card_num").val()
          }
          var data = JSON.stringify(arr);
          $.post(api, {
            participant: data
          }, function(res) {
            if (res.errno == 0) {
              location.href = url;
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        }
      }
    });
  }

  //我的页面
  ,
  profile: function() {  
      var utoken = tq.t.cookie.get("utoken");
      if(utoken == null || utoken == "" || !utoken){
        location.href = "registerpsw.html?profile.html";
      }
      else{
        var api = tq.url + "user?utoken=" + utoken + "";
        $.get(api, {}, function(res) {
          if (res.errno == 0) {
            if (res.data.avatar != "" && res.data.avatar) {
              $(".pro_info img").attr("src", res.data.avatar);
            } else {
              $(".pro_info img").attr("src", "image/default02.png");
            }
            $(".nickName").html(res.data.nickName);
            if(res.data.children){
              if(res.data.children.length !=0 ){
                var date = new Date(res.data.children[0].birthday.replace(/-/g, "/"));
                var age = new Date().getFullYear() - date.getFullYear();
                if(age == 0){
                  var month = new Date().getMonth() - date.getMonth();
                  $(".bbInfo").html(res.data.children[0].sex+"孩"+month+"个月");
                }else{
                  $(".bbInfo").html(res.data.children[0].sex+"孩"+age+"岁");
                }
              }
            }
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        });

        $(".pro_info").on("click", function(){
          location.href = "profileInfo.html";
        })
      }
  }
  //我的订单页面
  ,
  user_order: function() {
    var status = tq.t.getQueryString("status");
    var type = tq.t.getQueryString("type");
    var utoken = tq.t.cookie.get("utoken");
    var api = tq.url + "user/order?utoken=" + utoken + "&status=" + status + "&type=" + type + "&start=0&count=50";
    $.get(api, {}, function(res) {
      var id_arr = [];
      var pro_id_arr = [];
      var sku_id_arr = [];
      if (res.errno == 0) {
        var data = res.data.list;
        for (var i = 0; i < data.length; i++) {
          var s = "<div class='order_box'>";
          s += "<div class='ohd'>";
          if (status == "2" && type == "le") {
            s += "<i class='left'>待付款</i>";
          } else if (status == "3" && type == "ge") {
            s += "<i class='left'>已付款</i>";
          }
          s += "<i class='total_fee'>￥" + data[i].totalFee + "</i>";
          s += "</div>";
          s += "<div class='odt'>";
          s += "<h2 class='otitle'>" + data[i].title + "</h2>";
          s += "<p class='total_num'>"
          s += "<i class='fn'>活动时间:</i>";
          s += "<i>" + data[i].scheduler + "</i>";
          s += "</p>";
          s += "<p class='total_num'>"
          s += "<i class='fn'>活动人数:</i>";
          s += "<i>" + data[i].participants + "</i>";
          s += "</p>";
          s += "</div>";
          if (status == "2" && type == "le") {
            s += "<div class='ofd green'>";
            s += "<a href='' class='rbtn del green'>删除订单</a>";
            s += "<a href='' class='rbtn green pay'>继续支付</a>";
            s += "<div style='clear:both'></div>"
            s += "</div>";
          }
          s += "</div>";

          id_arr.push(data[i].id);
          pro_id_arr.push(data[i].productId);
          sku_id_arr.push(data[i].skuId);
          $(".user_order").append(s);
        }
      } else {
        tq.t.alert(res.errmsg);
        tq.t.cancel();
      }
      //删除订单
      $(".ofd .del").on("click", function() { 
        event.preventDefault();
        event.stopPropagation(); 
        var index = $(".del").index(this);
        tq.t.confirm("确定要删除么？", func_cancel, func_ok);
        function func_cancel(){
          tq.t.delshide();
        } 
        function func_ok(){
          var api = tq.urls + "order/delete";
          $.post(api, {
            utoken: utoken,
            id: id_arr[index]
          }, function(res) {
            if (res.errno == 0) {
              location.href = "user_order.html?status=2&type=le";
            } else {
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        }
      });

      //继续支付，跳转到支付页面
      $(".ofd .pay").on("click", function() {
        event.preventDefault();
        event.stopPropagation();
        var index = $(".pay").index(this);
        location.href = "orderPay.html?order_id=" + id_arr[index] + "&pro_id=" + pro_id_arr[index] + "&sku_id=" + sku_id_arr[index] + "&time="+tq.t.encodeUTF8(res.data.list[index].time)+"&participants="+tq.t.encodeUTF8(res.data.list[index].participants)+"&totalFee="+res.data.list[index].totalFee+"";
      })

      //查看当前活动详情
      $(".order_box").on("click", function() {
        var index = $(".order_box").index(this);
        var api = tq.url + "product";
        var id = pro_id_arr[index];
        $.get(api, {
          id: id
        }, function(res) {
          if (res.errno == 0) {
            location.href = "actsDetail.html?id=" + id + "";
          } else {
            tq.t.alert(res.errmsg);
            tq.t.cancel();
          }
        })
      });
    });
  }

  //微信支付
  ,
  wxPay: function() {
      var utoken = tq.t.cookie.get("utoken");
      var code = tq.t.getQueryString("code"); //获取返回的code;
      var participants = tq.t.decodeUTF8(tq.t.getQueryString("participants"));
      var time = tq.t.decodeUTF8(tq.t.getQueryString("time"));
      var totalFee = tq.t.getQueryString("totalFee");

      var orderID = tq.t.getQueryString("order_id");
      var proID = tq.t.getQueryString("pro_id");
      var skuID = tq.t.getQueryString("sku_id");
      var type = "JSAPI";

      //判断是否是在微信内置浏览器打开
      var ua = navigator.userAgent.toLowerCase();
      var isWeixin = ua.indexOf('micromessenger') != -1;

      $(".order .title").html(time);
      var s = "<div class='fitem_input'>"+participants+"";
      s +=  "<i class='total red right' style='margin-right:0.08rem;'>￥"+totalFee+"</i>";
      s += "</div>";

      $(".totalfee").append(s);
      if (!isWeixin) {
        $("#btn_submit").on("click", function() {
          var api = tq.urls + "payment/prepay/alipay";
          $.post(api, {
            utoken: utoken,
            oid: orderID,
            pid: proID,
            sid: skuID,
            type: "wap"
          }, function(res){
            if(res.errno == 0){
              console.log(res.data);
              console.log(res.data.return_url);
              var param = "_input_charset="+res.data.input_charset+"&body="+res.data.body+"&it_b_pay="+res.data.it_b_pay+"&notify_url="+encodeURIComponent(res.data.notify_url)+"&out_trade_no="+res.data.out_trade_no +"&partner="+res.data.partner+"&payment_type="+res.data.payment_type+"&seller_id="+res.data.seller_id+"&service="+res.data.service+"&sign="+res.data.sign+"&sign_type="+res.data.sign_type+"&subject="+res.data.subject+"&total_fee="+res.data.total_fee+"&show_url="+encodeURIComponent(res.data.show_url)+"&return_url="+encodeURIComponent(res.data.return_url)+"";
              var url = "https://mapi.alipay.com/gateway.do?"+param;
              location.href = url;
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        });
      } else {
        
        $("#btn_submit").on("click", function() {
          var param = window.location.href;
          var url = encodeURIComponent(param);

          location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+tq.appid+"&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

        });

        //如果返回的code不为，发送请求获取openid;
        if (code && code != "") {
          var api = tq.urls + "payment/prepay/wechatpay";
          $.post(api, {
            utoken: utoken,
            trade_type: type,
            code: code,
            oid: orderID,
            pid: proID,
            sid: skuID
          }, function(res) {
            if (res.errno == 0) {
              //判断是否有内置对象
              if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                  document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                } else if (document.attachEvent) {
                  document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                  document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
              } else {
                onBridgeReady();
              }

              //调JSAPI
              function onBridgeReady() {
                WeixinJSBridge.invoke(
                  'getBrandWCPayRequest', {
                    "appId": res.data.appId, //公众号名称，由商户传入     
                    "timeStamp": res.data.timeStamp, //时间戳，自1970年以来的秒数     
                    "nonceStr": res.data.nonceStr, //随机串     
                    "package": res.data.prepayId,
                    "signType": res.data.signType, //微信签名方式:     
                    "paySign": res.data.paySign //微信签名 
                  },
                  function(res) {
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                      var api = tq.urls + "payment/check";
                      $.post(api, {
                        utoken: utoken,
                        oid: orderID,
                        pid: proID,
                        sid: skuID
                      }, function(res) {
                        if (res.errno == 0) {
                          location.href = "payOk.html";
                        } else if (res.data == "ok") {
                          tq.t.alert(res.errmsg);
                          tq.t.cancel();
                        }
                      })
                    }// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
                    else{
                      location.href = "index.html";
                    }
                  }
                ); //WeixinJSBridge_end

              } //onBridgeReady_end   
            }
          });
        } // if_end
      } //else_end
  } //wxPay_end

  // 玩伴信息
  ,
  partner: function(){
    var id = tq.t.getQueryString("id");
    var api = tq.url + "/product/playmate";
    $.get(api, {id: id}, function(res){
      if(res.errno == 0){
        var data = res.data;
        for(var i=0; i<data.length; i++){
          var s = "<div class='part_calender'>";
          s += "<div class='cTitle'>";
          s += "<img src='image/calender2.png'>";
          s += "<div class='pDate'>";
          s += "<p class='date'>"+data[i].time+"</p>";
          s += "<p class='pNum'>"+data[i].joined+"</p>";
          s += "</div></div>";
          if(data[i].playmates){
            s += "<div class='pInfo none'>";
            var data1 = data[i].playmates;
            for(var j=0; j<data1.length; j++){
              s += "<div class='pDetail'>";
              if(data1[j].avatar == ""){
                s += "<img src='image/default.png'>";
              }else{
                s += "<img src="+data1[j].avatar+">";
              }
              s += "<div class='pMaMa'>";
              s += "<p class='MaMa'>"+data1[j].nickName+"</p>";
              if(data1[j].children){
                if(data1[j].children.length >= 2){
                  s += "<p class='pAge'>"+data1[j].children[0]+"|"+data1[j].children[1]+"</p>";
                }else if(data1[j].children.length == 0){
                  s += "<p class='pAge'></p>";
                }
                else{
                  s += "<p class='pAge'>"+data1[j].children[0]+"</p>";
                }
              }
              s += "</div></div>";
            }
            s += "</div>";
          }
          s += "</div>";
          $("section").append(s);
        }
        $('.cTitle').on('click',function(event){
            $(this).siblings(".pInfo").toggleClass("none");
            $(this).toggleClass("downforward");
        });
      }else{
        tq.t.alert(res.errmsg);
        tq.t.cancel();
      }
    });
  }
  // 我的信息
  ,
  profileInfo: function(){
    var utoken = tq.t.cookie.get("utoken");
    var api = tq.url + "user?utoken=" + utoken + "";
    $(".order_detail .chk_num .plus").on("click", function(){
      var input = $(this).siblings(".num01");
      if(parseInt(input.html()) >=5){
        input.html("5");
        return;
      }else{
        tq.home.addChild();
      }
      input.html(parseInt(input.html()) + 1);
    });

    $(".order_detail .chk_num .minus").on("click", function(){
      var cNickname = $(".cNickname");
      var cid = $(cNickname[cNickname.length - 1]).attr("id");
      var input = $(this).siblings(".num01");
      var api = tq.url + "user/child/delete";
      var botLength = $(".babyMain .bot").length;
      if(parseInt(input.html()) == 0){
        input.html("0");
        return;
      }else{
        $.post(api, {utoken:utoken,cid:cid}, function(res){
          if(res.errno == 0){
            $($(".babyMain .bot")[botLength-1]).remove();
            chidlIdArr.pop();
          }else{
            tq.t.alert(errmsg);
            tq.t.cancel();
          }
        })
      }
      input.html(parseInt(input.html()) - 1);
    });
    //点击编辑大人昵称
    $(".aNickname").on("click",function(){
      var api = tq.url + "user/nickname";
      tq.home.alertConfirmText("修改昵称", "nickname" ,api);
      tq.t.cancel();
    });

    // 点击编辑大人性别
    $(".aGender").on("click",function(){
      var api = tq.url + "user/sex";
      tq.home.alertConfirmText("修改性别", "sex" ,api);
      tq.t.cancel();
    });

    // 点击编辑常居地
    $(".address").on("click",function(){
      var api = tq.url + "user/address";
      tq.home.alertConfirmText("修改常居地", "address" ,api);
      tq.t.cancel();
    });

    // 点击编辑孩子的昵称
    $(".cNickname").live("click", function(){
      var api = tq.url + "user/child/name";
      var nickForm = $(".cNickname");
      var index = $(nickForm).index(this);
      var cid = $(this).attr("id");
      tq.home.alertConfirmChild("修改孩子昵称", "name", cid, index, api);
      tq.t.cancel();
    });

    // 点击编辑孩子的性别
    $(".cGender").live("click", function(){
      var api = tq.url + "user/child/sex";
      var nickForm = $(".cGender");
      var index = $(nickForm).index(this);
      var cid = $(this).attr("id");
      tq.home.alertConfirmChild("修改孩子性别", "sex", cid, index, api);
      tq.t.cancel();
    });

    // 点击编辑孩子的生日
    $(".cBirth").live("click", function(){
      var api = tq.url + "user/child/birthday";
      var nickForm = $(".cBirth");
      var index = $(nickForm).index(this);
      var cid = $(this).attr("id");
      tq.home.alertConfirmChild("修改孩子出生日期", "birthday", cid, index, api);
      tq.t.cancel();
    });
  }
  // 动态添加小孩信息标签
  ,
  addChildInfo: function(name, gender, birth, cid){
      var s = "<div class='form bot' style='border-top:1px solid #eee;border-bottom:1px solid #eee;margin-bottom:0.1rem;onclick=''>";
      s += "<div class='fitem_input edit'>";
      s += "<span class='fh'>大宝姓名</span>";
      s += "<span class='fd tr cNickname test' id="+cid+">"+name+"</span>";
      s += "</div>";
      s += "<div class='fitem_input edit'>";
      s += "<span class='fh'>性别</span>";
      s += "<span class='fd tr cGender test' id="+cid+">"+gender+"</span>";
      s += "</div>";
      s += "<div class='fitem_input edit'>";
      s += "<span class='fh'>生日</span>";
      s += "<span class='fd tr cBirth test' id="+cid+">"+birth+"</span>";
      s += "</div></div>";
      $(".babyMain").append(s);
  }
  // 添加小孩
  ,
  addChild: function(){
    var utoken = tq.t.cookie.get("utoken");
    var api = tq.url + "user/child";
    var dataArr = [];
    dataArr.push({
      "name":"Peter",
      "sex":"男",
      "birthday":"2015-01-01",
      "idType":1,
      "idNo":""
    });
    var children = JSON.stringify(dataArr);
    $.post(api, {utoken:utoken, children: children}, function(res){
      if(res.errno == 0){
        chidlIdArr = [];
        for(var i=0; i<res.data.children.length; i++){
          chidlIdArr.push(res.data.children[i].id);
        }
        tq.home.addChildInfo("Peter","男","2015-01-01",chidlIdArr[chidlIdArr.length-1]);
        chidlIdArr = [];
        for(var i=0; i<res.data.children.length; i++){
          chidlIdArr.push(res.data.children[i].id);
        }
      }else{
        tq.t.cancel();
      }
    })
  }

  // 弹框修改信息
  ,
  alertConfirmText: function (msg, param, url) {
      var utoken = tq.t.cookie.get("utoken");
      var s = "<div class='shide'>";
      s += "<div class='alert'>";
      s += "<img class='cancel' src='image/cancel2.png'>"+msg+"" ; 
      if(param == "sex"){
        s += "<div style='margin-top: 0.08rem;'>"
        s += "<input  type='radio' class='update radio' name='gender' value='男'>";
        s += "<label class='radio' style='margin-right:0.08rem;' for='gender1'>男</label>";
        s += "<input  type='radio' class='update radio' name='gender' value='女'>";
        s += "<label class='radio' style='margin-right:0.08rem;' for='gender2'>女</label>";
        s += "</div>"
      }else{
        s += "<input  type='text' class='update' value='' style='color: #333;margin-top: 0.08rem;background: #fff;'>";
      }
      s += "<span class= 'confirm'>"
      s += "<i class='green cancel2'>取消</i>";
      s += "<i class='green del2'>确定</i>";
      s += "</spn></div></div>";
      $(document.body).append(s);
      $(".cancel2").on("click", function(){
        tq.t.delshide();
      })
      $(".del2").on("click", function(){
        tq.t.delshide();
        if(param == "sex"){
          var value = $('input[name="gender"]:checked').val();
        }else{
          var value = $(".update").val();
        }
        if(param == "nickname"){
          if(value == ""){
            value = $(".aNickname").html();
          }
          $.post(url, {utoken:utoken, nickname:value}, function(res){
            if(res.errno == 0){
              $(".aNickname").html(value);
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        }else if(param == "sex"){
          if(value == ""){
            value = $(".aGender").html();
          }
          $.post(url, {utoken:utoken, sex:value}, function(res){
            if(res.errno == 0){
              $(".aGender").html(value);
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        }else if(param == "address"){
          if(value == ""){
            value = $(".address").html();
          }
          $.post(url, {utoken:utoken, address:value}, function(res){
            if(res.errno == 0){
              $(".address").html(value);
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        } 
      })       
  }

  // 修改孩子信息
  ,
  alertConfirmChild: function (msg, param, cid, index, url) {
      var utoken = tq.t.cookie.get("utoken");
      var s = "<div class='shide'>";
      s += "<div class='alert'>";
      s += "<img class='cancel' src='image/cancel2.png'>"+msg+"" ; 
      if(param == "birthday"){
        s += "<input type='date' value='2015-01-01' class='update'  onchange='$(this).prev().val(this.value)' onfocus='$(this).prev().val(this.value)' style='color: #333;margin-top: 0.1rem;background: #fff;'></input>";
      }
      else if(param == 'sex'){
        s += "<div style='margin-top: 0.08rem;'>"
        s += "<input  type='radio' class='update radio' name='gender' value='男'>";
        s += "<label class='radio' style='margin-right:0.08rem;' for='gender1'>男</label>";
        s += "<input  type='radio' class='update radio' name='gender' value='女'>";
        s += "<label class='radio' style='margin-right:0.08rem;' for='gender2'>女</label>";
        s += "</div>"
      }
      else{
        s += "<input  type='text' class='update' value='' style='color: #333;margin-top: 0.08rem;background: #fff;'>";
      }
      s += "<span class= 'confirm'>";
      s += "<i class='green cancel2'>取消</i>";
      s += "<i class='green del2'>确定</i>";
      s += "</spn></div></div>";
      $(document.body).append(s);
      $(".cancel2").on("click", function(){
        tq.t.delshide();
      })
      $(".del2").on("click", function(){
        tq.t.delshide();
        if(param == "sex"){
          var value = $('input[name="gender"]:checked').val();
        }else{
          var value = $(".update").val();
        }
        if(param == "name"){
          if(value == ""){
            value = $($(".cNickname")[index]).html();
          }
          $.post(url, {utoken:utoken, name:value, cid:cid}, function(res){
            console.log(url);
            console.log(utoken);
            console.log(value);
            console.log(cid);
            if(res.errno == 0){
              $($(".cNickname")[index]).html(value);
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        }else if(param == "sex"){
          if(value == ""){
            value = $($(".cGender")[index]).html();
          }
          $.post(url, {utoken:utoken, sex:value, cid:cid}, function(res){
            if(res.errno == 0){
              $($(".cGender")[index]).html(value);
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        }else if(param == "birthday"){
          if(value == ""){
            value = $($(".cBirth")[index]).html();
          }
          $.post(url, {utoken:utoken, birthday:value, cid:cid}, function(res){
            if(res.errno == 0){
              $($(".cBirth")[index]).html(value);
            }else{
              tq.t.alert(res.errmsg);
              tq.t.cancel();
            }
          });
        } 
      })       
  }

  // 上传头像
  ,
  uploadImg: function(){
    $("#browsefile").change(function(){
      var ext = this.value.substr(this.value.lastIndexOf('.') + 1).toLowerCase();
      if (ext != 'jpg' && ext != 'jpeg' && ext != 'gif' && ext != 'bmp' && ext != 'png') {
          tq.t.alert('图片格式错误'); 
          tq.t.cancel();
          return;
      }else{  
        previewInImage(this.files[0]);
      }
     });

     function previewInImage(file){
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function(e){
          var api = tq.url + "user/avatar";
          var utoken = tq.t.cookie.get("utoken");
          var data = new FormData();
          data.append('file', $('#browsefile')[0].files[0]);
          $.ajax({
              url: 'http://upload.momia.cn/upload/image',
              type: 'POST',
              data: data,
              processData: false,
              contentType: false,
              success: function(res){
                if(res.errno == 0){
                  var path = res.data.path;
                  $.post(api,{utoken:utoken,avatar:path},function(res){
                    if(res.errno == 0){
                      $(".nick_img").attr("src",res.data.avatar);
                    }else{
                      tq.t.alert(res.errmsg);
                      tq.t.cancel();
                    }
                  })
                }
              },
              error: function(res){
                console.log("erro");
              } 
          });
          return false;
        }
    };
  }
}