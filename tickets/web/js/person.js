'use strict';

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
    $('#information').click();

});


function showUserInfo() {
    $('#list').hide();
    $('#my_info').show();
    $('#info').show();
    $('#discount').hide();
    showBasicInfo();
    showAccountInfo();
}

function showBasicInfo() {
    $.ajax({
        type: 'GET',
        url: '/api/users/cookie',
        data: {},
        success: function (result) {
            if (result.success) {
                let vo = result.data;
                $('#my_email').text(vo.email);
                $('#my_nickname').text(vo.nickname);
                $('#my_level').text(vo.level);
                $('#my_points').text(vo.points);
                $('#my_register_time').text(vo.registerTime);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

function showAccountInfo() {
    $.ajax({
        type: 'GET',
        url: '/api/accounts',
        data: {},
        success: function (result) {
            let obj = $('#my_accounts');
            if (result.success) {
                let accounts = result.data;
                if (accounts.length === 0) {
                    obj.html('<lable>您尚未绑定账户</lable>');
                    return;
                }
                obj.empty();
                for (let i = 0; i < accounts.length; i++) {
                    let item = result.data[i];
                    obj.append('<label>' + item.type + ' ' + item.number + '</label><br>');
                }
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            // alert('账户信息获取失败');
        }
    });
}

function addUserCouponInfo(userCoupons) {
    if (userCoupons.length === 0) {
        addEmptyPrompt("暂无可兑换优惠券");
        return;
    }
    let coupon_list = $('#list');
    coupon_list.empty();
    for (let i = 0; i < userCoupons.length; i++) {
        let item = userCoupons[i];
        coupon_list.append(`
             <div class="col-lg-6">
                <div class="coupon">
                <div class="col-lg-6">
                    <label class="money">¥<label class="number">${item.money}</label></label>
                </div>
                <div class="col-lg-6">
                    <span>需 <label>${item.requirePoints}</label> 积分兑换</span><br>
                    <span>我拥有 <label>${item.number}</label> 张</span><br>
                </div>
                    <a id="coupon_${item.id}" class="btn btn-link">立即兑换</a>
                </div>
             </div>
             <script>
                $("#coupon_${item.id}").click(function () {
                $.ajax({
                    type: "POST",
                    url: "/api/users/coupons",
                    data: {
                        couponId: ${item.id},
                        number: 1
                    },
                    success: function (result) {
                        alert(result.message);
                        if (result.success) {
                            $("#coupon").click();
                        }
                    },
                    error: function (xhr, status, error) {
                        console.log(xhr.status);
                        alert("兑换失败,请重试");
                    }
                });
            });
            </script>
        `);
    }
}

function showCouponInfo() {
    $('#my_info').hide();
    $('#list').show();

    $.ajax({
        type: 'GET',
        url: '/api/users/coupons',
        data: {},
        success: function (result) {
            if (result.success) {
                let userCoupons = result.data;
                addUserCouponInfo(userCoupons);
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

$('#modify_nickname').click(function () {
    $.ajax({
        type: 'POST',
        url: '/api/users/modify',
        data: {
            nickname: $('#my_nickname').val()
        },
        success: function (result) {
            if (result.success) {
                alert('修改成功');
                location.reload();
            } else {
                console.log('this');
                alert(result.message);
            }
        },
        error: function (xhr) {
            alert('修改失败，请重试');
        }
    });
});

$('#cancel_btn').click(function () {
    // TODO 弹窗提示
    $.ajax({
        type: 'DELETE',
        url: '/api/users',
        data: {},
        success: function (result) {
            if (result.success) {
                alert(result.message);
                window.location.href = '/';
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            alert('注销失败');
        }
    });
});

function showLeftNavItem(item) {
    $('#information').removeClass("active");
    $('#coupon').removeClass("active");

    $('#' + item).addClass("active");
}

$('#information').click(function () {
    showLeftNavItem('information');
    showUserInfo();
    document.getElementById("view").innerText = "基本信息";
});

$('#coupon').click(function () {
    $('#info').hide();
    $('#discount').show();
    showLeftNavItem('coupon');
    showCouponInfo();
    document.getElementById("view").innerText = "优惠券中心";
});
