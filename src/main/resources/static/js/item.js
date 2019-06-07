$(document).ready(function () {
    $(".addItemToCartButton").click(function (e) {
        var itemId = $(this).closest(".itemRow").find("input[name='itemId']").val();
        var userId = $(this).closest(".itemRow").find("input[name='userId']").val();
        $.ajax({
            method: 'POST',
            url: "/addToCart/" + itemId,
            success: function (data, status, xhr) {
                // alert("Item added to cart");
                setTimeout(location.reload.bind(location), 1500);
                var x = xhr.getResponseHeader("myResponseHeader");
                if (x === "insufficientPoints") {
                    $("#errorCartAlert").append(data);
                    $("#errorCartAlert").show();
                }
                if (x === "cartAddSuccessful") {
                    $("#successCartAlert").append(data);
                    $("#successCartAlert").show();
                }

            }
        })
    });

    $("#successCartAlert").click(function () {
        window.location.reload();
    });

    $("#errorCartAlert").click(function () {
        window.location.reload();
    });

    $(".removeCartItemButton").click(function (e) {
        var itemId = $(this).closest(".cartRow").find("input[name='cartItemId']").val();
        console.log("Item " + itemId + " will be removed")
        $.ajax({
            method: 'PUT',
            url: "/removeFromCart/" + itemId,
            success: function (data) {
                window.location.reload();
            }
        });
    });
});