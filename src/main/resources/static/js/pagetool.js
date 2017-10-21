/**
 *
 */
function setPageSizeSel(pageSizeArray, pageSizeDefault) {
    var $pageSizeSel = $("#pageSize-sel");
    for (var x = 0; x < pageSizeArray.length; x++) {
        $pageSizeSel.append($('<option value="' + pageSizeArray[x] + '" ' + (pageSizeArray[x] === pageSizeDefault ? 'selected' : '') + '>' + pageSizeArray[x] + '</option>'));
    }
}

function setPage(pageNum, total, totalPage, pageFun) {
    $("#total").text(total);
    // 设置分页 disabled
    $(".head a").attr("href", "javascript:" + pageFun + "(1);");
    $(".lastpage a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 1) + ")");
    $(".currentpage a").text(pageNum);
    $(".nextpage a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 1) + ")");
    $(".tail a").attr("href", "javascript:" + pageFun + "(" + totalPage + ")");

    $(".head").removeClass("disabled");
    $(".lastpage").removeClass("disabled");
    $(".nextpage").removeClass("disabled");
    $(".tail").removeClass("disabled");

    if (pageNum - 5 > 0) {
        $(".morehead").show();
    } else {
        $(".morehead").hide();
    }
    if (pageNum - 4 > 0) {
        $(".page-4").show();
        $(".page-4 a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 4) + ")");
        $(".page-4 a").text(pageNum - 4);
    } else {
        $(".page-4").hide();
    }
    if (pageNum - 3 > 0) {
        $(".page-3").show();
        $(".page-3 a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 3) + ")");
        $(".page-3 a").text(pageNum - 3);
    } else {
        $(".page-3").hide();
    }
    if (pageNum - 2 > 0) {
        $(".page-2").show();
        $(".page-2 a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 2) + ")");
        $(".page-2 a").text(pageNum - 2);
    } else {
        $(".page-2").hide();
    }
    if (pageNum - 1 > 0) {
        $(".page-1").show();
        $(".page-1 a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 1) + ")");
        $(".page-1 a").text(pageNum - 1);
    } else {
        $(".page-1").hide();
    }

    if ((pageNum + 1) <= totalPage) {
        $(".page_1").show();
        $(".page_1 a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 1) + ")");
        $(".page_1 a").text(pageNum + 1);
    } else {
        $(".page_1").hide();
    }

    if (pageNum + 2 <= totalPage) {
        $(".page_2").show();
        $(".page_2 a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 2) + ")");
        $(".page_2 a").text(pageNum + 2);
    } else {
        $(".page_2").hide();
    }

    if (pageNum + 3 <= totalPage) {
        $(".page_3").show();
        $(".page_3 a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 3) + ")");
        $(".page_3 a").text(pageNum + 3);
    } else {
        $(".page_3").hide();
    }

    if (pageNum + 4 <= totalPage) {
        $(".page_4").show();
        $(".page_4 a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 4) + ")");
        $(".page_4 a").text(pageNum + 4);
    } else {
        $(".page_4").hide();
    }
    if (pageNum + 5 <= totalPage) {
        $(".moretail").show();
    } else {
        $(".moretail").hide();
    }

    if (pageNum <= 1) {
        $(".head").addClass("disabled");
        $(".lastpage").addClass("disabled");
        $(".head a").removeAttr("href");
        $(".lastpage a").removeAttr("href");
    }
    if (pageNum >= totalPage) {
        $(".nextpage").addClass("disabled");
        $(".tail").addClass("disabled");
        $(".nextpage a").removeAttr("href");
        $(".tail a").removeAttr("href");
    }

    return pageNum;
}