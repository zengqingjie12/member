var ctx = $("#ctxPath").val();
var themeId = $("#dataId").attr("data-themeId");
// videojs配置
var options = {
    controlBar: {
        fullscreenToggle: false // 全屏按钮
    }
};
var player = videojs("my_player", options, function (){});
// 点击视频
$("#video-list").on("click","li .poster", function () {
    var inx = $(this).parent().index();
    var src = $(this).attr("data-video");
    player.src({ src: src, type: "video/mp4"});
    player.load();
    $(".video-main").fadeIn();
    player.play();
});

// 关闭按钮
$("#video-close").click(function (e) {
    e.preventDefault();
    player.pause();
    $("video").find("source").remove();
    $(".video-main").hide();
});

// 投票
/*$(document).on("click", "#video-list .btn", function () {
    var flag = 0;
    var _this = $(this);
    var voteCount = parseInt($(this).parent().children(".bottom").children("div").children(".count").text());
    var optionId = $(this).parent().children(".poster").attr("data-optionId");
    var userCount = parseInt($(this).parent().children(".poster").attr("data-userCount"));
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
                    _this.parent().children(".bottom").children("div").children(".count").text(voteCount + 1);
                    _this.parent().children(".poster").attr("data-userCount", userCount + 1);
                    if ((userCount + 1) >= 3){
                        _this.addClass("on");
                        _this.text("已投票");
                    }
                } else {
                    var loginUrl = result.data.loginUrl;
                    if(loginUrl != null && loginUrl.length > 0) {
                        window.location.href = ctx + loginUrl;
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            }
        });
    }

});*/

// 阻止滚动
$(".video-main").on("touchmove", function () {
    event.preventDefault();
});
/*
$(function(){
    optionListAjax();
});
*/

function search() {
    optionListAjax();
    var top = $(".spx").offset().top;
    contentTop(top);
}

// 滚动到
function contentTop(top) {
    $('body,html').animate({
        scrollTop: top
    }, 500);
}

/*
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
                        "<div class=\"poster\" style=\"background: #333 url(" + data[i].coverUrl + ") center no-repeat; background-size:contain;\" data-optionId=\""+ data[i].id +"\" data-userCount=\""+ data[i].userCount +"\" data-video=\""+ data[i].optionUrl +"\"><em></em></div>" +
                        "<div class=\"name ellipsis\">" + data[i].optionName + "</div>" +
                        "<div class=\"bottom\">" +
                        "<span>" + data[i].author + "</span>" +
                        "<div>" +
                        "票数：" +
                        "<span class=\"count\">" + data[i].voteCount + "</span>" +
                        "</div>" +
                        "</div>";
                    if (data[i].userCount >= 3){
                        appendHtml += "<button href=\"javascript:;\" class=\"btn on\">" +
                            "已投票" +
                            "</button>";
                    }else {
                        appendHtml += "<button href=\"javascript:;\" class=\"btn\">" +
                            "投票" +
                            "</button>";
                    }
                    appendHtml += "</li>";
                }
                $("#video-list").html(appendHtml);
            } else {
                $("#video-list").html("");
            }
        }
    });
}*/

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
