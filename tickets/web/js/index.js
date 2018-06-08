'use strict';

$().ready(function () {
    checkIdentity();
    showProjects();
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

