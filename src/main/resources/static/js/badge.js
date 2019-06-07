$(document).ready(function () {
    $("#dashboard").hover(function () {
            $("#dashboard img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-dashboard-hover-7f7417f78ecf6ffe41cf442038834929.png'>");
            $("#dashboard img").css("background-color", "#ff3ccb");
            $("#dashboard").css({"background-color": "#ff3ccb", "color": "white"});
        },
        function () {
            $("#dashboard img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-dashboard-5dff3444ebd21480dadfeeb0f0598268.png'>")
            $("#dashboard img").css("background-color", "white");
            $("#dashboard").css({"background-color": "white", "color": "black"});
        });
    $("#dashboard").on("focus",function () {
        $("#dashboard img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-dashboard-hover-7f7417f78ecf6ffe41cf442038834929.png'>");
        $("#dashboard").css({"background-color": "#ff3ccb", "color": "#fff"});
    });
    $("#badges").hover(function () {
            $("#badges img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-badge-hover-6ad40a50088b6e0747f43352ae621d8c.png'>");
        },
        function () {
            $("#badges img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com//assets/ico-badge-f806ef61da2bf4d67425de6f36f427a9.png'>")
        });


})