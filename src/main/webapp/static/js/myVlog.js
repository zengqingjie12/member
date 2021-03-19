var ctx = $("#ctxPath").val();
var themeId = $("#dataId").attr("data-themeId");
// IE8提示
if(!document.getElementsByClassName) {
    alert('请使用IE8以上的浏览器！')
}
// 图片懒加载
$(".video-list img.lazy").lazyload({effect: "fadeIn"});

var playStatus = false; // 播放状态
var lastRunTime = Date.now();

// 鼠标hover效果
$(document).on("mouseenter", ".video-list li", function () {
    var _this = this;
    $(this).find('.cover').fadeOut();
    var video = $(this).find('video');
    if(!video.attr('src')) {
        video.attr('src', video.attr('data-original'));
    }

    var currentTime = Date.now();
    var protectTime = 300; //设置保护延时
    playStatus = false;
    if((currentTime  - lastRunTime) < protectTime){
        return; //两次执行太过频繁，直接退出
    }

    video[0].play();
    // 播放缓冲
    video[0].addEventListener('waiting', function() {
        $(_this).find(".loading").fadeIn();
    });
    // 播放
    video[0].addEventListener('playing', function() {
        $(_this).find(".loading").fadeOut();
        if(playStatus) return;
        lastRunTime = Date.now();
        playStatus = true;
    });
});
$(document).on("mouseleave", ".video-list li", function () {
    $(this).find('.cover').fadeIn();
    if(playStatus){
        $(this).find('video')[0].pause();
    }
    $(this).find(".loading").fadeOut();
});

// 视频弹窗播放
var docHeight = $(window).height();
var docWidth = $(window).width();
$(document).on('click', '.video-box', function() {
    var src = $(this).find('video').attr('data-original');
    var video = $('.video-main video');
    video.attr('src', src);
    video.css({'max-width': docWidth + 'px', 'max-height': docHeight + 'px'});
    $('.video-pop').fadeIn();
    video[0].play();
});

// 关闭视频播放
$('.close-video').click(function() {
    // $('.video-pop').fadeOut().find('video').remove();
    $('.video-pop').fadeOut().find('video').attr('src', '');
});

// 投票
$(document).on("click", "#video-list .btn", function () {
    var flag = 0;
    var _this = $(this);
    var voteCount = parseInt($(this).parent().children(".bottom").children(".ml20").children("i").text());
    var optionId = $(this).parent().children(".video-box").attr("data-optionId");
    var userCount = parseInt($(this).parent().children(".video-box").attr("data-userCount"));
    if (flag == 0){
        flag = 1;
        $.ajax({
            url: ctx + "/vote/voteAjax",
            data: {
                "themeId": themeId,
                "optionId": optionId
            },
            type: "post",
            cache: false,
            async: false,
            dataType: "json",
            success: function (result) {
                if (result.code == 1) {
                    layer.msg(result.msg, {icon: 6});
                    _this.parent().children(".bottom").children(".ml20").children("i").text(voteCount + 1);
                    _this.parent().children(".video-box").attr("data-userCount", userCount + 1);
                    if ((userCount + 1) >= 3) {
                        _this.attr("disabled", "");
                        _this.text("已投票");
                    }
                } else {
                    var loginUrl = result.data.loginUrl;
                    if(loginUrl != null && loginUrl.length > 0) {
                        window.location.href = ctx + loginUrl;
                    }else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            }
        });
    }

});


function search() {
   /* optionListAjax();*/
    var offsetTop = $('.video-list').offset().top - 20;
    $('html,body').animate({scrollTop: offsetTop}, 500);
}

function optionListAjax() {
    $.ajax({
        url: ctx + "/vote/getVoteOption",
        data: {
            "themeId": themeId,
            "optionName": $.trim($("#optionName").val())
        },
        type: "post",
        cache: false,
        async: false,
        dataType: "json",
        success: function (result) {
            var appendHtml = "";
            if (result.code == 1) {
                var data = result.data;
                for (var i = 0; i < data.length; i++){
                    appendHtml += "<li>" +
                        "                               <div class=\"video-box\" data-userCount=\"" + data[i].userCount + "\" data-optionId=\""+ data[i].id +"\">" +
                        "                                    <div class=\"cover\" >" +
                        "                                        <img class=\"lazy\" src=\"" + data[i].coverUrl + "\" alt=\"\">" +
                        "                                        <i class=\"icon-play\"></i>" +
                        "                                    </div>" +
                        "                                    <div class='loading'></div> " +
                        "                                    <video muted=\"\" data-original=\"" + data[i].optionUrl + "\" loop=\"loop\" width=\"100%\"></video>" +
                        "                                </div>" +
                        "                                <h2>" + data[i].optionName + "</h2>" +
                        "                                <div class=\"bottom\">" +
                        "                                    <span>" + data[i].author + "</span>" +
                        "                                    <span class=\"ml20\">票数：<i>" + data[i].voteCount + "</i></span>" +
                        "                                </div>";
                    if (data[i].userCount >= 3){
                        appendHtml += "<button type=\"button\" class=\"btn\" disabled>已投票</button>";
                    }else {
                        appendHtml += "<button type=\"button\" class=\"btn\"><i></i>我要投票</button>";
                    }
                    appendHtml += "</li>";
                }
                $("#video-list").html(appendHtml);
            } else {
                $("#video-list").html("");
            }
        }
    });
}


var layer;
layui.use(['layer'], function() {
    layer = layui.layer;

});
//留言
function addPillowTalk() {
    var talkContent = $.trim($("#talkContent").val());
    if (talkContent == null || talkContent == ""){
        layer.msg("不说点嘛 ㄟ(▔,▔)ㄏ", {icon: 5, time: 2000});
        return false;
    }
    if (talkContent.length > 200){
        layer.msg("悄悄话也长了吧 (ಥ_ಥ)", {icon: 5, time: 2000});
        return false;
    }
    $.ajax({
        url:  "/pillowTalk/addPillowTalk",
        data: {
            "talkContent":talkContent
        },
        type: "post",
        cache: false,
        async: false,
        dataType: "json",
        success: function (apiDate) {
            if (apiDate.code == 1) {
                layer.msg("萝卜正在偷偷听着~ ٩(๑>◡<๑)۶", {icon: 6, time: 2000},function () {
                    $("#talkContent").val("");
                    layer.closeAll();
                });
            } else {
                layer.msg(apiDate.msg, {icon: 5, time: 2000},function () {
                    layer.closeAll();
                });
            }
        }
    })
}