
let firstSeatLabel = 1;
let choseSeats = [];

$().ready(function() {
    let identity = checkIdentity();
    if (identity !== 'user') {
        alert('请以用户身份登录后下单');
        window.history.back();
    }
    showProjectAndSeatMap();
    showUserDiscount();
});

function showUserDiscount() {
    // 显示会员对应折扣
    $.ajax({
        type: 'GET',
        url: '/api/users/cookie',
        data: {},
        success: function (result) {
            if (result.success) {
                let discount = result.data.discount;
                $('#order_form_discount').text(discount === 100 ? '不打折' : discount + '折');
            }
        },
        error: function (xhr, status, error) {
            $('#order_form_discount').text('数据获取失败');
        }
    });
}

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
    choseSeats.splice(0, choseSeats.length);
    sc.find('selected').each(function () {
        if (this.settings.id === id) {
            isExist = true;
            return;
        }
        choseSeats.push(this.settings.id);
    });
    if (!isExist) {
        choseSeats.push(id);
    }
    showRealTimeOrderPrice(choseSeats.length);
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

$('input[type=radio][name=purchase_type]').change(function () {
    $('#real_time_money').text('¥ 0');
    let purchaseType = $('input[name="purchase_type"]:checked').val();
    if (purchaseType === '0') {
        $('#not_choose_seat').hide();
        $('#choose_seat').show();
        showRealTimeOrderPrice(choseSeats.length);
    } else if (purchaseType === '1') {
        $('#not_choose_seat').show();
        $('#choose_seat').hide();
        showRealTimeOrderPrice($('#buy_seat_number').val());
    } else {
        $('#not_choose_seat').hide();
        $('#choose_seat').hide();
    }
});

$('#confirm_make_order').click(function() {
    let orderFormAddVO = {};
    let purchaseType = $('input[name="purchase_type"]:checked').val();
    orderFormAddVO.purchaseType = purchaseType;
    orderFormAddVO.projectPriceId = getQueryVariable('project_price_id');
    orderFormAddVO.seatList = choseSeats;

    if (purchaseType === '0') {
        orderFormAddVO.seatNumber = choseSeats.length;
    } else {
        orderFormAddVO.seatNumber = $('#buy_seat_number').val();
    }

    // TODO 优惠券选择
    orderFormAddVO.couponId = null;

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
        error: function (xhr, status, error) {
            alert('下单出错，请重试');
        }
    });
});

function showRealTimeOrderPrice(seatNumber) {
    let orderFormInfo = {};
    // orderFormInfo.purchaseType = $('input[name="purchase_type"]:checked').val();
    orderFormInfo.projectPriceId = getQueryVariable('project_price_id');
    orderFormInfo.seatNumber = seatNumber;
    // TODO 优惠券选择
    orderFormInfo.couponId = null;

    $.ajax({
        type: 'POST',
        url: '/api/orders/price',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(orderFormInfo),
        success: function (result) {
            if (result.data !== null) {
                $('#real_time_money').text('¥ ' + result.data);
            } else {
                $('#real_time_money').text('¥ 0');
            }
        },
        error: function (xhr, status, error) {
            $('#real_time_money').text('请输入正确金额');
        }
    });
}

$('#buy_seat_number').on('input', function () {
    let seatNumber = $('#buy_seat_number').val();
    showRealTimeOrderPrice(seatNumber);
});

