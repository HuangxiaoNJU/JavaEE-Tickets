
$().ready(function () {
    let identity = checkIdentity();
    if (identity === 'visitor') {
        showSignInDialog();
        return;
    }
    if (identity !== 'user') {
        window.location.href = '/';
        return;
    }
    $('#all_order').click();
});

function addOrderInfo(orders) {
    console.log(orders[0]);
    let list = $('#list');
    list.empty();
    if (orders.length === 0) {
        addEmptyPrompt("未找到订单");
        return;
    }
    for (let i = 0; i < orders.length; i++) {
        let item = orders[i];
        let discount = item.discount === 100 ? '不打折' : item.discount + '折';
        let couponInfo = item.couponVO === null ? '未使用' : item.couponVO.name;
        let typeColor = (item.purchaseType === '选座购买' || item.state === '立即购买') ? 'skyblue' : '#4cae4c';

        list.append(`
            <div class="order-i">
            <div class="title">
                <label style="color: #2a6496">${item.state}</label>
                <span style="float: right">${item.createTime}</span> 
            </div>
            <div class="col-lg-3">
                <img src="${item.projectInfoVO.posterURL}" class="pre-img">
            </div>
            <div class="col-lg-6">
                <h6><a href="/order/detail?order_id=${item.id}" class="btn-link">
                    ${"【" + item.projectInfoVO.venueInfoVO.location.split(' ')[0] + "站】" +item.projectInfoVO.name}
                    </a>
                </h6>
                <span>场馆名字： ${item.projectInfoVO.venueInfoVO.name}</span><br>
                <span>场馆地址： ${item.projectInfoVO.venueInfoVO.location}</span><br>
                <span>购买类型： <label style="color: ${typeColor}">${item.purchaseType}</label></span><br>
                <span>座位类型： ${item.priceInfoVO.seatName} ${item.seatNumber}张</span><br><br>
                <!--<label>订单编号 : </label><span>&nbsp;${item.id}</span><br>-->
                <!--<label>会员折扣 : </label><span>&nbsp;${discount}</span><br>-->
                <!--<label>优惠券 : </label><span>&nbsp;${couponInfo}</span><br>-->
            </div>
            <div class="col-lg-3 text-center">
                <div style="margin-top: 20px">
                <span style="color: lightsalmon">¥ </span><span class="price">${item.totalPrice}</span>
                </div>
            </div>
            </div>
        `);
    }
}

function showOrders(state) {
    $.ajax({
        type: 'GET',
        url: '/api/orders/user',
        data: {
            state: state
        },
        success: function (result) {
            if (result.success) {
                addOrderInfo(result.data);
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            alert('获取失败，请重试');
        }
    });
}

function showLeftNavItem(item) {
    $('#all_order').removeClass("active");
    $('#finished_order').removeClass("active");
    $('#not_payed_order').removeClass("active");
    $('#canceled_order').removeClass("active");
    $('#refund_order').removeClass("active");
    $('#not_allocated_order').removeClass("active");

    $('#' + item).addClass("active");
}

$('#all_order').click(function () {
    $('#type').text("所有订单");
    showLeftNavItem('all_order');
    showOrders(-1);
});

$('#not_payed_order').click(function () {
    $('#type').text("未支付订单");
    showLeftNavItem('not_payed_order');
    showOrders(0);
});

$('#canceled_order').click(function () {
    $('#type').text("已取消订单");
    showLeftNavItem('canceled_order');
    showOrders(1);
});

$('#refund_order').click(function () {
    $('#type').text("已退款订单");
    showLeftNavItem('refund_order');
    showOrders(2);
});

$('#not_allocated_order').click(function () {
    $('#type').text("待分配订单");
    showLeftNavItem('not_allocated_order');
    showOrders(3);
});

$('#finished_order').click(function () {
    $('#type').text("已完成订单");
    showLeftNavItem('finished_order');
    showOrders(4);
});
