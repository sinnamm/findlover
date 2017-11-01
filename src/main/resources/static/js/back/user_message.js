var pageNum = 1;
var pageSize = 5;
var pageSizeArray = [1, 3, 5, 8, 10];

$(function () {
    getMessages();
    setPageSizeSel(pageSizeArray, pageSize);
    initPageSizeSel();
    clickFunction();
    
});

function getMessages() {
    var data={"pageNum":pageNum,
                "pageSize":pageSize,
                "column":$("#message-search-sel").val(),
                "keyword":$("#message-search-val").val()}
    $.ajax({
        url:contextPath+"admin/other_says/getMessages",
        type:"get",
        dataType:"json",
        data:data,
        success:function (data) {
            var $messageTableBody = $("#message-table").find(">tbody");
            $messageTableBody.empty();
            var list = data.list;
            for(var i=0;i<list.length;i++){
                var user = list[i].userBasic;
                $messageTableBody.append("<tr>" +
                    "                       <td><input type='checkbox'name='message' value='"+list[i].id+"'></td>"+
                    "                       <td>"+list[i].id+"</td>"+
                    "                       <td> "+ user.id +
                    "                           <img src='" + contextPath + "images/vip" + (user.vip ? '': '-grey') +".png' class='flag'>" +
                    "                           <img src='" + contextPath + "images/star-0" + (user.star ? '': '-grey') + ".png' class='flag'>" +
                    "                       </td>" +
                    "                       <td> "+ user.nickname +"</td>" +
                    "                       <td> "+ (list[i].content.length >30 ? list[i].content.substring(0,30)+'...' : list[i].content)+ "</td>" +
                    "                       <td> "+ list[i].pubTime + "</td>" +
                    "                       <td><button onclick='deleteOne("+list[i].id+")' class='btn btn-sm btn-warning'><i class='fa fa-gavel'></i>&nbsp;删除</button></td>" +
                    "                   </tr>"
                                        );
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
    })

}

function goPage(_pageNum) {
    pageNum = _pageNum;
    getMessages();
};

function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
};

function deleteOne(messageId) {
    swal({
        title: "确定要删除这条用户动态吗",
        icon: "warning",
        buttons: ["取消", "确定"]
    }).then(result => {
        if (result) {
            $.ajax({
                url: contextPath + "admin/other_says/deleteOne/" + messageId,
                type: "put",
                dataType: "text",
                success: function (data) {
                    if (data="success"){
                        getMessages();
                    }else {
                        swal("删除失败！", "", "Error");
                    }
                }
            });
        }
    });
}

function clickFunction() {
    $("#check-all").click(function () {
        if (this.checked) {
            $("input[name='message']").prop("checked",true);//全选
        }else{
            $("input[name='message']").prop("checked",false);//取消全选
        }
    });

    $("#message-search-btn").click(function () {
       getMessages()
    });

    $("#message-delete-btn").click(function () {
        swal({
            title: "确定要删除这条用户动态吗",
            icon: "warning",
            buttons: ["取消", "确定"]
        }).then(result => {
            if (result) {
                var ids = checkAll($("input[name='message']:checkbox"));
                $.ajax({
                    url:contextPath+"admin/other_says/deleteMany",
                    type:"put",
                    data: {"ids":ids},
                    dataType:"text",
                    success:function (data) {
                        if (data="success"){
                            getMessages();
                        }else {
                            swal("删除失败！", "", "Error");
                        }
                    }
                })
            }
        });


    })

}

//获取单选或者多选的值，返回一个值得数组，如果没有值，返回空数组，参数inputlist是jQuery对象
function checkAll(inputlist){
    var arr = [];
    var num = inputlist.length;
    for(var i = 0; i < num; i++){
        if(inputlist.eq(i).is(":checked")){
            arr.push(inputlist.eq(i).val());
        }
    }
    return arr;
}