
$().ready(function () {
    checkIdentity();
    showProjects();
});


$('#time_asc').click(function () {
    showProjectsByX("beginTime", "asc");
});

$('#time_desc').click(function () {
    showProjectsByX("beginTime", "desc");
});

function showProjectsByX(x, order) {
    $.ajax({
        type: 'GET',
        url: '/api/projects',
        data: {
            property: x,
            order: order
        },
        success: function (result) {
            addProjectInfo(result.data);
        },
        error: function (xhr) {
            console.log(xhr.status);
        }
    });
}

function showProjects() {
    $.ajax({
        type: 'GET',
        url: '/api/projects',
        data: {},
        success: function (result) {
            addProjectInfo(result.data);
        },
        error: function (xhr) {
            console.log(xhr.status);
        }
    });
}