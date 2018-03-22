'use strict';

$().ready(function () {
    let identity = checkIdentity();
    if (identity === 'visitor') {
        showSignInDialog();
        return;
    }
    if (identity !== 'manager') {
        window.location.href = '/';
        return;
    }
    $('#not_allocated').click();
});

function addProjectIncomeInfo(projectIncomes, isAllocated) {
    if (projectIncomes.length === 0) {
        addEmptyPrompt('所有结束活动均已分配');
        return;
    }
    let list = $('#list');
    list.empty();
    for (let i = 0; i < projectIncomes.length; i++) {
        let item = projectIncomes[i];
        list.append(`
            <div class="col-lg-8">
                <h5 id="id">${item.projectInfoVO.name}</h5>
                <span>${item.projectInfoVO.beginTime}&nbsp;至&nbsp;${item.projectInfoVO.endTime}</span><br><br>
                <label>场馆 : </label><span>&nbsp;${item.projectInfoVO.venueInfoVO.name}</span><br>
                <label>地点 : </label><span>&nbsp;${item.projectInfoVO.venueInfoVO.location}</span><br>
            </div>
        `);
        if (!isAllocated) {
            list.append(`
                <div class="col-lg-4">
                    <a href="/project/detail?project_id=${item.projectInfoVO.id}" class="btn btn-link">查看详情 >></a><br>
                    <label>线上总收入：</label><span>${item.onlineIncome}</span><br>
                    <label>线下总收入：</label><span>${item.offlineIncome}</span><br><br>
                    <!--<input class="input" type="text" placeholder="Tickets分配比例"><br>-->
                    <a id="allocate_project_${item.projectInfoVO.id}" class="btn btn-info">立即结算</a>
                </div>
                <hr width="1100px">
                <script>
                    $('#allocate_project_${item.projectInfoVO.id}').click(function() {
                        $.ajax({
                            type: 'POST',
                            url: '/api/projects/allocate',
                            data: {
                                projectId: ${item.projectInfoVO.id},
                                ratio: $('#ratio').val()
                            },
                            success: function(result) {
                                if (result.success) {
                                    alert(result.message);
                                    location.reload();
                                } else {
                                    alert(result.message);
                                }
                            },
                            error: function(xhr) {
                                alert('请输入正确的比例');
                            }
                        });
                    });
                </script>
            `);
        } else {
            list.append(`
                <div class="col-lg-4">
                    <a href="/project/detail?project_id=${item.projectInfoVO.id}" class="btn btn-link">查看详情 >></a><br>
                    <label>线上总收入：</label><span>${item.onlineIncome}</span><br>
                    <label>线下总收入：</label><span>${item.offlineIncome}</span><br><br>
                    <label>平台所得收入：</label><span>${item.platformIncome}</span><br>
                    <label>场馆所得收入：</label><span>${item.venueIncome}</span><br>
                </div>
                <hr width="1100px">
            `);
        }
    }
}

function showAllocated(isAllocated) {
    $.ajax({
        type: 'GET',
        url: '/api/projects/allocate',
        data: {
            isAllocated: isAllocated,
        },
        success: function (result) {
            if (result.success) {
                addProjectIncomeInfo(result.data, isAllocated);
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            alert('获取失败，请重试');
        }
    });
}

function showLeftNavItem(item) {
    $('#not_allocated').removeClass("active");
    $('#already_allocated').removeClass("active");

    $('#' + item).addClass("active");
}

$('#not_allocated').click(function () {
    showLeftNavItem('not_allocated');
    showAllocated(false);
});

$('#already_allocated').click(function () {
    showLeftNavItem('already_allocated');
    showAllocated(true);
});
