var tagList;
var totalPage;
var currentPageIndex=1;
$(function () {
    var pageInfo={};
    pageInfo.page=currentPageIndex;
    getTags(pageInfo);

// 父标签插入
    $("#insertParentTag").click(function () {

        var  insertTag={};
        insertTag.tagName=$("#insertParentTagName").val();
        insertTag.tagType=$("#insertParentTagType").val();
        insertTag.parentId=0;
        $.ajax({
            // async : false,    //表示请求是否异步处理
            type : "post",    //请求类型
            url : "/tags/insertTags",//请求的 URL地址
            data: insertTag,
            dataType : "json",//返回的数据类型
            success: function (data) {
                $("#insertModalBillingInfo").modal("hide")
                console.log(data);  //在控制台打印服务器端返回的数据
                if (data.code == '200') {
                    $.alert({
                        title: "提示",
                        content: data.message,
                        onClose: function () {
                            flushTags()
                        }
                    })}
                if (data.code == '500') {
                    $.alert({
                        title: "",
                        content: data.message,
                        onClose: function () {
                            flushTags()
                        }
                    });
                }
            }
        });



    });
});

function tagType(tagType) {
    if (tagType==1){return "能力点";}
    if (tagType=="能力点"){return 1;}
    if (tagType==2){return "知识点";}
    if (tagType=="知识点"){return 2;}

}
// updateTagId=null;
$("body").on('click','.editTagsBtn',function () {
    thistr = $(this).parent().parent();
    updateTagId = thistr.attr('id');
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type: "post",    //请求类型
        url: "/tags/readyToEdit",//请求的 URL地址
        data: {id:updateTagId},
        dataType: "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if (data.code == '200') {
                window.location.href = "math-tagsedit.html?id="+updateTagId;
            }
        }
    });
});
$("#selectShowType").change(function() {


    var pageInfo={};
    pageInfo.page=1;

    if ($(this).val()!="all") pageInfo.tagType=$(this).val();
    getTags(pageInfo);

})

function getTags(pageInfo) {
    $("#tagList").empty();
    pageInfo.size=20;
    $.ajax({
        // async : false,    //表示请求是否异步处理
        type : "get",    //请求类型
        url : "/tags/getTagsByPage",//请求的 URL地址
        data: pageInfo,
        dataType : "json",//返回的数据类型
        success: function (data) {
            console.log(data);  //在控制台打印服务器端返回的数据
            if(data.code=='200') {
                tagList=data.body.records;
                totalPage=data.body.pages;
                currentPageIndex=pageInfo.page;
                if(tagList.length != 0) {
                    for(var i=0;i<tagList.length;i++) {
                        $("#tagList").append(
                            ' <tr id="'+tagList[i].id+'">\n' +
                            '     <td id="trTagName">'+tagList[i].tagName+'</td>\n' +
                            '     <td id="trTagType">'+tagType(tagList[i].tagType)+'</td>\n' +
                            '     <td></td>\n' +
                            '     <td>\n' +
                            '         <button class="btn btn-sm btn-primary btn-uppercase editTagsBtn">编辑</button>\n' +
                            '     </td>\n' +
                            ' </tr>\n' +
                            ' <tr>                                                                /n'
                        );
                    }
                    // 神秘
                    $(".page-item").remove();
                    if(totalPage>3) {
                        if(currentPageIndex-1<1) {
                            var page1=currentPageIndex;
                            var page2=currentPageIndex+1;
                            var page3=currentPageIndex+2;
                            $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                            $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                            $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                        }else if(currentPageIndex+1>totalPage) {
                            console.log("situation 2--"+currentPageIndex);
                            console.log("totalpage--"+totalPage);
                            var page1=parseInt(currentPageIndex);
                            var page2=parseInt(currentPageIndex)-1;
                            var page3=parseInt(currentPageIndex)-2;
                            console.log("page----"+page1);
                            console.log("page----"+page2);
                            console.log("page----"+page3);
                            $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                            $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                            $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                        } else {
                            var page1=parseInt(currentPageIndex)-1;
                            var page2=parseInt(currentPageIndex);
                            var page3=parseInt(currentPageIndex)+1;
                            console.log("situation 3--"+currentPageIndex);
                            console.log("page----"+page1);
                            console.log("page----"+page2);
                            console.log("page----"+page3);
                            $("#nextPage").before('<li class="page-item" data-id=' + page1 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page1 + '</a></li>');
                            $("#nextPage").before('<li class="page-item" data-id=' + page2 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page2 + '</a></li>');
                            $("#nextPage").before('<li class="page-item" data-id=' + page3 + '><a class="page-link" href="#" onclick="pageChange(this)">' + page3 + '</a></li>');
                        }
                    } else {

                        for (i = 0; i < totalPage; i++) {
                            var current = i + 1;
                            $("#nextPage").before('<li class="page-item" data-id=' + current + '><a class="page-link" href="#" onclick="pageChange(this)">' + current + '</a></li>');

                        }
                    }
                }
            }
            if(data.code=='500') {
                alert(data.message);
            }
        },
        error:function (data) {
            alert(data.message);
        }

    })

}
//蒋万泽的神秘代码传的是个this
function pageChange(target) {
    currentPageIndex=$(target).text();
    console.log(currentPageIndex);
    flushTags()
}
function flushTags() {
    var pageInfo={};
    pageInfo.page=currentPageIndex;
    if ($("#selectShowType").val()!="all")pageInfo.tagType=$("#selectShowType").val();
    getTags(pageInfo);

}
function lastPage(){
    var pageInfo={};
    if (currentPageIndex>1) {
        pageInfo.page = Number(currentPageIndex )- 1;
        if ($("#selectShowType").val() != "all") pageInfo.tagType = $("#selectShowType").val();
        getTags(pageInfo);
    }
}
function nextPage(){
    var pageInfo={};
    if (currentPageIndex<totalPage) {
        pageInfo.page = Number(currentPageIndex )+1;
        if ($("#selectShowType").val() != "all") pageInfo.tagType = $("#selectShowType").val();
        getTags(pageInfo);
    }
}






   




