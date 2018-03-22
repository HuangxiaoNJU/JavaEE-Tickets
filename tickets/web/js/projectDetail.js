'use strict';

$().ready(function () {
    checkIdentity();
    let projectId = getQueryVariable('project_id');
    if (projectId === null) {
        alert('请输入正确的url');
        window.history.back();
    }
    showProjectDetail(projectId);
});

function showProjectDetail(projectId) {
    $.ajax({
        type: 'GET',
        url: '/api/projects/id/' + projectId,
        data: {},
        success: function (result) {
            if (result.success) {
                addProjectInfo(result.data);
            } else {
                alert(result.message);
                window.history.back();
            }
        },
        error: function (xhr, status, error) {
            alert('活动信息获取失败');
            window.history.back();
        }
    });
}

function addProjectInfo(project) {
    $('#project_name').text(project.name);
    $('#project_location').text(project.venueInfoVO.name + ' ' + project.venueInfoVO.location);
    $('#project_description').text(project.description);

    $('#project_begin_time').text(project.beginTime);
    $('#project_end_time').text(project.endTime);
    $('#project_type').text(project.type);
    $('#project_total_people').text(project.totalPeople);

    // 座位价格信息及下单选项
    let seatPriceList = $('#seat_price_list');
    seatPriceList.empty();
    for (let i = 0; i < project.prices.length; i++) {
        let item = project.prices[i];
        seatPriceList.append(`
            <div class="col-lg-3">
                <span>座位类型 : </span>
                <label>${item.seatName}</label>
            </div>
            <div class="col-lg-3">
                <span>座位数 : </span>
                <label>${item.seatNumber}人</label>
            </div>
            <div class="col-lg-3">
                <span>价格 : </span>
                <label>${item.price}</label>
            </div>
            <div class="col-lg-3">
                <a id="make_order_btn_${item.id}" href="/order/make?project_id=${project.id}&project_price_id=${item.id}" class="btn btn-info">线上购票</a>
                <a id="offline_order_btn_${item.id}" href="/order/make-offline?project_id=${project.id}&project_price_id=${item.id}" class="btn btn-info">线下购票</a>
            </div>
        `);
    }
}
