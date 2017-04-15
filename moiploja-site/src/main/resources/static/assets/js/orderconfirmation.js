var stompClient = null;
function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //alert("CONNECTED!: "+frame);
        //alert("PAYMENT_ID:" + the_payment_id);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/pay/' + the_payment_id, function (result) {
            //alert("RESULT RECIEVED!: "+result.body);
            if (result.body === "COMPLETED") {
                //alert("changing status label")
                $("#status_pedido").text("Compra autorizada e realizada com sucesso!");
                $("#status_pedido").css("color", "green");
                stompClient.unsubscribe();
                stompClient.disconnect();
            }
        });
    });
}

jQuery(document).ready(function () {
    connect();
});