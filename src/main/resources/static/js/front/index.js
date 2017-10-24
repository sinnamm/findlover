var age_low_limit = 18;
var age_high_limit = 66;
var height_low_limit = 145;
var height_high_limit = 210;
$(function () {
    initIndexSearch();
    flexisel();
    initToolBar();
    // Animations
    gridRotator();
    contentWayPoint();
    //搜索事件
    searchUser();
});

// 每日情缘
function flexisel() {
    $("#flexiselDemo3").flexisel({
        visibleItems: 1,
        animationSpeed: 1000,
        autoPlay: true,
        autoPlaySpeed: 8000,
        pauseOnHover: true,
        enableResponsiveBreakpoints: true,
        responsiveBreakpoints: {
            portrait: {
                changePoint: 480,
                visibleItems: 1
            },
            landscape: {
                changePoint: 640,
                visibleItems: 1
            },
            tablet: {
                changePoint: 768,
                visibleItems: 1
            }
        }
    });
}

function gridRotator(){
    $('#ri-grid').gridrotator({
        rows: 2,
        // number of columns
        columns: 3,
        w1024: {rows: 2, columns: 3},
        w768: {rows: 2, columns: 3},
        w480: {rows: 2, columns: 3},
        w320: {rows: 2, columns: 3},
        w240: {rows: 2, columns: 3},
        preventClick: false
    });
}

function contentWayPoint() {
    var i = 0;
    $('.animate-box').waypoint( function( direction ) {

        if( direction === 'down' && !$(this.element).hasClass('animated') ) {
            i++;
            $(this.element).addClass('item-animate');
            setTimeout(function(){

                $('body .animate-box.item-animate').each(function(k){
                    var el = $(this);
                    setTimeout( function () {
                        var effect = el.data('animate-effect');
                        if ( effect === 'fadeIn') {
                            el.addClass('fadeIn animated');
                        } else {
                            el.addClass('fadeInUp animated');
                        }

                        el.removeClass('item-animate');
                    },  k * 200, 'easeInOutExpo' );
                });

            }, 100);
        }
    } , { offset: '85%' } );
}

//初始化首页搜索条件
function initIndexSearch() {
    $.ajax({
        url:contextPath+"index/initSearch",
        typ:"get",
        dataType:"json",
        success:function (data) {
            var arr = data.workplace.split("-");
            initWorkplaceDropdown(null,"province-select","city-select",arr[0], arr.length > 1 ? arr[1] : -1);
            initAgeDropdown(null,data.ageLow,data.ageHigh);
            initHeightDropdown(null,data.heightLow,data.heightHigh)
        }
    })
}

function searchUser() {
    $("#submit-btn").click(function () {
        alert($("#search-form").serialize());
        $.ajax({
            url:contextPath+"index/getSearchUser",
            data:$("#search-form").serialize(),
            async:true,
            type:"post",
            dataType:"json",
            sunccess:function (data) {
                for(i=0;i<data.length;i++){
                    alert(data[i].nickname);
                }
            }
        })
    });
}