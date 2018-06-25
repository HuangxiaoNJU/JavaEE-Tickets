$().ready(function () {
    $('#page_2').hide();
    $('#page_3').hide();
});

$('#next_page_1').click(function () {
    $('#page_1').hide();
    $('#page_2').show();

    $(".ystep").setStep(2);
});


$(".ystep").loadStep({
    size: "large",
    color: "blue",
    steps: [{
        title: "第1步",
        content: "填写基本信息"
    },{
        title: "第2步",
        content: "安排座位"
    },{
        title: "第3步",
        content: "上传海报"
    }]
});