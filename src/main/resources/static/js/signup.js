$(document).ready(function () {
    console.log("helloooooo")

    $("#submit").click(function () {
        var password= $("#pwd").val();
        var confirmPassword= $("#confirm-pwd").val();
        if(password!=confirmPassword){
            alert("passwords does not match");
            console.log("not matched");
            return false;
        }
        console.log("not matched");

        return true;

    });

});