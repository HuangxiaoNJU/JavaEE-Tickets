
let firstSeatLabel = 1;
let price;
let choosedSeats = [];

$().ready(function() {
    let identity = checkIdentity();
    if (identity !== 'venue') {
        alert('请以场馆身份登录后下单');
        window.history.back();
    }
    showProjectAndSeatMap();
});

function setSeatMap(seatPrice) {
    let map = [];
    let row = '';
    for (let j = 0; j < seatPrice.column; j++) {
        row = row + 'e';
    }
    for (let i = 0; i < seatPrice.row; i++) {
        map[i] = row;
    }
    let sc = $('#seat-map').seatCharts({
        map: map,
        seats: {
            f: {
                price: seatPrice.price,
                classes: 'first-class', //your custom CSS class
                category: '未预订'
            },
        },
        naming: {
            top: false,
            getLabel: function (character, row, column) {
                return firstSeatLabel = row + '_' + column;
            },
        },
        legend : {
            node : $('#legend'),
            items : [
                [ 'e', 'available',   '未预定'],
                [ 'e', 'unavailable', '已预订']
            ]
        },
        click: function () {
            if (this.status() === 'available') {
                if (sc.find('selected').length >= 6) {
                    alert('选座购买最多选择6个座位');
                    return 'available';
                }
                reCollect(sc, this.settings.id);
                return 'selected';
            } else if (this.status() === 'selected') {
                reCollect(sc, this.settings.id);
                return 'available';
            } else if (this.status() === 'unavailable') {
                //seat has been already booked
                return 'unavailable';
            } else {
                return this.style();
            }
        }
    });
    sc.get(seatPrice.unavailableList).status('unavailable');
}

function reCollect(sc, id) {
    let isExist = false;
    choosedSeats.splice(0, choosedSeats.length);
    sc.find('selected').each(function () {
        if (this.settings.id === id) {
            isExist = true;
            return;
        }
        choosedSeats.push(this.settings.id);
    });
    if (!isExist) {
        choosedSeats.push(id);
    }
    showRealTimeOrderPrice(choosedSeats.length);
}

function addProjectInfo(project) {
    $('#order_form_project_name').text(project.name);
    $('#order_form_time_section').text(project.beginTime + ' - ' + project.endTime);
    $('#order_form_project_location').text(project.venueInfoVO.name + ' ' + project.venueInfoVO.location);

    let projectPriceId = getQueryVariable('project_price_id');
    let exist = false;
    for (let i = 0; i < project.prices.length; i++) {
        let item = project.prices[i];
        if (item.id.toString() === projectPriceId) {
            $('#order_form_seat_type').text(item.seatName);
            $('#order_form_seat_price').text(item.price);
            price = item.price;
            setSeatMap(item);
            exist = true;
            break;
        }
    }
    if (!exist) {
        alert('url参数错误');
        window.history.back();
    }
}

function showProjectAndSeatMap() {
    let projectId = getQueryVariable('project_id');

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
        error: function (xhr) {
            alert('信息获取失败');
            window.history.back();
        }
    });
}

$('#confirm_make_order').click(function() {
    let orderFormAddVO = {};
    orderFormAddVO.purchaseType = 0;
    orderFormAddVO.projectPriceId = getQueryVariable('project_price_id');
    orderFormAddVO.seatList = choosedSeats;

    orderFormAddVO.seatNumber = choosedSeats.length;

    $.ajax({
        type: 'POST',
        url: '/api/orders',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(orderFormAddVO),
        success: function (result) {
            if (result.success) {
                alert(result.message);
                window.location.href = '/order/detail?order_id=' + result.data;
            } else {
                alert(result.message);
            }
        },
        error: function (xhr) {
            alert('下单出错，请重试');
        }
    });
});

function showRealTimeOrderPrice() {
    $('#real_time_money').text('¥ ' + (price * choosedSeats.length));
}

