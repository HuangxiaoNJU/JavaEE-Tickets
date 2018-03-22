'use strict';

$().ready(function () {
    checkIdentity();
    $('#hot_project').click();
});

function showVenues() {
    $.ajax({
        type: 'GET',
        url: '/api/venues',
        success: function (result) {
            addVenueInfo(result.data);
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

function showProjects() {
    $.ajax({
        type: 'GET',
        url: '/api/projects',
        success: function (result) {
            addProjectInfo(result.data);
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

function showLeftNavItem(item) {
    $('#hot_venue').removeClass("active");
    $('#hot_project').removeClass("active");
    $('#manager_introduce').removeClass("active");

    $('#' + item).addClass("active");
}

$('#hot_venue').click(function () {
    showLeftNavItem('hot_venue');
    showVenues();
});

$('#hot_project').click(function () {
    showLeftNavItem('hot_project');
    showProjects();
});

$('#manager_introduce').click(function () {
    showLeftNavItem('manager_introduce');

});
