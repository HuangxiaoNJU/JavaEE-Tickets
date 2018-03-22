
$().ready(function () {
    checkIdentity();
    $('#recent').click();
});

function showRecentProjects() {
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

function showHotProjects() {

}

function showLeftNavItem(item) {
    $('#recent').removeClass("active");
    $('#hot').removeClass("active");

    $('#' + item).addClass("active");
}

$('#hot').click(function () {
    showLeftNavItem('hot');
    showHotProjects();
});

$('#recent').click(function () {
    showLeftNavItem('recent');
    showRecentProjects();
});

$('#search_project').on('input', function () {
    $.ajax({
        type: 'GET',
        url: '/api/projects/search',
        data: {
            keywords: $('#search_project').val()
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
