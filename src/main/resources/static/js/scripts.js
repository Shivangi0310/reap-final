let recognitionData = {
    "from": null,
    "to": null,
    "message": null,
    "badge": null
}

let revokeData = {
    "recognitionId": null,
    "revokeReason": null
}
var dataRows = "";

function setBadge(badge) {
    recognitionData.badge = badge;
    console.log({recognitionData})
}


function setRecogTo(id) {
    recognitionData.to = id;
    console.log({recognitionData})
}

//auto complete options
let options = {
    url: (phrase) => {
        return "http://localhost:8080/api/findUserByName?pattern=" + phrase
    },
    getValue: (item) => {
        console.log(item);
        return item.fullName;
    },
    template: {
        type: "iconLeft",
        fields: {
            iconSrc: "profilePic"
        }
    },
    list: {
        onChooseEvent: () => {
            var value = $("#search-employee").getSelectedItemData().id;
            console.log(value);
            setRecogTo(value);
        }
    }
}


//auto complete options
let walloptions = {
    url: (phrase) => {
        return "http://localhost:8080/api/findUserByName?pattern=" + phrase
    },
    getValue: (item) => {
        console.log(item);
        return item.fullName;
    },
    template: {
        type: "iconLeft",
        fields: {
            iconSrc: "profilePic"
        }
    },
    list: {
        onChooseEvent: () => {
            var value = $("#search-remployee").getSelectedItemData().id;
            console.log(value);
            axios.get(`/api/fetchReceivedRecognitions/${value}`).then((res) => {
                console.log(res.data)
                if(res.data.exceptionStatus == 'NO_DATA_FOUND')
                    $("#walldata").html("<p>No Data found for this user.</p>");
                else
                printWallOfFame(res.data);
            })
        }
    }
}
//give recognition
$('#doRecognize').click(() => {
    console.log("i am clicked");
    recognitionData.message = $('textarea#comment').val().trim() == "" ? null : $('textarea#comment').val().trim();
    console.log({recognitionData})
    if (recognitionData.badge == null) {
        Swal.fire({
            title: 'Error',
            text: 'Cannot Recognise without badge. Select a badge',
            type: 'error',
            confirmButtonText: 'Ok'
        }).then(() => {
            window.location.reload();
        })
        return;
    }/// end if

    if (recognitionData.to == null) {
        Swal.fire({
            title: 'Error',
            text: 'Cannot Recognise without Recognizee. Select a recognizee',
            type: 'error',
            confirmButtonText: 'Ok'
        })   .then(() => {
            window.location.reload(data);
        })
        return;
    }/// end if
    if (recognitionData.message == null) {
        Swal.fire({
            title: 'Error',
            text: 'Cannot Recognise without Recognition reason. Enter recognition reason',
            type: 'error',
            confirmButtonText: 'Ok'
        }).then(() => {
            window.location.reload();
        })
        return;
    }/// end if

    Swal.showLoading();
    axios.put("/api/recogniseNewers", recognitionData)
        .then(res => {
            //console.log(res);
            if (res.data.responseCode == "API_SUCCESS") {
                Swal.hideLoading();
                Swal.fire({
                    title: 'Success!',
                    text: 'Recognition Successfull;',
                    type: 'success',
                    confirmButtonText: 'Cool'
                }).then(() => {
                    getWallOfFame();
                    window.location.reload();
                })
            }

            if (res.data.exceptionStatus == "CAN_NOT_BE_SAME") {
                Swal.hideLoading();
                Swal.fire({
                    title: 'Error',
                    text: 'Employee Cannot Recognise himself',
                    type: 'error',
                    confirmButtonText: 'Ok'

                }) .then(() => {
                    window.location.reload();
                })
            }
            if (res.data.exceptionStatus == "BADGE_INSUFFICIENT") {
                Swal.hideLoading();
                Swal.fire({
                    title: 'Error',
                    text: 'Insufficient Badge. There are no more badge to give;',
                    type: 'error',
                    confirmButtonText: 'Ok'
                }) .then(() => {
                    window.location.reload();
                })
            }

        })

});

$("#search-employee").easyAutocomplete(options);
$("#search-remployee").easyAutocomplete(walloptions);

$(document).ready(() => {
    getWallOfFame();

})


function doRevoke() {
    var radioValue = $("input[name='revoke']:checked").val();
    if (radioValue == undefined) {
        alert("Select Revoke Reason");
        return;
    }
    if (radioValue) {
        if (radioValue == 'other') {
            var value = $('#other_reason').val().trim();
            if (value == "") {
                alert("Kindly Give Other Reason");
                return;
            }
            revokeData.revokeReason = value
        }
        else
            revokeData.revokeReason = radioValue;
    }
    Swal.showLoading();
    axios.put('/api/revokeRecog', revokeData).then((res) => {
        console.log(res.data);
        if (res.data.responseCode == "API_SUCCESS") {
            Swal.fire({
                title: 'Success!',
                text: 'Recognition revoked Successfully;',
                type: 'success',
                confirmButtonText: 'Cool'
            })
        }
    }).then(() => {
        getWallOfFame();
        $('#myModal').modal('toggle');

    })

}

function revokeRecog(id) {
    revokeData.recognitionId = id;
}

function getWallOfFame() {
    $('#search-remployee').val("")
    dataRows = "";
    $("#walldata").html(dataRows);
    axios.get("/api/recognitions").then(res => {
        if (localStorage.getItem("admin") === "true") {
            res.data.map(v => {
                dataRows += `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-10'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div><div class="col-1"><i onclick="revokeRecog(${v.revokeId})" data-toggle='modal' data-target='#myModal' style="cursor: pointer;" class="fa fa-trash fa-lg"></i></div> </div>`;
            })
        }
        else {
            res.data.map(v => {
                dataRows += `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-11'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div></div>`;
            })
        }

    }).then(() => {
        $("#walldata").append(dataRows);
    })

}

function printWallOfFame(data) {
    dataRows = "";
    $("#walldata").html(dataRows);
    if (localStorage.getItem("admin") === "true") {
        data.map(v => {
            dataRows += `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-10'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div><div class="col-1"><i onclick="revokeRecog(${v.revokeId})" data-toggle='modal' data-target='#myModal' style="cursor: pointer;" class="fa fa-trash fa-lg"></i></div> </div>`;
        })
    }
    else {
        data.map(v => {
            dataRows += `<div class='row' style='border-bottom: solid 1px #b7b7b7;padding: 5px'><div class='col-md-1'><img class='profileimg' src='${v.recognizeePic}'></div><div class='col-md-11'><span><strong>${v.recognizeeName}</strong></span> has received <span><img src=/imgs/${v.badge}.png></img> </span>badge from <span><strong>${v.recognizorName}</strong></span><p style='margin-bottom: 5px'><b>Reason: </b>${v.message}</p><p style='color: #a8a8a8;'>${v.date}</p></div></div>`;
        })
    }
    $("#walldata").append(dataRows);

}
