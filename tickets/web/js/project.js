
$().ready(function () {
    checkIdentity();
    showProjects();
});

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

$('#search_project').on('input', function () {
    let search_info = $('#search_project').val();

    if (search_info === "") {
        $('#imageTurn').show();
    } else {
        $('#imageTurn').hide();
    }
    $.ajax({
        type: 'GET',
        url: '/api/projects/search',
        data: {
            keywords: search_info
        },
        success: function (result) {
            if (result.success) {
                addProjectInfo(result.data);
            } else {
                alert(result.message);
            }
        },
        error: function (xhr) {
        }
    });
});
