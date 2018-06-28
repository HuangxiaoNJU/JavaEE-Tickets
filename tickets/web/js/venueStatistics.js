
$().ready(function () {
    let identity = checkIdentity();
    if (identity === 'visitor') {
        showSignInDialog();
        return;
    }
    if (identity !== 'venue') {
        alert('非场馆登录');
        window.history.back();
    }

    $('#total').click();
    showStatisticsData();
    individualStatistics();
});

function drawOnlineOfflinePie(statistics) {
    let myChart = echarts.init(document.getElementById('online_offline'));

    // 指定图表的配置项和数据
    let option = {
        title : {
            text: '收入来源（结算前）',
            // subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['线上收入','线下收入']
        },
        series : [
            {
                name: '收入来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:statistics.onlineIncome, name:'线上收入'},
                    {value:statistics.offlineIncome, name:'线下收入'},
                ],
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

function drawProjectIncomeBar(statistics) {
    let myChart = echarts.init(document.getElementById('project_income'));
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
                data : statistics.projectNameList,
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
                data:statistics.projectIncomeList
            }
        ]
    };

    myChart.setOption(option);
}

function showStatisticsData() {
    $.ajax({
        type: 'GET',
        url: '/api/venues/statistics/cookie',
        data: {},
        success: function (result) {
            if (result.success) {
                let venueStatistics = result.data;
                $('#income').text('¥ ' + venueStatistics.income);
                $('#profit').text('¥ ' + venueStatistics.profit);
                drawOnlineOfflinePie(venueStatistics);
                drawProjectIncomeBar(venueStatistics);

            } else {
                alert(result.message);
            }
        },
        error: function (xhr) {
            alert('统计信息获取失败');
        }
    });
}

function individualStatistics() {
    $.ajax({
        type: 'GET',
        url: '/api/venues/individual/statistics',
        success: function (result) {
            console.log(result);
            if (result.success) {
                let venueStatistics = result.data;
                $('#presentRatio').text(venueStatistics.presentRatio);
                $('#refundRatio').text(venueStatistics.refundRatio);
                $('#totalProjectNum').text(venueStatistics.totalProjectNum);

                drawChart("profitPerDay", result.data.profitPerDay);
                drawChart("profitPerMonth", result.data.profitPerMonth);
                drawChart("profitPerYear", result.data.profitPerYear);

                drawGraghChart("typeCompare");
            }
        }
    })
}

function drawGraghChart(chartName) {
    let myChart = echarts.init(document.getElementById(chartName));

    let option = {
        tooltip: {},
        legend: {
            data: ['年度收益', '月度收益']
        },
        radar: {
            // shape: 'circle',
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
                }
            },
            indicator: [
                { name: '讲座', max: 1000},
                { name: '电子竞技', max: 100},
                { name: '戏剧', max: 1000},
                { name: '篮球比赛', max: 400},
                { name: '典礼', max: 400},
                { name: '演唱会', max: 1000}
            ]
        },
        series: [{
            name: '时间维收益',
            type: 'radar',
            data : [
                {
                    value : [337.1, 0, 0, 0, 0, 0],
                    name : '2018年度总收益'
                },
                {
                    value : [28.1, 0, 0, 0, 0, 0],
                    name : '平均月度收益'
                }
            ]
        }]
    };

    myChart.setOption(option);
}


function drawChart(chartName, consumePerDay) {

    let dayTime = [];
    let dayConsume = [];
    for (let i in consumePerDay) {
        dayTime.push(i);
        dayConsume.push(parseInt(consumePerDay[i]));
    }

    let myChart = echarts.init(document.getElementById(chartName));
    let option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {
                type : 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: dayTime
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: dayConsume,
            type: 'bar',
            smooth: true
        }]
    };

    myChart.setOption(option);

}


$('#not_begin').click(function () {
    localStorage.setItem("projectType", "not_begin");
    window.location.href = "/venue/project";
});

$('#underway').click(function () {
    localStorage.setItem("projectType", "underway");
    window.location.href = "/venue/project";
});

$('#finished').click(function () {
    localStorage.setItem("projectType", "finished");
    window.location.href = "/venue/project";
});

$('#total').click(function () {
    $('#total').addClass("active");
    $('#comment').removeClass("active");
    $('#perform').removeClass("active");

    $('#total_view').show();
    $('#comment_view').hide();
    $('#perform_view').hide();

});

$('#perform').click(function () {
    $('#perform').addClass("active");
    $('#comment').removeClass("active");
    $('#total').removeClass("active");

    $('#perform_view').show();
    $('#comment_view').hide();
    $('#total_view').hide();

});

$('#comment').click(function () {
    $('#comment').addClass("active");
    $('#total').removeClass("active");
    $('#perform').removeClass("active");

    $('#comment_view').show();
    $('#total_view').hide();
    $('#perform_view').hide();

});