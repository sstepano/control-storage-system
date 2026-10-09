/**
 * 
 */

var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

$(document).ready(function () {
    let $taConsole = $("#taConsole");
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (message) {
            // Handle incoming messages
            $("#taConsole").val($("#taConsole").val() + message.body.replace(/["]+/g, '') + "\n");
            $taConsole.scrollTop($taConsole[0].scrollHeight);
        });

    });

    $("#btnSend").on("click", function () {
        sendMessage();
    });

    $("#tfMessage").on("keypress", function (e) {
        if (e.which == 13) {
            sendMessage();
        }
    })

    function sendMessage() {
        let message = document.getElementById("tfMessage").value;
        //stompClient.send("/app/hello", {}, JSON.stringify(message)); // saljemo tcp serveru koji salje SVE requeste na WS
        message = message.replaceAll('\?', '%3F'); // get request ne moze da salje questionmark, moramo da ga konvertujemo u %3F i dalje se ispravno prebaci u ?
        $.get(`/sendRawMessage/${message}`, function (data) {
            // ovo se nikad ne desi kada se povezem na kran.
            //$(".result").html(data);
        })
        .done(function(){
            console.log("<web client> AJAX GET SUCCESS::Message sent");
            $("#tfMessage").val("");
        })
        .fail(function() {
            console.log("<web client> AJAX GET FAIL::Error occured");
        });
    }
    
    $("#btnConvert").on("click", function () {
        let rmCoords = $("#tfRMCoordinates").val(); // 05 023 04 => 012 04 10
        
        let rmX = Number(rmCoords.substring(0,2));
        let rmY = Number(rmCoords.substring(2,5));
        let rmZ = Number(rmCoords.substring(5,7));
        
        let cX = Math.floor(rmY / 2) + rmY % 2;
        let cY = rmZ;
        let cZ = (rmX * 2) + ((rmY % 2)-1);
        
        let craneCoords = 
            String("000" + cX).slice(-3)
          + String("00" + cY).slice(-2)
          + String("00" + cZ).slice(-2);

        $("#tfCraneCoordinates").val(craneCoords);
        
    });
    
});
