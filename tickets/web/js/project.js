
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

