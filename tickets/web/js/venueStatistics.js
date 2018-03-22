
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

    showStatisticsData();
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
        title: {
            text: '场馆各活动收入柱状图',
            x: 'center'
        },
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