
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
        let stateColor = (item.state === '已取消' || item.state === '已退款') ? 'darkred' : '#4cae4c';

        list.append(`
            <div class="col-lg-8">
                <h6>${item.createTime}</h6>
                <h5>
                    ${item.projectInfoVO.name}&nbsp;
                    <span style="margin-left: 5%; font-size: 16px" >
                        &nbsp;${item.purchaseType}
                    </span>
                </h5>
                <span>${item.projectInfoVO.venueInfoVO.location} ${item.projectInfoVO.venueInfoVO.name}</span><br>
                <span>&nbsp;${item.priceInfoVO.seatName} ${item.seatNumber}张</span><br><br>
                <!--<label>订单编号 : </label><span>&nbsp;${item.id}</span><br>-->
                <!--<label>会员折扣 : </label><span>&nbsp;${discount}</span><br>-->
                <!--<label>优惠券 : </label><span>&nbsp;${couponInfo}</span><br>-->
            </div>
            <div class="col-lg-4 text-center">
                <a href="/order/detail?order_id=${item.id}" class="btn btn-link">查看详情 >></a><br>
                <span class="status" style="color: ${stateColor}">${item.state}</span><br><br><br>
                <span class="price">¥ ${item.totalPrice}</span>
            </div>
            <hr width="1100px">
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
    showLeftNavItem('all_order');
    showOrders(-1);
});

$('#not_payed_order').click(function () {
    showLeftNavItem('not_payed_order');
    showOrders(0);
});

$('#canceled_order').click(function () {
    showLeftNavItem('canceled_order');
    showOrders(1);
});

$('#refund_order').click(function () {
    showLeftNavItem('refund_order');
    showOrders(2);
});

$('#not_allocated_order').click(function () {
    showLeftNavItem('not_allocated_order');
    showOrders(3);
});

$('#finished_order').click(function () {
    showLeftNavItem('finished_order');
    showOrders(4);
});
