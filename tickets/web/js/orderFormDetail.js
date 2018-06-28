
$().ready(function () {
    let identity = checkIdentity();
    if (identity === 'visitor') {
        showSignInDialog();
        return;
    }
    if (identity !== 'user' && identity !== 'venue') {
        window.history.back();
    }
    let orderId = getQueryVariable('order_id');
    if (orderId === null) {
        alert('请输入正确的url');
        window.history.back();
    }
    if (identity === 'venue') {
        $('#user_operation').hide();
    }
    showOrderFormDetail(orderId);
});

function showOrderFormDetail(orderId) {
    $.ajax({
        type: 'GET',
        url: '/api/orders/id/' + orderId,
        data: {},
        success: function (result) {
            if (result.success) {
                addOrderDetail(result.data);
            } else {
                alert(result.message);
                window.history.back();
            }
        },
        error: function (xhr, status, error) {
            alert('订单信息获取失败，请重试');
            window.history.back();
        }
    });
}

function addOrderDetail(order) {
    $('#order_form_id').text(order.id);
    $('#order_form_purchase_type').text(order.purchaseType);
    $('#order_form_state').text(order.state);

    console.log(order.state);
    $('#order_form_project_name').text(order.projectInfoVO.name);
    $('#order_form_time_section').text(order.projectInfoVO.beginTime + ' - ' + order.projectInfoVO.endTime);
    $('#order_form_project_location').text(order.projectInfoVO.venueInfoVO.name + ' ' + order.projectInfoVO.venueInfoVO.location);

    $('#order_form_create_time').text(order.createTime);

    $('#order_form_discount').text(order.discount === 100 ? '不打折' : order.discount + ' 折');

    $('#order_form_coupon_name').text(order.couponVO === null ? '未使用' : order.couponVO.name);
    $('#order_form_coupon_money').text(order.couponVO === null ? '0' : order.couponVO.money);

    $('#order_form_seat_type').text(order.priceInfoVO.seatName);
    $('#order_form_seat_price').text(order.priceInfoVO.price);
    $('#order_form_seat_number').text(order.seatNumber);
    $('#order_form_seat_list').text(order.seatList == null ? '尚未分配' : order.seatList);

    $('#order_form_total_price').text('¥ ' + order.totalPrice);
    
    if (order.state === "已完成") {
        $('#pay').hide();
        $('#comment').show();
    } else if (order.state === "待支付") {
        $('#pay').show();
        $('#comment').hide();
    } else {

    }
}

function addAccountList(accounts) {
    let accountList = $('#account_list');
    accountList.empty();
    for (let i = 0; i < accounts.length; i++) {
        let item = accounts[i];
        accountList.append(`
            <label>
                <input type="radio" name="account" value="${item.id}"/>
            </label>
            <label style="margin-left: 5%">${item.type} ${item.number} (余额：¥ ${item.balance})</label>
            <br><br>
        `);
    }
}

$('#pay_now').click(function () {
    $.ajax({
        type: 'GET',
        url: '/api/accounts',
        data: {},
        success: function (result) {
            if (result.success) {
                addAccountList(result.data);
            }
        },
        error: function (xhr, status, error) {
        }
    });
});

$('#refund_now').click(function () {
    $.ajax({
        type: 'POST',
        url: '/api/orders/refund/id/' + getQueryVariable('order_id'),
        data: {},
        success: function (result) {
            if (result.success) {
                alert(result.message);
                location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (xhr) {
            alert('退款失败，请重试');
        }
    });
});

$('#confirm_pay').click(function () {
    let account_id = $('input[name="account"]:checked').val();
    if (account_id === undefined) {
        alert('未选择账户');
        return;
    }
    $.ajax({
        type: 'POST',
        url: '/api/orders/pay/id/' + getQueryVariable('order_id'),
        data: {
            accountId: account_id
        },
        success: function (result) {
            if (result.success) {
                alert('支付成功');
                location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            alert('支付失败，请重试');
        }
    });
});


//评分
$('#confirm_comment').click(function () {
    let mark = $('#mark').val();

    if (!(parseInt(mark) <=5 && parseInt(mark) >= 0)) {
        alert("请输入正确评分!");
    }

    $.ajax({
        type: 'POST',
        url: '/api/orders/score',
        data: {
            id: $('#order_form_id').val(),
            score: parseInt(mark),
        },
        success: function (result) {
            if (result.success) {
                alert('评分成功，感谢您的支持！');
                location.reload();
            } else {
                alert("请勿重复评分！");
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr);
            console.log(status);
        }
    })
});