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
            for (let i = 0; i < 5; i++ ) {
                let venue = result.data[i];
                $('#venue').append(`
                <a target="_blank" class="sa_hot_click best-show-item">
						<div class="show-index">${(i + 1)}</div>
						<div class="show-detail">
							<div class="show-name">
								${venue.name}
							</div>
							<div class="show-info">
								${venue.location}
							</div>
							<div class="show-info">
								${venue.email}
							</div>
						</div>
					</a>
                `)
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
                   
                    <div class="show-price">
                        ${project.prices[project.prices.length - 1].price}
                        <span class="price-unit">元起</span>		
					</div>
					<div class="show-date">${project.beginTime.substr(0,10)}<span>起上映</span></div>
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

    getProjectsByType(type);
});

function getProjectsByType(type) {
    $.ajax({
        type: 'GET',
        url: '/api/projects/type',
        data: {
            type: type
        },
        success: function (result) {
            if (result.success) {
                console.log(result.data);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    })
}
