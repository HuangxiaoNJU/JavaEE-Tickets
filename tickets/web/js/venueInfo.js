'use strict';

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
    showVenueInfo();
});

function showVenueInfo() {
    $.ajax({
        type: 'GET',
        url: '/api/venues/cookie',
        data: {},
        success: function (result) {
            if (result.success) {
                let vo = result.data;
                $('#my_email').val(vo.email);
                $('#my_name').val(vo.name);
                $('#my_seats').val(vo.seatNumber);
                $('#my_location').val(vo.location);

                $('#my_register_time').text(vo.registerTime);
            } else {
                alert(reuslt.message);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

$('#modify_btn').click(function () {
    let venueChangeVO = {};
    venueChangeVO.newName = $('#my_name').val();
    venueChangeVO.newSeatNumber = $('#my_seats').val();
    venueChangeVO.newLocation = $('#my_location').val();

    $.ajax({
        type: 'POST',
        url: '/api/venues/modify',
        contentType: 'application/json',
        data: JSON.stringify(venueChangeVO),
        success: function (result) {
            alert(result.message);
        },
        error: function (xhr) {
            alert('请求失败，请重试');
        }
    });
});