$(function () {
    initToolBar();
    // Animations
    gridRotator();
    contentWayPoint();
});
// 会员和星级用户logo toolbar
function initToolBar(){
    $('img[data-toolbar="vip-toolbar"]').toolbar({
        content: '#vip-deadline',
        position: 'top',
        adjustment: 25
    });
    $('img[data-toolbar="star-toolbar"]').toolbar({
        content: '#star-deadline',
        position: 'top',
        adjustment: 25
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
