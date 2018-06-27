'use strict';

function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if(pair[0] === variable) {
            return pair[1];
        }
    }
    return null;
}

// 导航栏
$('#head').append(`
    <div id="navigation">
        <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="row">
                    <div class="col-md-2 mob-logo">
                        <div class="row">
                            <div class="site-logo">
                                 <img src="../image/logo.png"/>
                            </div>
                        </div>
                    </div>
                    <div class="mob-menu">
                        <div class="row">
                            <div class="collapse navbar-collapse" id="menu">
                                <ul class="nav navbar-nav navbar-right">
                                    <li class="active"><a href="/">主页</a></li>
                                    <li><a href="/project">活动</a></li>
                                    <li><a href="/venue">场馆</a></li>
                                    <li><a id="sign" data-toggle="modal" data-target="#loginWindow">登录/注册</a></li>
                                    <li id="user_item" style="display: none" class="dropdown">
                                        <a class="dropdown-toggle" data-toggle="dropdown">欢迎,
                                        <label id="user_name"></label></a>
                                        <ul class="dropdown-menu">
                                            <li><a href="/user/person">我的信息</a></li>
                                            <li><a href="/user/order">我的订单</a></li>
                                            <li><a href="/user/statistics">我的统计</a></li>
                                            <li><a id="user_logout">登出</a></li>
                                        </ul>
                                    </li>
                                    <li id="venue_item" style="display: none" class="dropdown">
                                        <a class="dropdown-toggle" data-toggle="dropdown">欢迎,
                                            <label id="venue_name"></label></a>
                                        <ul class="dropdown-menu">
                                            <li><a href="/venue/info">场馆信息</a></li>
                                            <li><a href="/venue/project">场馆活动</a></li>
                                            <li><a href="/venue/statistics">统计信息</a></li>
                                            <li><a id="venue_logout">登出</a></li>
                                        </ul>
                                    </li>
                                    <li id="manager_item" style="display: none" class="dropdown">
                                        <a class="dropdown-toggle" data-toggle="dropdown">欢迎,
                                            <label id="manager_name"></label></a>
                                        <ul class="dropdown-menu">
                                            <li><a href="/manage/check">审核场馆</a></li>
                                            <li><a href="/manage/allocate">收入分配</a></li>
                                            <li><a href="/manage/statistics">统计信息</a></li>
                                            <li><a id="manager_logout">登出</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </div>

    <div class="modal fade" id="loginWindow" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <span>登录</span>
                </div>
                <div class="modal-body">
                    <table>
                        <th><a class="btn-self active" id="user_login">会员登录</a></th>
                        <th><a class="btn-self" id="venue_login">场馆登录</a></th>
                        <th><a class="btn-self" id="manager_login">经理登录</a></th>
                    </table>
                    <div class="inputView">
                        <input id="username" class="input" type="text" placeholder="邮箱"/>
                        <br>
                        <input id="password" class="input" type="password" placeholder="密码"/>
                        <br>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="/" class="btn btn-link" style="right: 200px;"> << 返回首页</a>
                    <button id="login" class="btn login" data-dismiss="modal">登录</button>
                    <button class="btn login" data-dismiss="modal" data-toggle="modal" data-target="#signupWindow">去注册</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="signupWindow" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <span>注册</span>
                </div>
                <div class="modal-body">
                    <table>
                        <th><a class="btn-self active" id="user_signup">会员注册</a></th>
                        <th><a class="btn-self" id="venue_singup">场馆注册</a></th>
                    </table>
                    <div class="inputView">
                        <input id="register_email" class="input" type="text" placeholder="邮箱"/>
                        <br>
                        <div class="col-lg-8">
                            <input id="verification" class="input" type="text" placeholder="验证码"/>
                        </div>
                        <div class="col-lg-4">
                            <button class="btn login" id="sendBtn">发送验证码</button>
                        </div>
                        <br>
                        <div id="user_special_info">
                           <input id="register_nickname" class="input" type="text" placeholder="昵称"/><br>
                           <input id="password_first" class="input" type="password" placeholder="密码"/><br>
                           <input id="password_again" class="input" type="password" placeholder="确认密码"/><br>
                        </div>
                        <div id="venue_special_info" style="display: none">
                           <input id="venue_register_name" class="input" type="text" placeholder="场馆名"><br>
                           <input id="venue_register_location" class="input" type="text" placeholder="地点"><br>
                           <input id="venue_register_seat_number" class="input" type="text" placeholder="座位数">
                        </div>
                     </div>
                </div>
                <div class="modal-footer">
                    <button id="user_register_btn" class="btn login" data-dismiss="modal">注册</button>
                    <button id="venue_register_btn" class="btn login" data-dismiss="modal" style="display: none">注册</button>
                </div>
            </div>
        </div>
    </div>
`);

let identityToLogin = 'user';

/**
 * 检查当前用户身份
 */
function checkIdentity() {
    let identity = 'visitor';
    $.ajax({
        type: 'GET',
        url: '/api/users/identity',
        data: {},
        async: false,
        success: function (result) {
            identity = result.message;
            if (identity !== 'visitor') {
                $('#sign').hide();
                $('#' + identity + '_name').text(result.data);
                $('#' + identity + '_item').show();
            }
        },
        error: function (xhr, status, error) {

        }
    });
    return identity;
}

function showSignInDialog() {
    $('#sign').click();
}

function logout() {
    $.ajax({
        type: 'POST',
        url: '/api/users/logout',
        data: {},
        success: function (result) {
            alert(result.message);
            location.href = "/";
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
}

$('#login').click(
    function () {
        $.ajax({
            type: 'POST',
            url: '/api/' + identityToLogin + 's/login',
            data: {
                username: $('#username').val(),
                password: $('#password').val()
            },
            success: function (result) {
                if (result.success) {
                    if (identityToLogin === 'user') {
                        location.reload();
                    } else if (identityToLogin === 'venue') {
                        window.location.href = "/venue/project";
                    } else {
                        location.reload();
                    }
                } else {
                    alert(result.message);
                    showSignInDialog();
                }
            },
            error: function (xhr) {
                alert('登录出错，请重试');
            }
        });
    }
);

$('#manager_logout').click(function () {
    logout();
});

$('#user_logout').click(function () {
    logout();
});

$('#venue_logout').click(function () {
    logout();
});

function showSignInIdentity(identityName) {
    $('#user_login').removeClass("active");
    $('#venue_login').removeClass("active");
    $('#manager_login').removeClass("active");

    $('#' + identityName + '_login').addClass("active");

}

$('#user_login').click(
    function () {
        identityToLogin = 'user';
        showSignInIdentity("user");
        $('#username').attr("placeholder", "邮箱");
        $('#password').attr("placeholder", "密码");
    }
);

$('#venue_login').click(
    function () {
        identityToLogin = 'venue';
        showSignInIdentity("venue");
        $('#username').attr("placeholder", "邮箱");
        $('#password').attr("placeholder", "识别码");
    }
);

$('#manager_login').click(
    function () {
        identityToLogin = 'manager';
        showSignInIdentity("manager");
        $('#username').attr("placeholder", "经理用户名");
        $('#password').attr("placeholder", "密码");
    }
);

$('#user_signup').click(
    function () {
        $('#venue_register_btn').hide();
        $('#user_register_btn').show();

        $('#user_signup').addClass("active");
        $('#venue_singup').removeClass("active");

        $('#venue_special_info').hide();
        $('#user_special_info').show();
    }
);

$('#venue_singup').click(
    function () {
        $('#user_register_btn').hide();
        $('#venue_register_btn').show();

        $('#venue_singup').addClass("active");
        $('#user_signup').removeClass("active");

        $('#user_special_info').hide();
        $('#venue_special_info').show();
    }
);

$('#sendBtn').click(
    function () {
        let email = $('#register_email').val();
        $.ajax({
            type: 'GET',
            url: '/api/users/verification',
            data: {
                email: email
            },
            success: function (result) {
                if (!result.success) {
                    alert(result.message);
                }
            },
            error: function (xhr, status, error) {
                alert('发送失败');
                console.log(xhr.status);
            }
        });
    }
);

$('#user_register_btn').click(function () {
    let password = $('#password_first').val();
    let password_again = $('#password_again').val();
    if (password !== password_again) {
        alert('密码输入不一致');
        return;
    }
    let registerVO = {};
    registerVO.password = password;
    registerVO.email = $('#register_email').val();
    registerVO.verificationCode = $('#verification').val();
    registerVO.nickname = $('#register_nickname').val();

    $.ajax({
        type: 'POST',
        url: '/api/users',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(registerVO),
        success: function (result) {
            if (result.success) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
});

$('#venue_register_btn').click(function () {
    let registerVO = {};

    registerVO.email = $('#register_email').val();
    registerVO.name = $('#venue_register_name').val();
    registerVO.verificationCode = $('#verification').val();
    registerVO.location = $('#venue_register_location').val();
    registerVO.seatNumber = $('#venue_register_seat_number').val();

    $.ajax({
        type: 'POST',
        url: '/api/venues',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(registerVO),
        success: function (result) {
            if (result.success) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
        }
    });
});

function addEmptyPrompt(message) {
    let list = $('#list');
    list.html('<label style="margin-left: 40%">' + message+ '</label>');
}

function addProjectInfo(projects) {
    let list = $('#list');
    list.empty();
    if (projects.length === 0) {
        addEmptyPrompt("未找到活动");
        return;
    }
    for (let i = 0; i < projects.length; i++) {
        let item = projects[i];
        list.append(`
            <div class="col-lg-6">
                <div class="col-lg-8">
                    <h5 id="id">${item.name}</h5>
                    <span>${item.beginTime}&nbsp;至&nbsp;${item.endTime}</span><br><br>
                    <label>活动简介 : </label><br>
                    <p  style="margin-left: 5%">
                        ${item.description.substr(0, 40)} · · ·
                    </p>
                </div>
                <div class="col-lg-4">
                    <span style="margin: 8%">${item.type}</span><br>
                    <a href="/project/detail?project_id=${item.id}" class="btn btn-link">查看详情 >></a>   
                </div>
                <br>
            </div>
        `);
    }
}

function addVenueInfo(venues) {
    let list = $('#list');
    list.empty();
    if (venues.length === 0) {
        addEmptyPrompt("未找到场馆");
        return;
    }
    for (let i = 0; i < venues.length; i++) {
        let item = venues[i];
        list.append(`
            <div style="box-shadow: 0 1px gainsboro;padding: 8px 15px">
                <h6>${item.name}</h6>
                <label>地址 : </label><span>&nbsp;${item.location}</span><br>
                <label>座位数 : </label><span>&nbsp;${item.seatNumber}</span><br>
                <label>联系方式 : </label><span>&nbsp;${item.email}</span>
            </div>
            <!--<div class="col-lg-4">-->
                <!--<a class="btn btn-primary">查看场馆活动</a>-->
            <!--</div>-->
            <br>
        `);
    }
}
