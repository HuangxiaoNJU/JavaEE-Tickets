$().ready(function () {
    checkIdentity();
    getUserStatistics();

});


function getUserStatistics() {
    $.ajax({
        type: 'GET',
        url: '/api/users/individual/statistics',
        success: function (result) {
            console.log(result);
            $('#totalPoints').text(result.data.totalPoints);
            $('#couponPoints').text(result.data.couponPoints);
            $('#totalConsume').text(result.data.totalPoints);
            $('#refundRatio').text(result.data.refundRatio);
            $('#averageEvaluation').text(result.data.averageEvaluation);
            $('#payOrderNum').text(result.data.payOrderNum);
            $('#refundOrderNum').text(result.data.refundOrderNum);

            drawChart("consume", result.data.consumePerDay);
            drawChart("monthConsume", result.data.consumePerMonth);
        },
        error: function (xhr) {
            console.log(xhr);
            console.log(xhr.status);
        }
    })
}

function drawChart(chartName, consumePerDay) {

    let dayTime = [];
    let dayConsume = [];
    for (let i in consumePerDay) {
        dayTime.push(i);
        dayConsume.push(consumePerDay[i]);
    }

    let myChart = echarts.init(document.getElementById(chartName));
    let option = {
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