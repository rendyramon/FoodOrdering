$(document).ready(function () {
    jQuery('a[href^="#"]').lightweightScrollTo({
        offset: 10
    });
    $("button.btn-primary").click(function() {
       var id = this.id;
       processAjax(id);
    });
});

function processAjax(id) {
    $.ajax({
        type: 'POST',
        url: 'WS.php',
        data: {
            action: "finish",
            input: id
        },
        success: successProcess,
        error: failProcess
    });
}

function successProcess(response) {
    var res = response["result"];
    if (res) {
        alert("Successfully!");
    } else {
        alert("Already done!");
    }
    location.reload();
}

function failProcess() {
    alert("Cannot access data at this time, please try again!");
}