'use strict';

$().ready(function () {
    checkIdentity();
    showProjects();
    showVenues();
});

function showVenues() {
    $.ajax({
        type: 'GET',
        url: '/api/venues',
        success: function (result) {
            console.log(result.data);
            for (let i = 0; i < 5; i++ ) {

            }

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
            console.log(result.data);
            for (let i = 0; i < 6; i++ ) {
                let project = result.data[i];
                $('#list').append(`
            <div class="col-lg-4">
                <a target="_blank" href="/project/detail?project_id=${project.id}" class="section-show sa_recent" data-sashowname="${project.name}">
                    <div class="image-holder">
                        <img src="${project.posterURL}" alt="${project.name}">
                    </div>
                    <div class="show-detail">
                        ${project.name}
                    </div>
                    <div class="show-date">${project.beginTime.substr(0,10)}<span>起上映</span></div>
                    <div class="show-price">
                        ${project.prices[project.prices.length - 1].price}
                        <span class="price-unit">元起</span>		
					</div>
                </a>
            </div>
            `);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}


$('#channel li').click(function(){
    let type = document.getSelection().anchorNode.data;

    $('#channel li').removeClass('active');
    $(this).attr('class', 'active');
    $('#channel li a div').hide();
    $(document.getSelection().anchorNode.nextSibling).show();
});