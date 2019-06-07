$(document).ready(() => {

    $("#allrecognitions").click(function () {
        fetchAll();
    });

    $("#sharedBadges").click(function () {
        fetchShared();
    });

    $("#receivedBadges").click(function () {
        fetchReceived();
    });

    $("#pointsRedeemed").click(function () {
        fetchRedeemedPoints();
    });

})

function fetchAll() {
    var dataRows = "";
    $("#content").html(dataRows);
    axios.get("/api/fetchAllRecognitions").then(res => {
        console.log(res.data)
        res.data.map(v => {
            dataRows+= `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-11'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div></div>`;
        })
    }).then(() => {
        $("#content").append(dataRows);
    })
}

function fetchShared() {
    var dataRows = "";
    $("#content").html(dataRows);

    axios.get("/api/fetchSharedRecognitions").then(res => {
        console.log(res.data)
        res.data.map(v => {
            dataRows+= `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-11'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div></div>`;
        })
    }).then(() => {
        $("#content").append(dataRows);
    })
}

function fetchReceived() {
    var dataRows = "";
    $("#content").html(dataRows);

    axios.get("/api/fetchReceivedRecognitions").then(res => {
        console.log(res.data)
        res.data.map(v => {
            dataRows+= `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-11'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div></div>`;
        })
    }).then(() => {
        $("#content").append(dataRows);
    })
}

function fetchRedeemedPoints() {
    $("#content").html('<p>redeemed points</p>');
}





