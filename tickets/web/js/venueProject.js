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
    $('#not_begin').click();
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

function showReleaseProjectForm() {
    $('#list').empty();
    $('#release_project_info').show();
}

function showLeftNavItem(item) {
    $('#not_begin').removeClass("active");
    $('#underway').removeClass("active");
    $('#finished').removeClass("active");
    $('#release_project').removeClass("active");

    $('#' + item).addClass("active");
}

$('#not_begin').click(function () {
    $('#release_project_info').hide();
    showLeftNavItem('not_begin');
    getProjectsAndShow('not_begin');
});

$('#underway').click(function () {
    $('#release_project_info').hide();
    showLeftNavItem('underway');
    getProjectsAndShow('underway');
});

$('#finished').click(function () {
    $('#release_project_info').hide();
    showLeftNavItem('finished');
    getProjectsAndShow('finished');
});

$('#release_project').click(function () {
    showLeftNavItem('release_project');
    showReleaseProjectForm();
});

$('#confirm_release_btn').click(function () {
    let projectAddVO = {};
    projectAddVO.name = $('#new_project_name').val();
    projectAddVO.beginTime = $('#new_project_begin_time').val();
    projectAddVO.endTime = $('#new_project_end_time').val();
    projectAddVO.type = $('#new_project_type').val();
    projectAddVO.description = $('#new_project_description').val();
    projectAddVO.prices = [];
    for (let i = 0; i < seat_number; i++) {
        let projectPriceVO = {};
        projectPriceVO.seatName = $('#new_project_seat_type_' + (i + 1)).val();
        projectPriceVO.seatPrice = $('#new_project_seat_price_' + (i + 1)).val();
        projectPriceVO.row = $('#new_project_seat_row_' + (i + 1)).val();
        projectPriceVO.column = $('#new_project_seat_column_' + (i + 1)).val();
        projectAddVO.prices[i] = projectPriceVO;
    }

    $.ajax({
        type: 'POST',
        url: '/api/projects',
        contentType: 'application/json',
        data: JSON.stringify(projectAddVO),
        success: function (result) {
            if (result.success) {
                alert(result.message);
                $('#not_begin').click();
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            alert('发布失败，请重试');
        }
    });
});

$('#begin_time_calendar').calendar({
    trigger: '#new_project_begin_time',
    zIndex: 999,
    format: 'yyyy-mm-dd',
    onSelected: function (view, date, data) {
        // console.log('event: onSelected');
    },
    onClose: function (view, date, data) {
        // console.log('event: onClose');
        // console.log('view:' + view);
        // console.log('date:' + date);
        // console.log('data:' + (data || 'None'));
    }
});

$('#end_time_calendar').calendar({
    trigger: '#new_project_end_time',
    zIndex: 999,
    format: 'yyyy-mm-dd',
    onSelected: function (view, date, data) {
        // console.log('event: onSelected');
    },
    onClose: function (view, date, data) {
        // console.log('event: onClose');
        // console.log('view:' + view);
        // console.log('date:' + date);
        // console.log('data:' + (data || 'None'));
    }
});

$('.choose_seat_type li a').click(function () {
        $('.choose_seat_type li').removeClass("choose_this");
        $(this).parent().addClass("choose_this");
        let typeNum = document.getElementsByClassName("choose_this").item(0).innerText;
        seat_number = typeNum;
        $("#num_of_seat_type").attr('value', typeNum);

        let seatTypeList = $('#seat_type');
        seatTypeList.empty();
        for (let i = 0; i < typeNum; i++ ) {
            seatTypeList.append(`
                <div class="col-lg-4">
                    <label>座位类型${i + 1} : </label>
                </div>
                <div class="col-lg-8">
                    <input id="new_project_seat_type_${i + 1}" class="input_seat input" placeholder="座位名称">
                    <input id="new_project_seat_price_${i + 1}" class="input_seat input" placeholder="座位单价">
                    <input id="new_project_seat_row_${i + 1}" class="input_seat input" placeholder="行数">
                    <input id="new_project_seat_column_${i + 1}" class="input_seat input" placeholder="列数">
                </div>
            `);
        }
    }
);

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