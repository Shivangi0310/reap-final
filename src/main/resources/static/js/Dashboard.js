$(document).ready(function () {

    $("#dashboard").hover(function () {
            $("#dashboard img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-dashboard-hover-7f7417f78ecf6ffe41cf442038834929.png'>");
            $("#dashboard img").css("background-color", "#ff3ccb");
            $("#dashboard").css("background-color", "#ff3ccb", "color", "white");
        },
        function () {
            $("#dashboard img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-dashboard-5dff3444ebd21480dadfeeb0f0598268.png'>")
            $("#dashboard img").css("background-color", "white");
            $("#dashboard").css("background-color", "white", "color", "black");
        });
    $("#dashboard").click(function () {
        $("#dashboard img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-dashboard-hover-7f7417f78ecf6ffe41cf442038834929.png'>");
        $("#dashboard img").css("background-color", "#ff3ccb");
        $("#dashboard").css("background-color", "#ff3ccb", "color", "white");
    });
    $("#badges").hover(function () {
            $("#badges img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com/assets/ico-badge-hover-6ad40a50088b6e0747f43352ae621d8c.png'>");
        },
        function () {
            $("#badges img").replaceWith("<img class='navBarButton' src='http://reap.tothenew.com//assets/ico-badge-f806ef61da2bf4d67425de6f36f427a9.png'>")
        });

    $("#goldBadge").click(function () {
        //$("#content").replaceWith('<button class="btn btn-secondary dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="content"><img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="margin-right: 18%;">Gold</button>');
        $("#content").html('<img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="padding: 2px;margin-right: 10px"><span style="padding: 2px;margin-right: 5px">Gold</span>');
    });
    $("#silverBadge").click(function () {
        //$("#content").replaceWith('<button class="btn btn-secondary dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="content"><img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="margin-right: 18%;">Gold</button>');
        $("#content").html('<img src="http://reap.tothenew.com/assets/ico-badge-grey-6e2741b0311f87111d40aac66392bf22.png" style="padding: 2px;margin-right: 10px"><span style="padding: 2px;margin-right: 5px">Silver</span>');
    });
    $("#bronzeBadge").click(function () {
        //$("#content").replaceWith('<button class="btn btn-secondary dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="content"><img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="margin-right: 18%;">Gold</button>');
        $("#content").html('<img src="http://reap.tothenew.com/assets/ico-badge-brown-6e5dc638c4ee9eef490cfcb0a5cce103.png" style="padding: 2px;margin-right: 10px"><span style="padding: 2px;margin-right: 5px">Bronze</span>');
    });

    $("#goldBadge1").click(function () {
        //$("#content").replaceWith('<button class="btn btn-secondary dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="content"><img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="margin-right: 18%;">Gold</button>');
        $("#content1").html('<img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="padding: 2px;margin-right: 10px"><span style="padding: 2px;margin-right: 5px">Gold</span>');
    });
    $("#silverBadge1").click(function () {
        //$("#content").replaceWith('<button class="btn btn-secondary dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="content"><img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="margin-right: 18%;">Gold</button>');
        $("#content1").html('<img src="http://reap.tothenew.com/assets/ico-badge-grey-6e2741b0311f87111d40aac66392bf22.png" style="padding: 2px;margin-right: 10px"><span style="padding: 2px;margin-right: 5px">Silver</span>');
    });
    $("#bronzeBadge1").click(function () {
        //$("#content").replaceWith('<button class="btn btn-secondary dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="content"><img src="http://reap.tothenew.com/assets/ico-badge-orange-7487656ca410c0f2051788659278f212.png" style="margin-right: 18%;">Gold</button>');
        $("#content1").html('<img src="http://reap.tothenew.com/assets/ico-badge-brown-6e5dc638c4ee9eef490cfcb0a5cce103.png" style="padding: 2px;margin-right: 10px"><span style="padding: 2px;margin-right: 5px">Bronze</span>');
    });

}) ;