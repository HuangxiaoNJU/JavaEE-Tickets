
$().ready(function () {
    checkIdentity();
    $('#scale').click();
});

$('#search').on('input', function () {
    $.ajax({
        type: 'GET',
        url: '/api/venues/search',
        data: {
            keywords: $('#search').val()
        },
        success: function (result) {
            if (result.success) {
                addVenueInfo(result.data);
            }
        },
        error: function (xhr) {
        }
    });
});

function showVenues(property, order) {
    $.ajax({
        type: 'GET',
        url: '/api/venues',
        data: {
            property: property,
            order: order
        },
        success: function (result) {
            if (result.success) {
                addVenueInfo(result.data);
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

function showLeftNavItem(item) {
    $('#scale').removeClass('active');
    $('#time').removeClass('active');
    $('#area').removeClass('active');

    $('#' + item).addClass('active');
}

$('#scale').click(function () {
    showLeftNavItem('scale');
    showVenues('seatNumber', 'desc');
});

$('#time').click(function () {
    showLeftNavItem('time');
    showVenues('registerTime', 'desc');
});

$('#area').click(function () {
    showLeftNavItem('area');
    showVenues('location', 'asc');
});