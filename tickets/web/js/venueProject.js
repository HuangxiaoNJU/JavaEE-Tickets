// 发布新活动座位种类数
let seat_number = 1;

$().ready(function () {
    let identity = checkIdentity();
    if (identity === 'visitor') {
        showSignInDialog();
        return;
    }
    if (identity !== 'venue') {
        window.location.href = '/';
        return;
    }

    $('#' + localStorage.getItem("projectType")).click();
    setType(localStorage.getItem("projectType"));
});

function getProjectsAndShow(state) {
    $.ajax({
        type: 'GET',
        url: '/api/projects/venue/cookie',
        data: {
            state: state
        },
        success: function (result) {
            if (result.success) {
                addProjectInfo(result.data);
            } else {
                alert(result.message);
            }
        },
        error: function (xhr) {
            console.log(xhr.status);
            alert('获取失败，请重试');
        }
    });
}


$('#not_begin').click(function () {
    getProjectsAndShow('not_begin');
    localStorage.setItem("projectType", "not_begin");
    setType(localStorage.getItem("projectType"));
});

$('#underway').click(function () {
    getProjectsAndShow('underway');
    localStorage.setItem("projectType", "underway");
    setType(localStorage.getItem("projectType"));
});

$('#finished').click(function () {
    getProjectsAndShow('finished');
    localStorage.setItem("projectType", "finished");
    setType(localStorage.getItem("projectType"));
});


$('#check_in_btn').click(function () {
    $.ajax({
        type: 'POST',
        url: '/api/orders/check-in',
        data: {
            id: $('#check_in_order_id').val()
        },
        success: function (result) {
            if (result.success) {
                alert(result.message);
                $('#check_in_order_id').val('');
            } else {
                alert(result.message);
            }
        },
        error: function (xhr) {
            alert('订单号输入有误');
        }
    });
});

function setType(type) {
    console.log(type);
    if (type === "not_begin") {
        $('#type').text("未完成活动");
    } else if (type === "underway") {
        $('#type').text("进行中活动");
    } else {
        $('#type').text("已结束活动");
    }
}