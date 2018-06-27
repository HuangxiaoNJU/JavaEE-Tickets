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
        },
        error: function (xhr) {
            console.log(xhr);
            console.log(xhr.status);
        }
    })
}