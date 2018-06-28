
$().ready(function () {
    let identity = checkIdentity();
    if (identity === 'visitor') {
        showSignInDialog();
        return;
    }
    if (identity !== 'manager') {
        alert('非经理登录');
        window.history.back();
    }
    $('#platform_statistics').click();
});

function drawLevelNumberPie(statistics) {
    let myChart = echarts.init(document.getElementById('user_level'));

    let data = [];
    for (let i = 0; i < statistics.levelList.length; i++) {
        data[i] = {value: statistics.numberList[i], name: statistics.levelList[i] + '级'};
    }

    let option = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: data
        },
        series : [
            {
                name: '会员等级分布',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data: data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    myChart.setOption(option);
}

function showUserStatistics() {
    $.ajax({
        type: 'GET',
        url: '/api/users/statistics',
        data: {},
        success: function (result) {
            if (result.success) {
                drawLevelNumberPie(result.data);
            }
        },
        error: function (xhr) {
            alert('获取信息失败');
        }
    });
}

function drawVenueIncomeBar(statistics) {
    let myChart = echarts.init(document.getElementById('venues_income'));
    let option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : statistics.venueList,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'收入',
                type:'bar',
                barWidth: '60%',
                data: statistics.incomeList
            }
        ]
    };

    myChart.setOption(option);
}

function showVenueStatistics() {
    $.ajax({
        type: 'GET',
        url: '/api/venues/statistics',
        data: {},
        success: function (result) {
            if (result.success) {
                drawVenueIncomeBar(result.data);
            }
        },
        error: function (xhr) {
            alert('获取信息失败');
        }
    });
}

$('#user_statistics').click(function () {
    activeLi("user");
    showLi("user");

    showUserStatistics();
    $('#type').text("会员信息统计");
});

$('#venue_statistics').click(function () {
    activeLi("venue");
    showLi("venue");

    showVenueStatistics();
    $('#type').text("场馆信息统计");
});

$('#platform_statistics').click(function () {
    activeLi("platform");
    showLi("platform");

    showUserStatistics();
    $('#type').text("平台收益统计");
});

$('#perform_statistics').click(function () {
    activeLi("perform");
    showLi("perform");

    showVenueStatistics();
    $('#type').text("演出信息统计");
});

function showLi(showName) {
    $('#platform_list').hide();
    $('#perform_list').hide();
    $('#venues_list').hide();
    $('#user_list').hide();

    $('#' + showName + '_list').show();
}

function activeLi(activeName) {
    $('#user_statistics').removeClass('active');
    $('#venue_statistics').removeClass('active');
    $('#platform_statistics').removeClass('active');
    $('#perform_statistics').removeClass('active');

    $('#' + activeName + '_statistics').addClass('active');
}