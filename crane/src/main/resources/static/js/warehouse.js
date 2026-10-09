$(document).ready(function () {

    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);
    stompClient.debug = null; // disable debug messages from stomp client


    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/statusmessages', function (message) {
            processMessage(message.body)
        });

    });

    function processMessage(message) {
        /* R1001OK     1001 receive when arrive
           S1003OK     1003 - 5004 send
           R5004OK     5004 receive when arrive
           S5004OK     5004 - 00xxyyzz send
           RR001OK     R001 - receive when arrive
           R00xxyyzzOK 00xxyyzz - receive when arrive
        */
        let progressBarCurrentValue = parseInt($('#progressBar').text().slice(0, -1));
        let percentageUpdate = 25;

        let msg = "";
        let alertResponse = "success";

        switch (message) {
            case "R1001OK":
                progressBarCurrentValue = 0;
                msg = "Paleta postavljena na poziciju 1001";
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
                $('#divStatusMessages').empty();
            break;
            case "R1001ER":
                msg = "Plt 1001 ERR";
                alertResponse = "danger";
            break;
            case "R1003ER":
                msg = "Plt 1003 ERR";
                alertResponse = "danger";
            break;            
            case (message.match('^I[0-9]{2}0[0-9]{2}[0-9]{2}OK$') || {}).input:
                msg = "Poslat zahtev za ulaz na poziciju " + message.substring(1, 8);
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
            break;
            case "R5004OK":
                msg = "Paleta stigla na poziciju 5004";
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
            break;
            case "R5004ER":
                msg = "Plt 5004 ERR";
                alertResponse = "danger";
            break;
            case "R7004OK":
                msg = "Paleta stigla na poziciju 7004";
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
            break;
            case "R7004ER":
                msg = "Plt 7004 ERR";
                alertResponse = "danger";
            break;
            case "RR001OK":
                msg = "Paleta stigla na poziciju R001";
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
            break;
            case "RR001ER":
                msg = "Plt R001 ERR";
                alertResponse = "danger";
            break;
            case "RR002OK":
                msg = "Paleta stigla na poziciju R002";
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
            break;
            case "RR002ER":
                msg = "Plt R002 ERR";
                alertResponse = "danger";
            break;
            case (message.match('^R[0-9]{2}0[0-9]{2}[0-9]{2}OK$') || {}).input:
                msg = "Paleta stigla na poziciju " + message.substring(1, 8);
                alertResponse = "success";
                $('#progressBar').removeClass('bg-success bg-danger');
                $("#tfRMCoordinates").prop('disabled', false);
            break;
            case (message.match('^R[0-9]{2}0[0-9]{2}[0-9]{2}ER$') || {}).input:
                msg = "Plt R001|R002 -> Warehouse error";
                alertResponse = "danger";
            break;
            case (message.match('^O[0-9]{2}0[0-9]{2}[0-9]{2}OK$') || {}).input: // zahtev za izlaz
            msg = "Poslat zahtev za izlaz sa pozicije " + message.substring(1, 8);
                alertResponse = "success";
                progressBarCurrentValue = 0;
                $('#divStatusMessages').empty();
                $('#progressBar').removeClass('bg-success bg-danger');
                percentageUpdate = 50;
            break;
            case "R3005OK":
                msg = "Paleta stigla na poziciju 3005";
                alertResponse = "success";
                percentageUpdate = 50;
                $('#progressBar').removeClass('bg-success bg-danger');
                $("#tfRMCoordinates").prop('disabled', false);
            break;
            case "R3005ER":
                msg = "Plt 3005 ERR";
                alertResponse = "danger";
                percentageUpdate = 50;
            break;
            case "R5005OK":
                msg = "Paleta stigla na poziciju 5005";
                alertResponse = "success";
                percentageUpdate = 50;
            break;
            default:
                msg = "Unknown message received: " + message;
                alertResponse = "danger";
            break;
        }

        let currentTime = new Date();
        let hrs = currentTime.getHours().toString().length == 1 ? "0".concat(currentTime.getHours().toString()) : currentTime.getHours().toString();
        let mins = currentTime.getMinutes().toString().length == 1 ? "0".concat(currentTime.getMinutes().toString()) : currentTime.getMinutes().toString();
        let secs = currentTime.getSeconds().toString().length == 1 ? "0".concat(currentTime.getSeconds().toString()) : currentTime.getSeconds().toString();
        let strTime = hrs.concat(":").concat(mins).concat(":").concat(secs);

        $('#divStatusMessages').append('<div class="alert alert-{{rsp}}"'.replace("{{rsp}}", alertResponse) + 'role="alert"><span class="badge bg-secondary me-2">' + strTime + '</span>' + msg + '</div>');
        
        if (message.slice(-2) != "ER" && progressBarCurrentValue < 100 && alertResponse.toLowerCase() != "danger") {
            progressBarCurrentValue += percentageUpdate;
            $('#progressBar').css('width', progressBarCurrentValue + "%");
            $('#progressBar').text(progressBarCurrentValue + "%");
            $('#progressBar').addClass('progress-bar-animated progress-bar-striped');
        } else if ((message.slice(-2) == "ER" && message.slice(0, 1) == "R") || alertResponse.toLowerCase() == "danger") {
            $('#progressBar').removeClass('progress-bar-animated progress-bar-striped');
            $('#progressBar').addClass('bg-danger');
        }

        if (progressBarCurrentValue == 100) {
            $('#progressBar').removeClass('progress-bar-animated progress-bar-striped');
            $('#progressBar').addClass('bg-success');
        }

    }

    function validateInput() {
        const regex = new RegExp("^[0-9]{2}0[0-9]{2}[0-9]{2}$");
        let message = $('#tfRMCoordinates').val();

        if (!regex.test(message)) {
            $('#tfRMCoordinates').addClass('is-invalid');
            $('#btnSend').addClass('disabled btn-danger');
            $('#btnSend').removeClass('btn-primary');
            return false;
        } else {
            $('#tfRMCoordinates').removeClass('is-invalid');
            $('#btnSend').removeClass('disabled btn-danger');
            $('#btnSend').addClass('btn-primary');
            return true;
        }
    }

    $("#btnSend").on("click", function () {
        if (validateInput()) {
            sendMessage();
        }
    });

    $("#tfRMCoordinates").on("keyup", function (e) {
        if (e.which == 13) {
            if (validateInput()) {
                sendMessage();
            }
        } else {
            validateInput();        
        }
    })

    function sendMessage() {
        let inOut = ''; // I or O
        if ($('#rbEntryToWarehouse').is(':checked')) {
            inOut = 'I';
        } else {
            inOut = 'O';
        }
        let message = inOut + $('#tfRMCoordinates').val() + 'OK';
        $('#btnSend').addClass('disabled');
        $('#tfRMCoordinates').val("");
        $('#tfRMCoordinates').blur();
        $("#tfRMCoordinates").prop('disabled', true);
        /**
        $('#divStatusMessages').empty();
        $('#progressBar').addClass('progress-bar-animated');
        */
       console.log(message);

        $.get(`/sendStatusMessage/${message}`, function (data) {
        })
        .done(function(){
            console.log("AJAX get success");
        })
        .fail(function(){
            console.log("AJAX get fail");
        });

        $.get(`/sendMessage/${message}`, function (data) {
        })
        .done(function(){
            console.log("AJAX get success");
        })
        .fail(function(){
            console.log("AJAX get fail");
        });
    }

}); // document.ready END